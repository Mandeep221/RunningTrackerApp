package com.msarangal.runningtracker.db

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class Converters {
    /**
     * To important classes used to save and retrieve the Bitmap from a database
     * [ByteArrayOutputStream] : Bitmap is compressed to be stored as 0s and 1s in this byte array output stream
     * [BitmapFactory] : Takes in a ByteArray and decodes it into a Bitmap.
     */
    @TypeConverter
    fun toByteArray(fromBitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        fromBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun toBitmap(fromByteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(fromByteArray, 0, fromByteArray.size)
    }
}

