package com.ihill.app.offer.service

import com.ihill.app.game.GameService
import com.ihill.app.offer.ErrorMsg
import com.ihill.app.offer.domain.Offer
import com.ihill.app.offer.domain.OfferStatus.OPEN
import com.ihill.app.offer.domain.toAcceptState
import com.ihill.app.offer.repository.OfferRepository
import com.ihill.app.player.repository.PlayerRepository
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
        return offerRepository.save(Offer(initiator = player.uuid, status = OPEN))
    }

    fun acceptOffer(offerUUID: String, acceptorUUID: String): Offer {
        playerRepository.findOne(acceptorUUID)
            ?: throw IllegalStateException("${ErrorMsg.PLAYER_NOT_FOUND} $acceptorUUID")

        val offer = offerRepository.findOneByUuidAndStatus(offerUUID, OPEN)
            ?.let { offerRepository.save(it.toAcceptState(acceptorUUID)) }
            ?: throw IllegalStateException("${ErrorMsg.OFFER_NOT_FOUND} $acceptorUUID")

        gameService.newGame(offer)
        return offer
    }


}

