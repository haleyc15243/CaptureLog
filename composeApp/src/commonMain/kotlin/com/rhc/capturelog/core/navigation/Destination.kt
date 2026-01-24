package com.rhc.capturelog.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.toRoute
import capturelog.composeapp.generated.resources.Res
import capturelog.composeapp.generated.resources.label_queue
import capturelog.composeapp.generated.resources.label_settings
import capturelog.composeapp.generated.resources.label_today
import com.rhc.capturelog.core.navigation.Destination.Queue
import com.rhc.capturelog.core.navigation.Destination.Settings
import com.rhc.capturelog.core.navigation.Destination.Today
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.StringResource

/**
 * Subclasses of [SubGraph] are each their own navigation graph.
 */
sealed class SubGraph {

    /**
     * Nested graph for Onboarding
     */
    @Serializable
    data object OnboardingGraph : SubGraph()

    /**
     * Nested graph for top level destinations
     * All Sub Classes need to be added to the module
     */
    @Serializable
    data object NavigationMain : SubGraph()
}

/**
 * Subclasses of [Destination] are each their own destination. All destinations are together regardless of whether
 * they shared the same navigation graph.
 */
@Serializable
sealed class Destination {
    abstract val navigationTitleRes: StringResource?

    companion object {
        /**
         * List of the top level destinations on the Main Navigation graph for easy access.
         */
        val topLevelDestinations: List<Destination>
            get() = listOf(
                Today,
                Queue,
                Settings()
            )

        val allDestinations: List<Destination>
            get() = listOf(
                Onboarding,
                Today,
                Queue,
                Settings(),
            )
    }

    @Serializable
    @SerialName("onboarding")
    data object Onboarding : Destination() {
        override val navigationTitleRes: StringResource?
            get() = null
    }

    /**
     * Top level destination for fantasy.
     */
    @Serializable
    @SerialName("today")
    data object Today : Destination() {
        override val navigationTitleRes
            get() = Res.string.label_today
    }

    @Serializable
    @SerialName("queue")
    data object Queue : Destination() {
        override val navigationTitleRes
            get() = Res.string.label_queue
    }


    @Serializable
    @SerialName("settings")
    data class Settings(val currentFilter: String = Filter.NONE.name) : Destination() {
        constructor(filter: Filter) : this(filter.name)

        override val navigationTitleRes
            get() = Res.string.label_settings

        enum class Filter {
            NONE,
            LICENSES
        }
    }
}

fun Destination.isTopLevelDestination() = this in Destination.topLevelDestinations

enum class BottomNavigationItem(val label: StringResource, val icon: ImageVector, val route: Destination) {
    TODAY(Res.string.label_today, Icons.Default.Home, Today),
    QUEUE(Res.string.label_queue, Icons.AutoMirrored.Filled.List, Queue),
    SETTINGS(Res.string.label_settings, Icons.Default.Settings, Settings())
}

fun BottomNavigationItem.toDestination() = this.route

fun Destination.toBottomNavigationItem() = when (this) {
    Today -> BottomNavigationItem.TODAY
    Queue -> BottomNavigationItem.QUEUE
    is Settings -> BottomNavigationItem.SETTINGS
    else -> throw IllegalArgumentException("Unknown bottom nav destination: $this")
}

inline fun <reified T : Any> NavBackStackEntry.toDestinationOrNull(): T? {
    val dest = destination
    val matchedDestination = Destination.allDestinations.find { destination ->
        dest.hasRoute(destination::class)
    } ?: return null

    return this.toRoute(matchedDestination::class) as? T
}