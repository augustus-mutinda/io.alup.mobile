package io.alup.insurance.view

import dagger.hilt.android.AndroidEntryPoint
import io.alup.insurance.databinding.RecyclerNotificationItemBinding
import io.alup.insurance.model.Util.Companion.format
import io.alup.insurance.model.Util.Companion.now
import io.alup.insurance.model.Util.Companion.set
import io.alup.insurance.model.custom.AlupFragment
import io.alup.insurance.model.data.Note

/**
 * @author Augustus, augustusmutinda16@gmail.com
 * A view to show all farms
 * @see AlupFragment
 */
@AndroidEntryPoint
class FarmsFragment : AlupListFragment<Note, RecyclerNotificationItemBinding>() {
    override fun fetch() = homeViewModel.getNotifications()

    override fun liveData() = homeViewModel.notificationsState

    override fun watch(data: Note, b: RecyclerNotificationItemBinding, pos: Int, end: Boolean) {
        b.tvTitle.set(data.title)
        b.tvDesc.set(data.message)
        b.tvDate.set((data.dateCreated ?: now()).format())
    }
}