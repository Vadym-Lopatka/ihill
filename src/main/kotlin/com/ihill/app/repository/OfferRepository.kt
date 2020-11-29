package com.ihill.app.repository

import com.ihill.app.domain.Offer
import com.ihill.app.domain.OfferStatusType
import org.springframework.stereotype.Service


@Service
class OfferRepository {
    private val store = HashMap<String, Offer>()

    fun save(offer: Offer): Offer {
        store[offer.initiatorUUID] = offer
        return offer
    }

    fun findOne(offerUUID: String): Offer? {
        return store[offerUUID]
    }

    fun findOneByUuidAndStatus(uuid: String, status: OfferStatusType): Offer? {
        return findOne(uuid)?.takeIf { it.status == status }
    }
}