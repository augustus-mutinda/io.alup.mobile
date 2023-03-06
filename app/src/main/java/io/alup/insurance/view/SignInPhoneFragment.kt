package io.alup.insurance.view

import io.alup.insurance.R
import io.alup.insurance.databinding.FragmentSignInBinding
import io.alup.insurance.model.AlupState
import io.alup.insurance.model.Util.Companion.showAuthOptions
import io.alup.insurance.model.Util.Companion.toast
import io.alup.insurance.model.Util.Companion.visible
import io.alup.insurance.model.Util.Companion.watch
import io.alup.insurance.model.custom.AlupFragment

/**
 * @author Augustus, augustusmutinda16@gmail.com
 * A view to request otp using phone number
 * @see AlupFragment
 */
class SignInPhoneFragment : AlupFragment<FragmentSignInBinding>() {

    override fun onResume() {
        super.onResume()
        this.showAuthOptions()
        authViewModel.signInState.observe(this) {
            binding.llProgress.visible(it is AlupState.Loading)
            binding.tvLogin.visible(it !is AlupState.Loading)
            if (it is AlupState.Verify) navigateById(R.id.signInOtpFragment)
            if (it is AlupState.Failed) toast(it.error)
        }

        binding.tvLogin.setOnClickListener {
            authViewModel.checkPreFlight().let {
                if (it == null) authViewModel.getOtp()
                else {
                    toast(it)
                    binding.editTextPhone.requestFocus()
                }
            }
        }
        binding.tvRegister.setOnClickListener { navigateById(R.id.signUpFragment) }
        binding.editTextPhone.watch { authViewModel.phone.postValue(it) }
    }
}