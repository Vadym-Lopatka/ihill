package com.ihill.app.gameRequest.service

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


class GameRequestServiceTest {

    private val repository = mockk<GameRequestRepository>()
    private val playerRepository = mockk<PlayerRepository>()


    private val service = GameRequestService(repository,playerRepository)

    @BeforeEach
    fun setup() {
        every { repository.save(any()) } returns buildGameRequest(EXIST_INITIATOR_UUID)

        every { playerRepository.findOne(EXIST_INITIATOR_UUID) } returns Player(EXIST_INITIATOR_UUID)
        every { playerRepository.findOne(DOES_NOT_EXIST_INITIATOR_UUID) } returns null
    }

    @Test
    fun `should save and return saved GameRequest by existed initiator`() {
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
        val initiator = DOES_NOT_EXIST_INITIATOR_UUID

        // when then
        Assertions.assertThrows(IllegalStateException::class.java) {
            service.openGameRequest(initiator)
        }
    }

    companion object {
        private const val EXIST_INITIATOR_UUID = "uuid-that-exist"
        private const val DOES_NOT_EXIST_INITIATOR_UUID = "uuid-that-does-not-exist"
    }

}