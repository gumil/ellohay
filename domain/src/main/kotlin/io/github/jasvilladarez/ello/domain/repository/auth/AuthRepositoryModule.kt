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

package io.github.jasvilladarez.ello.domain.repository.auth

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import io.github.jasvilladarez.ello.domain.ApiFactory
import io.github.jasvilladarez.ello.domain.BuildConfig
import io.github.jasvilladarez.ello.domain.entity.Token
import io.github.jasvilladarez.ello.domain.preference.auth.AuthPreference
import io.github.jasvilladarez.ello.domain.preference.auth.AuthPreferenceImpl

@Module
class AuthRepositoryModule {

    @Provides
    internal fun providesAuthApi(): AuthApi = ApiFactory.createAuthApi(BuildConfig.DEBUG)

    @Provides
    internal fun providesAuthPreference(sharedPreferences: SharedPreferences): AuthPreference =
            AuthPreferenceImpl(sharedPreferences)

    @Provides
    internal fun providesAuthRepository(authApi: AuthApi,
                                        authPreference: AuthPreference,
                                        token: Token): AuthRepository =
            AuthRepositoryImpl(authApi, authPreference, token)
}