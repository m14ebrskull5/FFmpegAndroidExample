package com.google.ffmpeg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.ffmpeg.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Example of a call to a native method
        binding.sampleText.text = getVersion()
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