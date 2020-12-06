package com.d.submission3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.d.submission3.R
import com.d.submission3.model.FollowingResponse
import kotlinx.android.synthetic.main.item_cardview.view.*

class FollowingAdapter  : RecyclerView.Adapter<FollowingAdapter.ViewHolder>() {

    private val mFollowingData = ArrayList<FollowingResponse>()

    fun setFollowingData(item: ArrayList<FollowingResponse>) {
        mFollowingData.clear()
        mFollowingData.addAll(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_cardview, parent, false)
        )
    }

    override fun getItemCount(): Int = mFollowingData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mFollowingData[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(FollowingResponse: FollowingResponse) {
            with(itemView) {

                txt_username.text = FollowingResponse.login
                Glide.with(itemView.context)
                    .load(FollowingResponse.avatar_url)
                    .into(img_photo)

            }
        }
    }
}