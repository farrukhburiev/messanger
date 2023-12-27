package farrukh.messanger.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import farrukh.mathgame.nav_graph.Screens
import farrukh.messanger.Message
import farrukh.messanger.database.Database
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navController: NavController, key: String) {
    var msg by remember { mutableStateOf(TextFieldValue("")) }
    val context = LocalContext.current
    var from = Database.getToken(context)
//    var messages = remember { mutableListOf(farrukh.messanger.Message()) }
    var messages by remember { mutableStateOf(emptyList<Message>()) }

//
    Database.getMessages(from,key) { list -> messages = list.toMutableList() }
    Log.d("TAG", "ChatScreen: "+messages.joinToString())
    Scaffold(containerColor = Color(14, 22, 33), topBar = {
        TopAppBar(colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color(
                23, 33, 43
            )
        ), title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { navController.navigate(Screens.Home.route) }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back Icon",
                        tint = Color.White,
                    )
                }
//                Text(text = name, color = Color.White)
            }
        })
    }, bottomBar = {
        BottomAppBar(containerColor = Color(23, 33, 43)) {
            OutlinedTextField(
                colors = androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.White
                ),
                value = msg,
                onValueChange = {
                    msg = it
                },
                label = { Text(text = "Enter your Message") },
                placeholder = { Text(text = "message") },
            )
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = {
                    val currentDateTime = LocalDateTime.now()
                    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                    val formattedDateTime = currentDateTime.format(formatter)


                    var message = farrukh.messanger.Message(from, key, msg.text, formattedDateTime)


                    Database.sendMessage(message)


//                    msg = TextFieldValue()
                }) {
                    Icon(
                        modifier = Modifier.size(35.dp),
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send Icon",
                        tint = Color(108, 120, 131),
                    )
                }
            }
        }
    }) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            items(messages) {
                it.date?.let { it1 ->
                    it.body?.let { it2 ->
                        MessegeItem(
                            it2,
                            it1,
                            position = from == it.from
                        )
                    }
                }
            }
        }
    }


}

@Composable
fun MessegeItem(msg: String, time: String, position: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
    ) {
        if (position) {
            Spacer(modifier = Modifier.weight(1f))
            Column(horizontalAlignment = Alignment.End) {
                Surface(
                    modifier = Modifier
                        .background(Color(43, 82, 120), RoundedCornerShape(33))
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        modifier = Modifier.background(Color(43, 82, 120)),
                        fontSize = 25.sp,
                        text = msg,
                        color = Color.White,
                    )
                }
                Text(
                    modifier = Modifier.padding(top = 3.dp),
                    fontSize = 17.sp,
                    text = time,
                    color = Color.White
                )
            }
        } else {
            Column(horizontalAlignment = Alignment.Start) {
                Surface(
                    modifier = Modifier
                        .background(Color(24, 37, 51), RoundedCornerShape(33))
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        modifier = Modifier.background(Color(24, 37, 51)),
                        fontSize = 25.sp,
                        text = msg,
                        color = Color.White,
                    )
                }
                Text(
                    modifier = Modifier.padding(top = 3.dp),
                    fontSize = 17.sp,
                    text = time,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
