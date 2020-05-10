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