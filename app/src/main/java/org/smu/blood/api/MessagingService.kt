package org.smu.blood.api

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MessagingService() : FirebaseMessagingService() {

    private var TAG = "[FCM]"

    /**
     * Called if the FCM registration token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the
     * FCM registration token is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.d(TAG, "token: $token")
    }

    override fun onMessageReceived(remoteMessage:  RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val from = remoteMessage.from
        val title = remoteMessage.notification?.title
        val body = remoteMessage.notification?.body
        val data = remoteMessage.data

        Log.d(TAG, "message received: $remoteMessage")
        Log.d(TAG, "from: $from, title: $title, body: $body, data: $data")
    }


}