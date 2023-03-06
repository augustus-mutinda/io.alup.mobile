package io.alup.insurance.view.adapters

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * @author Augustus, augustusmutinda16@gmail.com
 * A generic view holder that holds a view binding
 * @param VH The view binding class
 * @param binding The view binding
 */
class Holder<VH : ViewBinding>(var binding: VH) : RecyclerView.ViewHolder(binding.root)