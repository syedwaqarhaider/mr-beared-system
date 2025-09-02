package com.beard.walletservice.service;

import com.beard.walletservice.enums.WalletTxnType;
import com.beard.walletservice.mapper.WalletMapper;
import com.beard.walletservice.model.PointsExpiration;
import com.beard.walletservice.model.Wallet;
import com.beard.walletservice.model.WalletTransaction;
import com.beard.walletservice.repository.PointsExpirationRepository;
import com.beard.walletservice.repository.WalletRepository;
import com.beard.walletservice.repository.WalletTransactionRepository;
import com.beard.walletservice.requests.EarnPointsRequest;
import com.beard.walletservice.requests.RedeemPointsRequest;
import com.beard.walletservice.responses.WalletResponse;
import com.beard.walletservice.responses.WalletTransactionResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WalletService {

    private final WalletRepository walletRepository;
    private final WalletTransactionRepository walletTransactionRepository;
    private final PointsExpirationRepository pointsExpirationRepository;
    private final WalletMapper mapper;

    public WalletResponse getOrCreateWallet(Long userId){
        Wallet w = walletRepository.findById(userId)
                .orElseGet(() -> walletRepository.save(Wallet.builder()
                        .userId(userId)
                        .balancePoints(0L)
                        .lifetimeEarned(0L)
                        .lifetimeRedeemed(0L)
                        .tier("BASIC")
                        .build()));
        return mapper.toResponse(w);
    }

    public WalletResponse earn(EarnPointsRequest req){
        // idempotency
        walletTransactionRepository.findByUserIdAndIdempotencyKey(req.getUserId(), req.getIdempotencyKey())
                .ifPresent(tx -> { throw new IllegalArgumentException("Duplicate earn operation"); });

        Wallet w = walletRepository.findById(req.getUserId())
                .orElseGet(() -> walletRepository.save(Wallet.builder()
                        .userId(req.getUserId())
                        .balancePoints(0L).lifetimeEarned(0L).lifetimeRedeemed(0L).tier("BASIC").build()));

        long newBalance = Math.addExact(w.getBalancePoints(), req.getPoints());
        w.setBalancePoints(newBalance);
        w.setLifetimeEarned(Math.addExact(w.getLifetimeEarned(), req.getPoints()));
        walletRepository.save(w);

        WalletTransaction tx = walletTransactionRepository.save(WalletTransaction.builder()
                .userId(req.getUserId())
                .type(WalletTxnType.EARN)
                .points(req.getPoints())
                .description(req.getDescription())
                .referenceId(req.getReferenceId())
                .idempotencyKey(req.getIdempotencyKey())
                .build());

        // optional: add expiration bucket (e.g., 180 days)
        // pointsExpirationRepository.save(PointsExpiration.builder()
        //        .userId(req.getUserId()).points(req.getPoints()).expiresAt(LocalDateTime.now().plusDays(180)).consumed(0L).build());

        return mapper.toResponse(w);
    }

    public WalletResponse redeem(RedeemPointsRequest req){
        walletTransactionRepository.findByUserIdAndIdempotencyKey(req.getUserId(), req.getIdempotencyKey())
                .ifPresent(tx -> { throw new IllegalArgumentException("Duplicate redeem operation"); });

        Wallet w = walletRepository.findById(req.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Wallet not found"));

        if (w.getBalancePoints() < req.getPoints()){
            throw new IllegalArgumentException("Insufficient points");
        }

        w.setBalancePoints(w.getBalancePoints() - req.getPoints());
        w.setLifetimeRedeemed(Math.addExact(w.getLifetimeRedeemed(), req.getPoints()));
        walletRepository.save(w);

        walletTransactionRepository.save(WalletTransaction.builder()
                .userId(req.getUserId())
                .type(WalletTxnType.REDEEM)
                .points(req.getPoints())
                .description(req.getDescription())
                .idempotencyKey(req.getIdempotencyKey())
                .build());

        // Optional: consume from expiration buckets FIFO
        // consumePointsFIFO(req.getUserId(), req.getPoints());

        return mapper.toResponse(w);
    }

    public Page<WalletTransactionResponse> history(Long userId, int page, int size){
        Page<WalletTransaction> p = walletTransactionRepository
                .findByUserIdOrderByCreatedAtDesc(userId, PageRequest.of(page, size));
        return p.map(mapper::toTxnResponse);
    }

    // OPTIONAL helper for expirations
    private void consumePointsFIFO(Long userId, long pointsToUse){
        List<PointsExpiration> buckets = pointsExpirationRepository
                .findByUserIdAndExpiresAtAfterOrderByExpiresAtAsc(userId, LocalDateTime.now());
        long remaining = pointsToUse;
        for (PointsExpiration b : buckets){
            if (remaining <= 0) break;
            long available = b.getPoints() - b.getConsumed();
            long consume = Math.min(available, remaining);
            b.setConsumed(b.getConsumed() + consume);
            pointsExpirationRepository.save(b);
            remaining -= consume;
        }
        // if remaining > 0, it means non-expiring points covered the rest (if you track them)
    }
}

