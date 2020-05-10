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
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class GameRequestServiceInitiatorTest {

    private val repository = mockk<GameRequestRepository>()
    private val playerRepository = mockk<PlayerRepository>()
    private val gameService = mockk<GameService>()

    private val service = GameRequestService(gameService, repository, playerRepository)

    @BeforeEach
    fun setup() {
        every { repository.save(any()) } returns buildGameRequest(EXIST_INITIATOR_UUID)

        every { playerRepository.findOne(EXIST_INITIATOR_UUID) } returns Player(EXIST_INITIATOR_UUID)
        every { playerRepository.findOne(NOT_EXIST_INITIATOR_UUID) } returns null
    }

    @Test
    fun `should open GameRequest when initiator does exist`() {
        // given
        val initiator = EXIST_INITIATOR_UUID

        // when
        val gameRequest = service.openGameRequest(initiator)

        // then
        assertThat(gameRequest.initiator).isEqualTo(EXIST_INITIATOR_UUID)
        assertThat(gameRequest.status).isEqualTo(GameRequestStatus.OPEN)
        assertThat(gameRequest.createdAt).isNotNull()
    }

    @Test
    fun `should throw an exception when initiator does not exist`() {
        // given
        val initiator = NOT_EXIST_INITIATOR_UUID

        // when then
        Assertions.assertThrows(IllegalStateException::class.java) {
            service.openGameRequest(initiator)
        }
    }

    companion object {
        private const val EXIST_INITIATOR_UUID = "initiator-uuid-that-exist"
        private const val NOT_EXIST_INITIATOR_UUID = "initiator-uuid-that-does-not-exist"
    }
}