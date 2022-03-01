package com.app.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.cache.converters.Converters
import com.app.cache.dao.CommunityMemberDao
import com.app.cache.dao.MemberLikeDao
import com.app.cache.dao.RemoteKeysDao
import com.app.cache.entity.CommunityMember
import com.app.cache.entity.MemberLike
import com.app.cache.entity.RemoteKeys

@Database(
    entities = [CommunityMember::class, RemoteKeys::class, MemberLike::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun communityMemberDao(): CommunityMemberDao

    abstract fun remoteKeysDao(): RemoteKeysDao

    abstract fun memberLikeDao(): MemberLikeDao

    companion object {
        const val DATABASE_NAME = "community.db"
    }
}