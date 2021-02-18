package com.example.assignmentapp.utils

import android.R
import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import dmax.dialog.SpotsDialog

object NetworkUtils {
    private lateinit var dialog: AlertDialog

    fun initDialog(context:Context){
        dialog = SpotsDialog.Builder().setContext(context).build()
    }

    fun Context.isInternetAvailable(): Boolean {
        try {
            val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            return if (netInfo != null && netInfo.isConnected) {
                true
            } else {
                showErrorToast("Internet not available. Please try again!!")
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    fun Context.showErrorToast(message: String?) {

        try {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    // show progressbar
    fun Context.showProgressBar() {
        try {
            dialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // hide progressbar
    fun hideProgressBar() {
        try {
            dialog?.let {
                it.dismiss()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}