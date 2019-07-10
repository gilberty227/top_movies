package br.com.topmovies.commons

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

class Utils {

    fun convertFormatDate(date: String?): String {
        val formatDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dateCorrectFormat = formatDate.parse(date)
        formatDate.applyPattern("dd/MM/yyyy")
        return formatDate.format(dateCorrectFormat)
    }

    fun saveImageInternalStorage(inputStreamFile: InputStream, fileOutputStream: FileOutputStream): Boolean {
        return try {
            val bitmap = BitmapFactory.decodeStream(inputStreamFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
            true
        } catch (e: FileNotFoundException) {
            false
        }
    }
}