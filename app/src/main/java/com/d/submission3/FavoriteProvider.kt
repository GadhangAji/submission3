//package com.d.submission3
//
//import android.content.ContentProvider
//import android.content.ContentValues
//import android.content.Context
//import android.content.UriMatcher
//import android.database.Cursor
//import android.net.Uri
//import com.d.submission3.data.Database
//import com.d.submission3.data.FavData.AUTHORITY
//import com.d.submission3.data.FavData.NoteColumns.Companion.CONTENT_URI
//import com.d.submission3.data.FavData.NoteColumns.Companion.TABLE_NAME
//
//class FavoriteProvider : ContentProvider() {
//    companion object {
//        private const val USER = 1
//        private const val USERID = 2
//
//        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
//        private lateinit var db: Database
//
//        init {
//            uriMatcher.addURI(AUTHORITY, TABLE_NAME, USER)
//            uriMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", USERID)
//        }
//    }
//
//    override fun onCreate(): Boolean {
//        db = Database.getInstance(context as Context)
//        return false
//    }
//
////    override fun query(
////        uri: Uri,
////        projection: Array<out String>?,
////        selection: String?,
////        selectionArgs: Array<out String>?,
////        sortOrder: String?
////    ): Cursor? {
////        return when (uriMatcher.match(uri)) {
////            USER -> db.favoriteDao().getUsers()
////            USERID -> uri.lastPathSegment?.toInt()?.let { db.favoriteDao().getUserById(it) }
////            else -> null
////        }
////    }
//
////    override fun insert(uri: Uri, values: ContentValues?): Uri? {
////        val added: Long = when (USER) {
////            uriMatcher.match(uri) -> values?.toFavorite()?.let {
////                db.favoriteDao().insertUser(it)
////            } ?: 0
////            else -> 0
////        }
////
////        context?.contentResolver?.notifyChange(CONTENT_URI, null)
////
//////        refreshWidget()
////
////        return Uri.parse("$CONTENT_URI/$added")
////    }
//
//    override fun update(
//        uri: Uri,
//        values: ContentValues?,
//        selection: String?,
//        selectionArgs: Array<out String>?
//    ): Int {
//        return 0
//    }
//
//    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
//        val deleted: Int = when (USERID) {
//            uriMatcher.match(uri) -> uri.lastPathSegment?.toInt()?.let {
//                db.favoriteDao().deleteUser(
//                    it
//                )
//            } ?: 0
//            else -> 0
//        }
//
//        context?.contentResolver?.notifyChange(CONTENT_URI, null)
//
////        refreshWidget()
//
//        return deleted
//    }
//
//    override fun getType(uri: Uri): String? {
//        return null
//    }
//
////    private fun refreshWidget() {
////        FavoriteWidget.sendRefreshBroadcast(context as Context)
////    }
//}