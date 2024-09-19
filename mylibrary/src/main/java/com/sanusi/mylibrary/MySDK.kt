package com.sanusi.shakerapi

import android.content.Context
import android.util.Log
import android.widget.Toast

class MySDK private constructor(private val context: Context) {
    private var shakeDetector: ShakeDetector? = null
    private var isShakeEnabled = false

    companion object {
        var instance: MySDK? = null

        fun initialize(context: Context): MySDK {
            if (instance == null) {
                instance = MySDK(context)
            }
            return instance!!
        }
    }

    fun enableShakeGesture(onAppShake: (Boolean) -> Unit) {
        // Implement your logic for enabling gestures
        shakeDetector = ShakeDetector(context) {
            Toast.makeText(context, "response", Toast.LENGTH_SHORT).show()
            onAppShake(true)
        }

        // Start the ShakeDetector
        shakeDetector?.start()
        isShakeEnabled = true
        Log.d("MySDK", "Shake gesture detection enabled.")
    }

    fun disableShakeGesture() {
        shakeDetector?.stop()
    }

    private fun showInputModal(onTextSubmitted: (Boolean) -> Unit) {
        onTextSubmitted(true)
    }
}

