package com.numo.portfolio.user.adapter.in.web;

import com.numo.portfolio.user.adapter.in.web.dto.AddUserDto;
import com.numo.portfolio.user.domain.SocialType;
import com.numo.portfolio.user.application.command.AddUserCommand;
import com.numo.portfolio.user.application.port.in.AddUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AddUserUseCase addUserUseCase;

    @PostMapping("/{socialType}")
    public ResponseEntity<Long> signIn(@PathVariable("socialType") SocialType type,
                                       @RequestBody AddUserDto addUserDto) {
        AddUserCommand addUserCommand = new AddUserCommand(
                addUserDto.socialId(),
                type,
                addUserDto.nickname()
        );
        Long id = addUserUseCase.signIn(addUserCommand);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }
}
