package com.angel.shoppingapp.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.angel.shoppingapp.database.User
import com.angel.shoppingapp.database.UserDB
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class LoginModel: ViewModel() {
    var userDB = UserDB
    @OptIn(DelicateCoroutinesApi::class)
    fun WriteData(context: Context, user: List<User>){
            for (item in user){
                val user = User(item.id, userName = item.userName, password = item.password)
                GlobalScope.launch{
                    userDB.getDatabase(context).userDao().add(user)
                }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun ReadData(context: Context, mailName: String, pw: String){
        GlobalScope.launch{
            userDB.getDatabase(context).userDao().getAll(mailName, pw)
        }
    }
}