/*
 * MIT License
 *
 * Copyright (c) 2018 Jasmine Villadarez
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.jasvilladarez.ello.domain

import io.github.jasvilladarez.ello.domain.entity.Token
import io.github.jasvilladarez.ello.domain.network.AuthHeader
import io.github.jasvilladarez.ello.domain.repository.auth.AuthApi
import io.github.jasvilladarez.ello.domain.repository.browse.BrowseApi
import io.github.jasvilladarez.ello.domain.util.gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * This object involves creating of initializing
 */
internal object ApiFactory {

    private const val ELLO_V2_PREFIX = "v2/"

    private const val ELLO_API_URL = "https://ello.co/api/"
    private const val DEFAULT_TIMEOUT = 120L

    private fun <T : Any> createApi(clazz: Class<T>,
                                    baseUrl: String = ELLO_API_URL,
                                    urlPrefix: String = "",
                                    isDebug: Boolean = false,
                                    vararg interceptors: Interceptor): T =
            createApi(clazz, baseUrl + urlPrefix,
                    makeOkHttpClient(makeLoggingInterceptor(isDebug),
                            *interceptors))

    private fun <T : Any> createApi(clazz: Class<T>,
                                    baseUrl: String,
                                    okHttpClient: OkHttpClient): T =
            Retrofit.Builder().baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(makeConverterFactory())
                    .build().create(clazz)

    private fun makeOkHttpClient(vararg interceptors: Interceptor) = OkHttpClient.Builder()
            .apply {
                interceptors.forEach {
                    addInterceptor(it)
                }
                connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            }.build()

    private fun makeLoggingInterceptor(isDebug: Boolean = false) = HttpLoggingInterceptor().apply {
        level = if (isDebug) HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE
    }

    private fun makeConverterFactory(): Converter.Factory =
            GsonConverterFactory.create(gson)

    internal fun createAuthApi(isDebug: Boolean = false,
                               baseUrl: String = ELLO_API_URL): AuthApi =
            ApiFactory.createApi(AuthApi::class.java, baseUrl,
                    isDebug = isDebug)

    internal fun createBrowseApi(token: Token,
                                 isDebug: Boolean = false,
                                 baseUrl: String = ELLO_API_URL): BrowseApi =
            ApiFactory.createApi(BrowseApi::class.java, baseUrl, ELLO_V2_PREFIX,
                    isDebug, AuthHeader(token))

}