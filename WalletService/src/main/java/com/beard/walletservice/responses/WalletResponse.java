package com.beard.walletservice.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletResponse {
    private Long userId;
    private Long balancePoints;
    private Long lifetimeEarned;
    private Long lifetimeRedeemed;
    private String tier;
    private LocalDateTime updatedAt;
}

