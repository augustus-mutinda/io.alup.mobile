package io.alup.insurance.model

import android.content.SharedPreferences
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import io.alup.insurance.AlupActivity
import io.alup.insurance.R
import io.alup.insurance.model.Constants.Companion.TKN
import io.alup.insurance.model.data.AlupUI
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Augustus, augustusmutinda16@gmail.com
 * A collection of utility functions
 */
class Util {
    companion object {
        fun SharedPreferences.getAuthToken() = "Bearer ${this.getString(TKN, "")?.reversed()}"

        fun View.visible(visible: Boolean?) {
            this.visibility =
                when (visible) {
                    null -> View.INVISIBLE
                    true -> View.VISIBLE
                    else -> View.GONE
                }
        }

        fun String.fullMsisdn(): String {
            return if (this.startsWith("+")) {
                this.removePrefix("+")
            } else {
                val msisdn =
                    if (this.length > 8)
                        this.substring(this.length - 9, this.length)
                    else
                        this

                "254$msisdn"
            }
        }

        fun EditText.watch(listener: (String) -> Unit) {
            this.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    listener.invoke(s?.toString() ?: "")
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })
        }

        fun TextView.set(text: String) {
            this.text = text
        }

        fun ImageView.setRes(res: Int) {
            Glide.with(this)
                .load(res)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(this)
        }

        fun ImageView.setRound(res: String) {
            Glide.with(this)
                .load(res)
                .centerCrop()
                .circleCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(this)
        }

        fun Fragment.toast(message: String) {
            Toast.makeText(this.requireContext(), message, Toast.LENGTH_SHORT).show()
        }

        fun View.click(listener: () -> Unit) {
            this.setOnClickListener { listener.invoke() }
        }

        fun now() = Calendar.getInstance().time

        fun Date.format(): String {
            val format = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            return format.format(this)
        }


        fun Fragment.showAuthOptions() {
            this.showHomeOptions(false, false)
        }

        fun Fragment.showHomeOptions() {
            this.showHomeOptions(true, true)
        }

        fun Fragment.showHomeOptions(st: Boolean, sb: Boolean) {
            (activity as AlupActivity).homeViewModel.ui.postValue(
                AlupUI().apply {
                    showToolbar = st
                    showBottomNav = sb
                })
        }
    }
}