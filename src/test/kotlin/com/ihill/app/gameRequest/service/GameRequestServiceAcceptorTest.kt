package com.ihill.app.gameRequest.service

import com.ihill.app.game.GameService
import com.ihill.app.gameRequest.ACCEPTOR_UUID
import com.ihill.app.gameRequest.CLOSED_GAME_REQUEST_UUID
import com.ihill.app.gameRequest.ErrorMsg.GAME_REQUEST_NOT_FOUND
import com.ihill.app.gameRequest.GameRequestDataHelper.buildGameRequest
import com.ihill.app.gameRequest.INITIATOR_UUID
import com.ihill.app.gameRequest.NOT_EXIST_GAME_REQUEST_UUID
import com.ihill.app.gameRequest.OPENED_GAME_REQUEST_UUID
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
    private val gameRequestRepository = mockk<GameRequestRepository>()
    private val playerRepository = mockk<PlayerRepository>()
    private val gameService = mockk<GameService>()

    private val service = GameRequestService(gameService, gameRequestRepository, playerRepository)

    @BeforeEach
    fun setup() {
        // player
        every { playerRepository.findOne(ACCEPTOR_UUID) } returns Player(ACCEPTOR_UUID)
        // game
        every { gameService.newGame(any()) } returns buildGame(INITIATOR_UUID, ACCEPTOR_UUID)

        // game request
        val openGameRequest = buildGameRequest(INITIATOR_UUID, null, OPEN)
        val acceptedGameRequest = buildGameRequest(INITIATOR_UUID, ACCEPTOR_UUID, ACCEPTED)
        every { gameRequestRepository.findOne(OPENED_GAME_REQUEST_UUID) } returns openGameRequest
        every { gameRequestRepository.findOneByUuidAndStatus(OPENED_GAME_REQUEST_UUID, OPEN) } returns openGameRequest
        every { gameRequestRepository.save(acceptedGameRequest) } returns acceptedGameRequest

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
        every { gameRequestRepository.findOneByUuidAndStatus(NOT_EXIST_GAME_REQUEST_UUID, OPEN) } returns null

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
        val closedGameRequest = buildGameRequest(INITIATOR_UUID, ACCEPTOR_UUID, CLOSED_BY_INITIATOR)

        every { gameRequestRepository.findOne(CLOSED_GAME_REQUEST_UUID) } returns closedGameRequest
        every { gameRequestRepository.findOneByUuidAndStatus(CLOSED_GAME_REQUEST_UUID, OPEN) } returns null
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


    }

}