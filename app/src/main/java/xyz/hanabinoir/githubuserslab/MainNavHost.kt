package xyz.hanabinoir.githubuserslab

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import xyz.hanabinoir.githubuserslab.ui.screen.HomeScreen
import xyz.hanabinoir.githubuserslab.ui.screen.UserDetailScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavHost(navController: NavHostController) {
    var title by remember { mutableStateOf("GitHub Users") }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val route = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    if (route != "home") {
                        BackIcon {
                            navController.navigateUp()
                            title = "GitHub Users"
                        }
                    } else null
                },
                title = { Text(text = title) }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            NavHost(
                navController = navController,
                startDestination = "home"
            ) {
                composable("home") {
                    HomeScreen(
                        onUserClick = { username ->
                            navController.navigate("userDetail/$username")
                            title = username
                        }
                    )
                }
                composable(
                    route = "userDetail/{username}",
                    arguments = listOf(navArgument("username") { defaultValue = "" })
                ) { backStackEntry ->
                    val username = backStackEntry.arguments?.getString("username") ?: ""
                    UserDetailScreen(username = username)
                }
            }
        }
    }
}

@Composable
fun BackIcon(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.padding(start = 8.dp, end = 8.dp)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back"
        )
    }
}