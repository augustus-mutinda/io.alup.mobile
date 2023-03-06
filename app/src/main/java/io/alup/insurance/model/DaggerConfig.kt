package io.alup.insurance.model

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.alup.insurance.R
import io.alup.insurance.model.Constants.Companion.BASE
import io.alup.insurance.model.Constants.Companion.sourceApplication
import io.alup.insurance.model.Constants.Companion.sourceType
import io.alup.insurance.model.Constants.Companion.sourceVersion
import io.alup.insurance.model.Util.Companion.getAuthToken
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @author Augustus, augustusmutinda16@gmail.com
 * A collection of utility functions initiated by Hilt
 */
@Module
@InstallIn(SingletonComponent::class)
object DaggerConfig {

    @Provides
    @Singleton
    fun provideOkHttpClient(sharedPrefs: SharedPreferences) =
        OkHttpClient.Builder().let { httpClient ->
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            httpClient.addInterceptor(logging)

            httpClient.readTimeout(60, TimeUnit.SECONDS)
            httpClient.connectTimeout(60, TimeUnit.SECONDS)
            httpClient.callTimeout(2, TimeUnit.MINUTES)

            httpClient.addInterceptor { chain ->
                val originalRequest = chain.request()
                val builder = originalRequest.newBuilder()
                builder.header("X-Source-Application", sourceApplication())
                builder.header("X-Source-Version", sourceVersion())
                builder.header("X-Source-Type", sourceType())

                if (!originalRequest.url.pathSegments.contains("open"))
                    sharedPrefs.getAuthToken().let {
                        builder.header("Authorization", it)
                    }
                chain.proceed(builder.build())
            }

            httpClient.build()
        }

    @Provides
    @Singleton
    fun provideOpenApi(client: OkHttpClient): Api = Retrofit
        .Builder()
        .baseUrl(BASE)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Api::class.java)

    @Provides
    @Singleton
    fun providedOpenRepositoryImpl(
        api: Api,
        sharedPrefs: SharedPreferences,
        @ApplicationContext context: Context
    ): ApiRepository = ApiRepository(api, sharedPrefs)

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(
            context.getString(R.string.db_name),
            Context.MODE_PRIVATE
        )
    }
}