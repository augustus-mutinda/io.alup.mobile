package io.alup.insurance.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.viewbinding.ViewBinding
import io.alup.insurance.databinding.ContentListBinding
import io.alup.insurance.model.AlupState
import io.alup.insurance.model.Util.Companion.click
import io.alup.insurance.model.Util.Companion.showHomeOptions
import io.alup.insurance.model.Util.Companion.visible
import io.alup.insurance.model.custom.AlupFragment
import io.alup.insurance.view.adapters.RecyclerAdapter

/**
 * @author Augustus, augustusmutinda16@gmail.com
 * An abstract class for all list fragments
 * @param Model The model class
 * @param RVBinding The view binding class
 * @see AlupFragment
 */
abstract class AlupListFragment<Model : Any, RVBinding : ViewBinding> :
    AlupFragment<ContentListBinding>() {
    private var adapter = RecyclerAdapter<Model, RVBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ContentListBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onResume() {
        super.onResume()
        this.showHomeOptions()
        adapter.bind { p -> createBindingInstance(inflater(), p) }
        adapter.watch { data, b, pos, end -> watch(data, b, pos, end) }
        binding.rv.adapter = adapter
        liveData().observe(this){
            binding.llEmpty.visible(it is AlupState.Empty)
            binding.rv.visible(it is AlupState.Success)
            binding.llError.visible(it is AlupState.Failed)
            binding.llLoading.visible(it is AlupState.Loading)
            if (it is AlupState.Success) adapter.set(it.data)
            if (it is AlupState.Default) fetch()
        }
        binding.fetch.click { fetch() }
        binding.retry.click { fetch() }
        binding.swipeRefresh.setOnRefreshListener {
            fetch()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    abstract fun liveData(): MutableLiveData<AlupState<List<Model>>>

    abstract fun fetch()

    open fun watch(data: Model, b: RVBinding, pos: Int, end: Boolean) {}
}