package com.ellipsonic.vultus.Utils


import android.content.Context
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.util.ArrayList


object Utility {


    fun displayMessageAlert(Message: String, context: Context) {
        try {
            val builder = AlertDialog.Builder(context)
            builder.setMessage(Message)
                    .setPositiveButton("OK") { dialog, which -> }.create().show()
        } catch (e: Exception) {

            e.printStackTrace()
        }

    }


    fun centerToolbarTitle(toolbar: Toolbar) {
        val title = toolbar.title
        val outViews = ArrayList<View>(1)
        toolbar.findViewsWithText(outViews, title, View.FIND_VIEWS_WITH_TEXT)
        if (!outViews.isEmpty()) {
            val titleView = outViews[0] as TextView
            titleView.gravity = Gravity.CENTER
            val layoutParams = titleView.layoutParams as Toolbar.LayoutParams
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            toolbar.requestLayout()

        }
    }


}
