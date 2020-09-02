package com.reddit.client.data.entity

import com.google.gson.annotations.SerializedName

class RedditEntry {
    var title: String? = null
    var thumbnail: String? = null
    @SerializedName("created_utc")
    var dateCreated: Double? = null
    @SerializedName("num_comments")
    var commentsCount = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RedditEntry

        if (title != other.title) return false
        if (thumbnail != other.thumbnail) return false
        if (dateCreated != other.dateCreated) return false
        if (commentsCount != other.commentsCount) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title?.hashCode() ?: 0
        result = 31 * result + (thumbnail?.hashCode() ?: 0)
        result = 31 * result + (dateCreated?.hashCode() ?: 0)
        result = 31 * result + commentsCount
        return result
    }


}