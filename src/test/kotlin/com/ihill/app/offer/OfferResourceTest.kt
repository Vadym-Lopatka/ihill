package com.ihill.app.offer

import com.ihill.app.TestHelper.getRandomString
import com.ihill.app.offer.OfferDataHelper.buildOffer
import com.ihill.app.offer.domain.Offer
import com.ihill.app.offer.domain.OfferStatus
import com.ihill.app.offer.service.OfferService
import com.ihill.app.offer.web.OfferResource.Companion.OFFER_URL
import com.ihill.app.offer.web.request.AcceptOfferRequest
import com.ihill.app.offer.web.request.OpenOfferRequest
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
class OfferResourceTest {

    @MockkBean
    lateinit var service: OfferService

    @Autowired
    private lateinit var testRestTemplate: TestRestTemplate

    @BeforeEach
    fun setup() {
        val offer = buildOffer(getRandomString(UuidSettings.MIN_LENGTH), null, OfferStatus.OPEN)
        every { service.openOffer(any()) } returns offer
        every { service.acceptOffer(any(), any()) } returns offer.apply {
            acceptor = ACCEPTOR_UUID
            status = OfferStatus.ACCEPTED
        }
    }

    @Test
    fun `should open a new Offer`() {
        // given
        val openOffer = OpenOfferRequest(getRandomString(UuidSettings.MIN_LENGTH))

        // when
        val httpRequest = HttpEntity(openOffer)
        val response = testRestTemplate.exchange(OFFER_URL, HttpMethod.POST, httpRequest, Offer::class.java)

        //then
        assertTrue(response.statusCode.is2xxSuccessful)
    }

    @Test
    fun `should accept opened Offer`() {
        // given
        val acceptOffer = AcceptOfferRequest(
            acceptorUUID = ACCEPTOR_UUID,
            offerUUID = OPENED_OFFER_UUID
        )

        // when
        val httpRequest = HttpEntity(acceptOffer)
        val response = testRestTemplate.exchange(OFFER_URL, HttpMethod.PUT, httpRequest, Offer::class.java)

        //then
        assertTrue(response.statusCode.is2xxSuccessful)
    }

}