package com.persistence.global

import com.common.global.auth.support.AuthenticationContext
import java.util.Optional
import org.springframework.data.domain.AuditorAware
import org.springframework.stereotype.Component

@Component
class MemberAuditorAware(
    private val authenticationContext: AuthenticationContext
) : AuditorAware<Long> {
    override fun getCurrentAuditor(): Optional<Long> {
        return try {
            Optional.of(authenticationContext.getPrincipal())
        } catch (exception: Exception) {
            Optional.empty()
        }
    }
}
