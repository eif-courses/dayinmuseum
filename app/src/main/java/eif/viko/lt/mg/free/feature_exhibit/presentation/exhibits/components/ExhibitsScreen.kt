package eif.viko.lt.mg.free.feature_exhibit.presentation.exhibits.components

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eif.viko.lt.mg.free.feature_exhibit.presentation.exhibits.ExhibitsEvent
import eif.viko.lt.mg.free.feature_exhibit.presentation.exhibits.ExhibitsViewModel
import eif.viko.lt.mg.free.feature_exhibit.presentation.util.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExhibitsScreen(
    navController: NavController,
    viewModel: ExhibitsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    // SOLUTION FOR MATERIAL DESIGN 3
    // https://androiderrors.com/show-snackbar-in-material-design-3-using-scafffold/
    val snackbarHostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditExhibitScreen.route)
                },
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Exhibit")
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Your exhibit",
                    style = MaterialTheme.typography.headlineMedium
                )
                IconButton(
                    onClick = {
                        viewModel.onEvent(ExhibitsEvent.ToggleOrderSection)
                    },
                ) {
                    Icon(imageVector = Icons.Default.ThumbUp, contentDescription = "Sort")
                }
                AnimatedVisibility(
                    visible = state.isOrderAscending,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    OrderSection(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        exhibitOrder = state.exhibitOrder,
                        onOrderChange = {
                            viewModel.onEvent(ExhibitsEvent.Order(it))
                        }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.exhibits) { exhibit ->
                        ExhibitItem(
                            exhibit = exhibit,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate(
                                        Screen.AddEditExhibitScreen.route +
                                                "?exhibitId=${exhibit.id}"
                                    )
                                },
                            onDeleteClick = {
                                viewModel.onEvent(ExhibitsEvent.DeleteExhibit(exhibit))
                                scope.launch {
                                    val result = snackbarHostState.showSnackbar(
                                        "Exhibit deleted",
                                        actionLabel = "Undo"
                                    )
                                    if (result == SnackbarResult.ActionPerformed) {
                                        viewModel.onEvent(ExhibitsEvent.RestoreExhibit)
                                    }
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}