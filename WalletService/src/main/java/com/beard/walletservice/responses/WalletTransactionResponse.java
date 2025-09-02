package com.beard.walletservice.responses;

import com.beard.walletservice.enums.WalletTxnType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletTransactionResponse {
    private Long id;
    private WalletTxnType type;
    private Long points;
    private String description;
    private String referenceId;
    private LocalDateTime createdAt;
}
