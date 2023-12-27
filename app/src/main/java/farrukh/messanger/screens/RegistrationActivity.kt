package farrukh.messanger.screens

import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Cyan
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import farrukh.mathgame.nav_graph.Screens
import farrukh.messanger.R
import farrukh.messanger.User
import farrukh.messanger.database.Database
import farrukh.messanger.ui.theme.WHITE


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Registration(navController: NavHostController) {
    var name by remember { mutableStateOf(TextFieldValue("")) }
    val context = LocalContext.current
    var username by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    val dataFetched = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current




    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WHITE),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
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
                painter = rememberAsyncImagePainter(R.drawable.gif_login, imageLoader),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.dp)
                    .padding(end = 30.dp, bottom = 30.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = name,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            onValueChange = { it ->
                name = it
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            shape = RoundedCornerShape(16.dp),
            placeholder = {
                Text(text = "name", fontSize = 14.sp)
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.AccountCircle,
                    contentDescription = "",
                    tint = Color.Black
                )
            },
            colors = TextFieldDefaults.textFieldColors(
//                textColor = Text2,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
//                cursorColor = Text2,
//                containerColor = Secondary
            ),
            textStyle = TextStyle(fontSize = 16.sp),

            )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = username,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            onValueChange = { it ->
                username = it
                            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            shape = RoundedCornerShape(16.dp),
            placeholder = {
                Text(text = "Username", fontSize = 14.sp)
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.AccountCircle,
                    contentDescription = "",
                    tint = Color.Black
                )
            },
            colors = TextFieldDefaults.textFieldColors(
//                textColor = Text2,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
//                cursorColor = Text2,
//                containerColor = Secondary
            ),
            textStyle = TextStyle(fontSize = 16.sp),

            )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = password,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            onValueChange = {
                password = it
                            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            shape = RoundedCornerShape(16.dp),
            placeholder = {
                Text(text = "Password", fontSize = 14.sp)
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Lock, contentDescription = "", tint = Color.Black
                )
            },
            colors = TextFieldDefaults.textFieldColors(
//                textColor = Text2,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
//                cursorColor = Text2,
//                containerColor = Secondary
            ),
            textStyle = TextStyle(fontSize = 16.sp),
           )

        Spacer(modifier = Modifier.height(42.dp))

        Button(
            enabled = name.text.isNotEmpty() &&  password.text.isNotEmpty() && username.text.isNotEmpty(),
            onClick = {
                focusManager.clearFocus(true)
                val user = User(username.text, password.text,name.text)
                Database.checkUser(user){
                    if (it){
                        Database.addUser(user) { success ->
                            if (success) {
                                Database.giveToken(context,user.username!!)
                                Toast.makeText(context, "Successfully signed up", Toast.LENGTH_SHORT).show()
                                navController.navigate(Screens.Home.route) {
                                    popUpTo(navController.graph.id) {
                                        inclusive = true
                                    }
                                }
                            }
                        }
                    }

                    else Toast.makeText(context, "you already have an account", Toast.LENGTH_SHORT).show()
                }


            },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Green
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 62.dp)
        ) {
            Text(
                text = "Sign up",
                fontSize = 14.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = { navController.navigate(Screens.Login.route) },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Cyan),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 62.dp)
        ) {
            Text(
                text = "Log in",
//                color = Text2,
                fontSize = 14.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )

        }


    }
}
