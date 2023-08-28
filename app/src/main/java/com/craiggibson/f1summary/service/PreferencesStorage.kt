package com.craiggibson.f1summary.service

import android.content.Context
import android.util.Log
import java.io.*

object PreferencesStorage {
    private var applicationContext: Context? = null
    private val TAG = PreferencesStorage::class.java.simpleName

    fun init(context: Context) {
        applicationContext = context
    }

    fun writeData(fileName: String, fileContent: String?) {
        applicationContext?.let {
            try {
                val fos: FileOutputStream = it.openFileOutput(fileName, Context.MODE_PRIVATE)
                fos.write(fileContent?.toByteArray())
                fos.flush()
                fos.close()
                Log.i(TAG, "Writing to file completed.")
            } catch (e: IOException) {
                e.printStackTrace()
                Log.i(TAG, "Failed to write to file.")
            }
        }
    }

    fun readData(fileName: String): String? {
        return applicationContext?.let {
            val file = File(it.filesDir, fileName)
            if (!file.exists()) return null
            val fileReader = FileReader(file)
            val bufferedReader = BufferedReader(fileReader)
            val stringBuilder = StringBuilder()
            var line: String? = bufferedReader.readLine()
            while (line != null) {
                stringBuilder.append(line).append("\n")
                line = bufferedReader.readLine()
            }
            bufferedReader.close()
            stringBuilder.toString()
        }
    }
}