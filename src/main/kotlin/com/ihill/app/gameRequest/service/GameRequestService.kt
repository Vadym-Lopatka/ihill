package com.ihill.app.gameRequest.service

import com.ihill.app.game.GameService
import com.ihill.app.gameRequest.ErrorMsg
import com.ihill.app.gameRequest.domain.GameRequest
import com.ihill.app.gameRequest.domain.GameRequestStatus
import com.ihill.app.gameRequest.domain.GameRequestStatus.OPEN
import com.ihill.app.gameRequest.domain.toAcceptState
import com.ihill.app.gameRequest.repository.GameRequestRepository
import com.ihill.app.player.repository.PlayerRepository
import org.springframework.stereotype.Service

@Service
class GameRequestService(
    private val gameService: GameService,
    private val repository: GameRequestRepository,
    private val playerRepository: PlayerRepository
) {

    fun openGameRequest(initiatorUuid: String): GameRequest {
        val player = playerRepository.findOne(initiatorUuid)
            ?: throw IllegalStateException("${ErrorMsg.PLAYER_NOT_FOUND} $initiatorUuid")
        return repository.save(GameRequest(initiator = player.uuid, status = OPEN))
    }

    fun acceptGameRequest(gameRequestUUID: String, acceptorUUID: String): GameRequest {
        playerRepository.findOne(acceptorUUID)
            ?: throw IllegalStateException("${ErrorMsg.PLAYER_NOT_FOUND} $acceptorUUID")

        val gameRequest = repository.findOneByUuidAndStatus(gameRequestUUID, OPEN)
            ?.let { repository.save(it.toAcceptState(acceptorUUID)) }
            ?: throw IllegalStateException("${ErrorMsg.GAME_REQUEST_NOT_FOUND} $acceptorUUID")

        gameService.newGame(gameRequest)
        return gameRequest
    }


}

