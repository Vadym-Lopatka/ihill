package com.ihill.app.offer

import com.ihill.app.offer.domain.Offer
import com.ihill.app.offer.domain.OfferStatus


object OfferDataHelper {
    fun buildOffer(
        initiator: String,
        acceptor: String?,
        status: OfferStatus
    ) = Offer(
        initiator = initiator,
        acceptor = acceptor,
        status = status
    )
}

const val INITIATOR_UUID = "initiator-uuid"
const val NOT_EXIST_INITIATOR_UUID = "initiator-uuid-that-does-not-exist"

const val ACCEPTOR_UUID = "ACCEPTOR-1111111111111111111111111111111111111"
const val NOT_EXIST_ACCEPTOR_UUID = "acceptor-uuid-that-does-not-exist"

const val OPENED_OFFER_UUID = "opened-offer-uuid"
const val NOT_EXIST_OFFER_UUID = "not-exist-offer-uuid"
const val CLOSED_OFFER_UUID = "closed-offer-uuid"
const val FAILED_OFFER_UUID = "failed-offer-uuid"