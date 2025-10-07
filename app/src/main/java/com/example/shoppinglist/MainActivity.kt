package com.example.shoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.shoppinglist.component.ShoppingListScreen
import com.example.shoppinglist.ui.theme.ShoppingListTheme
import kotlinx.coroutines.launch
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.ui.unit.dp
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingListTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    //  Ubah judul TopBar sesuai halaman aktif
    val currentTitle = when (currentDestination?.route) {
        "home" -> "Shopping List"
        "profile" -> "Profile"
        "settings" -> "Settings"
        else -> "Shopping List"
    }

    // Drawer kiri (berisi menu Settings)
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "Menu",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp)
                )
                NavigationDrawerItem(
                    label = { Text("Settings") },
                    selected = currentDestination?.route == "settings",
                    onClick = {
                        scope.launch { drawerState.close() }
                        navigateSingleTopTo(navController, "settings")
                    },
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") }
                )
            }
        }
    ) {
        Scaffold(
            // Top bar dengan ikon drawer
            topBar = {
                TopAppBar(
                    title = { Text(currentTitle) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Open Drawer")
                        }
                    }
                )
            },

            //  Bottom navigation bar
            bottomBar = {
                NavigationBar {
                    val items = listOf(
                        BottomNavItem("home", Icons.Default.Home, "Shopping List"),
                        BottomNavItem("profile", Icons.Default.Person, "Profile")
                    )

                    items.forEach { item ->
                        val isSelected =
                            currentDestination?.hierarchy?.any { it.route == item.route } == true

                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { navigateSingleTopTo(navController, item.route) },
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) }
                        )
                    }
                }
            }
        ) { innerPadding ->
            //  Navigasi antar halaman + animasi transisi
            AnimatedNavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                composable(
                    "home",
                    enterTransition = { fadeIn(animationSpec = tween(800)) },
                    exitTransition = { fadeOut(animationSpec = tween(800)) }
                ) { ShoppingListScreen() }

                composable(
                    "profile",
                    enterTransition = { fadeIn(animationSpec = tween(800)) },
                    exitTransition = { fadeOut(animationSpec = tween(800)) }
                ) { ProfileScreen() }

                composable(
                    "settings",
                    enterTransition = { fadeIn(animationSpec = tween(800)) },
                    exitTransition = { fadeOut(animationSpec = tween(800)) }
                ) { SettingScreen() }
            }
        }
    }
}

// Fungsi bantu navigasi (biar tiap klik nggak tumpuk halaman)
private fun navigateSingleTopTo(navController: androidx.navigation.NavHostController, route: String) {
    navController.navigate(route) {
        launchSingleTop = true
        restoreState = true
        popUpTo(navController.graph.startDestinationId) { saveState = true }
    }
}

// Data class item navigasi bawah
data class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)
