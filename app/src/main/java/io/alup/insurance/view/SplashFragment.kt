package io.alup.insurance.view

import dagger.hilt.android.AndroidEntryPoint
import io.alup.insurance.R
import io.alup.insurance.databinding.FragmentSplashBinding
import io.alup.insurance.model.AlupState
import io.alup.insurance.model.Util.Companion.showAuthOptions
import io.alup.insurance.model.Util.Companion.visible
import io.alup.insurance.model.custom.AlupFragment

/**
 * @author Augustus, augustusmutinda16@gmail.com
 * Entry point to the app, checks if there's a token, if not, shows login and register options
 * If there's a token, navigates to home
 * @see AlupFragment
 */
@AndroidEntryPoint
class SplashFragment : AlupFragment<FragmentSplashBinding>() {

    override fun onResume() {
        super.onResume()
        this.showAuthOptions()
        authViewModel.initiate()
        binding.tvLogin.setOnClickListener { navigateById(R.id.signInPhoneFragment) }
        binding.tvRegister.setOnClickListener { navigateById(R.id.signUpFragment) }
        authViewModel.authState.observe(this) {
            binding.llLoaded.visible(it !is AlupState.Loading)
            binding.llLoading.visible(it is AlupState.Loading)
            if (it is AlupState.Resolved) navigateById(if (it.data) R.id.homeFragment else R.id.signInPhoneFragment)
        }
    }
}