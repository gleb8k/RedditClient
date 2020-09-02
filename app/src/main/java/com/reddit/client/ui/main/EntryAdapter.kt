package com.reddit.client.ui.main

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.reddit.client.data.entity.RedditEntry

class EntryAdapter: PagingDataAdapter<RedditEntry, EntryViewHolder>(EntryComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
        return EntryViewHolder.newInstance(parent)
    }

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    object EntryComparator : DiffUtil.ItemCallback<RedditEntry>() {
        override fun areItemsTheSame(oldItem: RedditEntry, newItem: RedditEntry): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: RedditEntry, newItem: RedditEntry): Boolean {
            return oldItem == newItem
        }
    }
}