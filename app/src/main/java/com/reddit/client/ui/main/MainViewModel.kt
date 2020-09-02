package com.reddit.client.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.reddit.client.RedditApp
import com.reddit.client.domain.RedditPagingSource
import com.reddit.client.domain.RedditRepository
import javax.inject.Inject

class MainViewModel : ViewModel() {

    @Inject
    lateinit var repository: RedditRepository

    init {
        RedditApp.getInstance().component.inject(this)
    }

    fun getTop() = Pager(PagingConfig(pageSize = 25)) {
        RedditPagingSource(repository)
    }.flow
        .cachedIn(viewModelScope)

}