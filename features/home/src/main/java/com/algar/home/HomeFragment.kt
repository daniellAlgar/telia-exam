package com.algar.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.algar.common.BaseFragment
import com.algar.common.BaseViewModel
import com.algar.home.databinding.FragmentHomeBinding
import com.algar.home.views.HomeAdapter
import org.koin.android.ext.android.inject

class HomeFragment : BaseFragment() {

    private val viewModel: HomeViewModel by inject()
    private lateinit var viewBinding: FragmentHomeBinding

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentHomeBinding.inflate(inflater, container, false)
        viewBinding.viewModel = viewModel
        viewBinding.lifecycleOwner = viewLifecycleOwner
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        configureRecyclerView()
    }

    private fun configureRecyclerView() {
        viewBinding.forecastRecyclerView.adapter = HomeAdapter(viewModel = viewModel)
        viewBinding.forecastRecyclerView.addItemDecoration(getDividerItemDecorator())
    }

    private fun getDividerItemDecorator() = DividerItemDecoration(context, RecyclerView.VERTICAL).apply {
        setDrawable(ContextCompat.getDrawable(context!!, R.drawable.divider_item_decorator)!!)
    }

    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.home_toolbar_title)
    }
}