package farrukh.mathgame.nav_graph

sealed class Screens(val route: String) {
    object Splash : Screens("splash_screen")
//    object Home : Screens("home_screen")
    object Login : Screens("login_screen")
    object Registration : Screens("reg_screen")
    object Home : Screens("home_screen")
    object Chat : Screens("chat" + "/{key}")
    object Profile : Screens("profile_screen")
    object Settings : Screens("settings_screen")
}