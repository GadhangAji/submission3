package com.d.submission3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.d.submission3.R
import com.d.submission3.model.FollowersResponse
import kotlinx.android.synthetic.main.item_cardview.view.*

class FollowersAdapter : RecyclerView.Adapter<FollowersAdapter.ViewHolder>() {

    private val mFollowersData = ArrayList<FollowersResponse>()

    fun setFollowersData(item: ArrayList<FollowersResponse>) {
        mFollowersData.clear()
        mFollowersData.addAll(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_cardview, parent, false)
        )
    }

    override fun getItemCount(): Int = mFollowersData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mFollowersData[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(FollowersResponse: FollowersResponse) {
            with(itemView) {

                txt_username.text = FollowersResponse.login
                Glide.with(itemView.context)
                    .load(FollowersResponse.avatar_url)
                    .into(img_photo)

            }
        }
    }
}