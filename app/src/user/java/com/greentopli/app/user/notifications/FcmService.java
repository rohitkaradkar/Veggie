package com.greentopli.app.user.notifications;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.greentopli.Constants;
import com.greentopli.model.DataMessage;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by rnztx on 20/11/16.
 */

public class FcmService extends FirebaseMessagingService {
	@Override
	public void onMessageReceived(RemoteMessage remoteMessage) {
		NotificationHelper helper = new NotificationHelper(getApplicationContext());

		// Notification Message (from Firebase Console)
		if (remoteMessage.getNotification()!=null){
			RemoteMessage.Notification notification = remoteMessage.getNotification();
			helper.showNotification(notification.getTitle(),notification.getBody());
		}
		// Data Message (from server)
		else if (remoteMessage.getData().size()>0){
			DataMessage dataMessage = parseMessage(remoteMessage.getData());
			if (dataMessage!=null && !dataMessage.isEmpty()){
				helper.showNotification(dataMessage.getTitle(),dataMessage.getMessage());
			}
		}
	}
	private DataMessage parseMessage(Map<String,String> mappedData){
		if (mappedData.containsKey(Constants.JSON_KEY_TITLE) && mappedData.containsKey(Constants.JSON_KEY_MESSAGE)){
			DataMessage dataMessage = new DataMessage();
			dataMessage.setTitle(mappedData.get(Constants.JSON_KEY_TITLE));
			dataMessage.setMessage(mappedData.get(Constants.JSON_KEY_MESSAGE));
			return dataMessage;
		}
		return null;
	}
}
