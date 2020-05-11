package com.ihill.app.gameRequest.web

import com.ihill.app.config.getLogger
import com.ihill.app.gameRequest.service.GameRequestService
import com.ihill.app.gameRequest.domain.GameRequest
import com.ihill.app.gameRequest.web.GameRequestResource.Companion.GAME_REQUEST_URL
import com.ihill.app.gameRequest.web.request.AcceptGameRequest
import com.ihill.app.gameRequest.web.request.OpenGameRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping(GAME_REQUEST_URL)
class GameRequestResource (val service: GameRequestService) {
    private val log = getLogger(javaClass)

    @PostMapping
    fun openGameRequest(@RequestBody @Valid request: OpenGameRequest): GameRequest {
        log.debug("[API] open GameRequest by request: $request")
        return service.openGameRequest(request.initiatorUUID)
    }
    
    @PutMapping
    fun acceptGameRequest(@RequestBody @Valid request: AcceptGameRequest): GameRequest {
        log.debug("[API] accept GameRequest by request: $request")
        return service.acceptGameRequest(request.gameRequestUUID, request.acceptorUUID)
    }

    companion object{
        const val GAME_REQUEST_URL = "/api/game-request"
    }
}

