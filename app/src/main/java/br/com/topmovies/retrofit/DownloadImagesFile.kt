package br.com.topmovies.retrofit

import android.content.Context
import br.com.topmovies.commons.Utils
import br.com.topmovies.data.LoadingFinishListener
import br.com.topmovies.tmdb.InfoTMDB
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class DownloadImagesFile {

    fun downloadImage(
        context: Context,
        fileNameImage: String,
        listener: LoadingFinishListener?
    ) {

        val request = Request.Builder().url(InfoTMDB.urlBaseImage + fileNameImage).build()
        OkHttpClient().newCall(request).enqueue(object : okhttp3.Callback {

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                response.body()?.byteStream()?.let {
                    val fileOutputStream = context.openFileOutput(fileNameImage, Context.MODE_PRIVATE)
                    Utils().saveImageInternalStorage(it, fileOutputStream)
                }

                listener?.finish()
            }

            override fun onFailure(call: okhttp3.Call, e: IOException) {}

        })

    }
}