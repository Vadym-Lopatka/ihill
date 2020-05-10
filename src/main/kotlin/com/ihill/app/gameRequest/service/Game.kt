package com.ihill.app.gameRequest.service

import java.time.LocalDateTime

data class Game(
    val initiator: String,
    val acceptor: String,
    var status: GameStatus = GameStatus.LOBBY,
    val createdAt: LocalDateTime = LocalDateTime.now()
)