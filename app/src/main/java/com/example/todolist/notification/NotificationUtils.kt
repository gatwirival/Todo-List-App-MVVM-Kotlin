package com.example.todolist.notification

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import com.example.todolist.data.database.TodoItem
import com.example.todolist.utilities.stringToAscii
import java.util.*

class NotificationUtils {

    fun setNotification(todoItem: TodoItem, activity: Activity) {

        if (todoItem.dueTime!! > 0) {

            val alarmManager = activity.getSystemService(Activity.ALARM_SERVICE) as AlarmManager

            val alarmIntent = Intent(activity.applicationContext, AlarmReceiver::class.java)

            alarmIntent.putExtra("reason", "notification")
            alarmIntent.putExtra("timestamp", todoItem.dueTime)

            val calendar = Calendar.getInstance()
            calendar.timeInMillis = todoItem.dueTime

            val pendingIntent = PendingIntent.getBroadcast(
                activity,
                stringToAscii(todoItem.title),
                alarmIntent,
                PendingIntent.FLAG_CANCEL_CURRENT
            )
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        }

    }

    fun cancelNotification(todoItem: TodoItem, activity: Activity) {

        if (todoItem.dueTime!! > 0) {
            val alarmManager = activity.getSystemService(Activity.ALARM_SERVICE) as AlarmManager

            val alarmIntent = Intent(activity.applicationContext, AlarmReceiver::class.java)

            alarmIntent.putExtra("reason", "notification")
            alarmIntent.putExtra("timestamp", todoItem.dueTime)

            val calendar = Calendar.getInstance()
            calendar.timeInMillis = todoItem.dueTime

            val pendingIntent = PendingIntent.getBroadcast(
                activity,
                stringToAscii(todoItem.title),
                alarmIntent,
                PendingIntent.FLAG_CANCEL_CURRENT
            )
            alarmManager.cancel(pendingIntent)
        }

    }

}