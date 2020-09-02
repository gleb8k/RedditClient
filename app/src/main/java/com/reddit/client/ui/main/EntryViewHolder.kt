package com.reddit.client.ui.main

import com.reddit.client.utils.Utils
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.reddit.client.R
import com.reddit.client.data.entity.RedditEntry
import kotlinx.android.synthetic.main.view_item_entry.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class EntryViewHolder(view: View): RecyclerView.ViewHolder(view) {

    companion object {
        fun newInstance(root: ViewGroup): EntryViewHolder {
            return EntryViewHolder(
                LayoutInflater.from(root.context).inflate(
                    R.layout.view_item_entry,
                    root,
                    false
                )
            )
        }
    }

    val title = itemView.title
    val thumbnail = itemView.thumbnail
    val commentsCount = itemView.commentsCount
    val date = itemView.date
    var entry: RedditEntry? = null

    init {
        thumbnail.setOnClickListener {
            val url = entry?.thumbnail.orEmpty()
            if (url.isNotEmpty()) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(itemView.context, browserIntent, null)
            }
        }
    }

    fun bind(entry: RedditEntry) {
        this.entry = entry
        title.text = entry.title.orEmpty()
        commentsCount.text = "${entry.commentsCount} comments"
        date.text = ""
        Utils.dateFromDouble(entry.dateCreated)?.let {
            date.text = Utils.getTimeAgo(it.time)
        }
        Glide.with(itemView.context)
            .asBitmap()
            .load(entry.thumbnail.orEmpty())
            .centerCrop()
            .addListener(object : RequestListener<Bitmap>{
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?,
                    dataSource: DataSource?, isFirstResource: Boolean
                ): Boolean {
                    if (isFirstResource && resource != null) {
                        CoroutineScope(Dispatchers.Main).launch {
                            withContext(Dispatchers.IO) {
                                Utils.saveImageToGallery(itemView.context, resource, entry.thumbnail.orEmpty())
                            }
                        }
                    }
                    return false
                }

            })
            .into(thumbnail)

    }
}