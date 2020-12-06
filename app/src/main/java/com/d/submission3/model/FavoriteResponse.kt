package com.d.submission3.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.d.submission3.data.FavData.NoteColumns.Companion.AVATAR
import com.d.submission3.data.FavData.NoteColumns.Companion.TABLE_NAME
import com.d.submission3.data.FavData.NoteColumns.Companion.TYPE
import com.d.submission3.data.FavData.NoteColumns.Companion.USERNAME
import com.d.submission3.data.FavData.NoteColumns.Companion.USER_ID
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = TABLE_NAME)
data class FavoriteResponse(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = USER_ID) val userId: Int?,
    @ColumnInfo(name = USERNAME) val username: String?,
    @ColumnInfo(name = AVATAR) val avatar: String?
): Parcelable