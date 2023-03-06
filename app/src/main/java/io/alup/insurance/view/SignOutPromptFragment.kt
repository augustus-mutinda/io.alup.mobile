package io.alup.insurance.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.alup.insurance.databinding.FragmentSignOutPromptBinding
import io.alup.insurance.model.custom.AlupFragment

/**
 * @author Augustus, augustusmutinda16@gmail.com
 * A popup to confirm sign out
 * @param prompt the function to execute when the user confirms sign out
 * @see AlupFragment
 */
class SignOutPromptFragment(var prompt: () -> Unit) : BottomSheetDialogFragment() {
    lateinit var b: FragmentSignOutPromptBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSignOutPromptBinding.inflate(inflater, container, false).also { b = it }.root

    override fun onResume() {
        super.onResume()
        b.btnSignOut.setOnClickListener {
            prompt()
            dismiss()
        }
        b.btnCancel.setOnClickListener { dismiss() }
    }
}