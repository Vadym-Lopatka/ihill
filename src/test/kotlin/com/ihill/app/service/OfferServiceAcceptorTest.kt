package com.ihill.app.service

import com.ihill.app.domain.Game
import com.ihill.app.domain.enums.GameStatusType
import com.ihill.app.domain.enums.OfferStatusType
import com.ihill.app.domain.enums.OfferStatusType.CLOSED
import com.ihill.app.domain.enums.OfferStatusType.OPEN
import com.ihill.app.domain.Player
import com.ihill.app.helper.*
import com.ihill.app.helper.ErrorMsg.OFFER_NOT_FOUND
import com.ihill.app.helper.GameDataHelper.buildGame
import com.ihill.app.helper.OfferDataHelper.buildOffer
import com.ihill.app.helper.PlayerDataHelper.buildPlayer
import com.ihill.app.repository.OfferRepository
import com.ihill.app.repository.PlayerRepository
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OfferServiceAcceptorTest {
    private val offerRepository = mockk<OfferRepository>()
    private val playerRepository = mockk<PlayerRepository>()
    private val gameService = mockk<GameService>()

    private val service = OfferService(gameService, offerRepository, playerRepository)

    @BeforeEach
    fun setup() {
        // player
        every { playerRepository.findOne(ACCEPTOR_UUID) } returns Player(ACCEPTOR_UUID)

        // game
        every { gameService.newGame(any()) } returns buildGame(INITIATOR_UUID, ACCEPTOR_UUID)

        // offer
        val openedOffer = buildOffer(INITIATOR_UUID, null, OPEN)
        val acceptedOffer = buildOffer(INITIATOR_UUID, ACCEPTOR_UUID, OfferStatusType.ACCEPTED)
        every { offerRepository.findOne(OPENED_OFFER_UUID) } returns openedOffer
        every { offerRepository.findOneByUuidAndStatus(OPENED_OFFER_UUID, OPEN) } returns openedOffer
        every { offerRepository.save(acceptedOffer) } returns acceptedOffer

    }

    @Test
    fun `should accept opened Offer`() {
        // given
        val openedOfferUUID = OPENED_OFFER_UUID
        val acceptorUUID = ACCEPTOR_UUID

        // when
        val game = service.acceptOffer(openedOfferUUID, acceptorUUID)

        // then
        assertThat(acceptorUUID).isEqualTo(game.acceptor.uuid)
        assertThat(GameStatusType.LOBBY).isEqualTo(game.status)
    }

    @Test
    fun `should throw an exception when Offer does not exist`() {
        // given
        every { offerRepository.findOneByUuidAndStatus(NOT_EXIST_OFFER_UUID, OPEN) } returns null

        val openedOfferUUID = NOT_EXIST_OFFER_UUID
        val acceptorUUID = ACCEPTOR_UUID

        // when then
        Assertions.assertThrows(IllegalStateException::class.java) {
            service.acceptOffer(openedOfferUUID, acceptorUUID)
        }.let {
            assertThat(it.message).contains(OFFER_NOT_FOUND)
        }
    }

    @Test
    fun `should throw an exception when Offer is already closed`() {
        // given
        val closedOffer = buildOffer(INITIATOR_UUID, ACCEPTOR_UUID, CLOSED)

        every { offerRepository.findOne(CLOSED_OFFER_UUID) } returns closedOffer
        every { offerRepository.findOneByUuidAndStatus(CLOSED_OFFER_UUID, OPEN) } returns null
        val openedOfferUUID = CLOSED_OFFER_UUID
        val acceptorUUID = ACCEPTOR_UUID

        // when then
        Assertions.assertThrows(IllegalStateException::class.java) {
            service.acceptOffer(openedOfferUUID, acceptorUUID)
        }/*.let {
            assertThat(it.message).contains(GAME_REQUEST_CAN_NOT_BE_ACCEPTED)
        }*/
    }

}