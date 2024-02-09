package com.example.abschlussprojektmyapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.abschlussprojektmyapp.MainViewmodel
import com.example.abschlussprojektmyapp.adapter.MarketAdapter
import com.example.abschlussprojektmyapp.adapter.TopMarketAdapter
import com.example.abschlussprojektmyapp.databinding.FragmentTopLossGainBinding

class TopLossGainFragment : Fragment() {

    private val viewModel: MainViewmodel by activityViewModels()
    lateinit var binding: FragmentTopLossGainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTopLossGainBinding.inflate(layoutInflater)

        //getMarketData()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMarketData()

        viewModel.market.observe(viewLifecycleOwner) {

            binding.topGainLoseRecyclerView.adapter =
                TopMarketAdapter(requireContext(), it.data.cryptoCurrencyList)

            binding.topGainLoseRecyclerView.adapter =
                MarketAdapter(requireContext(), it.data.cryptoCurrencyList)

            val position = requireArguments().getInt("position")
        }
    }
}




