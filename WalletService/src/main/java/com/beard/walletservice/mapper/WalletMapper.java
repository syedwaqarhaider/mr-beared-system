package com.beard.walletservice.mapper;

import com.beard.walletservice.model.Wallet;
import com.beard.walletservice.model.WalletTransaction;
import com.beard.walletservice.responses.WalletResponse;
import com.beard.walletservice.responses.WalletTransactionResponse;
import org.springframework.stereotype.Component;

@Component
public class WalletMapper {
    public WalletResponse toResponse(Wallet w){
        return WalletResponse.builder()
                .userId(w.getUserId())
                .balancePoints(w.getBalancePoints())
                .lifetimeEarned(w.getLifetimeEarned())
                .lifetimeRedeemed(w.getLifetimeRedeemed())
                .tier(w.getTier())
                .updatedAt(w.getUpdatedAt())
                .build();
    }

    public WalletTransactionResponse toTxnResponse(WalletTransaction tx){
        return WalletTransactionResponse.builder()
                .id(tx.getId())
                .type(tx.getType())
                .points(tx.getPoints())
                .description(tx.getDescription())
                .referenceId(tx.getReferenceId())
                .createdAt(tx.getCreatedAt())
                .build();
    }
}
