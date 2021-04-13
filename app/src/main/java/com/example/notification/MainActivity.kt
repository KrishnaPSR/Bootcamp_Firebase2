package com.example.notification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val imageURL = getCustomDataFromFCM()
        if (imageURL != null) {
            updateUI(imageURL)
        } else {
            Toast.makeText(this, "Please Check for the Correct Image URL", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(imageURL: String) {
        Glide
            .with(this)
            .load(imageURL)
            .into(imageView)
    }
    private fun getCustomDataFromFCM(): String? {
        Log.i(TAG, "${ intent.extras?.getString(MyFirebaseMessagingService.IMAGE_URL_KEY) }")
        return intent.extras?.getString(MyFirebaseMessagingService.IMAGE_URL_KEY)
    }

    private fun getFirebaseFCMDeviceToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.i(TAG, "FCM Registration Token Failure", task.exception)
                return@OnCompleteListener
            }

            val token = task.result  //to get new FCM registration token
            Log.i(TAG, token.toString())
            Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
        })
    }
}