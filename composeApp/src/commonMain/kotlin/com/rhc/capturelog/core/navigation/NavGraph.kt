package com.rhc.capturelog.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.resources.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import capturelog.composeapp.generated.resources.Res
import capturelog.composeapp.generated.resources.back
import com.rhc.capturelog.features.dailynotes.DailyNotesScreen
import org.jetbrains.compose.resources.StringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaptureLogNavigation() {
    val navController = rememberNavController() // Or pass from a higher level
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.toRoute<Destination>()
    val toolbarTitle = currentDestination?.navigationTitleRes
    val isTopLevelDestination = currentDestination?.isTopLevelDestination() ?: false

    Scaffold(
        topBar = {
            CaptureLogTopAppBar(toolbarTitle, null, isTopLevelDestination) {
                navController.popBackStack()
            }
        },
        bottomBar = {
            CaptureLogBottomNavigationBar(
                navController = navController,
                currentDestination = currentDestination
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = SubGraph.NavigationMain
        ) {
            navigation<SubGraph.NavigationMain>(startDestination = Destination.Today) {
                composable<Destination.Today> {
                    DailyNotesScreen(paddingValues)
                }
                composable<Destination.Queue> {
//                    SocialScreen(coordinator)
                }
                composable<Destination.Settings> {
//                    SettingsScreen(coordinator)
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
private fun CaptureLogTopAppBar(
    title: StringResource?,
    subtitle: StringResource?,
    showBackButton: Boolean,
    onBackClicked: () -> Unit
) {
    TopAppBar(
        title = {
            title?.let {
                Text(
                    text = stringResource(it),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            subtitle?.let {
                Text(
                    text = stringResource(it),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }, // TODO add search icon
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
    else null
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