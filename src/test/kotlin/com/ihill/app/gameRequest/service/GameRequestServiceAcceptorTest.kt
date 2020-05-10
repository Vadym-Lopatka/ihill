package com.ihill.app.gameRequest.service

import com.ihill.app.game.GameService
import com.ihill.app.gameRequest.ErrorMsg.GAME_REQUEST_CAN_NOT_BE_ACCEPTED
import com.ihill.app.gameRequest.ErrorMsg.GAME_REQUEST_NOT_FOUND
import com.ihill.app.gameRequest.GameRequestDataHelper.buildGameRequest
import com.ihill.app.gameRequest.domain.GameRequestStatus.ACCEPTED
import com.ihill.app.gameRequest.domain.GameRequestStatus.CLOSED_BY_INITIATOR
import com.ihill.app.gameRequest.domain.GameRequestStatus.OPEN
import com.ihill.app.gameRequest.repository.GameRequestRepository
import com.ihill.app.player.repository.Player
import com.ihill.app.player.repository.PlayerRepository
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class GameRequestServiceAcceptorTest {

    private val repository = mockk<GameRequestRepository>()
    private val playerRepository = mockk<PlayerRepository>()
    private val gameService = mockk<GameService>()

    private val service = GameRequestService(gameService, repository, playerRepository)

    @BeforeEach
    fun setup() {
        val openGameRequest = buildGameRequest(INITIATOR_UUID, null, OPEN)
        val acceptedGameRequest = buildGameRequest(INITIATOR_UUID, ACCEPTOR_UUID, ACCEPTED)
        val closedGameRequest = buildGameRequest(INITIATOR_UUID, ACCEPTOR_UUID, CLOSED_BY_INITIATOR)

        every { repository.findOne(OPENED_GAME_REQUEST_UUID) } returns openGameRequest
        every { repository.findOneByUuidAndStatus(OPENED_GAME_REQUEST_UUID, OPEN) } returns openGameRequest
        every { repository.save(acceptedGameRequest) } returns acceptedGameRequest

        every { playerRepository.findOne(ACCEPTOR_UUID) } returns Player(ACCEPTOR_UUID)

        every { repository.findOne(CLOSED_GAME_REQUEST_UUID) } returns closedGameRequest
        every { repository.findOneByUuidAndStatus(CLOSED_GAME_REQUEST_UUID, OPEN) } returns null
        every { repository.findOneByUuidAndStatus(NOT_EXIST_GAME_REQUEST_UUID, OPEN) } returns null

        every { gameService.newGame(any()) } returns buildGame(INITIATOR_UUID, ACCEPTOR_UUID)
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
        assertThat(gameRequest.status).isEqualTo(ACCEPTED)
    }

    @Test
    fun `should throw an exception when GameRequest does not exist`() {
        // given
        val openedGameRequestUUID = NOT_EXIST_GAME_REQUEST_UUID
        val acceptorUUID = ACCEPTOR_UUID

        // when then
        Assertions.assertThrows(IllegalStateException::class.java) {
            service.acceptGameRequest(openedGameRequestUUID, acceptorUUID)
        }.let {
            assertThat(it.message).contains(GAME_REQUEST_NOT_FOUND)
        }
    }

    @Test
    fun `should throw an exception when GameRequest is already closed`() {
        // given
        val openedGameRequestUUID = CLOSED_GAME_REQUEST_UUID
        val acceptorUUID = ACCEPTOR_UUID

        // when then
        Assertions.assertThrows(IllegalStateException::class.java) {
            service.acceptGameRequest(openedGameRequestUUID, acceptorUUID)
        }/*.let {
            assertThat(it.message).contains(GAME_REQUEST_CAN_NOT_BE_ACCEPTED)
        }*/
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