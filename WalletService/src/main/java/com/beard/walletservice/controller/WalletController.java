package com.beard.walletservice.controller;

import com.beard.walletservice.requests.EarnPointsRequest;
import com.beard.walletservice.requests.RedeemPointsRequest;
import com.beard.walletservice.requests.ReferralRequest;
import com.beard.walletservice.responses.ApiResponse;
import com.beard.walletservice.responses.WalletResponse;
import com.beard.walletservice.responses.WalletTransactionResponse;
import com.beard.walletservice.service.RewardAutomationService;
import com.beard.walletservice.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;
    private final RewardAutomationService rewardAutomationService;

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<WalletResponse>> getWallet(@PathVariable Long userId){
        WalletResponse w = walletService.getOrCreateWallet(userId);
        return ResponseEntity.ok(ApiResponse.ok("Wallet fetched", w));
    }

    @PostMapping("/earn")
    public ResponseEntity<ApiResponse<WalletResponse>> earn(@RequestBody @Valid EarnPointsRequest req){
        WalletResponse w = walletService.earn(req);
        return ResponseEntity.ok(ApiResponse.ok("Points earned", w));
    }

    @PostMapping("/redeem")
    public ResponseEntity<ApiResponse<WalletResponse>> redeem(@RequestBody @Valid RedeemPointsRequest req){
        WalletResponse w = walletService.redeem(req);
        return ResponseEntity.ok(ApiResponse.ok("Points redeemed", w));
    }

    @GetMapping("/{userId}/transactions")
    public ResponseEntity<ApiResponse<Page<WalletTransactionResponse>>> history(
            @PathVariable Long userId,
            @RequestParam(defaultValue="0") int page,
            @RequestParam(defaultValue="20") int size){
        Page<WalletTransactionResponse> tx = walletService.history(userId, page, size);
        return ResponseEntity.ok(ApiResponse.ok("Transactions fetched", tx));
    }

    // automation hooks
    @PostMapping("/automation/signup-bonus/{userId}")
    public ResponseEntity<ApiResponse<WalletResponse>> signupBonus(@PathVariable Long userId,
                                                                   @RequestParam(defaultValue="100") long points){
        WalletResponse w = rewardAutomationService.grantSignupBonus(userId, points);
        return ResponseEntity.ok(ApiResponse.ok("Signup bonus granted", w));
    }

    @PostMapping("/automation/order-completed/{orderId}")
    public ResponseEntity<ApiResponse<WalletResponse>> orderCompleted(@PathVariable Long orderId,
                                                                      @RequestParam(defaultValue="100") long perCurrency,
                                                                      @RequestParam(defaultValue="true") boolean useAmount){
        WalletResponse w = rewardAutomationService.grantOrderCompletion(orderId, perCurrency, useAmount);
        return ResponseEntity.ok(ApiResponse.ok("Order completion reward granted", w));
    }

    @PostMapping("/automation/referral")
    public ResponseEntity<ApiResponse<String>> referral(@RequestBody @Valid ReferralRequest req,
                                                        @RequestParam(defaultValue="200") long points){
        rewardAutomationService.grantReferral(req.getReferrerUserId(), req.getReferredUserId(), points);
        return ResponseEntity.ok(ApiResponse.ok("Referral granted", "OK"));
    }
}
