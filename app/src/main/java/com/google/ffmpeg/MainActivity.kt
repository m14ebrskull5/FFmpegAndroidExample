package com.google.ffmpeg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.room.Dao
import androidx.room.Database
import com.google.ffmpeg.databinding.ActivityListadapterBinding
import com.google.ffmpeg.databinding.ActivityMainBinding
import com.google.ffmpeg.db.MyDatabase
import com.google.ffmpeg.db.User
import com.google.ffmpeg.db.UserDao
import com.google.ffmpeg.ui.MainActivityViewModel
import com.google.ffmpeg.ui.MainActivityViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListadapterBinding
    private val vm by viewModels<MainActivityViewModel> {
        MainActivityViewModelFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListadapterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mutableListOf())
        lifecycleScope.launchWhenCreated {
            vm.flow.collectLatest {
                adapter.clear()
                adapter.addAll(it.map {

                    it.toString()
                })
            }
        }
        vm.start()
        binding.listview.adapter = adapter
        binding.listview.divider = null
        binding.listview.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
//            Log.d("MainActivity", "${id}")
//            adapter.add(getVersion())

           vm.post()
        }
        binding.listview.onItemLongClickListener = object: AdapterView.OnItemLongClickListener {
            override fun onItemLongClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ): Boolean {
                vm.del(position)
                return true
            }

        }
    }

    /**
     * A native method that is implemented by the 'ffmpeg' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String
    external fun configurationinfo(): String
    external fun getVersion(): String

    companion object {
        // Used to load the 'ffmpeg' library on application startup.
        init {
            System.loadLibrary("ffmpeg")
            System.loadLibrary("avcodec")
            System.loadLibrary("swresample")
            System.loadLibrary("avutil")
            System.loadLibrary("avfilter")
            System.loadLibrary("swscale")
            System.loadLibrary("avdevice")
//            avcodec swresample avutil avfilter swscale postproc avdevice
        }
    }
}