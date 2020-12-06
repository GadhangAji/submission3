package com.d.submission3.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.d.submission3.R
import com.d.submission3.model.UserResponse
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_cardview.view.*

class userAdapter(
    private val context: Context,
    private val listItems: ArrayList<UserResponse.Item>
) : RecyclerView.Adapter<userAdapter.ViewHolder>(){

    private var onItemClickCallback: OnItemClickCallback? = null

    interface OnItemClickCallback {
        fun onItemClicked(data: UserResponse.Item)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_cardview, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = listItems[position]
        holder.username.text = user.login
        Glide.with(context)
            .load(user.avatar_url)
            .apply(RequestOptions())
            .into(holder.avatar)


        holder.itemView.setOnClickListener {
            val User = UserResponse.Item(user.login, user.organizations_url, user.avatar_url)
            onItemClickCallback?.onItemClicked(User)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(userItem : UserResponse.Item){
            with(itemView){
                txt_username.text = userItem.login
                Glide.with(itemView.context)
                    .load(userItem.avatar_url)
                    .into(img_photo)
                itemView.setOnClickListener{onItemClickCallback?.onItemClicked(userItem)}
            }
        }


        val username: TextView = itemView.findViewById(R.id.txt_username)
        val avatar: CircleImageView = itemView.findViewById(R.id.img_photo)
    }
}
