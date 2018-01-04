/*
 * MIT License
 *
 * Copyright (c) 2017 Jasmine Villadarez
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

import io.github.jasvilladarez.domain.entity.ArtistInvite
import io.github.jasvilladarez.ello.util.fromHtml
import io.github.jasvilladarez.ello.util.toDate
import java.util.*

internal data class ArtistInviteItem(
        val title: String,
        val inviteType: String,
        val status: Status,
        val headerImageUrl: String?,
        val logoImageUrl: String?,
        val description: CharSequence?,
        val openedAt: Date,
        val closedAt: Date
) {
    enum class Status {
        UPCOMING, OPEN, SELECTING, CLOSED, PREVIEW
    }
}

internal fun ArtistInvite.mapToViewItem(): ArtistInviteItem = ArtistInviteItem(
        title,
        inviteType,
        ArtistInviteItem.Status.valueOf(status.name),
        headerImage.mdpi?.url,
        logoImage.optimized?.url,
        shortDescription.fromHtml(),
        openedAt.toDate(),
        closedAt.toDate()
)