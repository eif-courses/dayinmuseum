package eif.viko.lt.mg.free.feature_exhibit.domain.use_case

import eif.viko.lt.mg.free.feature_exhibit.domain.model.Exhibit
import eif.viko.lt.mg.free.feature_exhibit.domain.repository.ExhibitRepository
import eif.viko.lt.mg.free.feature_exhibit.domain.util.ExhibitOrder
import eif.viko.lt.mg.free.feature_exhibit.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetExhibits(
    private val repository: ExhibitRepository
) {
    operator fun invoke(
        exhibitOrder: ExhibitOrder = ExhibitOrder.Title(OrderType.Ascending)
    ): Flow<List<Exhibit>> {
        return repository.getExhibits().map { exhibits ->
            when (exhibitOrder.orderType) {
                is OrderType.Ascending -> {
                    when (exhibitOrder) {
                        is ExhibitOrder.Title -> exhibits.sortedBy { it.title.lowercase() }
                    }
                }
                is OrderType.Descending -> {
                    when (exhibitOrder) {
                        is ExhibitOrder.Title -> exhibits.sortedByDescending { it.title.lowercase() }
                    }
                }
            }
        }
    }
}