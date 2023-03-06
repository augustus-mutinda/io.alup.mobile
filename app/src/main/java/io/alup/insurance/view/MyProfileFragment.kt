package io.alup.insurance.view

import dagger.hilt.android.AndroidEntryPoint
import io.alup.insurance.R
import io.alup.insurance.databinding.FragmentMyProfileBinding
import io.alup.insurance.model.AlupState
import io.alup.insurance.model.Util.Companion.click
import io.alup.insurance.model.Util.Companion.set
import io.alup.insurance.model.Util.Companion.setRound
import io.alup.insurance.model.custom.AlupFragment
import io.alup.insurance.model.data.User

/**
 * @author Augustus, augustusmutinda16@gmail.com
 * An overview of user profile
 * @see AlupFragment
 */
@AndroidEntryPoint
class MyProfileFragment : AlupFragment<FragmentMyProfileBinding>() {

    override fun onResume() {
        super.onResume()
        authViewModel.currentUserState.observe(this) {
            if (it is AlupState.Success) setupUser(it.data)
            if (it is AlupState.Default) authViewModel.getCurrent()
        }
        binding.llGroups.click { navigateById(R.id.myPoliciesFragment) }
        binding.llTransactions.click { navigateById(R.id.myPayoutsFragment) }
        binding.llAccountSettings.click { comingSoon() }
        binding.llNotificationSettings.click { comingSoon() }
        binding.llSignOut.click {
            SignOutPromptFragment { signOut() }.show(
                childFragmentManager,
                "s"
            )
        }
    }

    private fun signOut() {
        authViewModel.signOut()
        pop(R.id.splashFragment)
    }

    private fun setupUser(data: User) {
        binding.tvName.set("${data.firstName} ${data.lastName}")
        binding.tvUsername.set(data.userName)
        data.avatars?.let { if (it.isNotEmpty()) binding.ivLogo.setRound(it[0]) }
    }
}