package com.ihill.app.gameRequest

import com.ihill.app.gameRequest.GameRequestDataHelper.buildGameRequest
import com.ihill.app.TestHelper.getRandomString
import com.ihill.app.gameRequest.domain.GameRequest
import com.ihill.app.gameRequest.service.GameRequestService
import com.ihill.app.gameRequest.web.GameRequestResource.Companion.GAME_REQUEST_URL
import com.ihill.app.gameRequest.web.OpenGameRequest
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

}