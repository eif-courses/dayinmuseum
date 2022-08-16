package eif.viko.lt.mg.free.feature_exhibit.domain.util


sealed class OrderType{
    object Ascending: OrderType()
    object Descending: OrderType()
}
