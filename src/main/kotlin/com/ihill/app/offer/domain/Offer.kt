package com.ihill.app.offer.domain

data class Offer(
    val initiator: String,
    var acceptor: String? = null,
    var status: OfferStatus = OfferStatus.OPEN
)

enum class OfferStatus {
    OPEN,
    CLOSED,
    ACCEPTED,
    FAILED
}

fun Offer.toAcceptState(acceptorUUID: String): Offer {
    return this.copy().apply {
        acceptor = acceptorUUID
        status = OfferStatus.ACCEPTED
    }
}