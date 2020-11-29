package com.ihill.app.helper

import com.ihill.app.domain.Game

object GameDataHelper {

    fun buildGame(initiatorUuid: String, acceptorUuid: String) = Game(
            initiator = PlayerDataHelper.buildPlayer(initiatorUuid),
            acceptor = PlayerDataHelper.buildPlayer(acceptorUuid)
    )
}