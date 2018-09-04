package com.luizmatias.mvvmsample.data.repository.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.luizmatias.mvvmsample.data.model.User

@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDAO(): UserDAO

    companion object {
        private lateinit var instance: UserDatabase

        @Synchronized
        fun getInstance(context: Context): UserDatabase {
            instance = Room.databaseBuilder(context.applicationContext,
                    UserDatabase::class.java, "user.db")
                    .build()
            return instance
        }
    }

}