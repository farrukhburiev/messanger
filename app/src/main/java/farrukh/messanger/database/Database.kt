package farrukh.messanger.database

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import farrukh.messanger.Message
import farrukh.messanger.User

class Database {
    companion object {
        private val users = FirebaseDatabase.getInstance().reference.child("users")
        private val messages = FirebaseDatabase.getInstance().reference.child("messages")


        fun addUser(user: User, callback: (Boolean) -> Unit) {
            users.child(user.username!!).setValue(user)
            callback(true)
        }

        fun sendMessage(message: Message) {
            messages.push().setValue(message)
        }

        fun getMessages(chat: String, user: String, callback: (List<Message>) -> Unit) {
            messages.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val messages = mutableListOf<Message>()
                    val children = dataSnapshot.children
                    children.forEach {
                        val message = it.getValue(Message::class.java)
                        if (message != null) {
                            if ((message.from == chat && message.to == user) || (message.from == user && message.to == chat)) {
                                messages.add(message)
                            }
                        }
                    }
                    callback(messages)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    callback(emptyList())
                }
            })
        }


        fun getLastMessage(from: String, to: String, callback: (Message) -> Unit) {
            messages.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val messages = mutableListOf<Message>()
                    val children = dataSnapshot.children
                    children.forEach {
                        val message = it.getValue(Message::class.java)
                        if (message != null) {
                            if ((message.from == from && message.to == to) || (message.from == to && message.to == from)) {
                                messages.add(message)
                            }
                        }
                    }
                    if (messages.isEmpty()) {
                        callback(Message("","","",""))
                        return
                    }
                    messages.sortByDescending { it.date }
                    callback(messages[0])
                }

                override fun onCancelled(databaseError: DatabaseError) {
                }
            })
        }

//        fun getMessage(message: Message, callback: (Boolean) -> Unit) {
//            messages.addValueEventListener(object : ValueEventListener {
//                override fun onDataChange(dataSnapshot: DataSnapshot) {
//
//                    callback(false)
//                }
//
//                override fun onCancelled(databaseError: DatabaseError) {
//                    callback(false)
//                }
//            })
//        }

        fun giveToken(context: Context, user: String) {
            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences("db", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("user", user)
            editor.apply()
        }

        fun getToken(context: Context): String {
            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences("db", Context.MODE_PRIVATE)
            return sharedPreferences.getString("user", "") ?: ""
        }

//        fun register(user: User, callback: (Boolean) -> Unit) {
//            val key = users.push().key.toString()
//            users.child(key).setValue(user)
//            callback(true)
//        }


        fun getUsers(username: String, callback: (List<User>) -> Unit) {
            users.addValueEventListener(object : ValueEventListener {
                val userlar = mutableListOf<User>()
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    dataSnapshot.children.forEach {
                        val user = it.getValue(User::class.java)
                        if (user!!.username != username) {
                            userlar.add(user)
                        }

                        callback(userlar)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    callback(emptyList())
                }
            })
        }


        fun getAllExistingUsers(callback: (List<User>) -> Unit) {
            users.addValueEventListener(object : ValueEventListener {
                val userlar = mutableListOf<User>()
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    dataSnapshot.children.forEach {
                        val user = it.getValue(User::class.java)
                        userlar.add(user!!)
                        callback(userlar)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    callback(emptyList())
                }
            })
        }

        fun getUser(username: String, password: String, callback: (Boolean) -> Unit) {
            users.child(username).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists() && dataSnapshot.getValue(User::class.java)!!.password.toString() == password) {
                        callback(true)
                    }
                    callback(false)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    callback(false)
                }
            })
        }

        fun checkUser(user: User, callback: (Boolean) -> Unit) {
            users.child(user.username!!)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            Log.d("TAG", snapshot.toString())
                            callback(false)
                        } else callback(true)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        callback(false)
                    }
                })
        }

        fun updateUser(user: String, fullname: String, password: String) {
            users.child(user).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val changedUser = dataSnapshot.getValue(User::class.java)
                    if (changedUser != null) {
                        changedUser.password = password
                        changedUser.fullName = fullname
                    }
                    users.child(user).setValue(changedUser)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                }
            })
        }

    }
}