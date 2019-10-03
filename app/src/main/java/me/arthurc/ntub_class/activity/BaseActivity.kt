package me.arthurc.ntub_class.activity

import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import me.arthurc.ntub_class.R

abstract class BaseActivity : AppCompatActivity() {

    private var progressDialog: AlertDialog? = null

    protected fun t(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    protected fun t(resId: Int) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
    }

    protected fun log(message: String, tag: String = "") {
        if (message.isBlank()) {
            return
        }

        Log.d(if (tag.isBlank()) getString(R.string.app_name) else tag, message)
    }

    protected fun showLoadingDialog(message: String = "") {
        val builder = AlertDialog.Builder(this)
        val dialogView = View.inflate(this, R.layout.progress_dialog, null)
        val messageTextView = dialogView.findViewById<TextView>(R.id.message)

        messageTextView.text = if (message.isNotBlank()) message else getString(R.string.loading)

        builder.setView(dialogView)
        builder.setCancelable(false)

        progressDialog = builder.create()
        progressDialog!!.show()
    }

    protected fun dismissLoadingDialog() {
        progressDialog?.dismiss()
    }

}