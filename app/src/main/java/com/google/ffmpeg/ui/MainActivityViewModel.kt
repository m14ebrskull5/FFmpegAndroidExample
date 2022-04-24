package com.google.ffmpeg.ui

import android.app.Application
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.ffmpeg.db.MyDatabase
import com.google.ffmpeg.db.User
import com.google.ffmpeg.db.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainActivityViewModel(private val db: UserDao): ViewModel() {
    private lateinit var list: List<User>
    fun post() {
        viewModelScope.launch(Dispatchers.IO) {

            db.insertAll(User(firstName = "A", lastName = "B", uid = null))
            refresh()
        }
    }
    suspend fun refresh()
    {
        list = db.getAll()
        flow.emit(list)
    }
    fun start() {
        viewModelScope.launch(Dispatchers.IO) {
            refresh()
        }
    }

    fun del(position: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            db.delete(list[position])
            refresh()
        }
    }


    var flow:MutableStateFlow<List<User>> = MutableStateFlow<List<User>>(listOf())
}

class MainActivityViewModelFactory(var mApplication: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        var db = MyDatabase.getDatabase(mApplication).userDao()
        return MainActivityViewModel(db) as T
    }

}