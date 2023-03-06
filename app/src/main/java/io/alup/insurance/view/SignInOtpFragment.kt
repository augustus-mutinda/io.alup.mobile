package io.alup.insurance.view

import io.alup.insurance.R
import io.alup.insurance.databinding.FragmentSignInOtpBinding
import io.alup.insurance.model.AlupState
import io.alup.insurance.model.Util.Companion.click
import io.alup.insurance.model.Util.Companion.set
import io.alup.insurance.model.Util.Companion.showAuthOptions
import io.alup.insurance.model.Util.Companion.toast
import io.alup.insurance.model.Util.Companion.visible
import io.alup.insurance.model.Util.Companion.watch
import io.alup.insurance.model.custom.AlupFragment


/**
 * @author Augustus, augustusmutinda16@gmail.com
 * A view to verify the user's phone number using otp
 * @see AlupFragment
 */
class SignInOtpFragment : AlupFragment<FragmentSignInOtpBinding>() {

    override fun onResume() {
        super.onResume()
        this.showAuthOptions()
        authViewModel.resendCounterState.observe(this) {
            if (it is AlupState.Success)
                it.data.let { count ->
                    binding.resendPlaceholder.set("Resend${if (count > 0) " in $count seconds" else ""}")
                    binding.resendPlaceholder.visible(count > 0)
                    binding.resend.visible(count < 1)
                }
            binding.resendLoading.visible(it !is AlupState.Success)
        }

        authViewModel.verifyOtpState.observe(this) {
            if (it is AlupState.Failed) toast("We couldn't verify your OTP. Please try again")
            if (it is AlupState.Success) {
                toast("Welcome back, you are now logged in")
                navigateById(R.id.homeFragment)
            }
            binding.verify.visible(it !is AlupState.Loading)
            binding.verifyLoading.visible(it is AlupState.Loading)
        }

        binding.resend.click { authViewModel.getOtp() }
        binding.verify.click { verify(null) }
        binding.editTextOtp.watch { authViewModel.otp = it }
    }

    private fun verify(otp: String?) {
        authViewModel.checkOtpPreFlight().let {
            if (it == null) authViewModel.verifyOtp(otp)
            else toast(it)
        }
    }
}