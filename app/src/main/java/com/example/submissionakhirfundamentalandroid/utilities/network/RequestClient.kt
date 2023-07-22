package com.example.submissionakhirfundamentalandroid.utilities.network



import com.example.submissionakhirfundamentalandroid.BuildConfig
import com.example.submissionakhirfundamentalandroid.utilities.libraries.Header
import com.example.submissionakhirfundamentalandroid.data.remote.service.GithubService
import okhttp3.CipherSuite
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


class RequestClient {

    private fun getHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val tlsEcdsa = CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256
        val tlsRsa = CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256
        val tlsDhe = CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256
        val spec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
            .tlsVersions(TlsVersion.TLS_1_2)
            .cipherSuites(tlsEcdsa, tlsRsa, tlsDhe)
            .build()

        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                val reqBuilder = request.newBuilder()
                reqBuilder.header("Accept", Header.HEADER_APP_JSON)
                reqBuilder.header("Content-Type", Header.HEADER_APP_JSON)
                reqBuilder.header("Authorization", "Bearer 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")

                val response = chain.proceed(reqBuilder.build())
                return@addInterceptor response.newBuilder().build()
            }
            .addInterceptor(interceptor)
            .build()
    }

    private fun getClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(getHttpClient())
            .build()
    }


    fun user(): GithubService {
        return getClient().create(GithubService::class.java)
    }
}