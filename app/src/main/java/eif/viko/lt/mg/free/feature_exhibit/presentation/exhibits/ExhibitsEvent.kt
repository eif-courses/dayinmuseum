package eif.viko.lt.mg.free.feature_exhibit.presentation.exhibits

import eif.viko.lt.mg.free.feature_exhibit.domain.model.Exhibit
import eif.viko.lt.mg.free.feature_exhibit.domain.util.ExhibitOrder

sealed class ExhibitsEvent{
    data class Order(val exhibitOrder: ExhibitOrder): ExhibitsEvent()
    data class DeleteExhibit(val exhibit: Exhibit): ExhibitsEvent()
    object RestoreExhibit: ExhibitsEvent()
    object ToggleOrderSection: ExhibitsEvent()
}
