package com.beard.walletservice.service;

import com.beard.walletservice.clients.QueueClient;
import com.beard.walletservice.model.ReferralReward;
import com.beard.walletservice.repository.ReferralRewardRepository;
import com.beard.walletservice.requests.EarnPointsRequest;
import com.beard.walletservice.responses.ApiResponse;
import com.beard.walletservice.responses.QueueOrderLite;
import com.beard.walletservice.responses.WalletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional
public class RewardAutomationService {

    private final WalletService walletService;
    private final ReferralRewardRepository referralRewardRepository;
    private final QueueClient queueClient;

    // Call from User service after signup
    public WalletResponse grantSignupBonus(Long userId, long bonusPoints){
        return walletService.earn(EarnPointsRequest.builder()
                .userId(userId).points(bonusPoints)
                .description("Signup bonus")
                .referenceId("SIGNUP")
                .idempotencyKey("SIGNUP:"+userId)
                .build());
    }

    // Call from Queue service after order becomes COMPLETED
    public WalletResponse grantOrderCompletion(Long orderId, long pointsPerCurrency, boolean useAmount){
        ApiResponse<QueueOrderLite> resp = queueClient.getOrderLite(orderId);
        if (resp == null || !resp.isSuccess() || resp.getData()==null){
            throw new IllegalArgumentException("Order not found for rewards");
        }
        QueueOrderLite order = resp.getData();
        // Example: earn 1 point per 100 currency, or flat 50 points
        long points = useAmount
                ? order.getTotalPrice().divide(new BigDecimal(pointsPerCurrency)).longValue()
                : 50L;

        return walletService.earn(EarnPointsRequest.builder()
                .userId(order.getUserId())
                .points(Math.max(points, 0))
                .description("Order completion reward (orderId="+orderId+")")
                .referenceId(String.valueOf(orderId))
                .idempotencyKey("ORDER_COMPLETED:"+orderId)
                .build());
    }

    public void grantReferral(Long referrerUserId, Long referredUserId, long points){
        if (referralRewardRepository.existsByReferrerUserIdAndReferredUserId(referrerUserId, referredUserId))
            return; // already granted
        walletService.earn(EarnPointsRequest.builder()
                .userId(referrerUserId).points(points)
                .description("Referral bonus for user "+referredUserId)
                .referenceId("REF:"+referredUserId)
                .idempotencyKey("REF:"+referrerUserId+":"+referredUserId)
                .build());
        referralRewardRepository.save(ReferralReward.builder()
                .referrerUserId(referrerUserId)
                .referredUserId(referredUserId)
                .granted(true).build());
    }
}
