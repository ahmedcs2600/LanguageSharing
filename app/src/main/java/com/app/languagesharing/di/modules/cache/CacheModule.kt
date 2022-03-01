package com.app.languagesharing.di.modules.cache

import android.content.Context
import androidx.room.Room
import com.app.cache.AppDatabase
import com.app.cache.dao.CommunityMemberDao
import com.app.cache.dao.MemberLikeDao
import com.app.cache.dao.RemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class CacheModule {

    @Singleton
    @Provides
    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context.applicationContext, AppDatabase::class.java, AppDatabase.DATABASE_NAME
        ).build()

    @Provides
    fun providesCommunityMemberDao(appDatabase: AppDatabase): CommunityMemberDao =
        appDatabase.communityMemberDao()

    @Provides
    fun providesRemoteKeysDao(appDatabase: AppDatabase): RemoteKeysDao = appDatabase.remoteKeysDao()

    @Provides
    fun providesMemberLikeDao(appDatabase: AppDatabase): MemberLikeDao = appDatabase.memberLikeDao()
}