package io.alup.insurance.model.data

/**
 * @author Augustus, augustusmutinda16@gmail.com
 * User POJO class
 */
data class User(
    var id: String,
    var userName: String,
    var firstName: String,
    var middleName: String,
    var lastName: String,
    var email: String,
    var avatars: List<String>?,
    var phone: String,
    var address: String,
    var city: String,
    var state: String,
    var zip: String,
    var country: String,
)

data class UserDto(
    var userName: String,
    var firstName: String,
    var middleName: String = "",
    var lastName: String,
    var email: String,
    var phone: String,
    var address: String = "",
    var city: String = "",
    var state: String = "",
    var zip: String = "",
    var country: String = "",
)
