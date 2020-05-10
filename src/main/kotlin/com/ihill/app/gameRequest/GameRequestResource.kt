package com.ihill.app.gameRequest

import com.ihill.app.gameRequest.GameRequestResource.Companion.GAME_REQUEST_URL
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping(GAME_REQUEST_URL)
class GameRequestResource (val service: GameRequestService) {

    @PostMapping
    fun openGameRequest(@RequestBody @Valid request: OpenGameRequest): GameRequest {
        return service.openGameRequest(request.initiatorUuid)
    }

    companion object{
        const val GAME_REQUEST_URL = "/api/game-request"
    }
}

