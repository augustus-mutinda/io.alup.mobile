package io.alup.insurance.model

/**
 * @author Augustus, augustusmutinda16@gmail.com
 * A sealed class to hold all states
 */
sealed class AlupState<out T : Any> {
    data class Success<out T : Any>(val data: T) : AlupState<T>()
    data class Editing<out T : Any>(val data: T) : AlupState<T>()
    data class Resolved<out T : Any>(val data: T) : AlupState<T>()
    data class Verify<out T : Any>(val data: T) : AlupState<T>()
    data class Failed(val error: String) : AlupState<Nothing>()
    object Default : AlupState<Nothing>()
    object Loading : AlupState<Nothing>()
    object Error : AlupState<Nothing>()
    object Empty : AlupState<Nothing>()
}