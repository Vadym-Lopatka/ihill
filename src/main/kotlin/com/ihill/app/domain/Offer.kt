package com.ihill.app.domain

data class Offer(
        val initiatorUUID: String,
        var acceptorUUID: String? = null,
        var status: OfferStatusType = OfferStatusType.OPEN
)

enum class OfferStatusType {
    OPEN,
    CLOSED,
    ACCEPTED,
    FAILED
}

fun Offer.toAcceptedState(acceptorUUID: String): Offer {
    return this.copy().apply {
        this.acceptorUUID = acceptorUUID
        status = OfferStatusType.ACCEPTED
    }
}