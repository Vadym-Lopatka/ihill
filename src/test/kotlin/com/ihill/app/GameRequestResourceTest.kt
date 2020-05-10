package com.ihill.app

import com.ihill.app.gameRequest.GameRequest
import com.ihill.app.gameRequest.GameRequestResource.Companion.GAME_REQUEST_URL
import com.ihill.app.gameRequest.GameRequestService
import com.ihill.app.gameRequest.GameRequestStatus
import com.ihill.app.gameRequest.OpenGameRequest
import com.ihill.app.gameRequest.UuidSettings
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GameRequestResourceTest {

    @MockkBean
    lateinit var service: GameRequestService

    @Autowired
    private lateinit var testRestTemplate: TestRestTemplate

    @BeforeEach
    fun setup() {
        every { service.openGameRequest(any()) } returns buildGameRequest()
    }

    @Test
    fun `should consume a valid request and return GameRequest`() {
        // given
        val openGameRequest = OpenGameRequest(getRandomString(UuidSettings.MIN_LENGTH))

        // when
        val httpRequest = HttpEntity(openGameRequest)
        val response = testRestTemplate.postForEntity(GAME_REQUEST_URL, httpRequest, GameRequest::class.java)

        //then
        assertTrue(response.statusCode.is2xxSuccessful)
    }

    private fun buildGameRequest() = GameRequest(initiator = VALID_INITIATOR_UUID, status = GameRequestStatus.OPEN)

    fun getRandomString(length: Int): String {
        val allowedChars = ('A'..'Z') + ('a'..'z')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

    companion object {
        private const val VALID_INITIATOR_UUID = "test-initiator-uuid"
    }

}