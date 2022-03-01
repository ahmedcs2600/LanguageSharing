package com.app.cache.mapper

import com.app.cache.entity.RemoteKeys
import com.app.common.mapper.Mapper
import javax.inject.Inject
import com.app.data.model.RemoteKeys as DataRemoteKeys

class RemoteKeysMapper @Inject constructor() : Mapper<RemoteKeys, DataRemoteKeys> {
    override fun mapTo(input: RemoteKeys): DataRemoteKeys {
        return DataRemoteKeys(
            input.id,
            input.nextKey,
        )
    }
}