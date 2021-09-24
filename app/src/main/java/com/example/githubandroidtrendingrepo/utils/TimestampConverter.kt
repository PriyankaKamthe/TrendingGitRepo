package com.example.githubandroidtrendingrepo.utils

import androidx.room.TypeConverter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 *Created by Priyanka K on 9/20/2021
 */
object TimestampConverter {
    private val df = SimpleDateFormat(AppConstants.DATE_TIME_FORMAT)

    @TypeConverter
    fun fromTimestamp(value: String): Date? {
        try {
            val timeZone = TimeZone.getTimeZone("IST")
            df.timeZone = timeZone
            return df.parse(value)

        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return null
    }


    @TypeConverter
    fun dateToTimestamp(value: Date): String {
        val timeZone = TimeZone.getTimeZone("IST")
        df.timeZone = timeZone
        return df.format(value)
    }
}