package com.d.submission3.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class UserResponse(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
) {
    @Parcelize
    data class Item(
        var login: String? = null,
        var organizations_url: String? = null,
        var avatar_url: String? = null,
        var location: String? = null,
        var repository: Int = 0,
        var followers: Int = 0,
        var following: Int = 0
    )
        :Parcelable
}