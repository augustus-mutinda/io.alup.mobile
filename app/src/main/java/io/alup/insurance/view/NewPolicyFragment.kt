package io.alup.insurance.view

import io.alup.insurance.R
import io.alup.insurance.databinding.FragmentNewPolicyBinding
import io.alup.insurance.model.Util.Companion.click
import io.alup.insurance.model.custom.AlupFragment

/**
 * @author Augustus, augustusmutinda16@gmail.com
 * A view to create a new policy
 * @see AlupFragment
 */
class NewPolicyFragment : AlupFragment<FragmentNewPolicyBinding>() {
    override fun onResume() {
        super.onResume()
        binding.create.click {
            pop(R.id.myPoliciesFragment)
            navigateById(R.id.myPoliciesFragment)
        }
    }
}