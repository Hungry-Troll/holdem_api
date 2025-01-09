package net.lodgames.userCharacter.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.security.UserPrincipal;
import net.lodgames.userCharacter.param.UserCharacterAddParam;
import net.lodgames.userCharacter.param.UserCharacterModParam;
import net.lodgames.userCharacter.service.UserCharacterService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api/v1")
public class UserCharacterController {

        private final UserCharacterService userCharacterService;

        // 유저 캐릭터 등록 (유저가 뽑기나 재화로 구매 시)
        @PostMapping("/userCharacters")
        public ResponseEntity<?> addUserCharacter(@RequestBody UserCharacterAddParam userCharacterAddParam,
                                                  @AuthenticationPrincipal UserPrincipal userPrincipal) {
            userCharacterAddParam.setUserId(userPrincipal.getUserId());
            userCharacterService.addUserCharacter(userCharacterAddParam);
            return ResponseEntity.ok().build();
        }

        // 유저 캐릭터 단일 조회
        @GetMapping("/userCharacters/{id}")
        public ResponseEntity<?> getUserCharacter(@PathVariable(name = "id") Long id,
                                                  @AuthenticationPrincipal UserPrincipal userPrincipal) {
            return ResponseEntity.ok(userCharacterService.getUserCharacter(id, userPrincipal.getUserId()));
        }

        // 유저 캐릭터 전체 조회
        @GetMapping("/userCharacters")
        public ResponseEntity<?> getUserCharacters(@AuthenticationPrincipal UserPrincipal userPrincipal) {
            return ResponseEntity.ok(userCharacterService.getUserCharacters(userPrincipal.getUserId()));
        }

        // 유저 캐릭터 삭제 (기록 X)
        @DeleteMapping("/userCharacters/{id}")
        public ResponseEntity<?> deleteUserCharacter(@PathVariable(name = "id") Long id,
                                                     @AuthenticationPrincipal UserPrincipal userPrincipal) {
            userCharacterService.deleteUserCharacter(id, userPrincipal.getUserId());
            return ResponseEntity.ok().build();
        }

        // 유저 캐릭터 수정
        @PutMapping("/userCharacters/{id}")
        public ResponseEntity<?> modUserCharacter(@PathVariable(name = "id") Long id,
                                                  @RequestBody UserCharacterModParam userCharacterModParam,
                                                  @AuthenticationPrincipal UserPrincipal userPrincipal) {
            userCharacterModParam.setId(id);
            userCharacterModParam.setUserId(userPrincipal.getUserId());
            return ResponseEntity.ok(userCharacterService.modUserCharacter(userCharacterModParam));
        }

        // 유저 캐릭터 레벨업
        @PutMapping("/userCharacters/{id}/levelUp")
        public ResponseEntity<?> levelUpUserCharacter(@PathVariable(name = "id") Long id,
                                                      @AuthenticationPrincipal UserPrincipal userPrincipal) {
            userCharacterService.levelUpUserCharacter(id, userPrincipal.getUserId());
            return ResponseEntity.ok().build();
        }

        // 유저 캐릭터 등급업
        @PutMapping("/userCharacters/{id}/gradeUp")
        public ResponseEntity<?> gradeUpUserCharacter(@PathVariable(name = "id") Long id,
                                                      @AuthenticationPrincipal UserPrincipal userPrincipal) {
            userCharacterService.gradeUpUserCharacter(id, userPrincipal.getUserId());
            return ResponseEntity.ok().build();
        }
}
