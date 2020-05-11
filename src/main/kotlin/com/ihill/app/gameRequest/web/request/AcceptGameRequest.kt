package com.ihill.app.gameRequest.web.request

import com.ihill.app.gameRequest.UuidSettings
import javax.validation.constraints.Size

data class AcceptGameRequest(
    @field:Size(min = UuidSettings.MIN_LENGTH, max = UuidSettings.MAX_LENGTH)
    val acceptorUUID: String,
    val gameRequestUUID: String
)

