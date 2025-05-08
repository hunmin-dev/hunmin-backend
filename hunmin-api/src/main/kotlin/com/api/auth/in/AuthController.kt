package com.api.auth.`in`

import com.api.auth.`in`.request.SignInRequest
import com.api.auth.`in`.request.SignUpRequest
import com.api.auth.`in`.response.AuthResponse
import com.domain.auth.port.`in`.AuthUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/auth")
@RestController
class AuthController(
    private val authUseCase: AuthUseCase,
) : AuthApi {

    @PostMapping("/sign-up")
    override fun signUp(@RequestBody request: SignUpRequest) =
        authUseCase.signUp(request.toCommand())
            .let {
                ResponseEntity.status(HttpStatus.CREATED)
                    .body(AuthResponse(it))
            }

    @PostMapping("/sign-in")
    override fun signIn(@RequestBody request: SignInRequest) =
        authUseCase.signIn(request.toCommand())
            .let { ResponseEntity.ok(AuthResponse(it)) }
}
