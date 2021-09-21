package com.abdullah.socialmedia.utils

import android.content.Context
import android.content.Intent
import com.abdullah.socialmedia.R
import com.abdullah.socialmedia.common.Const

object ShareUtil {
    fun sharePost(context: Context, content: String) {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = Const.INTENT_TYPE_TEXT
            val shareMessage =
                context.getString(R.string.post_to_share, content)
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            context.startActivity(
                Intent.createChooser(
                    shareIntent,
                    context.getString(R.string.choose_one)
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}