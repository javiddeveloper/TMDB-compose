package ir.javid.sattar.tmdbmovies.data.remote.interceptors

import ir.javid.sattar.tmdbmovies.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor  : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestWithContentType = originalRequest.newBuilder()
            .header("accept", "application/json")
            .header("Authorization", "Bearer ${ BuildConfig.API_KEY}")
            .build()
        return chain.proceed(requestWithContentType)
    }
}