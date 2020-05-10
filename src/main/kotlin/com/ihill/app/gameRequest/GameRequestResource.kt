package com.ihill.app.gameRequest

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/game-request")
class GameRequestResource (val service: GameRequestService) {

    @PostMapping
    fun hello(request: NewGameRequest): GameRequest {
        return service.openGameRequest(request.initiatorUuid)
    }
}

