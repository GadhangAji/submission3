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
import com.d.submission3.adapter.FollowersAdapter
import com.d.submission3.model.UserResponse
import com.d.submission3.viewModel.FollowersViewModel
import kotlinx.android.synthetic.main.followers_fragment.*

class FollowersFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.followers_fragment, container, false)
    }

    private lateinit var followersAdapter: FollowersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val intent =
            activity?.intent?.getParcelableExtra(DetailActivity.EXTRA_DATA) as UserResponse.Item
        val getUsername = intent.login

        if (getUsername != null) {
            configFollowersViewModel(getUsername)
        }

        configRecyclerView()
    }

    private fun configFollowersViewModel(getUsername: String) {
        val followersViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowersViewModel::class.java)
        followersViewModel.setFollowersUser(getUsername, activity!!)
        followersViewModel.getFollowersUser().observe(activity!!, Observer {
            if (it != null) {
                followersAdapter.setFollowersData(it)
                notFound.visibility = View.GONE
            }
            if (it.isEmpty()) {
                notFound.visibility = View.VISIBLE
            }
        })
    }

    private fun configRecyclerView() {
        followersAdapter = FollowersAdapter()
        followersAdapter.notifyDataSetChanged()

        followersRecyclerView.layoutManager = LinearLayoutManager(activity)
        followersRecyclerView.adapter = followersAdapter
        followersRecyclerView.setHasFixedSize(true)
    }
}