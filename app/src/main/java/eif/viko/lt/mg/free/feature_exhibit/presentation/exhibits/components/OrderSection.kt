package eif.viko.lt.mg.free.feature_exhibit.presentation.exhibits.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import eif.viko.lt.mg.free.feature_exhibit.domain.util.ExhibitOrder
import eif.viko.lt.mg.free.feature_exhibit.domain.util.OrderType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    exhibitOrder: ExhibitOrder = ExhibitOrder.Title(OrderType.Ascending),
    onOrderChange: (ExhibitOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            DefaultRadioButton(
                text = "Ascending",
                selected = exhibitOrder is ExhibitOrder.Title,
                onSelect = { onOrderChange(exhibitOrder.copy(OrderType.Ascending)) }
            )
            Spacer(modifier = Modifier.width(8.dp))

            DefaultRadioButton(
                text = "Descending",
                selected = exhibitOrder is ExhibitOrder.Title,
                onSelect = { onOrderChange(exhibitOrder.copy(OrderType.Descending)) }
            )
        }
    }
}