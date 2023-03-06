package io.alup.insurance.model.data

import java.util.*

/**
 * @author Augustus, augustusmutinda16@gmail.com
 * A class to hold all api request common fields
 * @param header RequestHeader
 * @param body T
 */
open class Request<T>(
    open val header: RequestHeader,
    open val body: T,
) {
    var dueForRetry: Boolean = false
    var retryCount: Int = 0

    fun retry() = retryCount < 1 && dueForRetry
}

class PagedRequest<T>(
    val header: RequestHeader,
    val body: Page<T>
) {
    var dueForRetry: Boolean = false
    var retryCount: Int = 0

    fun retry() = retryCount < 1 && dueForRetry
}

class Page<T>(
    val page: Int,
    val size: Int,
    val totalPages: Int,
    val totalResults: Int,
    val nextPage: Int,
    val previousPage: Int,
    val hasNext: Boolean = true,
    val hasPrevious: Boolean = true,
    val results: List<T>
)

class RequestHeader(
    val requestId: String,
    val message: String,
    val code: Int,
    val timestamp: Date,
    val successful: Boolean = true
)

class CreateUserResponse(
    val body: User,
    val message: String
)

class VerifyOtpResponse(
    val verified: Boolean,
    val message: String,
    val token: RefreshTokenResponse
)

class GetOtpResponse(
    val code: String,
)

class RefreshTokenResponse(
    val id: String,
    val code: String,
)