package io.alup.insurance.model.data

import java.util.*

/**
 * @author Augustus, augustusmutinda16@gmail.com
 * Notes POJO class
 */
data class Note(
    var id: String,
    var type: String,
    var title: String,
    var message: String,
    var dateCreated: Date?,
    var status: String
)
