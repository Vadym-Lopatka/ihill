package com.ihill.app.gameRequest.domain

import java.time.LocalDateTime

data class GameRequest(
    val initiator: String,
    var acceptor: String? = null,
    var status: GameRequestStatus = GameRequestStatus.OPEN,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime? = null
)

enum class GameRequestStatus {
    OPEN,
    CLOSED_BY_INITIATOR,
    ACCEPTED,
    FAILED
}
