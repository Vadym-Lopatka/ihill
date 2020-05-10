package com.ihill.app.gameRequest.service

import com.ihill.app.game.GameService
import com.ihill.app.gameRequest.GameRequestDataHelper.buildGameRequest
import com.ihill.app.gameRequest.domain.GameRequestStatus
import com.ihill.app.gameRequest.repository.GameRequestRepository
import com.ihill.app.player.repository.Player
import com.ihill.app.player.repository.PlayerRepository
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class GameRequestServiceAcceptorTest {

    private val repository = mockk<GameRequestRepository>()
    private val playerRepository = mockk<PlayerRepository>()
    private val gameService = mockk<GameService>()

    private val service = GameRequestService(gameService, repository, playerRepository)

    @BeforeEach
    fun setup() {
        every { repository.findOne(any()) } returns buildGameRequest(INITIATOR_UUID)

        every { gameService.newGame(any()) } returns buildGame(INITIATOR_UUID, ACCEPTOR_UUID)

        every { playerRepository.findOne(INITIATOR_UUID) } returns Player(INITIATOR_UUID)
        every { playerRepository.findOne(ACCEPTOR_UUID) } returns Player(ACCEPTOR_UUID)
        every { playerRepository.findOne(NOT_EXIST_INITIATOR_UUID) } returns null
        every { playerRepository.findOne(NOT_EXIST_ACCEPTOR_UUID) } returns null
    }

    private fun buildGame(initiatorUuid: String, acceptorUuid: String)= Game(
        initiator = initiatorUuid,
        acceptor = acceptorUuid
    )

    @Test
    fun `should accept opened GameRequest`() {
        // given
        val openedGameRequestUUID = OPENED_GAME_REQUEST_UUID
        val acceptorUUID = ACCEPTOR_UUID

        // when
        val gameRequest = service.acceptGameRequest(openedGameRequestUUID, acceptorUUID)

        // then
        assertThat(gameRequest.initiator).isEqualTo(INITIATOR_UUID)
        assertThat(gameRequest.acceptor).isEqualTo(ACCEPTOR_UUID)
        assertThat(gameRequest.status).isEqualTo(GameRequestStatus.ACCEPTED)
        assertThat(gameRequest.createdAt).isNotNull()
        assertThat(gameRequest.updatedAt).isAfter(gameRequest.createdAt)
    }


    companion object {
        private const val INITIATOR_UUID = "initiator-uuid"
        private const val NOT_EXIST_INITIATOR_UUID = "initiator-uuid-that-does-not-exist"

        private const val ACCEPTOR_UUID = "acceptor-uuid"
        private const val NOT_EXIST_ACCEPTOR_UUID = "acceptor-uuid-that-does-not-exist"

        private const val OPENED_GAME_REQUEST_UUID = "opened-game-request-uuid"
        private const val NOT_EXIST_GAME_REQUEST_UUID = "not-exist-game-request-uuid"
        private const val CLOSED_GAME_REQUEST_UUID = "closed-game-request-uuid"
        private const val FAILED_GAME_REQUEST_UUID = "failed-game-request-uuid"

    }

}