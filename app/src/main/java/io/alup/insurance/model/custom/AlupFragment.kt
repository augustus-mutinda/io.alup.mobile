package io.alup.insurance.model.custom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.viewbinding.ViewBinding
import io.alup.insurance.AlupActivity
import io.alup.insurance.AlupApplication
import io.alup.insurance.model.Util.Companion.showHomeOptions
import io.alup.insurance.model.Util.Companion.toast
import io.alup.insurance.viewModel.AuthViewModel
import io.alup.insurance.viewModel.HomeViewModel
import java.lang.reflect.ParameterizedType

/**
 * @author Augustus, augustusmutinda16@gmail.com
 * An abstract class for all fragments
 * @param Binding The view binding class
 * @see Fragment
 * @see ViewBinding
 * @see AlupActivity
 * @see AlupApplication
 */
@Suppress("UNCHECKED_CAST")
open class AlupFragment<Binding : ViewBinding> : Fragment() {
    lateinit var binding: Binding

    val authViewModel by activityViewModels<AuthViewModel>()
    val homeViewModel by activityViewModels<HomeViewModel>()

    fun inflater() = layoutInflater

    fun lifecycle() = lifecycle

    fun app() = (requireActivity() as AlupActivity).application as AlupApplication

    fun comingSoon() {
        toast("Coming soon")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = createBindingInstance<Binding>(inflater, container).also { binding = it }.root

    @Suppress("UNCHECKED_CAST")
    open fun <VBX> createBindingInstance(inflater: LayoutInflater, container: ViewGroup?): VBX {
        val vbType =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.let { it[it.size - 1] }
        val vbClass = vbType as Class<VBX>
        val method = vbClass.getMethod(
            "inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java
        )

        return method.invoke(null, inflater, container, false) as VBX
    }

    fun navigateById(id: Int) {
        findNavController(this).navigate(id)
    }

    fun Fragment.pop(direction: Int) {
        (this.requireActivity() as AlupActivity).controller.popBackStack(
            direction,
            true
        )
    }

    override fun onResume() {
        super.onResume()
        this.showHomeOptions()
    }
}