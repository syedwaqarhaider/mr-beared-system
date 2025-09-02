package com.beard.walletservice.requests;

import jakarta.validation.constraints.Min;
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
public class RedeemPointsRequest {
    @NotNull
    private Long userId;
    @NotNull @Min(1) private Long points;
    private String description;
    @NotBlank
    private String idempotencyKey;
}

