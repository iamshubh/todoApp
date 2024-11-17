package com.sps.todoitems.ui

import android.app.Application
import androidx.compose.runtime.Applier
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TodoApp : Application() {

    override fun onCreate() {
        super.onCreate()

    }

}