package com.d.submission3.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FollowersResponse(
    var login: String? = null,
    var organizations_url: String? = null,
    var avatar_url: String? = null,
    var name: String? = null,
    var location: String? = null,
    var public_repos: Int = 0,
    var followers: Int = 0,
    var following: Int = 0
):Parcelable
