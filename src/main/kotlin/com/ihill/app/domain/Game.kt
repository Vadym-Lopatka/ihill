package com.ihill.app.domain

import com.ihill.app.domain.enums.GameStatusType
import java.time.LocalDateTime

data class Game(
        val initiator: Player,
        val acceptor: Player,
        var status: GameStatusType = GameStatusType.LOBBY,
        val createdAt: LocalDateTime = LocalDateTime.now()
)