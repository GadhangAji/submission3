package com.d.submission3.data

import android.net.Uri
import android.provider.BaseColumns

object FavData {
    const val AUTHORITY = "com.d.submission3"
    const val SCHEME = "content"

    class NoteColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favorite_table"
            const val USER_ID = "userId"
            const val USERNAME = "username"
            const val TYPE = "type"
            const val AVATAR = "avatar"

            val CONTENT_URI: Uri = Uri.Builder().apply {
                scheme(SCHEME)
                authority(AUTHORITY)
                appendPath(TABLE_NAME)
            }.build()
        }
    }
}