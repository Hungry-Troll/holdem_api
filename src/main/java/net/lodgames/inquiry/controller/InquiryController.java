package net.lodgames.inquiry.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.security.UserPrincipal;
import net.lodgames.inquiry.param.InquiresGetByUserIdParam;
import net.lodgames.inquiry.param.InquiresGetParam;
import net.lodgames.inquiry.param.InquiryAddParam;
import net.lodgames.inquiry.param.InquiryModParam;
import net.lodgames.inquiry.service.InquiryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class InquiryController {

    private final InquiryService inquiryService;

    // 문의 생성 (유저)
    @PostMapping("/inquires")
    public ResponseEntity<?> addInquiry(@RequestBody InquiryAddParam inquiryAddParam,
                                        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        inquiryAddParam.setUserId(userPrincipal.getUserId());
        inquiryService.addInquiry(inquiryAddParam);
        return ResponseEntity.ok().build();
    }

    // 문의 확인 (유저)
    @GetMapping("/inquires")
    public ResponseEntity<?> getInquiresByUserId(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                 @RequestBody InquiresGetByUserIdParam inquiresGetByUserIdParam) {
        return ResponseEntity.ok(inquiryService.getInquiresByUserId(userPrincipal.getUserId(), inquiresGetByUserIdParam));
    }

    // 이하 관리자 코드는 테스트용

    // 문의 확인 (관리자)
    @GetMapping("/inquires/{id}")
    public ResponseEntity<?> getInquiry(@PathVariable(name = "id") Long inquiryId,
                                        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(inquiryService.getInquiry(inquiryId));
    }

    // 전체 문의 리스트 (관리자)
    @GetMapping("/inquires/all")
    public ResponseEntity<?> getInquires(@RequestBody InquiresGetParam inquiresGetParam,
                                         @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(inquiryService.getInquires(inquiresGetParam));
    }

    // 문의 해결 (관리자)
    @PutMapping("/inquires/{id}/resolve")
    public ResponseEntity<?> resolveInquiry(@PathVariable(name = "id") Long inquiryId,
                                            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        inquiryService.resolveInquiry(inquiryId);
        return ResponseEntity.ok().build();
    }

    // 문의 수정 (관리자)
    @PutMapping("/inquires/{id}/mod")
    public ResponseEntity<?> modInquiry(@PathVariable(name = "id") Long inquiryId,
                                        @RequestBody InquiryModParam inquiryModParam,
                                        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        inquiryService.modInquiry(inquiryId, inquiryModParam);
        return ResponseEntity.ok().build();
    }

    // 문의 삭제 (테스트용)
    @DeleteMapping("/inquires/{id}")
    public ResponseEntity<?> delInquiry(@PathVariable(name = "id") Long inquiryId,
                                        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        inquiryService.delInquiry(inquiryId);
        return ResponseEntity.ok().build();
    }
}
