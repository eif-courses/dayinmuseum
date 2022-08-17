package eif.viko.lt.mg.free.feature_exhibit.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import eif.viko.lt.mg.free.feature_exhibit.presentation.add_edit_item.components.AddEditExhibitScreen
import eif.viko.lt.mg.free.feature_exhibit.presentation.exhibits.components.ExhibitsScreen
import eif.viko.lt.mg.free.feature_exhibit.presentation.util.Screen
import eif.viko.lt.mg.free.ui.theme.DayinmuseumTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DayinmuseumTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.ExhibitScreen.route
                    ) {
                        composable(route = Screen.ExhibitScreen.route) {
                            ExhibitsScreen(navController = navController)
                        }
                        composable(
                            route = Screen.AddEditExhibitScreen.route +
                                    "?exhibitId={exhibitId}&exhibitColor={exhibitColor}",
                            arguments = listOf(
                                navArgument(
                                    name = "exhibitId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "exhibitColor"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                })
                        ) {
                            val color = it.arguments?.getInt("exhibitColor") ?: -1
                            AddEditExhibitScreen(
                                navController = navController,
                                exhibitBackgroundColor = color
                            )
                        }
                    }
                }
            }
        }
    }
}
