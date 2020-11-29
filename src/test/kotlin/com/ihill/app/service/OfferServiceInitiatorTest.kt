package com.ihill.app.service

import com.ihill.app.domain.enums.OfferStatusType.OPEN
import com.ihill.app.domain.Player
import com.ihill.app.helper.OfferDataHelper.buildOffer
import com.ihill.app.repository.OfferRepository
import com.ihill.app.repository.PlayerRepository
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class OfferServiceInitiatorTest {

    private val repository = mockk<OfferRepository>()
    private val playerRepository = mockk<PlayerRepository>()
    private val gameService = mockk<GameService>()

    private val service = OfferService(gameService, repository, playerRepository)

    @BeforeEach
    fun setup() {
        every { repository.save(any()) } returns buildOffer(EXIST_INITIATOR_UUID, null, OPEN)

        every { playerRepository.findOne(EXIST_INITIATOR_UUID) } returns Player(EXIST_INITIATOR_UUID)
        every { playerRepository.findOne(NOT_EXIST_INITIATOR_UUID) } returns null
    }

    @Test
    fun `should open Offer when initiator does exist`() {
        // given
        val initiator = EXIST_INITIATOR_UUID

        // when
        val offer = service.openOffer(initiator)

        // then
        assertThat(offer.initiatorUUID).isEqualTo(EXIST_INITIATOR_UUID)
        assertThat(offer.status).isEqualTo(OPEN)
    }

    @Test
    fun `should throw an exception when initiator does not exist`() {
        // given
        val initiator = NOT_EXIST_INITIATOR_UUID

        // when then
        Assertions.assertThrows(IllegalStateException::class.java) {
            service.openOffer(initiator)
        }
    }

    companion object {
        private const val EXIST_INITIATOR_UUID = "initiator-uuid-that-exist"
        private const val NOT_EXIST_INITIATOR_UUID = "initiator-uuid-that-does-not-exist"
    }
}