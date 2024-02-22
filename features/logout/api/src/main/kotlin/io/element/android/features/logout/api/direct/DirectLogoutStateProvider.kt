/*
 * Copyright (c) 2023 New Vector Ltd
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

package io.element.android.features.logout.api.direct

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.element.android.libraries.architecture.AsyncAction

open class DirectLogoutStateProvider : PreviewParameterProvider<DirectLogoutState> {
    override val values: Sequence<DirectLogoutState>
        get() = sequenceOf(
            aDirectLogoutState(),
            aDirectLogoutState(logoutAction = AsyncAction.Confirming),
            aDirectLogoutState(logoutAction = AsyncAction.Loading),
            aDirectLogoutState(logoutAction = AsyncAction.Failure(Exception("Error"))),
            aDirectLogoutState(logoutAction = AsyncAction.Success("success")),
        )
}

fun aDirectLogoutState(
    canDoDirectSignOut: Boolean = true,
    logoutAction: AsyncAction<String?> = AsyncAction.Uninitialized,
    eventSink: (DirectLogoutEvents) -> Unit = {},
) = DirectLogoutState(
    canDoDirectSignOut = canDoDirectSignOut,
    logoutAction = logoutAction,
    eventSink = eventSink,
)
