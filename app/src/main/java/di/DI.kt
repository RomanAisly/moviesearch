package di

import com.example.moviesearch.BuildConfig
import data.ApiConstants
import data.MainRepository
import data.TmdbApi
import domain.Interactor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object DI {
    val mainModule = module {
        single { MainRepository() }
        single<TmdbApi> {
            val okHttpClient = OkHttpClient.Builder().callTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).addInterceptor(HttpLoggingInterceptor().apply {
                    if (BuildConfig.DEBUG) {
                        level = HttpLoggingInterceptor.Level.BASIC
                    }
                }).build()

            val retrofit = Retrofit.Builder().baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
            retrofit.create(TmdbApi::class.java)
        }
        single { Interactor(get(), get()) }
    }


}