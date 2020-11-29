package com.ihill.app.service

import com.ihill.app.domain.Game
import com.ihill.app.domain.Offer
import com.ihill.app.domain.OfferStatusType
import com.ihill.app.domain.OfferStatusType.OPEN
import com.ihill.app.domain.toAcceptedState
import com.ihill.app.helper.ErrorMsg
import com.ihill.app.repository.OfferRepository
import com.ihill.app.repository.PlayerRepository
import org.springframework.stereotype.Service

@Service
class OfferService(
        private val gameService: GameService,
        private val offerRepository: OfferRepository,
        private val playerRepository: PlayerRepository
) {

    fun openOffer(initiatorUuid: String): Offer {
        val player = playerRepository.findOne(initiatorUuid)
            ?: throw IllegalStateException("${ErrorMsg.PLAYER_NOT_FOUND} $initiatorUuid")
        return offerRepository.save(Offer(initiatorUUID = player.uuid, status = OPEN))
    }

    fun acceptOffer(offerUUID: String, acceptorUUID: String): Game {
        playerRepository.findOne(acceptorUUID)
            ?: throw IllegalStateException("${ErrorMsg.PLAYER_NOT_FOUND} $acceptorUUID")

        val offer = offerRepository.findOneByUuidAndStatus(offerUUID, OPEN)
            ?.let { offerRepository.save(it.toAcceptedState(acceptorUUID)) }
            ?: throw IllegalStateException("${ErrorMsg.OFFER_NOT_FOUND} $acceptorUUID")

        return gameService.newGame(offer)
    }
}

