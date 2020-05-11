package com.ihill.app.offer.repository

import com.ihill.app.offer.domain.Offer
import com.ihill.app.offer.domain.OfferStatus
import org.springframework.stereotype.Service


@Service
class OfferRepository {
    private val store = HashMap<String, Offer>()

    fun save(offer: Offer): Offer {
        store[offer.initiator] = offer
        return offer
    }

    fun findOne(offerUUID: String): Offer? {
        return store[offerUUID]
    }

    fun findOneByUuidAndStatus(uuid: String, status: OfferStatus): Offer? {
        return findOne(uuid)?.takeIf { it.status == status }
    }
}