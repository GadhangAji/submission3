package com.d.submission3.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.d.submission3.DetailActivity
import com.d.submission3.R
import com.d.submission3.adapter.FollowingAdapter
import com.d.submission3.model.UserResponse
import com.d.submission3.viewModel.FollowingViewModel
import kotlinx.android.synthetic.main.following_fragment.*

class FollowingFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.following_fragment, container, false)
    }

    private lateinit var followingAdapter: FollowingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val intent =
            activity?.intent?.getParcelableExtra(DetailActivity.EXTRA_DATA) as UserResponse.Item
        val getUsername = intent.login

        if (getUsername != null) {
            configFollowingViewModel(getUsername)
        }

        configRecyclerView()
    }

    private fun configFollowingViewModel(getUsername: String) {
        val followingViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowingViewModel::class.java)
        followingViewModel.setFollowingUser(getUsername, activity!!)
        followingViewModel.getFollowingUser().observe(activity!!, Observer {
            if (it != null) {
                followingAdapter.setFollowingData(it)
                notFound.visibility = View.GONE
            }
            if (it.isEmpty()) {
                notFound.visibility = View.VISIBLE
            }
        })
    }

    private fun configRecyclerView() {
        followingAdapter = FollowingAdapter()
        followingAdapter.notifyDataSetChanged()

        followingRecyclerView.layoutManager = LinearLayoutManager(activity)
        followingRecyclerView.adapter = followingAdapter
        followingRecyclerView.setHasFixedSize(true)
    }

}