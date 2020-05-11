package com.ihill.app.offer.web.request

import com.ihill.app.offer.UuidSettings
import javax.validation.constraints.Size

data class AcceptOfferRequest(
    @field:Size(min = UuidSettings.MIN_LENGTH, max = UuidSettings.MAX_LENGTH)
    val acceptorUUID: String,
    val offerUUID: String
)

