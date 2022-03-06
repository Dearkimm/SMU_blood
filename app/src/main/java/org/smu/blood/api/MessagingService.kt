package org.smu.blood.api

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_MUTABLE
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.smu.blood.R
import org.smu.blood.ui.my.Card.CardRequestActivity

class MessagingService() : FirebaseMessagingService() {

    private var TAG = "[NOTIFICATION]"

    /**
     * Called if the FCM registration token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the
     * FCM registration token is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.d(TAG, "FCM token created: $token")
    }

    override fun onMessageReceived(remoteMessage:  RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val from = remoteMessage.from
        val title = remoteMessage.notification?.title
        val body = remoteMessage.notification?.body
        val data = remoteMessage.data

        Log.d(TAG, "message received: $remoteMessage")
        Log.d(TAG, "from: $from, title: $title, body: $body, data: $data")

        sendNotification(title!!, body!!)
    }

    // get current registered token
    fun getToken(): String?{
        var token: String? = null
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if(!task.isSuccessful){
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // get new FCM registration token
            token = task.result
            Log.d(TAG, "FCM token: $token")
        })
        return token
    }

    fun sendNotification(title: String, text: String){
        // 알림 탭하면 보여줄 내용 - 요청 정보
        val intent = Intent(this, CardRequestActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, FLAG_MUTABLE)

        val channelId = getString(R.string.notification_channel_id)
        val channelName = getString(R.string.default_notification_channel_name)
        val defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSound(defaultSound)
            .setContentText(text)
            .setContentTitle(title)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.bling)
        // create notification channel
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(channel)
        notificationManager.notify(0, notificationBuilder.build())
    }
}