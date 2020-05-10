package com.ihill.app.gameRequest.web

import com.ihill.app.config.getLogger
import com.ihill.app.gameRequest.service.GameRequestService
import com.ihill.app.gameRequest.domain.GameRequest
import com.ihill.app.gameRequest.web.GameRequestResource.Companion.GAME_REQUEST_URL
import org.springframework.web.bind.annotation.PostMapping
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
        log.debug("[API] request to open GameRequest by request: $request")
        return service.openGameRequest(request.initiatorUuid)
    }

    companion object{
        const val GAME_REQUEST_URL = "/api/game-request"
    }
}

