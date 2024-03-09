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
import java.util.Timer
import java.util.TimerTask

class HomeFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding
    //private var currentPageNews = 0
    private var currentPageCurrency = 0
    //private var timerNews: Timer? = null
    private var timerCurrency: Timer? = null

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
            //startAutoScrollNews()
        }

        viewModel.getMarketData()
        viewModel.market.observe(viewLifecycleOwner) {
            binding.topCurrencyRecyclerView.adapter = TopMarketAdapter(it.data.cryptoCurrencyList, viewModel, requireContext())
            startAutoScrollCurrency()
        }

        val adapter = TopLossGainPagerAdapter(this)
        binding.contentViewPager.adapter = adapter
    }

    /*private fun startAutoScrollNews() {
        timerNews = Timer()
        timerNews?.schedule(object : TimerTask() {
            override fun run() {
                activity?.runOnUiThread {
                    if (currentPageNews == viewModel.newsList.value?.articles?.size) {
                        currentPageNews = 0
                    }
                    binding.topNewsRecyclerView.smoothScrollToPosition(currentPageNews++)
                }
            }
        }, 0, 5000) // Change the interval as needed for news RecyclerView
    }

     */

    private fun startAutoScrollCurrency() {
        timerCurrency = Timer()
        timerCurrency?.schedule(object : TimerTask() {
            override fun run() {
                activity?.runOnUiThread {
                    if (currentPageCurrency == viewModel.market.value?.data?.cryptoCurrencyList?.size ) {
                        currentPageCurrency = 0
                    }
                    binding.topCurrencyRecyclerView.smoothScrollToPosition(currentPageCurrency++)
                }
            }
        }, 0, 5000) // Change the interval as needed for currency RecyclerView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //timerNews?.cancel()
        timerCurrency?.cancel()
    }
}














































/*

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
            viewModel.getMarketData()
            viewModel.market.observe(viewLifecycleOwner) {
                binding.topCurrencyRecyclerView.adapter =
                    TopMarketAdapter(it.data.cryptoCurrencyList, viewModel, requireContext())
            }
            val adapter = TopLossGainPagerAdapter(this)
            binding.contentViewPager.adapter = adapter

        }
    }

 */



























