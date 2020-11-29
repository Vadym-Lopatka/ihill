package com.ihill.app.domain

import java.time.LocalDateTime

data class Game(
        val initiator: String,
        val acceptor: String,
        var status: GameStatusType = GameStatusType.LOBBY,
        val createdAt: LocalDateTime = LocalDateTime.now()
)