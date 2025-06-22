package com.bootstrap.subscribe.`in`

import com.bootstrap.subscribe.`in`.request.UpdateSubscribeRequest
import com.bootstrap.subscribe.`in`.response.SubscribeResponse
import com.common.global.auth.annotation.AuthMember
import com.common.global.auth.role.Role
import com.domain.subscribe.port.`in`.SubscribeUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/subscribes")
@RestController
class SubscribeController(
    private val subscribeUseCase: SubscribeUseCase,
) : SubscribeApi {

    @PatchMapping("/{subscribeId}")
    override fun update(
        @AuthMember(requiredRole = Role.USER) userId: Long,
        @PathVariable subscribeId: Long,
        @RequestBody request: UpdateSubscribeRequest
    ): ResponseEntity<SubscribeResponse> =
        subscribeUseCase.update(userId = userId, subscribeId = subscribeId, command = request.toCommand())
            .let {
                ResponseEntity.status(HttpStatus.OK)
                    .body(SubscribeResponse.from(it))
            }

    @GetMapping
    override fun findByUserId(
        @AuthMember(requiredRole = Role.USER) userId: Long
    ): ResponseEntity<SubscribeResponse?> =
        subscribeUseCase.findByUserId(userId = userId)
            .let {
                ResponseEntity.status(HttpStatus.OK)
                    .body(SubscribeResponse.from(it))
            }
}
