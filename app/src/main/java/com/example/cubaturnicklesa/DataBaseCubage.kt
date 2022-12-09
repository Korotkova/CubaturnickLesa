package com.example.cubaturnicklesa

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Clients::class, DataStandarts::class], version = 1)
abstract class DataBaseCubage : RoomDatabase() {

    abstract fun getDao(): Dao

    companion object{
        fun getDB(context: Context): DataBaseCubage{
            return Room.databaseBuilder(
                context.applicationContext,
                DataBaseCubage::class.java,
                "cubageDB.db"
            ).build()
        }
    }
}