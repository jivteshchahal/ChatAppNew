package com.example.chatapp.service.firebaseService

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.chatapp.view.ui.activities.ChatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessaging : FirebaseMessagingService() {
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        println("hjvfjvjvjbkjkk" + p0)
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        val sented = p0.data["sented"]
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null && sented.equals(user.uid)) {
            sendNotification(p0)
            println("hjvfjvjvjbkjkk" + p0)
            Log.e("dddddddddddddddd", p0.toString())
        }
    }

    private fun sendNotification(p0: RemoteMessage) {
        val user = p0.data["user"]
        val icon = p0.data["icon"]
        val title = p0.data["title"]
        val body = p0.data["body"]
        println("hjvfjvjvjbkjkk" + p0)
        val notification: RemoteMessage.Notification = p0.notification!!
        val j = Integer.parseInt(user!!.replace("[\\D]", ""))
        val intent = Intent(this, ChatActivity::class.java)
        val bundle = Bundle()
        bundle.putString("userid", user)
        intent.putExtras(bundle)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, j, intent, PendingIntent.FLAG_ONE_SHOT)
        val defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(this)
            .setSmallIcon(Integer.parseInt(icon!!))
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(defaultSound)
            .setContentIntent(pendingIntent)
        val noti = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        var i = 0
        if (j > 0) {
            i = j
        }
        noti.notify(i, builder.build())
    }
}