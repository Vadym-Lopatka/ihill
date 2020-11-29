package com.ihill.app.offer.domain

data class Offer(
        val initiatorUUID: String,
        var acceptorUUID: String? = null,
        var status: OfferStatus = OfferStatus.OPEN
)

enum class OfferStatus {
    OPEN,
    CLOSED,
    ACCEPTED,
    FAILED
}

fun Offer.toAcceptedState(acceptorUUID: String): Offer {
    return this.copy().apply {
        this.acceptorUUID = acceptorUUID
        status = OfferStatus.ACCEPTED
    }
}