package com.example.githubprofiles.constants

import android.app.Application
import android.content.Context

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        private lateinit var context: Context

        fun getContext(): Context {
            return context
        }
    }
}