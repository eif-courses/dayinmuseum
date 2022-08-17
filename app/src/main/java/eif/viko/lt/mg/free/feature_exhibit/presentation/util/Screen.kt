package eif.viko.lt.mg.free.feature_exhibit.presentation.util

sealed class Screen(val route: String){
    object ExhibitScreen: Screen("exhibits_screen")
    object AddEditExhibitScreen: Screen("add_edit_exhibit")
}