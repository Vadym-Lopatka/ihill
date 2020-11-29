package com.ihill.app.offer

import com.ihill.app.game.Game
import com.ihill.app.game.GameStatus
import com.ihill.app.offer.OfferDataHelper.buildOffer
import com.ihill.app.offer.domain.Offer
import com.ihill.app.offer.domain.OfferStatus
import com.ihill.app.offer.service.OfferService
import com.ihill.app.offer.web.OfferResource.Companion.OFFER_URL
import com.ihill.app.offer.web.request.AcceptOfferRequest
import com.ihill.app.offer.web.request.OpenOfferRequest
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OfferResourceTest {

    @MockkBean
    lateinit var service: OfferService

    @Autowired
    private lateinit var testRestTemplate: TestRestTemplate

    @BeforeEach
    fun setup() {
        every { service.openOffer(any()) } returns buildOffer(INITIATOR_UUID, null, OfferStatus.OPEN)
        every { service.acceptOffer(any(), any()) } returns Game(INITIATOR_UUID, ACCEPTOR_UUID,GameStatus.LOBBY)
    }

    @Test
    fun `should open a new Offer`() {
        // given
        val openOffer = OpenOfferRequest(INITIATOR_UUID)

        // when
        val httpRequest = HttpEntity(openOffer)
        val response = testRestTemplate.exchange(OFFER_URL, HttpMethod.POST, httpRequest, Offer::class.java)

        //then
        assertTrue(response.statusCode.is2xxSuccessful)
        val offer = response.body!!
        assertThat(openOffer.initiatorUUID).isEqualTo(offer.initiatorUUID)
        assertThat(offer.status).isEqualTo(OfferStatus.OPEN)
    }

    @Test
    fun `should accept opened Offer`() {
        // given
        val acceptOffer = AcceptOfferRequest(ACCEPTOR_UUID, OPENED_OFFER_UUID)

        // when
        val httpRequest = HttpEntity(acceptOffer)
        val response = testRestTemplate.exchange(OFFER_URL, HttpMethod.PUT, httpRequest, Game::class.java)

        //then
        assertTrue(response.statusCode.is2xxSuccessful)
        val game = response.body!!
        assertThat(game.initiator).isEqualTo(INITIATOR_UUID)
        assertThat(game.acceptor).isEqualTo(ACCEPTOR_UUID)
        assertThat(game.status).isEqualTo(GameStatus.LOBBY)
    }

}