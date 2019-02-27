package br.com.topmovies.commons

import java.text.SimpleDateFormat
import java.util.*

class Utils {

    fun convertFormatDate(date: String?): String {
        val formatDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dateCorrectFormat = formatDate.parse(date)
        formatDate.applyPattern("dd/MM/yyyy")
        return formatDate.format(dateCorrectFormat)
    }
}