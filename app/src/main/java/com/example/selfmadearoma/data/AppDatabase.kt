package com.example.selfmadearoma.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.selfmadearoma.data.aromaOil.AromaOil
import com.example.selfmadearoma.data.aromaOil.AromaOilDao
import com.example.selfmadearoma.data.myArom.MyAroma
import com.example.selfmadearoma.data.myArom.MyAromaDao
import com.example.selfmadearoma.data.myArom.MyAromaOilInfo
import com.example.selfmadearoma.data.myArom.MyAromaOilInfoDao
import com.example.selfmadearoma.utilities.AROMAOIL_DATA_FILENAME
import com.example.selfmadearoma.utilities.DATABASE_NAME
import com.example.selfmadearoma.workers.SeedDatabaseWorker
import com.example.selfmadearoma.workers.SeedDatabaseWorker.Companion.KEY_FILENAME

@Database(entities = [AromaOil::class, MyAroma::class, MyAromaOilInfo::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun aromaOilDao(): AromaOilDao
    abstract fun myAromaDao(): MyAromaDao
    abstract fun myAromaOilInfoDao(): MyAromaOilInfoDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(
                    object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>()
                                .setInputData(workDataOf(KEY_FILENAME to AROMAOIL_DATA_FILENAME))
                                .build()
                            WorkManager.getInstance(context).enqueue(request)
                        }
                    }
                )
                .build()
        }
    }
}