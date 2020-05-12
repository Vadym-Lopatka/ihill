package com.ihill.app.offer.web

import com.ihill.app.config.getLogger
import com.ihill.app.game.Game
import com.ihill.app.offer.domain.Offer
import com.ihill.app.offer.service.OfferService
import com.ihill.app.offer.web.OfferResource.Companion.OFFER_URL
import com.ihill.app.offer.web.request.AcceptOfferRequest
import com.ihill.app.offer.web.request.OpenOfferRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping(OFFER_URL)
class OfferResource(val service: OfferService) {
    private val log = getLogger(javaClass)

    @PostMapping
    fun openOffer(@RequestBody @Valid request: OpenOfferRequest): Offer {
        log.debug("[API] open Offer by request: $request")
        return service.openOffer(request.initiatorUUID)
    }

    @PutMapping
    fun acceptOffer(@RequestBody @Valid request: AcceptOfferRequest): Game {
        log.debug("[API] accept Offer by request: $request")
        return service.acceptOffer(request.offerUUID, request.acceptorUUID)
    }

    companion object{
        const val OFFER_URL = "/api/offer"
    }
}

