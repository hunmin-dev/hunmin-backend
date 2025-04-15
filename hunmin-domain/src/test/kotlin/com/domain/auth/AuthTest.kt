package com.domain.auth

import com.domain.auth.service.StubAuthPasswordEncryptorAdaptor
import io.kotest.assertions.assertSoftly
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class AuthTest {

    private val authPasswordEncryptorPort = StubAuthPasswordEncryptorAdaptor()

    @Test
    fun `패스워드를 암호화하며 저장을 한다`() {
        // given
        val rawPassword = "mySecret123"
        val username = "testUser"

        // when
        val auth = Auth.signUpWithEncryption(username, rawPassword, authPasswordEncryptorPort)

        // then
        assertSoftly {
            assertSoftly { assertEquals(username, auth.username) }
            assertSoftly { assertTrue(auth.password.startsWith("encrypted:")) }
            assertSoftly { assertTrue(auth.matches(rawPassword, authPasswordEncryptorPort)) }
        }
    }
}
