package io.alup.insurance.model

import io.alup.insurance.model.data.*
import retrofit2.Response
import retrofit2.http.*

/**
 * @author Augustus, augustusmutinda16@gmail.com
 * An interface to hold all api endpoints
 */
interface Api {
    @GET("eb1b615a-1d76-44ad-8acf-2c4a433456f3")
    suspend fun getOtp(@Query("msisdn") msisdn: String): Response<Request<GetOtpResponse>>

    @GET("7fcb90f6-7a4b-4e24-9b5a-533bcee8e41e")
    suspend fun verifyOtp(
        @Query("otp") otp: String,
        @Query("code") code: String
    ): Response<Request<VerifyOtpResponse>>

    @GET("ed3b32f9-0724-4509-9bba-a93f07ae6191")
    suspend fun getCurrentUser(): Response<Request<User>>

    @GET("42520ee8-b003-4942-8855-b6bae2f16f29")
    suspend fun getNotifications(): Response<PagedRequest<Note>>

    @GET("ea10ce75-33a7-4b85-8048-87c3a5a5d367")
    suspend fun getPayouts(): Response<PagedRequest<Payout>>

    @GET("67daace7-c35a-481b-9271-36d813521d0f")
    suspend fun getPolicies(): Response<PagedRequest<Policy>>

    @GET("94e52e7b-ce2c-4014-bb28-ba4fa417d34f")
    suspend fun signUp(): Response<Request<CreateUserResponse>>
}