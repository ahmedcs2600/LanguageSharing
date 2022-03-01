package com.app.languagesharing.di.modules.repository

import com.app.cache.repository.CommunityLocalImp
import com.app.data.repository.CommunityLocal
import com.app.data.repository.CommunityMemberDataRepository
import com.app.data.repository.CommunityRemote
import com.app.domain.repository.CommunityMemberRepository
import com.app.remote.repository.CommunityRemoteImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindsCommunityMemberRepository(repository: CommunityMemberDataRepository): CommunityMemberRepository

    @Binds
    abstract fun bindsCommunityLocal(local : CommunityLocalImp) : CommunityLocal

    @Binds
    abstract fun bindsRemoteLocal(remote : CommunityRemoteImpl) : CommunityRemote
}