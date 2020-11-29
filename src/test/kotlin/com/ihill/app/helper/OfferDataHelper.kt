package com.ihill.app.helper

import com.ihill.app.domain.Offer
import com.ihill.app.domain.enums.OfferStatusType


object OfferDataHelper {
    fun buildOffer(initiatorUUID: String, acceptorUUID: String?, status: OfferStatusType) = Offer(
            initiatorUUID = initiatorUUID,
            acceptorUUID = acceptorUUID,
            status = status
    )
}

const val INITIATOR_UUID = "INITIATOR-1111111111111111111111111111111111111"
const val NOT_EXIST_INITIATOR_UUID = "initiator-uuid-that-does-not-exist"

const val ACCEPTOR_UUID = "ACCEPTOR-1111111111111111111111111111111111111"
const val NOT_EXIST_ACCEPTOR_UUID = "acceptor-uuid-that-does-not-exist"

const val OPENED_OFFER_UUID = "opened-offer-uuid"
const val NOT_EXIST_OFFER_UUID = "not-exist-offer-uuid"
const val CLOSED_OFFER_UUID = "closed-offer-uuid"
const val FAILED_OFFER_UUID = "failed-offer-uuid"