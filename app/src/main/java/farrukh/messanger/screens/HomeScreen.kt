package farrukh.messanger.screens

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import farrukh.mathgame.nav_graph.Screens
import farrukh.messanger.Message
import farrukh.messanger.R
import farrukh.messanger.database.Database
import farrukh.messanger.User
import farrukh.messanger.ui.theme.PurpleGrey40
import farrukh.messanger.ui.theme.WHITE


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavHostController) {
//    val context  = LocalContext.current
    var u = mutableListOf<User>()
    var m = mutableListOf<Message>()
    var users by remember { mutableStateOf(u) }
    var messages by remember { mutableStateOf(m) }
    val context  = LocalContext.current
    val from_user = Database.getToken(context)

    val dataFetched = remember { mutableStateOf(false) }

    Database.getUsers(from_user) {
        users = it.toMutableList()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WHITE),
//        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//            Icon(
//                imageVector = Icons.Rounded.Face,
//                contentDescription = "Person Icon",
//                Modifier
//                    .size(60.dp)
//                    .padding(start = 30.dp)
//                    .clickable {
//                        navController.navigate(Screens.Profile.route)
//                    },
//                tint = Color.Black,
//            )
            val imageLoader = ImageLoader.Builder(LocalContext.current)
                .components {
                    if (Build.VERSION.SDK_INT >= 28) {
                        add(ImageDecoderDecoder.Factory())
                    } else {
                        add(GifDecoder.Factory())
                    }
                }
                .build()
            if (!dataFetched.value) Box(
            ) {
                Image(
                    painter = rememberAsyncImagePainter(R.drawable.profile, imageLoader),
                    contentDescription = null,

                    modifier =
                    Modifier
                        .size(80.dp)
                        .padding(start = 30.dp)
                        .clickable {
                            navController.navigate(Screens.Profile.route)
                        },
                )
            }

            Image(
                painter = rememberAsyncImagePainter(R.drawable.catalyst, imageLoader),
                contentDescription = null,

                modifier =
                Modifier
                    .size(80.dp)
                    .padding(end = 30.dp, top = 10.dp)
                    .clickable {
                        Toast
                            .makeText(context, "coming soon", Toast.LENGTH_SHORT)
                            .show()
                    },
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 17.dp)
//messages[messages.size].body.toString()
        ) {

            items(users) { item ->

                val last_msg = remember {
                    mutableStateOf(Message("","","",""))
                }
                Database.getLastMessage(from_user, item.username.toString()) { it ->
                    last_msg.value = it
//                    last_msg.value.date = it.date!!
                }

                Log.d("TAG", "Home: $last_msg")
                ChatItem(item, last_msg, navController)
            }
        }
    }
}


@Composable
fun ChatItem(name: User, last_msg: MutableState<Message>, navController: NavController) {



    val context  = LocalContext.current
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                navController.navigate("chat/${name.username}")
            },
        verticalAlignment = Alignment.CenterVertically) {
//        Icon(
//            imageVector = R.drawable.icon,
//            contentDescription = "Person Icon",
//            Modifier.size(32.dp),
//            tint = Color.Black,
//        )

        Image(
            painter = rememberAsyncImagePainter(R.drawable.img),
            contentDescription = null,

            modifier =
            Modifier
                .size(40.dp)
                .clickable {
//                    navController.navigate(Screens.Profile.route)
                },
        )
        Column(verticalArrangement = Arrangement.Center) {
            Text(fontSize = 24.sp, text = name.fullName.toString(), modifier = Modifier
                .padding(start = 15.dp)
                .wrapContentHeight())
//            Spacer(modifier = Modifier.height(16.dp).width(8.dp))
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                Text(fontSize = 12.sp, text = last_msg.value.body.toString(), color = PurpleGrey40, modifier = Modifier.padding(start = 18.dp,top = 5.dp))
//                Text(fontSize = 12.sp, text = last_msg.value.date.toString(), color = PurpleGrey40, modifier = Modifier.padding(end = 18.dp,top = 10.dp))

            }
             }

    }
    Divider(modifier = Modifier.fillMaxWidth(), thickness = 0.5.dp, color = Color(108, 120, 131))
}
