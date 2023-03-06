package io.alup.insurance.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import io.alup.insurance.R
import io.alup.insurance.databinding.FragmentHomeBinding
import io.alup.insurance.databinding.RecyclerQuickLinkItemBinding
import io.alup.insurance.model.AlupState
import io.alup.insurance.model.Util.Companion.click
import io.alup.insurance.model.Util.Companion.set
import io.alup.insurance.model.Util.Companion.setRes
import io.alup.insurance.model.Util.Companion.setRound
import io.alup.insurance.model.Util.Companion.toast
import io.alup.insurance.model.Util.Companion.visible
import io.alup.insurance.model.custom.AlupFragment
import io.alup.insurance.model.data.QuickLink
import io.alup.insurance.model.data.User
import io.alup.insurance.view.adapters.RecyclerAdapter

/**
 * @author Augustus, augustusmutinda16@gmail.com
 * An overview of  various functions of the app
 * @see AlupFragment
 */
class HomeFragment : AlupFragment<FragmentHomeBinding>() {
    private var adapter = RecyclerAdapter<QuickLink, RecyclerQuickLinkItemBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentHomeBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onResume() {
        super.onResume()
        authViewModel.currentUserState.observe(this) {
            if (it is AlupState.Success) setupUser(it.data)
            if (it is AlupState.Default) authViewModel.getCurrent()
            binding.llLoading.visible(it is AlupState.Loading)
            binding.llLoaded.visible(it !is AlupState.Loading)
        }

        adapter.bind { p -> RecyclerQuickLinkItemBinding.inflate(inflater(), p, false) }
        adapter.watch { data, b, pos, end -> watch(data, b, pos, end) }
        binding.rvQuickActions.layoutManager = GridLayoutManager(context, 2)
        binding.rvQuickActions.adapter = adapter
        adapter.set(getQuickLinks())
    }

    private fun watch(data: QuickLink, b: RecyclerQuickLinkItemBinding, pos: Int, end: Boolean) {
        b.ivIcon.setRes(data.icon)
        b.tvTitle.set(data.title)
        b.tvDesc.set(data.desc)
        b.root.click {
            if (data.navigate) navigateById(data.navId)
            else toast("Not implemented yet")
        }
    }

    private fun getQuickLinks() = listOf(
        QuickLink(
            "New Policy",
            "Apply for a new policy",
            R.drawable.plus_solid,
            true,
            R.id.newPolicyFragment
        ),
        QuickLink(
            "My Policies",
            "View active policies",
            R.drawable.shield_bolt,
            true,
            R.id.myPoliciesFragment
        ),
        QuickLink(
            "Create a Farm",
            "Create a new farm",
            R.drawable.location_pin_plus,
            true,
            R.id.newFarmFragment
        ),
        QuickLink(
            "My Farms",
            "View your farms",
            R.drawable.map_location,
            true,
            R.id.farmsFragment
        )
    )

    private fun setupUser(data: User) {
        binding.tvName.set("${data.firstName} ${data.lastName}")
        binding.tvStatus.set("Insured")
        data.avatars?.let { if (it.isNotEmpty()) binding.ivAvatar.setRound(it[0]) }
    }
}