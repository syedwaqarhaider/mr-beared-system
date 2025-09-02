package com.beard.walletservice.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReferralRequest {
    @NotNull
    private Long referrerUserId;
    @NotNull private Long referredUserId;
    @NotBlank
    private String idempotencyKey;
}

