package com.ihill.app.domain

import com.ihill.app.domain.enums.OfferStatusType

data class Offer(
        val initiatorUUID: String,
        var acceptorUUID: String? = null,
        var status: OfferStatusType = OfferStatusType.OPEN
)

fun Offer.toAcceptedState(acceptorUUID: String): Offer {
    return this.copy().apply {
        this.acceptorUUID = acceptorUUID
        status = OfferStatusType.ACCEPTED
    }
}