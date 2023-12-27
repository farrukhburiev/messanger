package farrukh.messanger.screens

import android.os.Build
import android.os.Build.VERSION.SDK_INT
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import farrukh.mathgame.nav_graph.Screens
import farrukh.messanger.R
import farrukh.messanger.database.Database
import farrukh.messanger.ui.theme.Primary
import kotlinx.coroutines.delay



@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun SplashScreen(navController: NavController) {
    val context = LocalContext.current
    LaunchedEffect(true) {
        delay(4000)
        if (Database.getToken(context) == "")
            navController.navigate(Screens.Registration.route) {
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
            }
        else {
            navController.navigate(Screens.Home.route) {
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
            }
        }
    }


    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    Image(
        painter = rememberAsyncImagePainter(R.drawable.splash, imageLoader),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
            .background(Primary)
    )

}