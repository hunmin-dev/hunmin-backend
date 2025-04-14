package com.api.auth.`in`

import com.api.auth.`in`.request.SignInRequest
import com.api.auth.`in`.response.AuthResponse
import com.api.auth.`in`.request.SignUpRequest
import com.application.auth.port.`in`.AuthUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/auth")
@RestController
class AuthController(
    private val authUseCase: AuthUseCase,
) : AuthApi {

    @PostMapping("/sign-up")
    override fun signUp(request: SignUpRequest): ResponseEntity<AuthResponse> {
        val token = authUseCase.signUp(request.toCommand())
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(AuthResponse(token))
    }

    @GetMapping("/sign-in")
    override fun signIn(request: SignInRequest): ResponseEntity<AuthResponse> {
        val token = authUseCase.signIn(request.toCommand())
        return ResponseEntity.ok(AuthResponse(token))
    }
}
