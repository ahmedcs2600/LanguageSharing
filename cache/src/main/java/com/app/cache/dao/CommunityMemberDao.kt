package com.app.cache.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.app.cache.entity.CommunityMember
import com.app.common.models.CommunityMemberLike

@Dao
interface CommunityMemberDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(members : List<CommunityMember>)

    @Query("Select mm.*,IFNULL(ml.isLiked,0) AS isLiked from community_member AS mm LEFT JOIN member_like AS ml ON mm.id = ml.id ORDER BY mm.id ASC")
    fun getCommunityMembers() : PagingSource<Int, CommunityMemberLike>

    @Query("DELETE FROM community_member")
    suspend fun deleteAll()

    @Transaction
    suspend fun deleteAllAndInsert(members : List<CommunityMember>) {
        deleteAll()
        insertAll(members)
    }
}