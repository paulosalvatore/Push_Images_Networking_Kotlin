package br.com.paulosalvatore.push_images_networking_kotlin.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.support.v4.app.NotificationCompat
import br.com.paulosalvatore.push_images_networking_kotlin.R
import br.com.paulosalvatore.push_images_networking_kotlin.view.PushActivity
import com.squareup.picasso.Picasso
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class NotificationCreation {
	companion object {
		private var notificationManager: NotificationManager? = null

		const val sNOTIFY_ID = 1000
		private val sVIBRATION = longArrayOf(300, 400, 500, 400, 300)

		// Channel Information
		private const val sCHANNEL_ID = "MovileNext_1"
		private const val sCHANNEL_NAME = "MovileNext - Push Channel 1"
		private const val sCHANNEL_DESCRIPTION = "MovileNext - Push Channel - Used for main notifications"

		fun create(context: Context, title: String, body: String) {
			create(context, title, body, false)
		}

		fun create(context: Context,
		           title: String,
		           body: String,
		           imageUrl: String,
		           imageLoadingType: ImageLoadingType,
		           includeActions: Boolean = false) {

			doAsync {
				val bitmap = when (imageLoadingType) {
					ImageLoadingType.PICASSO -> Picasso
							.get()
							.load(imageUrl)
							.get()

					ImageLoadingType.GLIDE -> Picasso
							.get()
							.load(imageUrl)
							.get()
				}

				uiThread {
					create(context, title, body, false, bitmap, includeActions)
				}
			}
		}

		fun create(context: Context,
		           title: String,
		           body: String,
		           imageLoading: Boolean,
		           bitmap: Bitmap? = null,
		           includeActions: Boolean = false) {

			if (notificationManager == null)
				notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				var channel = notificationManager?.getNotificationChannel(sCHANNEL_ID)

				if (channel == null) {
					val importance = NotificationManager.IMPORTANCE_HIGH

					channel = NotificationChannel(sCHANNEL_ID, sCHANNEL_NAME, importance)
					channel.description = sCHANNEL_DESCRIPTION
					channel.enableVibration(true)
					channel.enableLights(true)
					channel.vibrationPattern = sVIBRATION

					notificationManager?.createNotificationChannel(channel)
				}
			}

			val intent = Intent(context, PushActivity::class.java)
			intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

			val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

			val builder =
					NotificationCompat.Builder(context, sCHANNEL_ID)
							.setContentTitle(title)
							.setSmallIcon(R.drawable.ic_notification)
							.setContentText(body)
							.setDefaults(Notification.DEFAULT_ALL)
							.setAutoCancel(true)
							.setContentIntent(pendingIntent)
							.setTicker(title)
							.setLargeIcon(bitmap)
							.setVibrate(sVIBRATION)
							.setOnlyAlertOnce(true)
							.setStyle(NotificationCompat
									.BigTextStyle()
									.bigText(body))

			if (imageLoading) {
				builder.setProgress(100, 0, true)
			} else {
				builder.setProgress(0, 0, false)
			}

			// Image
			if (bitmap != null) {
				builder.setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
			}

			// Actions
			if (includeActions) {
				val intent1 = Intent(context, PushActivity::class.java)
				intent1.action = context.getString(R.string.disliked)
				val pendingIntent1 = PendingIntent.getActivity(context, 0, intent1, 0)
				val action1 = NotificationCompat.Action(R.drawable.ic_thumb_down,
						context.getString(R.string.disliked),
						pendingIntent1)

				val intent2 = Intent(context, PushActivity::class.java)
				intent2.action = context.getString(R.string.liked)
				val pendingIntent2 = PendingIntent.getActivity(context, 0, intent2, 0)
				val action2 = NotificationCompat.Action(
						R.drawable.ic_thumb_up,
						context.getString(R.string.liked),
						pendingIntent2)

				builder
						.addAction(action1)
						.addAction(action2)
			}

			val notificationApp = builder.build()
			notificationManager?.notify(sNOTIFY_ID, notificationApp)
		}
	}
}
