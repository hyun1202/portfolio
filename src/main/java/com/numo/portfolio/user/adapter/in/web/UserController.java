package com.numo.portfolio.user.adapter.in.web;

import com.numo.portfolio.user.adapter.in.web.dto.AddUserDto;
import com.numo.portfolio.user.adapter.in.web.dto.UserResponse;
import com.numo.portfolio.user.application.command.AddUserCommand;
import com.numo.portfolio.user.application.command.GetUserCommand;
import com.numo.portfolio.user.application.port.in.AddUserUseCase;
import com.numo.portfolio.user.application.port.in.GetUserUseCase;
import com.numo.portfolio.user.domain.SocialType;
import com.numo.portfolio.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final GetUserUseCase getUserUseCase;

    @GetMapping
    public ResponseEntity<UserResponse> getUser(@AuthenticationPrincipal UserDetails userDetails) {
        GetUserCommand getUserCommand = new GetUserCommand(
                userDetails.getUsername()
        );
        UserResponse res = getUserUseCase.getUser(getUserCommand);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }
}
