package com.luizmatias.mvvmsample.data.repository.local

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.luizmatias.mvvmsample.data.model.User
import io.reactivex.Flowable

@Dao
interface UserDAO {

    @Insert(onConflict = REPLACE)
    fun add(user: User)

    @Query("SELECT * FROM user WHERE user.id = :id LIMIT 1")
    fun getById(id: Int): Flowable<User>

    @Query("SELECT * FROM user WHERE user.login = :login LIMIT 1")
    fun getByLogin(login: String): Flowable<User>

    @Query("SELECT * FROM user")
    fun getAll(id: Int): Flowable<List<User>>

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

}