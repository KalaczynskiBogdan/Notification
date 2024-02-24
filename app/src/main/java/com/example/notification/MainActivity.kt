package com.example.notification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = NotificationFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()

        FirebaseMessaging.getInstance().token.addOnCompleteListener{task->
            val token = task.result
            Log.d("TOKEN", token)
        }
    }
}