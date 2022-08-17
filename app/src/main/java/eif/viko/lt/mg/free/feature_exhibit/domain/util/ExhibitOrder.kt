package eif.viko.lt.mg.free.feature_exhibit.domain.util

sealed class ExhibitOrder(val orderType: OrderType){
    class Title(orderType: OrderType): ExhibitOrder(orderType)
    // More sorting types if you need it

    fun copy(orderType: OrderType):ExhibitOrder{
        return when(this){
            is Title -> Title(orderType)
        }
    }
}
