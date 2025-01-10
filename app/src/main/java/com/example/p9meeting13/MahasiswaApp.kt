package com.example.p9meeting13

import android.app.Application
import com.example.p9meeting13.di.MahasiswaContainer

class MahasiswaApp : Application() {

    // Fungsi untuk menyimpan instance ContainerApp
    lateinit var containerApp: MahasiswaContainer
    override fun onCreate() {
        super.onCreate()
        // Membuat instance ContainerApp
        containerApp = MahasiswaContainer(this)
        // Instance adalah object yang dibuat dari class
    }
}