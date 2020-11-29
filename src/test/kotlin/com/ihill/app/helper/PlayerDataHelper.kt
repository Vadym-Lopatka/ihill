package com.ihill.app.helper

import com.ihill.app.domain.Player

object PlayerDataHelper {
    fun buildPlayer(uuid: String) = Player(
            uuid=uuid
    )
}