package com.ihill.app.gameRequest

import com.ihill.app.TestHelper.getRandomString
import com.ihill.app.gameRequest.GameRequestDataHelper.buildGameRequest
import com.ihill.app.gameRequest.domain.GameRequest
import com.ihill.app.gameRequest.domain.GameRequestStatus
import com.ihill.app.gameRequest.service.GameRequestService
import com.ihill.app.gameRequest.web.GameRequestResource.Companion.GAME_REQUEST_URL
import com.ihill.app.gameRequest.web.request.AcceptGameRequest
import com.ihill.app.gameRequest.web.request.OpenGameRequest
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GameRequestResourceTest {

    @MockkBean
    lateinit var service: GameRequestService

    @Autowired
    private lateinit var testRestTemplate: TestRestTemplate

    @BeforeEach
    fun setup() {
        val buildGameRequest = buildGameRequest(getRandomString(UuidSettings.MIN_LENGTH), null, GameRequestStatus.OPEN)
        every { service.openGameRequest(any()) } returns buildGameRequest
        every { service.acceptGameRequest(any(),any()) } returns buildGameRequest.apply {
            acceptor= ACCEPTOR_UUID
            status=GameRequestStatus.ACCEPTED
        }
    }

    @Test
    fun `should open a new GameRequest`() {
        // given
        val openGameRequest = OpenGameRequest(getRandomString(UuidSettings.MIN_LENGTH))

        // when
        val httpRequest = HttpEntity(openGameRequest)
        val response = testRestTemplate.exchange(GAME_REQUEST_URL, HttpMethod.POST, httpRequest, GameRequest::class.java)

        //then
        assertTrue(response.statusCode.is2xxSuccessful)
    }

    @Test
    fun `should accept opened GameRequest`() {
        // given
        val acceptGameRequest = AcceptGameRequest(
            acceptorUUID = ACCEPTOR_UUID,
            gameRequestUUID = OPENED_GAME_REQUEST_UUID
        )

        // when
        val httpRequest = HttpEntity(acceptGameRequest)
        val response = testRestTemplate.exchange(GAME_REQUEST_URL, HttpMethod.PUT, httpRequest, GameRequest::class.java)

        //then
        assertTrue(response.statusCode.is2xxSuccessful)
    }

}