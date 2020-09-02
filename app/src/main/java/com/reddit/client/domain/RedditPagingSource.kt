package com.reddit.client.domain

import androidx.paging.PagingSource
import com.reddit.client.data.entity.RedditEntry
import retrofit2.HttpException
import java.io.IOException

class RedditPagingSource(val repository: RedditRepository): PagingSource<String, RedditEntry>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, RedditEntry> {
        return try {
            val data = repository.getTop(params.loadSize,
                                        if (params is LoadParams.Append) params.key else null).data

            LoadResult.Page(
                data = data?.children?.map { it.data ?: RedditEntry() } ?: listOf(),
                prevKey = null,
                nextKey = data?.after
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}