package com.ihill.app.gameRequest.service

import com.ihill.app.gameRequest.repository.GameRequestRepository
import com.ihill.app.gameRequest.domain.GameRequest
import com.ihill.app.gameRequest.domain.GameRequestStatus
import org.springframework.stereotype.Service

@Service
class GameRequestService(val repository: GameRequestRepository) {

    fun openGameRequest(initiator: String): GameRequest {
        return repository.save(GameRequest(initiator = initiator, status = GameRequestStatus.OPEN))
    }
}

