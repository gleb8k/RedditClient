package com.reddit.client.domain

import com.reddit.client.data.RedditApi
import com.reddit.client.data.entity.RedditTopResponse

class RedditRepository(val api: RedditApi) {

    suspend fun getTop(itemsCount: Int, nextPageKey: String? = null): RedditTopResponse {
        return api.getTop(itemsCount, nextPageKey)
    }
}