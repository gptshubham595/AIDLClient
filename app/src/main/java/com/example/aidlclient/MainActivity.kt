package com.example.aidlclient

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aidlclient.databinding.ActivityMainBinding
import com.example.aidlserver.IAidlColorInterface

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var aidlColorService: IAidlColorInterface

    val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            aidlColorService = IAidlColorInterface.Stub.asInterface(service)
            Log.d("Color","${aidlColorService.color}")
        }

        override fun onServiceDisconnected(name: ComponentName?) {

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent("AidlService")
        intent.setPackage("com.example.aidlserver")
        bindService(intent, serviceConnection, BIND_AUTO_CREATE)

        binding.changeColorButton.setOnClickListener {
            val color = aidlColorService.color
            it.setBackgroundColor(color)
        }
    }
}