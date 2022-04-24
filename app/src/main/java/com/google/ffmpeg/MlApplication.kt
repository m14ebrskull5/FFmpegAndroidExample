package com.google.ffmpeg

import android.app.Application
import com.google.ffmpeg.db.MyDatabase

class MlApplication:Application() {
    val database by lazy { MyDatabase.getDatabase(this) }
}