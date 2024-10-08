package com.example.abschlussprojektmyapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.abschlussprojektmyapp.MainViewModel
import com.example.abschlussprojektmyapp.adapter.MarketAdapter
import com.example.abschlussprojektmyapp.databinding.FragmentTopLossGainBinding

class TopLossGainFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    lateinit var binding: FragmentTopLossGainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTopLossGainBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMarketData()

        viewModel.market.observe(viewLifecycleOwner) {

            binding.topGainLoseRecyclerView.adapter =
                MarketAdapter(it.data.cryptoCurrencyList,viewModel, requireContext())

            binding.button3.setOnClickListener {
                viewModel.getMarketData()
            }
        }
    }
}