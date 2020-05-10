package com.ihill.app.gameRequest.repository

import com.ihill.app.gameRequest.domain.GameRequest
import com.ihill.app.gameRequest.domain.GameRequestStatus
import org.springframework.stereotype.Service


@Service
class GameRequestRepository {
    private val store = HashMap<String, GameRequest>()

    fun save(request: GameRequest): GameRequest {
        store[request.initiator] = request
        return request
    }

    fun findOne(gameRequestUUID: String): GameRequest? {
        return store[gameRequestUUID]
    }

//    fun findOneByInitiatorAndStatus(uuid: String, status: GameRequestStatus) : GameRequest? {
//        return findOne(uuid)?.takeIf { it.status == status }
//    }

    fun findOneByUuidAndStatus(uuid: String, status: GameRequestStatus) : GameRequest?{
        return findOne(uuid)?.takeIf { it.status == status }
    }
}