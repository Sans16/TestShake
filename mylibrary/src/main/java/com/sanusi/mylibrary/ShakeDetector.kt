package com.sanusi.shakerapi

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import kotlin.math.sqrt

class ShakeDetector(
    private val context: Context,
    private var onShake: () -> Unit
) : SensorEventListener {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val shakeThreshold = 8.0f
    private var lastShakeTime = 0L

    fun start() {
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_UI
        )
    }

    fun stop() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]

                val gForce = sqrt(x * x + y * y + z * z) / SensorManager.GRAVITY_EARTH

                if (gForce > shakeThreshold) {
                    val currentTime = System.currentTimeMillis()

                    // Only react if the shake happens after 500ms to avoid multiple triggers
                    if (currentTime - lastShakeTime > 500) {
                        lastShakeTime = currentTime
                        onShake()
                    }
                }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Do nothing
    }
}

