package com.ihill.app.gameRequest

import com.ihill.app.gameRequest.domain.GameRequest
import com.ihill.app.gameRequest.domain.GameRequestStatus
import com.ihill.app.gameRequest.domain.GameRequestStatus.OPEN


object GameRequestDataHelper {
    fun buildGameRequest(
        initiator: String,
        acceptor: String?,
        status: GameRequestStatus
    ) = GameRequest(
        initiator = initiator,
        acceptor = acceptor,
        status = status
    )
}

const val INITIATOR_UUID = "initiator-uuid"
const val NOT_EXIST_INITIATOR_UUID = "initiator-uuid-that-does-not-exist"

const val ACCEPTOR_UUID = "ACCEPTOR-1111111111111111111111111111111111111"
const val NOT_EXIST_ACCEPTOR_UUID = "acceptor-uuid-that-does-not-exist"

const val OPENED_GAME_REQUEST_UUID = "opened-game-request-uuid"
const val NOT_EXIST_GAME_REQUEST_UUID = "not-exist-game-request-uuid"
const val CLOSED_GAME_REQUEST_UUID = "closed-game-request-uuid"
const val FAILED_GAME_REQUEST_UUID = "failed-game-request-uuid"