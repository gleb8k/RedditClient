package com.reddit.client.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import java.io.OutputStream
import java.util.*


class Utils {

    companion object {

        private const val SECOND_MILLIS = 1000
        private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
        private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
        private const val DAY_MILLIS = 24 * HOUR_MILLIS

        fun dateFromDouble(date: Double?): Date? {
            date?.let {
                return Date(it.toLong())
            }
            return null
        }

        fun getTimeAgo(time: Long): String {
            var time = time
            if (time < 1000000000000L) {
                time *= 1000
            }
            val now = System.currentTimeMillis()
            if (time > now || time <= 0) {
                return ""
            }
            val diff = now - time
            return when {
                diff < MINUTE_MILLIS -> {
                    "just now"
                }
                diff < 2 * MINUTE_MILLIS -> {
                    "a minute ago"
                }
                diff < 50 * MINUTE_MILLIS -> {
                    (diff / MINUTE_MILLIS).toString() + " minutes ago"
                }
                diff < 90 * MINUTE_MILLIS -> {
                    "an hour ago"
                }
                diff < 24 * HOUR_MILLIS -> {
                    (diff / HOUR_MILLIS).toString() + " hours ago"
                }
                diff < 48 * HOUR_MILLIS -> {
                    "yesterday"
                }
                else -> {
                    (diff / DAY_MILLIS).toString() + " days ago"
                }
            }
        }

        fun getFileName(url: String): String {
            return url.substring(url.lastIndexOf('/') + 1, url.length)
        }

        fun saveImageToGallery(context: Context, bitmap: Bitmap, url: String): String? {
            val values = ContentValues()
            val name = getFileName(url)
            values.put(MediaStore.Images.Media.TITLE, name)
            values.put(MediaStore.Images.Media.DISPLAY_NAME, name)
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
            values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())

            val cr = context.contentResolver
            var uri: Uri? = null
            var imageOut: OutputStream? = null
            try {
                uri = cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                imageOut = cr.openOutputStream(uri!!)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, imageOut)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            finally {
                imageOut?.close()
            }
            return uri?.toString()
        }

    }
}