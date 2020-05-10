package com.ihill.app.gameRequest.service

import com.ihill.app.gameRequest.domain.GameRequest
import com.ihill.app.gameRequest.domain.GameRequestStatus
import com.ihill.app.gameRequest.repository.GameRequestRepository
import com.ihill.app.player.repository.PlayerRepository
import org.springframework.stereotype.Service

@Service
class GameRequestService(
    private val repository: GameRequestRepository,
    private val playerRepository: PlayerRepository
) {

    fun openGameRequest(initiatorUuid: String): GameRequest {
        val player = playerRepository.findOne(initiatorUuid)
            ?: throw IllegalStateException("Can't find player with uuid: $initiatorUuid")
        return repository.save(GameRequest(initiator = player.uuid, status = GameRequestStatus.OPEN))
    }
}

