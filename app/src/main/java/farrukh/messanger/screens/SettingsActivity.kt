package farrukh.messanger.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import farrukh.mathgame.nav_graph.Screens
import farrukh.messanger.User
import farrukh.messanger.database.Database


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController:NavController) {
    val context = LocalContext.current


    var users by remember { mutableStateOf<List<User>>(emptyList()) }

    var user = remember {
        mutableStateOf(User("", "", ""))
    }

    var from = Database.getToken(context)



    for (i in users){
        if (i.username == from){
            user.value = i
        }
    }

    var name by remember { mutableStateOf(TextFieldValue(user.value.fullName.toString())) }

    var username by remember { mutableStateOf(TextFieldValue(user.value.username.toString())) }
    var password by remember { mutableStateOf(TextFieldValue(user.value.password.toString())) }


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        OutlinedTextField(value = name, onValueChange = {
            name = it
        },
            label = { Text(text = "Enter your name") },
            placeholder = { Text(text = "full name") }
        )
        OutlinedTextField(value = username, onValueChange = {
            username = it
        },
            label = { Text(text = "Enter your username") },
            placeholder = { Text(text = "username") }
        )

        OutlinedTextField(value = password, onValueChange = {
            password = it
        },
            label = { Text(text = "Enter your password") },
            placeholder = { Text(text = "password") }
        )

        Button(onClick = {
            Database.giveToken(context, "")
            navController.navigate(Screens.Login.route)
        }) {
            Text(text = "Log out")
        }
    }

}