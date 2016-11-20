package com.greentopli.app.user.notifications;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by rnztx on 20/11/16.
 */

public class FcmService extends FirebaseMessagingService {
	@Override
	public void onMessageReceived(RemoteMessage remoteMessage) {
		super.onMessageReceived(remoteMessage);
	}
}
