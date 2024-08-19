package com.sneha.network.api

import com.sneha.network.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyManagementException
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.util.Arrays
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager


/**
 * @author Created by Sneha on 15/08/2024
 */
object NetworkBuilder {

    /**
     * [getNetworkInstance] for getting instance of the network library
     * @param [baseUrl] Base URL for network operation
     * @param [headersMap] Headers for network operation (Optional)
     * @param [timeOutTime] Connection Time Out time
     */
    fun getNetworkInstance(
        baseUrl: String,
        headersMap: HashMap<String, String>? = null,
        timeOutTime: Long
    ): Retrofit {
        var sslSocketFactory: SSLSocketFactory? = null
        var trustManager: X509TrustManager? = null
        try {
            val trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm()
            )
            trustManagerFactory.init(null as KeyStore?)
            val trustManagers = trustManagerFactory.trustManagers
            if (trustManagers.size != 1 || trustManagers[0] !is X509TrustManager) {
                throw IllegalStateException(
                    "Unexpected default trust managers:${
                        Arrays.toString(
                            trustManagers
                        )
                    }"
                )
            }
            trustManager = trustManagers[0] as X509TrustManager
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, arrayOf<TrustManager?>(trustManager), null)
            sslSocketFactory = sslContext.socketFactory
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: KeyStoreException) {
            e.printStackTrace()
        } catch (e: KeyManagementException) {
            e.printStackTrace()
        }

        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(timeOutTime, TimeUnit.SECONDS)
        httpClient.readTimeout(timeOutTime, TimeUnit.SECONDS)
        httpClient.writeTimeout(timeOutTime, TimeUnit.SECONDS)
        if (sslSocketFactory != null && trustManager != null) {
            httpClient.sslSocketFactory(sslSocketFactory, trustManager)
        }
        httpClient.addInterceptor(getInterceptor(headersMap))

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.HEADERS
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(interceptor)
        }

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * [getInterceptor] Define the interceptor, add authentication headers
     * @param [hashMap] Headers for network operation
     */
    private fun getInterceptor(hashMap: HashMap<String, String>? = null): Interceptor {
        return Interceptor { chain ->
            val newRequest = chain.request().newBuilder()
            hashMap?.let {
                for (i in it) {
                    newRequest.addHeader(i.key, i.value)
                }
            }
            chain.proceed(newRequest.build())
        }
    }
}