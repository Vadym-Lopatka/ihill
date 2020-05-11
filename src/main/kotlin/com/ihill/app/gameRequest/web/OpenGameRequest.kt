package com.ihill.app.gameRequest.web

import com.ihill.app.gameRequest.UuidSettings
import javax.validation.constraints.Size

data class OpenGameRequest(
    @field:Size(min = UuidSettings.MIN_LENGTH, max = UuidSettings.MAX_LENGTH)
    val initiatorUuid: String
)

