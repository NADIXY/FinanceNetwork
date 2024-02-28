package com.example.abschlussprojektmyapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.abschlussprojektmyapp.MainViewModel
import com.example.abschlussprojektmyapp.R
import com.example.abschlussprojektmyapp.adapter.NewsAdapter
import com.example.abschlussprojektmyapp.adapter.TopLossGainPagerAdapter
import com.example.abschlussprojektmyapp.adapter.TopMarketAdapter
import com.example.abschlussprojektmyapp.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageView2.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }


        viewModel.getBusinessNews()

        viewModel.newsList.observe(viewLifecycleOwner) {
            binding.topNewsRecyclerView.adapter = NewsAdapter(it.articles, viewModel)
        }

        viewModel.getMarketData()
        viewModel.market.observe(viewLifecycleOwner) {
            binding.topCurrencyRecyclerView.adapter =
                TopMarketAdapter(requireContext(), it.data.cryptoCurrencyList)
        }

        val adapter = TopLossGainPagerAdapter(this)
        binding.contentViewPager.adapter = adapter

    }
}
























