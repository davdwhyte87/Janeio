package com.app.janeio

import Janeio.R
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.app.janeio.database.TodoItemDBHelper
import com.app.janeio.model.TodoItem
import kotlin.random.Random

import android.media.RingtoneManager

import android.media.Ringtone


import android.media.MediaPlayer







class AlarmReciever : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        // make vibrate
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(3000, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(3000)
        }

        //make notification
        val i = Intent(context, DestinationActivity::class.java)
        intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(context, 0,i, PendingIntent.FLAG_IMMUTABLE)
        //get todov item id from intent data
        val itemID =intent.getIntExtra("item_id",999);
        Log.i("XXXXXXHHHXX R item", itemID.toString())

        //get data from database on todox item we get from prev activity
        val dbHelper = TodoItemDBHelper(context)

        val todoItemArr = dbHelper.readItem(itemID.toString())
        var todoItem =TodoItem(
        null,
            "",
            "",
            "",
            "",
            0
        )
        if (todoItemArr.size>0){
            todoItem = todoItemArr[0]
        }

        val builder = NotificationCompat.Builder(context!!, "AlarmNotify")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Alarm")
            .setContentText("Task: "+todoItem.Title)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify( Random(85395).nextInt(10039,997330300), builder.build())


        val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        val ringtone = RingtoneManager.getRingtone(context, notification)
        ringtone.play()
        val mp = MediaPlayer.create(context, notification)
        mp.start()

    }
}