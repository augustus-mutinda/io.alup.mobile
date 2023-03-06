package io.alup.insurance.model.data

/**
 * @author Augustus, augustusmutinda16@gmail.com
 * QuickLink POJO class
 * used by HomeFragment
 */
data class QuickLink(
    var title: String,
    var desc: String,
    var icon: Int,
    var navigate: Boolean,
    var navId: Int
)
