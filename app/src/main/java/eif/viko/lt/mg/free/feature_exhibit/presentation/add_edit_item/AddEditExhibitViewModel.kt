package eif.viko.lt.mg.free.feature_exhibit.presentation.add_edit_item

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eif.viko.lt.mg.free.feature_exhibit.domain.model.Exhibit
import eif.viko.lt.mg.free.feature_exhibit.domain.model.InvalidExhibitException
import eif.viko.lt.mg.free.feature_exhibit.domain.use_case.ExhibitUseCases
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditExhibitViewModel @Inject constructor(
    private val exhibitUseCases: ExhibitUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel(){

    private val _exhibitTitle = mutableStateOf(ExhibitTextFieldState(
        hint = "Enter title..."
    ))
    val exhibitTitle: State<ExhibitTextFieldState> = _exhibitTitle

    private val _exhibitContent = mutableStateOf(ExhibitTextFieldState(
        hint = "Enter some content..."
    ))
    val exhibitContent: State<ExhibitTextFieldState> = _exhibitContent

    private val _exhibitColor = mutableStateOf(Exhibit.exhibitColors.random().toArgb())
    val exhibitColor: State<Int> = _exhibitColor


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentExhibitId: Int? = null

    init {
        savedStateHandle.get<Int>("exhibitId")?.let{ exhibitId ->
            if(exhibitId != -1){
                viewModelScope.launch {
                    exhibitUseCases.getExhibit(exhibitId)?.also { exhibit ->
                        currentExhibitId = exhibit.id
                        _exhibitTitle.value = exhibitTitle.value.copy(
                            hint = exhibit.title,
                            isHintVisible = false
                        )
                        _exhibitContent.value = exhibitContent.value.copy(
                            hint = exhibit.content,
                            isHintVisible = false
                        )
                        _exhibitColor.value = exhibit.color
                    }
                }
            }
        }
    }


    fun onEvent(event: AddEditExhibitEvent){
        when(event){

            is AddEditExhibitEvent.EnteredTitle -> {
                _exhibitTitle.value = exhibitTitle.value.copy(
                    text = event.value
                )
            }
            is AddEditExhibitEvent.ChangeTitleFocus -> {
                _exhibitTitle.value = exhibitTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            exhibitTitle.value.text.isBlank()
                )
            }

            is AddEditExhibitEvent.EnteredContent -> {
                _exhibitContent.value = exhibitContent.value.copy(
                    text = event.value
                )
            }
            is AddEditExhibitEvent.ChangeContentFocus -> {
                _exhibitContent.value = exhibitContent.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            exhibitContent.value.text.isBlank()
                )
            }

            is AddEditExhibitEvent.ChangeColor -> {
                _exhibitColor.value = event.color
            }

            is AddEditExhibitEvent.SaveExhibit -> {
                viewModelScope.launch {
                    try {
                        exhibitUseCases.addExhibit(
                            Exhibit(
                                title = exhibitTitle.value.text,
                                content = exhibitTitle.value.text,
                                color = exhibitColor.value,
                                imageURL = "",
                                audioURL = "",
                                id = currentExhibitId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveExhibit)
                    }catch (e: InvalidExhibitException){
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save exhibit "
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvent{
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveExhibit: UiEvent()
    }
}