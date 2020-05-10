package com.ihill.app.player.repository

import com.ihill.app.gameRequest.domain.GameRequest
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class PlayerRepository {
    private val store = HashMap<String, Player>()

    init {
        val player1 = buildPlayer(UUID.randomUUID())
        val player2 = buildPlayer(UUID.randomUUID())

        store += player1.uuid to player1
        store += player2.uuid to player2
    }

    private fun buildPlayer(randomUUID: UUID)= Player(
        uuid = randomUUID.toString()
    )

    fun findOne(uuid: String): Player? {
        return store[uuid]
    }

}

data class Player(
    val uuid: String
)
