package com.greentopli.app.user.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.greentopli.app.R;
import com.greentopli.app.user.ui.OrderHistoryActivity;

import java.util.Calendar;

/**
 * Created by rnztx on 20/11/16.
 */

public class NotificationHelper {
	public static final int ID_PRODUCT_DELIVERED = 9090;
	private Context mContext;

	public NotificationHelper(Context mContext) {
		this.mContext = mContext;
	}
	public void showNotification(String title, String message){
		NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);

		NotificationCompat.InboxStyle notificationStyle = new NotificationCompat.InboxStyle();

		Intent intent = new Intent(mContext, OrderHistoryActivity.class);
		/**
		 * CLEAR_TOP: if application is running give it as new intent, instead of new task
		 * SINGLE_TOP: Activity will not be launched if it is already running
		 */
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent onClickIntent = PendingIntent.getActivity(mContext,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
		// use launcher icon
		Bitmap largeIcon = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.ic_launcher);

		Notification notification = builder
				.setSmallIcon(R.drawable.ic_shopping) // shown on notification bar
				.setLargeIcon(largeIcon)// shown in notification tray
//				.setColor(ContextCompat.getColor(mContext,R.color.colorPrimary)) // works when large icon is not set
				.setTicker(title)
				.setWhen(Calendar.getInstance().getTimeInMillis())
				.setAutoCancel(true)
				.setContentTitle(title)
				.setStyle(notificationStyle)
				.setContentText(message)
				.setContentIntent(onClickIntent) // on click action intent
				.setDefaults(Notification.DEFAULT_SOUND) // play default notification sound
				.setPriority(Notification.PRIORITY_HIGH) // make it popup on any screen
				.build();

		NotificationManager manager = (NotificationManager)mContext
				.getSystemService(Context.NOTIFICATION_SERVICE);
		manager.notify(ID_PRODUCT_DELIVERED,notification);
	}
}
