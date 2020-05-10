package com.ihill.app.gameRequest

import java.time.LocalDateTime

data class GameRequest(
        val initiator: String,
        val defender: String? = null,
        val status: GameRequestStatus = GameRequestStatus.OPEN,
        val createdAt: LocalDateTime = LocalDateTime.now(),
        val updatedAt: LocalDateTime? = null
)

//fun GameRequest.toOpenRequest(initiator: String) = GameRequest(initiator = initiator, status = GameRequestStatus.OPEN)

enum class GameRequestStatus {
    OPEN,
    CLOSED_BY_INITIATOR,

    ACCEPTED,
    CLOSED_BY_DEFENDER,

    FAILED
}
