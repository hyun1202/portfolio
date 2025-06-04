package com.numo.portfolio.user.adapter.in.web;

import com.numo.portfolio.security.service.UserDetailsImpl;
import com.numo.portfolio.user.adapter.in.web.dto.UpdateDomainDto;
import com.numo.portfolio.user.adapter.in.web.dto.UserResponse;
import com.numo.portfolio.user.application.command.GetUserCommand;
import com.numo.portfolio.user.application.command.UpdateDomainCommand;
import com.numo.portfolio.user.application.port.in.GetUserUseCase;
import com.numo.portfolio.user.application.port.in.UpdateDomainUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final GetUserUseCase getUserUseCase;
    private final UpdateDomainUseCase updateDomainUseCase;

    @GetMapping
    public ResponseEntity<UserResponse> getUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        GetUserCommand getUserCommand = new GetUserCommand(
                userDetails.getUser()
        );

        UserResponse res = getUserUseCase.getUser(getUserCommand);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PatchMapping("/domain")
    public ResponseEntity<Long> updateDomain(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                             @RequestBody UpdateDomainDto req) {
        UpdateDomainCommand updateDomainCommand = new UpdateDomainCommand(
                userDetails.getUser(),
                req.domain()
        );

        Long id = updateDomainUseCase.updateUserDomain(updateDomainCommand);
        return ResponseEntity.ok(id);
    }
}
