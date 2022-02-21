package com.example.selfmadearoma.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.selfmadearoma.data.AppDatabase
import com.example.selfmadearoma.data.aromaOil.AromaOil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val filename = inputData.getString(KEY_FILENAME)
            if (filename != null) {
                applicationContext.assets.open(filename).bufferedReader().use {
                    val type = object : TypeToken<List<AromaOil>>() {}.type
                    val aromaList: List<AromaOil> = Gson().fromJson(it, type)

                    val database = AppDatabase.getInstance(applicationContext)
                    database.aromaOilDao().insertAll(aromaList)

                    Log.d(TAG, "成功")

                    Result.success()
                }
//                applicationContext.assets.open(filename).use { inputStream ->
//                    JsonReader(inputStream.reader()).use { jsonReader ->
//                        val type = object : TypeToken<List<EssentialOil>>() {}.type
//                        val plantList: List<EssentialOil> = Gson().fromJson(jsonReader, type)
//
//                        val database = AppDatabase.getInstance(applicationContext)
//                        database.essentialOilDao().insertAll(plantList)
//
//                        Result.success()
//                    }
//                }
            } else {
                Log.e(TAG, "Error seeding database - no valid filename")
                Result.failure()
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "SeedDatabaseWorker"
        const val KEY_FILENAME = "AROMAOIL_DATA_FILENAME"
    }
}