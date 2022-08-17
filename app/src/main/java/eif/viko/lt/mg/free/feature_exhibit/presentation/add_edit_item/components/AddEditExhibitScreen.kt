package eif.viko.lt.mg.free.feature_exhibit.presentation.add_edit_item.components

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eif.viko.lt.mg.free.feature_exhibit.domain.model.Exhibit
import eif.viko.lt.mg.free.feature_exhibit.presentation.add_edit_item.AddEditExhibitEvent
import eif.viko.lt.mg.free.feature_exhibit.presentation.add_edit_item.AddEditExhibitViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditExhibitScreen(
    navController: NavController,
    viewModel: AddEditExhibitViewModel = hiltViewModel(),
    exhibitBackgroundColor: Int
) {
    val titleState = viewModel.exhibitTitle.value
    val contentState = viewModel.exhibitContent.value
    val exhibitBackgroundAnimatable = remember {
        Animatable(
            Color(if (exhibitBackgroundColor != -1) exhibitBackgroundColor else viewModel.exhibitColor.value)
        )
    }

    val scope = rememberCoroutineScope()

    // SOLUTION FOR MATERIAL DESIGN 3
    // https://androiderrors.com/show-snackbar-in-material-design-3-using-scafffold/
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditExhibitViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is AddEditExhibitViewModel.UiEvent.SaveExhibit -> {
                    navController.navigateUp()
                }
            }
        }
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditExhibitEvent.SaveExhibit)
                },
                //backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Save Exhibit")
            }
        },
        //scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(exhibitBackgroundAnimatable.value)
                .padding(it)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Exhibit.exhibitColors.forEach { color ->
                    val colorInt = color.toArgb()
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp,
                                color = if (viewModel.exhibitColor.value == colorInt) {
                                    Color.Black
                                } else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    exhibitBackgroundAnimatable.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                viewModel.onEvent(AddEditExhibitEvent.ChangeColor(colorInt))
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = titleState.text,
                hint = titleState.hint,
                onValueChange = { itx ->
                    viewModel.onEvent(AddEditExhibitEvent.EnteredTitle(itx))
                },
                onFocusChange = { itx ->
                    viewModel.onEvent(AddEditExhibitEvent.ChangeTitleFocus(itx))
                },
                isHintVisible = titleState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = contentState.text,
                hint = contentState.hint,
                onValueChange = { itx ->
                    viewModel.onEvent(AddEditExhibitEvent.EnteredContent(itx))
                },
                onFocusChange = { itx ->
                    viewModel.onEvent(AddEditExhibitEvent.ChangeContentFocus(itx))
                },
                isHintVisible = contentState.isHintVisible,
                textStyle = MaterialTheme.typography.displayMedium,
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}