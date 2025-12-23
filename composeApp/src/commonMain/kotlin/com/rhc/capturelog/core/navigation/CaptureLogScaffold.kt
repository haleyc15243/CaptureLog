package com.rhc.capturelog.core.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import org.jetbrains.compose.resources.stringResource
import capturelog.composeapp.generated.resources.Res
import capturelog.composeapp.generated.resources.back
import capturelog.composeapp.generated.resources.label_add_note
import capturelog.composeapp.generated.resources.label_search
import com.rhc.capturelog.core.state.AppViewModel
import com.rhc.capturelog.core.state.TopAppBarState
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaptureLogScaffold(
    navController: NavController,
    content: @Composable (PaddingValues) -> Unit
) {
    val appViewModel: AppViewModel = koinViewModel()
    val appState = appViewModel.uiState.collectAsStateWithLifecycle()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination: Destination? = remember(navBackStackEntry) {
        navBackStackEntry?.toDestinationOrNull()
    }
    currentDestination?.navigationTitleRes?.let { titleRes ->
        val title = stringResource(titleRes)
        LaunchedEffect(title) {
            appViewModel.setTitle(title)
        }
    }

    val isTopLevelDestination = currentDestination?.isTopLevelDestination() ?: false

    Scaffold(
        topBar = {
            CaptureLogTopAppBar(
                state = appState.value.topAppBarState,
                showBackButton = !isTopLevelDestination,
                onTopAppBarEvent = { appViewModel.emitAppStateEvent(it) },
                onBackClicked = { navController.popBackStack() }
            )
        },
        floatingActionButton = {
            if (appState.value.isFabVisible) {
                CaptureLogFAB { appViewModel.emitAppStateEvent(FABClicked) }
            }
        },
        bottomBar = {
            if (isTopLevelDestination) {
                CaptureLogBottomNavigationBar(
                    navController = navController,
                    currentDestination = currentDestination
                )
            }
        }
    ) { paddingValues ->
        content(paddingValues)
    }
}

@ExperimentalMaterial3Api
@Composable
private fun CaptureLogTopAppBar(
    state: TopAppBarState,
    showBackButton: Boolean,
    onTopAppBarEvent: (TopBarEvent) -> Unit,
    onBackClicked: () -> Unit
) {
    TopAppBar(
        title = {
            Column {
                state.title?.let {
                    Text(
                        text = it,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                state.subtitle?.let {
                    Text(
                        text = it,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        },
        actions = {
            when {
                state.showSearchTopBarIcon -> {
                    IconButton(
                        onClick = { onTopAppBarEvent(TopBarEvent.SearchClicked) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(Res.string.label_search)
                        )
                    }
                }
            }
        },
        navigationIcon = {
            CaptureLogNavigationIcon(showBackButton, onBackClicked)
        }
    )
}

@Composable
private fun CaptureLogNavigationIcon(
    showBackButton: Boolean,
    onClick: () -> Unit
) {
    if (showBackButton) {
        IconButton(
            onClick = onClick
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(Res.string.back)
            )
        }
    }
}

@Composable
private fun CaptureLogFAB(
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(Res.string.label_add_note)
        )
    }
}

@Composable
private fun CaptureLogBottomNavigationBar(
    navController: NavController,
    currentDestination: Destination?
) {
    NavigationBar {
        BottomNavigationItem.entries.forEach { item ->
            val destination = item.toDestination()
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = stringResource(item.label)
                    )
                },
                label = { Text(stringResource(item.label)) },
                selected = currentDestination?.toBottomNavigationItem() == item,
                onClick = {
                    navController.navigate(destination) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}