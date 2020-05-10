package com.ihill.app.gameRequest

import org.springframework.stereotype.Service


@Service
class GameRequestRepository {
    private val store = HashMap<String, GameRequest>()

    fun save(request: GameRequest): GameRequest {
        store[request.initiator] = request
        return request
    }

}