package com.ihill.app.gameRequest.service

import com.ihill.app.game.GameService
import com.ihill.app.gameRequest.domain.GameRequest
import com.ihill.app.gameRequest.domain.GameRequestStatus
import com.ihill.app.gameRequest.repository.GameRequestRepository
import com.ihill.app.player.repository.PlayerRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class GameRequestService(
    private val gameService: GameService,
    private val repository: GameRequestRepository,
    private val playerRepository: PlayerRepository
) {

    fun openGameRequest(initiatorUuid: String): GameRequest {
        val player = playerRepository.findOne(initiatorUuid)
            ?: throw IllegalStateException("Can't find player with uuid: $initiatorUuid")
        return repository.save(GameRequest(initiator = player.uuid, status = GameRequestStatus.OPEN))
    }

    fun acceptGameRequest(gameRequestUUID: String, acceptorUUID: String): GameRequest {
        playerRepository.findOne(acceptorUUID)
            ?: throw IllegalStateException("Can't find player with uuid: $acceptorUUID")

        return repository.findOne(gameRequestUUID)
            ?.apply {
                status = GameRequestStatus.ACCEPTED
                acceptor = acceptorUUID
                updatedAt = LocalDateTime.now()
            }
            ?.also { repository::save }
            ?.also { gameService.newGame(it) }

            ?: throw IllegalStateException("Can't find GameRequest with uuid: $gameRequestUUID")
    }
}

