package br.com.paulosalvatore.push_images_networking_kotlin.view

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.util.Log
import br.com.paulosalvatore.push_images_networking_kotlin.R
import br.com.paulosalvatore.push_images_networking_kotlin.notification.NotificationCreation
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_push.*
import java.io.File
import java.io.IOException
import java.text.MessageFormat
import java.text.SimpleDateFormat
import java.util.*


class PushActivity :
		AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_push)

		val notificationManager =
				getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
		notificationManager.cancel(NotificationCreation.sNOTIFY_ID)

		if (intent.action != null) {
			textView.text = MessageFormat.format(
					"{0}: {1}",
					getString(R.string.action),
					intent.action
			)
		} else {
			textView.text = getString(R.string.no_action)
		}
	}
}
