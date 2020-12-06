package com.d.submission3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.d.submission3.R
import com.d.submission3.model.FavoriteResponse
import kotlinx.android.synthetic.main.item_cardview.view.*
import java.util.ArrayList

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>(){
    var favorites = ArrayList<FavoriteResponse>()
        set(favorite) {
            this.favorites.clear()
            this.favorites.addAll(favorite)
            notifyDataSetChanged()
        }

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_cardview, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(favorites[position])
    }

    override fun getItemCount() = favorites.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(favorite: FavoriteResponse) {
            with(itemView) {
                txt_username.text = favorite.username
                Glide.with(itemView.context)
                    .load(favorite.avatar)
                    .into(img_photo)

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(favorite) }

            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(favorite: FavoriteResponse)
    }
}