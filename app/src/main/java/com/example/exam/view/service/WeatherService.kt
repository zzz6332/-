package com.example.exam.view.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Binder
import android.os.Build
import android.os.IBinder
import com.example.exam.App
import com.example.exam.R
import com.example.exam.view.activity.HomeActivity

class WeatherService : Service() {
    private lateinit var manager: NotificationManager
    private lateinit var builder: Notification.Builder
    override fun onBind(intent: Intent): IBinder {
        return WeatherListener()
    }

    override fun onCreate() {
        super.onCreate()
        val intent = Intent(this, HomeActivity::class.java)
        builder = Notification.Builder(App.context)
        builder.setContentIntent(PendingIntent.getActivity(this, 0, intent, 0))
            .setContentTitle(R.string.app_name.toString())
            .setContentText(R.string.app_name.toString())
            .setSmallIcon(R.drawable.ic_app)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_foreground_service))
            .setWhen(System.currentTimeMillis())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //修改安卓8.0以上系统报错
            val notificationChannel = NotificationChannel(
                "CHANNEL_ONE_ID",
                "CHANNEL_ONE_NAME",
                NotificationManager.IMPORTANCE_MIN
            )
            notificationChannel.enableLights(false)//如果使用中的设备支持通知灯，则说明此通知通道是否应显示灯
            notificationChannel.setShowBadge(false)//是否显示角标
            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_SECRET
            manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(notificationChannel)
            builder.setChannelId("CHANNEL_ONE_ID")
        }
        val notification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            builder.build()
        } else {
            TODO("VERSION.SDK_INT < JELLY_BEAN")
        }
        startForeground(1, notification)
    }

    fun updateWeather(city: String, temp: String) {
        builder.setContentTitle(city)
            .setContentText(temp)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            manager.notify(1, builder.build())
        }
    }

    inner class WeatherListener : Binder() {
        fun getService(): WeatherService {
            return this@WeatherService
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopSelf()
    }
}
