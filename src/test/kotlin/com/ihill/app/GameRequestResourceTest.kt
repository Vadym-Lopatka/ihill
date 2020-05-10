package com.ihill.app

import com.ihill.app.gameRequest.GameRequest
import com.ihill.app.gameRequest.GameRequestService
import com.ihill.app.gameRequest.GameRequestStatus
import com.ihill.app.gameRequest.NewGameRequest
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity

//@WireMockTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GameRequestResourceTest {

    @MockkBean
    lateinit var service: GameRequestService

    @Autowired
    private lateinit var testRestTemplate: TestRestTemplate

    @Test
    fun `should consume a valid request and return GameRequest`() {
        every { service.openGameRequest(VALID_INITIATOR_UUID) } returns buildGameRequest()
        val clientRequest = NewGameRequest(initiatorUuid = VALID_INITIATOR_UUID)

        // when
        val httpRequest = HttpEntity(clientRequest)
        val response = testRestTemplate.postForEntity("/api/game-request", httpRequest, String::class.java)

        //then
        Assertions.assertThat(response.statusCode).isEqualTo(200)
    }

    private fun buildGameRequest() = GameRequest(
            initiator = VALID_INITIATOR_UUID,
            status = GameRequestStatus.OPEN
    )

    companion object {
        private const val VALID_INITIATOR_UUID = "test-initiator-uuid"
    }

}