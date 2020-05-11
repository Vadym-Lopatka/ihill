package com.ihill.app.gameRequest.domain

data class GameRequest(
    val initiator: String,
    var acceptor: String? = null,
    var status: GameRequestStatus = GameRequestStatus.OPEN
)

enum class GameRequestStatus {
    OPEN,
    CLOSED_BY_INITIATOR,
    ACCEPTED,
    FAILED
}

fun GameRequest.toAcceptState(acceptorUUID: String): GameRequest {
    return this.copy().apply {
        acceptor = acceptorUUID
        status = GameRequestStatus.ACCEPTED
    }
}