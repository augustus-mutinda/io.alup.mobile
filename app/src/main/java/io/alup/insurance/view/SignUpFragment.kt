package io.alup.insurance.view

import io.alup.insurance.R
import io.alup.insurance.databinding.FragmentSignUpBinding
import io.alup.insurance.model.AlupState
import io.alup.insurance.model.Util.Companion.click
import io.alup.insurance.model.Util.Companion.showAuthOptions
import io.alup.insurance.model.Util.Companion.toast
import io.alup.insurance.model.Util.Companion.visible
import io.alup.insurance.model.Util.Companion.watch
import io.alup.insurance.model.custom.AlupFragment

/**
 * @author Augustus, augustusmutinda16@gmail.com
 * A view to register a new user
 * @see AlupFragment
 */
class SignUpFragment : AlupFragment<FragmentSignUpBinding>() {
    override fun onResume() {
        super.onResume()
        this.showAuthOptions()
        authViewModel.registerState.observe(this) {
            if (it is AlupState.Success) {
                navigateById(R.id.signInPhoneFragment)
                toast("Registration successful, please login")
            }
            if (it is AlupState.Failed) toast("Something went wrong, please try again")
            binding.register.visible(it !is AlupState.Loading)
            binding.registerLoading.visible(it is AlupState.Loading)
        }
        binding.register.click {
            authViewModel.checkRegisterPreFlight().let {
                if (it == null) authViewModel.register()
                else toast(it)
            }
        }
        binding.logIn.click { navigateById(R.id.signInPhoneFragment) }
        binding.editTextEmail.watch { authViewModel.email.postValue(it) }
        binding.editTextPhone.watch { authViewModel.phone.postValue(it) }
        binding.editTextFirstname.watch { authViewModel.firstName.postValue(it) }
        binding.editTextLastname.watch { authViewModel.lastName.postValue(it) }
    }
}