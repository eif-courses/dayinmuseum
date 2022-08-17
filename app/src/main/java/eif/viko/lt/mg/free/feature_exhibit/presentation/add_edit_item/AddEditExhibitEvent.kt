package eif.viko.lt.mg.free.feature_exhibit.presentation.add_edit_item

import androidx.compose.ui.focus.FocusState

sealed class AddEditExhibitEvent {
    data class EnteredTitle(val value: String): AddEditExhibitEvent()
    data class ChangeTitleFocus(val focusState: FocusState): AddEditExhibitEvent()
    data class EnteredContent(val value: String): AddEditExhibitEvent()
    data class ChangeContentFocus(val focusState: FocusState): AddEditExhibitEvent()
    data class ChangeColor(val color: Int): AddEditExhibitEvent()
    object SaveExhibit: AddEditExhibitEvent()
}

