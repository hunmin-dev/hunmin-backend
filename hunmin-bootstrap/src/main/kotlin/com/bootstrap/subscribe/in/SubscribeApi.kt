package com.bootstrap.subscribe.`in`

import com.bootstrap.subscribe.`in`.request.UpdateSubscribeRequest
import com.bootstrap.subscribe.`in`.response.SubscribeResponse
import com.common.global.auth.annotation.AuthMember
import com.common.global.auth.role.Role
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/subscribes")
@Tag(name = "Subscribe API")
interface SubscribeApi {

    @Operation(summary = "알림 구독 정보 변경")
    @PatchMapping("/{subscribeId}")
    fun update(
        @AuthMember(requiredRole = Role.USER) memberId: Long,
        @PathVariable subscribeId: Long,
        @RequestBody request: UpdateSubscribeRequest
    ): ResponseEntity<SubscribeResponse>

    @Operation(summary = "알림 구독 정보 조회")
    @GetMapping
    fun findByMemberId(
        @AuthMember(requiredRole = Role.USER) memberId: Long
    ): ResponseEntity<SubscribeResponse?>
}
