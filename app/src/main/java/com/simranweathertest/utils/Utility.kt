package com.simranweathertest.utils

import android.app.ProgressDialog
import android.content.Context
import com.simranweathertest.R

class Utility {
    companion object {
        private val progressDialog: ProgressDialog? = null

        /**
         * this function is use for showing progress dialog
         */
        open fun showDialog(context: Context) {
            try {
                if (progressDialog != null) {
                    progressDialog.setCancelable(false)
                    progressDialog.setMessage(context.resources.getString(R.string.please_wait))
                    // progressDialog.setContentView(R.layout.loader_screen);
                    progressDialog.show()
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }

        /**
         * this function is use for hiding progress dialog
         */
        open fun hideDialog() {
            try {
                if (progressDialog != null) {
                    progressDialog.dismiss()
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }
}