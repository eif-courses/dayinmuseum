package eif.viko.lt.mg.free.feature_exhibit.presentation.exhibits

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eif.viko.lt.mg.free.feature_exhibit.domain.model.Exhibit
import eif.viko.lt.mg.free.feature_exhibit.domain.use_case.ExhibitUseCases
import eif.viko.lt.mg.free.feature_exhibit.domain.util.ExhibitOrder
import eif.viko.lt.mg.free.feature_exhibit.domain.util.OrderType
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExhibitsViewModel @Inject constructor(
    private val exhibitUseCases: ExhibitUseCases
): ViewModel(){

    private val _state = mutableStateOf(ExhibitsState())

    val state: State<ExhibitsState> = _state

    private  var recentlyDeletedExhibit: Exhibit? = null

    private var getExhibitJob: Job? = null

    init {
        getExhibits(ExhibitOrder.Title(OrderType.Ascending))
    }

    fun onEvent(event: ExhibitsEvent){
        when(event){
            is ExhibitsEvent.Order -> {
                if(state.value.exhibitOrder::class == event.exhibitOrder::class &&
                        state.value.exhibitOrder.orderType == event.exhibitOrder.orderType){
                    return
                }
                getExhibits(event.exhibitOrder)
            }
            is ExhibitsEvent.DeleteExhibit -> {
                viewModelScope.launch {
                    exhibitUseCases.deleteExhibit(event.exhibit)
                    recentlyDeletedExhibit = event.exhibit
                }
            }
            is ExhibitsEvent.RestoreExhibit -> {
                viewModelScope.launch {
                    exhibitUseCases.addExhibit(recentlyDeletedExhibit?: return@launch)
                    recentlyDeletedExhibit = null
                }
            }
            is ExhibitsEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderAscending = !state.value.isOrderAscending
                )
            }
        }
    }
    private fun getExhibits(exhibitOrder: ExhibitOrder){
        getExhibitJob?.cancel()
        getExhibitJob = exhibitUseCases.getExhibits(exhibitOrder)
            .onEach { exhibits ->
                _state.value = state.value.copy(
                    exhibits = exhibits,
                    exhibitOrder = exhibitOrder
                )
            }
            .launchIn(viewModelScope)
    }
}