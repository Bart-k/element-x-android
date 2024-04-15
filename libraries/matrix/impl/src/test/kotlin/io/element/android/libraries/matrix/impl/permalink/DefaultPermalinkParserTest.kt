/*
 * Copyright (c) 2024 New Vector Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.element.android.libraries.matrix.impl.permalink

import com.google.common.truth.Truth.assertThat
import io.element.android.libraries.matrix.api.core.EventId
import io.element.android.libraries.matrix.api.core.RoomId
import io.element.android.libraries.matrix.api.core.UserId
import io.element.android.libraries.matrix.api.permalink.PermalinkData
import kotlinx.collections.immutable.persistentListOf
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DefaultPermalinkParserTest {
    @Test
    fun `parsing an invalid url returns a fallback link`() {
        val sut = DefaultPermalinkParser(
            matrixToConverter = DefaultMatrixToConverter(),
        )
        val url = "https://element.io"
        assertThat(sut.parse(url)).isInstanceOf(PermalinkData.FallbackLink::class.java)
    }

    @Test
    fun `parsing an invalid url with the right path but no content returns a fallback link`() {
        val sut = DefaultPermalinkParser(
            matrixToConverter = DefaultMatrixToConverter(),
        )
        val url = "https://app.element.io/#/user"
        assertThat(sut.parse(url)).isInstanceOf(PermalinkData.FallbackLink::class.java)
    }

    @Test
    fun `parsing an invalid url with the right path but empty content returns a fallback link`() {
        val sut = DefaultPermalinkParser(
            matrixToConverter = DefaultMatrixToConverter(),
        )
        val url = "https://app.element.io/#/user/"
        assertThat(sut.parse(url)).isInstanceOf(PermalinkData.FallbackLink::class.java)
    }

    @Test
    fun `parsing an invalid url with the right path but invalid content returns a fallback link`() {
        val sut = DefaultPermalinkParser(
            matrixToConverter = DefaultMatrixToConverter(),
        )
        val url = "https://app.element.io/#/user/some%20user!"
        assertThat(sut.parse(url)).isInstanceOf(PermalinkData.FallbackLink::class.java)
    }

    @Test
    fun `parsing a valid user url returns a user link`() {
        val sut = DefaultPermalinkParser(
            matrixToConverter = DefaultMatrixToConverter(),
        )
        val url = "https://app.element.io/#/user/@test:matrix.org"
        assertThat(sut.parse(url)).isEqualTo(
            PermalinkData.UserLink(
                userId = UserId("@test:matrix.org")
            )
        )
    }

    @Test
    fun `parsing a valid room id url returns a room link`() {
        val sut = DefaultPermalinkParser(
            matrixToConverter = DefaultMatrixToConverter(),
        )
        val url = "https://app.element.io/#/room/!aBCD1234:matrix.org"
        assertThat(sut.parse(url)).isEqualTo(
            PermalinkData.RoomIdLink(
                roomId = RoomId("!aBCD1234:matrix.org"),
                viaParameters = persistentListOf(),
            )
        )
    }

    @Test
    fun `parsing a valid room id with event id url returns a room link`() {
        val sut = DefaultPermalinkParser(
            matrixToConverter = DefaultMatrixToConverter(),
        )
        val url = "https://app.element.io/#/room/!aBCD1234:matrix.org/$1234567890abcdef:matrix.org"
        assertThat(sut.parse(url)).isEqualTo(
            PermalinkData.EventIdLink(
                roomId = RoomId("!aBCD1234:matrix.org"),
                eventId = EventId("$1234567890abcdef:matrix.org"),
                viaParameters = persistentListOf(),
            )
        )
    }

    @Test
    fun `parsing a valid room id with and invalid event id url returns a room link with no event id`() {
        val sut = DefaultPermalinkParser(
            matrixToConverter = DefaultMatrixToConverter(),
        )
        val url = "https://app.element.io/#/room/!aBCD1234:matrix.org/1234567890abcdef:matrix.org"
        assertThat(sut.parse(url)).isEqualTo(
            PermalinkData.RoomIdLink(
                roomId = RoomId("!aBCD1234:matrix.org"),
                viaParameters = persistentListOf(),
            )
        )
    }

    @Test
    fun `parsing a valid room id with event id and via parameters url returns a room link`() {
        val sut = DefaultPermalinkParser(
            matrixToConverter = DefaultMatrixToConverter(),
        )
        val url = "https://app.element.io/#/room/!aBCD1234:matrix.org/$1234567890abcdef:matrix.org?via=matrix.org&via=matrix.com"
        assertThat(sut.parse(url)).isEqualTo(
            PermalinkData.EventIdLink(
                roomId = RoomId("!aBCD1234:matrix.org"),
                eventId = EventId("$1234567890abcdef:matrix.org"),
                viaParameters = persistentListOf("matrix.org", "matrix.com"),
            )
        )
    }

    @Test
    fun `parsing a valid room alias url returns a room link`() {
        val sut = DefaultPermalinkParser(
            matrixToConverter = DefaultMatrixToConverter(),
        )
        val url = "https://app.element.io/#/room/#element-android:matrix.org"
        assertThat(sut.parse(url)).isEqualTo(
            PermalinkData.RoomAliasLink(
                roomAlias = "#element-android:matrix.org",
                viaParameters = persistentListOf(),
            )
        )
    }

    @Test
    fun `parsing a valid room alias with eventId url returns a room link`() {
        val sut = DefaultPermalinkParser(
            matrixToConverter = DefaultMatrixToConverter(),
        )
        val url = "https://app.element.io/#/room/#element-android:matrix.org/$1234567890abcdef:matrix.org"
        assertThat(sut.parse(url)).isEqualTo(
            PermalinkData.EventIdAliasLink(
                roomAlias = "#element-android:matrix.org",
                eventId = EventId("$1234567890abcdef:matrix.org"),
                viaParameters = persistentListOf(),
            )
        )
    }

    @Test
    fun `parsing a url with an invalid signurl returns a fallback link`() {
        val sut = DefaultPermalinkParser(
            matrixToConverter = DefaultMatrixToConverter(),
        )
        // This url has no private key
        val url = "https://app.element.io/#/room/%21aBCDEF12345%3Amatrix.org" +
            "?email=testuser%40element.io" +
            "&signurl=https%3A%2F%2Fvector.im%2F_matrix%2Fidentity%2Fapi%2Fv1%2Fsign-ed25519%3Ftoken%3Da_token" +
            "&room_name=TestRoom" +
            "&room_avatar_url=" +
            "&inviter_name=User" +
            "&guest_access_token=" +
            "&guest_user_id=" +
            "&room_type="
        assertThat(sut.parse(url)).isInstanceOf(PermalinkData.FallbackLink::class.java)
    }

    @Test
    fun `parsing a url with signurl returns a room email invite link`() {
        val sut = DefaultPermalinkParser(
            matrixToConverter = DefaultMatrixToConverter(),
        )
        val url = "https://app.element.io/#/room/%21aBCDEF12345%3Amatrix.org" +
            "?email=testuser%40element.io" +
            "&signurl=https%3A%2F%2Fvector.im%2F_matrix%2Fidentity%2Fapi%2Fv1%2Fsign-ed25519%3Ftoken%3Da_token%26private_key%3Da_private_key" +
            "&room_name=TestRoom" +
            "&room_avatar_url=" +
            "&inviter_name=User" +
            "&guest_access_token=" +
            "&guest_user_id=" +
            "&room_type="
        assertThat(sut.parse(url)).isEqualTo(
            PermalinkData.RoomEmailInviteLink(
                roomId = RoomId("!aBCDEF12345:matrix.org"),
                email = "testuser@element.io",
                signUrl = "https://vector.im/_matrix/identity/api/v1/sign-ed25519?token=a_token&private_key=a_private_key",
                roomName = "TestRoom",
                roomAvatarUrl = "",
                inviterName = "User",
                identityServer = "vector.im",
                token = "a_token",
                privateKey = "a_private_key",
                roomType = "",
            )
        )
    }
}
