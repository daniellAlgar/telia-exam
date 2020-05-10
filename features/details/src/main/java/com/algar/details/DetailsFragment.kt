package com.algar.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.algar.common.BaseFragment
import com.algar.common.BaseViewModel
import com.algar.details.databinding.FragmentDetailsBinding
import org.koin.android.ext.android.inject

class DetailsFragment : BaseFragment() {

    private val args: DetailsFragmentArgs by navArgs()
    private val viewModel: DetailsViewModel by inject()

    private lateinit var binding: FragmentDetailsBinding

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.cityId = args.cityId
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()
        viewModel.fetchFiveDayForecast(id = args.cityId)
    }

    private fun configureRecyclerView() {
        binding.fiveDayForecastRecyclerView.adapter = DetailsAdapter(cityName = args.cityName)
        binding.fiveDayForecastRecyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
    }

    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.supportActionBar?.title = args.cityName
    }
}