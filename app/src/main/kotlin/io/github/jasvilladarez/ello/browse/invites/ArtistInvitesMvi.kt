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

package io.github.jasvilladarez.ello.browse.invites

import io.github.jasvilladarez.ello.common.MviIntent
import io.github.jasvilladarez.ello.common.MviResult
import io.github.jasvilladarez.ello.common.MviViewState

internal sealed class ArtistInvitesIntent : MviIntent {

    object Load : ArtistInvitesIntent()

    data class LoadMore(
            val nextPageId: String?
    ) : ArtistInvitesIntent()
}

internal sealed class ArtistInvitesResult : MviResult {

    enum class ArtistInviteMode {
        LOAD, LOAD_MORE
    }

    data class Success(
            val artistInvites: List<ArtistInviteItem> = emptyList(),
            val nextPageId: String?,
            val mode: ArtistInviteMode
    ) : ArtistInvitesResult()

    data class Error(
            val error: Throwable
    ) : ArtistInvitesResult()

    data class InProgress(
            val mode: ArtistInviteMode
    ) : ArtistInvitesResult()
}

internal sealed class ArtistInvitesViewState : MviViewState {

    data class DefaultView(
            val artistInvites: List<ArtistInviteItem> = emptyList(),
            val nextPageId: String? = null
    ) : ArtistInvitesViewState()

    data class MoreView(
            val artistInvites: List<ArtistInviteItem> = emptyList(),
            val nextPageId: String? = null
    ) : ArtistInvitesViewState()

    data class ErrorView(
            val errorMessage: String?
    ) : ArtistInvitesViewState()

    object InitialLoadingView : ArtistInvitesViewState()

    object MoreLoadingView : ArtistInvitesViewState()
}