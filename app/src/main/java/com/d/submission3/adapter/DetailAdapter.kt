package com.d.submission3.adapter

import android.content.Context
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.d.submission3.R
import com.d.submission3.fragment.FollowersFragment
import com.d.submission3.fragment.FollowingFragment

class DetailAdapter (private val mContext: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val tabTitles = intArrayOf(
        R.string.followers,
        R.string.following
    )

    private val fragment = listOf(
        FollowersFragment(),
        FollowingFragment()
    )

    override fun getItem(position: Int): Fragment {
        return fragment[position]
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(tabTitles[position])
    }

    override fun getCount(): Int {
        return 2
    }
}