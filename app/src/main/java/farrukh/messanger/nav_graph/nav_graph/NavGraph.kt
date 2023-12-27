package farrukh.mathgame

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import farrukh.mathgame.nav_graph.Screens
import farrukh.messanger.screens.ChatScreen
import farrukh.messanger.screens.Home
import farrukh.messanger.screens.Login
import farrukh.messanger.screens.ProfileScreen
import farrukh.messanger.screens.Registration
import farrukh.messanger.screens.SettingsScreen
import farrukh.messanger.screens.SplashScreen


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.Splash.route
    )
    {
        composable(route = Screens.Splash.route)
             {
            SplashScreen(navController)
        }
        composable(route = Screens.Home.route) {
            Home(navController)
        }
//        composable(route = Screens.Home.route){
//            HomeScreen()
//        }

        composable(route = Screens.Profile.route) {
            ProfileScreen(navController)
        }
        composable(route = Screens.Login.route) {
            Login(navController)
        }
        composable(route = Screens.Registration.route) {
            Registration(navController)

        }

        composable(route = Screens.Settings.route) {
            SettingsScreen(navController)

        }

        composable(route = Screens.Chat.route, arguments = listOf(navArgument("key") {
            type = NavType.StringType
        })
        ) {
            ChatScreen(navController = navController, it.arguments?.getString("key")!!)
        }
    }
}