package io.alup.insurance.view.adapters

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * @author Augustus, augustusmutinda16@gmail.com
 * A generic view holder that holds a view binding
 * @param VB The view binding class
 * @param T The data class
 */
class RecyclerAdapter<T : Any, VB : ViewBinding> :
    RecyclerView.Adapter<Holder<VB>>() {
    private lateinit var watcher: (data: T, b: VB, pos: Int, end: Boolean) -> Unit
    private lateinit var b: (p: ViewGroup) -> VB
    private var i = AsyncListDiffer(
        this,
        object : DiffUtil.ItemCallback<T>() {
            override fun areItemsTheSame(oldItem: T, newItem: T) = isSame(oldItem, newItem)

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: T, newItem: T) = contentsSame(oldItem, newItem)
        })

    fun set(items: List<T>) {
        this.i.submitList(items)
    }

    fun watch(listener: (data: T, b: VB, pos: Int, end: Boolean) -> Unit) {
        this.watcher = listener
    }

    fun bind(binder: (p: ViewGroup) -> VB) {
        this.b = binder
    }

    fun isSame(x: T, y: T) = x == y

    fun contentsSame(x: T, y: T) = x == y

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = Holder(b.invoke(parent))

    override fun onBindViewHolder(holder: Holder<VB>, position: Int) {
        watcher.invoke(i.currentList[position], holder.binding, position, position == itemCount - 1)
    }

    override fun getItemCount() = i.currentList.size
}