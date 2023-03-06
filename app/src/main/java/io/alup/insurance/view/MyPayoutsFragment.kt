package io.alup.insurance.view

import dagger.hilt.android.AndroidEntryPoint
import io.alup.insurance.databinding.RecyclerNotificationItemBinding
import io.alup.insurance.model.Util.Companion.format
import io.alup.insurance.model.Util.Companion.now
import io.alup.insurance.model.Util.Companion.set
import io.alup.insurance.model.custom.AlupFragment
import io.alup.insurance.model.data.Payout

/**
 * @author Augustus, augustusmutinda16@gmail.com
 * An overview of user payouts
 * @see AlupFragment
 */
@AndroidEntryPoint
class MyPayoutsFragment : AlupListFragment<Payout, RecyclerNotificationItemBinding>() {
    override fun fetch() = homeViewModel.getPayouts()

    override fun liveData() = homeViewModel.payOutsState

    override fun watch(data: Payout, b: RecyclerNotificationItemBinding, pos: Int, end: Boolean) {
        b.tvTitle.set(data.title)
        b.tvDesc.set(data.message)
        b.tvDate.set((data.dateCreated ?: now()).format())
    }
}