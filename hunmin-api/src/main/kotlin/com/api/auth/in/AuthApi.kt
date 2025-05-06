package com.api.auth.`in`

import com.api.auth.`in`.request.SignInRequest
import com.api.auth.`in`.request.SignUpRequest
import com.api.auth.`in`.response.AuthResponse
import com.common.global.exceptions.base.ExceptionResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "Auth API")
interface AuthApi {
    @Operation(summary = "회원가입")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "회원가입 성공",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = AuthResponse::class)
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "값 누락으로 인한 회원가입 불가",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ExceptionResponse::class)
                )]
            ),
            ApiResponse(
                responseCode = "409",
                description = "이미 존재하는 username 으로 인한 회원가입 불가",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ExceptionResponse::class)
                )]
            )
        ]
    )
    @PostMapping("/sign-up")
    fun signUp(
        @RequestBody request: SignUpRequest,
    ): ResponseEntity<AuthResponse>

    @Operation(summary = "로그인")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "로그인 성공",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = AuthResponse::class)
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "값 누락으로 인한 로그인 불가",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ExceptionResponse::class)
                )]
            ),
            ApiResponse(
                responseCode = "401",
                description = "비밀번호 불일치로 인한 로그인 불가",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ExceptionResponse::class)
                )]
            ),
            ApiResponse(
                responseCode = "404",
                description = "인증 정보 조회 실패",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ExceptionResponse::class)
                )]
            )
        ]
    )
    @PostMapping("/sign-in")
    fun signIn(
        @RequestBody request: SignInRequest,
    ): ResponseEntity<AuthResponse>
}
