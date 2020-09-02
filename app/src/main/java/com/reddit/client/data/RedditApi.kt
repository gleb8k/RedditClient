package com.reddit.client.data

import com.reddit.client.data.entity.RedditTopResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditApi {

    @GET("/top.json")
    suspend fun getTop(@Query("limit") itemsCount: Int,
               @Query("after") nextPageKey: String?): RedditTopResponse
}