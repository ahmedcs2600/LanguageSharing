package com.app.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.cache.entity.MemberLike

@Dao
interface MemberLikeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item : MemberLike)

    @Query("DELETE FROM member_like WHERE id=:id")
    suspend fun deleteById(id : Long)
}