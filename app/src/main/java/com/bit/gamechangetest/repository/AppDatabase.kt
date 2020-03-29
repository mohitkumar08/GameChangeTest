package com.bit.gamechangetest.repository

import android.content.Context
import androidx.room.*
import com.bit.gamechangetest.repository.dao.CommentModelDao
import com.bit.gamechangetest.repository.dao.IssueModelDao
import com.bit.gamechangetest.repository.server.CommentModel
import com.bit.gamechangetest.repository.server.IssueModel
import java.util.*


const val DATABASE_NAME = "GameChangeDb.db"

@Database(
    entities = [IssueModel::class, CommentModel::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    DateConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase? {
            if (null == INSTANCE) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                                context.applicationContext,
                                AppDatabase::class.java, DATABASE_NAME
                            )
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build()
                    }
                }
            }
            return INSTANCE
        }

        private val sRoomDatabaseCallback = object : RoomDatabase.Callback() {

        }
    }
    fun clearDatabase() {
        INSTANCE?.clearAllTables()
    }

    abstract fun issueDao(): IssueModelDao
    abstract fun commentModelDao(): CommentModelDao

}


class DateConverter {

    @TypeConverter
    fun toDate(dateLong: Long): Date {
        return Date(dateLong)
    }

    @TypeConverter
    fun fromDate(date: Date): Long {
        return (date.time)
    }
}


