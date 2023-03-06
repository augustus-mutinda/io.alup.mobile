package io.alup.insurance.view

import io.alup.insurance.R
import io.alup.insurance.databinding.FragmentNewFarmBinding
import io.alup.insurance.model.Util.Companion.click
import io.alup.insurance.model.custom.AlupFragment

/**
 * @author Augustus, augustusmutinda16@gmail.com
 * A view to register a new user
 * @see AlupFragment
 */
class NewFarmFragment : AlupFragment<FragmentNewFarmBinding>() {
    override fun onResume() {
        super.onResume()
        binding.create.click {
            pop(R.id.farmsFragment)
            navigateById(R.id.farmsFragment)
        }
    }
}