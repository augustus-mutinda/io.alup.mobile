package io.alup.insurance.model.data

import java.util.*

/**
 * @author Augustus, augustusmutinda16@gmail.com
 * Payout POJO class
 */
data class Payout(
    var id: String,
    var type: String,
    var title: String,
    var message: String,
    var dateCreated: Date?,
    var status: String
)
