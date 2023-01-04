package io.element.android.x.features.roomlist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import io.element.android.x.features.roomlist.model.RoomListEvents
import io.element.android.x.matrix.core.RoomId
import io.element.android.x.presentation.presenterConnector

class RoomListNode(
    buildContext: BuildContext,
    presenter: RoomListPresenter,
    private val onRoomClicked: (RoomId) -> Unit
) : Node(buildContext) {

    private val connector = presenterConnector(presenter)

    private fun updateFilter(filter: String) {
        connector.emitEvent(RoomListEvents.UpdateFilter(filter))
    }

    private fun updateVisibleRange(range: IntRange) {
        connector.emitEvent((RoomListEvents.UpdateVisibleRange(range)))
    }

    private fun logout() {
        connector.emitEvent(RoomListEvents.Logout)
    }

    @Composable
    override fun View(modifier: Modifier) {
        val state by connector.stateFlow.collectAsState()
        RoomListView(
            state = state,
            onRoomClicked = onRoomClicked,
            onFilterChanged = this::updateFilter,
            onScrollOver = this::updateVisibleRange,
            onLogoutClicked = this::logout
        )
    }
}
