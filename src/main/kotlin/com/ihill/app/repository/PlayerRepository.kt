package com.ihill.app.repository

import com.ihill.app.domain.Player
import org.springframework.stereotype.Component
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.plusAssign

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

