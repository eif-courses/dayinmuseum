package eif.viko.lt.mg.free.feature_exhibit.presentation.exhibits

import eif.viko.lt.mg.free.feature_exhibit.domain.model.Exhibit
import eif.viko.lt.mg.free.feature_exhibit.domain.util.ExhibitOrder
import eif.viko.lt.mg.free.feature_exhibit.domain.util.OrderType

data class ExhibitsState(
    val exhibit: List<Exhibit> = emptyList(),
    val exhibitOrder: ExhibitOrder = ExhibitOrder.Title(OrderType.Ascending),
    val isOrderAscending: Boolean = false
)
