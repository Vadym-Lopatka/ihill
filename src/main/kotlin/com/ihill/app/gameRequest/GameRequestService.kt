package com.ihill.app.gameRequest

import org.springframework.stereotype.Service

@Service
class GameRequestService(val repository: GameRequestRepository) {

    fun openGameRequest(initiator: String): GameRequest {
        return repository.save(GameRequest(initiator = initiator, status = GameRequestStatus.OPEN))
    }
}

