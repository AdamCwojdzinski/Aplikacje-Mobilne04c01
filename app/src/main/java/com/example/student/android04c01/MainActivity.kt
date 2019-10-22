package com.example.student.android04c01

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var buttonToast: Button
    private lateinit var buttonCreateNotification: Button
    private lateinit var buttonUpdateNotification: Button
    val CHANNEL_ID = "com.example.student.android04c01"
    private var mManager: NotificationManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonToast = findViewById(R.id.create_toast_button)
        buttonToast.setOnClickListener({v -> toast()})

        buttonCreateNotification = findViewById(R.id.create_notification_button)
        buttonCreateNotification.setOnClickListener({v -> createNotification()})

        buttonUpdateNotification = findViewById(R.id.update_notification_button)
        buttonUpdateNotification.setOnClickListener ({ v -> onDestroy() })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.close -> {
                val newFragment = OnExitDialogFragment()
                newFragment.show(supportFragmentManager, "do_you_want_exit")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        val newFragment = OnExitDialogFragment()
        newFragment.show(supportFragmentManager, "do_you_want_exit")
    }

    fun toast(){
        val text: String = getString(R.string.text_toast)
        val duration = Toast.LENGTH_LONG
        val toast = Toast.makeText(applicationContext, text, duration)
        toast.show()
    }

    fun createNotification(){
        val intent = Intent(applicationContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            applicationContext, 0, intent, PendingIntent.FLAG_ONE_SHOT
        )
        val builder: NotificationCompat.Builder
        val bm = BitmapFactory.decodeResource(resources, R.drawable.oak_round)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            getNotificationManager()?.createNotificationChannel(channel)
        }
        builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_grade_black_24dp)
            .setLargeIcon(bm)
            .setContentTitle("Na zapas!")
            .setContentText("Nie zapomnij o mnie!")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        with(NotificationManagerCompat.from(this)){
            notify(1, builder.build())
        }
    }

    override fun onDestroy() {
        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.cancel(1)
        super.onDestroy()
    }

    private fun getNotificationManager(): NotificationManager? {
        if (mManager == null){
            mManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }
        return mManager
    }

}
