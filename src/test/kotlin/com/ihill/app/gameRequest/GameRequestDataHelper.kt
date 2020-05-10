package com.ihill.app.gameRequest


object GameRequestDataHelper {
    private const val VALID_INITIATOR_UUID = "test-initiator-uuid"

    fun buildGameRequest() = GameRequest(initiator = VALID_INITIATOR_UUID, status = GameRequestStatus.OPEN)
}