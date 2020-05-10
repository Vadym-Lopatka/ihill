package com.ihill.app.gameRequest

import com.ihill.app.gameRequest.domain.GameRequest
import com.ihill.app.gameRequest.domain.GameRequestStatus


object GameRequestDataHelper {
    private const val VALID_INITIATOR_UUID = "test-initiator-uuid"

    fun buildGameRequest(initiator: String = VALID_INITIATOR_UUID) = GameRequest(
        initiator = initiator,
        status = GameRequestStatus.OPEN
    )
}