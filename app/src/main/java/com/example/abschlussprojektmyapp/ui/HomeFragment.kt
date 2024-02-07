package com.example.abschlussprojektmyapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.abschlussprojektmyapp.MainViewmodel
import com.example.abschlussprojektmyapp.adapter.NewsAdapter
import com.example.abschlussprojektmyapp.adapter.TopLossGainPagerAdapter
import com.example.abschlussprojektmyapp.adapter.TopMarketAdapter
import com.example.abschlussprojektmyapp.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    private val viewModel: MainViewmodel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        //getTopCurrencyList()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getBusinessNews()

        viewModel.newsList.observe(viewLifecycleOwner) {

            binding.topNewsRecyclerView.adapter = NewsAdapter(it.articles)

        }

        viewModel.getMarketData()

        viewModel.market.observe(viewLifecycleOwner) {

            binding.detailSymbolTextView2.text//setText("Welkome")

            binding.topCurrencyRecyclerView.adapter =
                TopMarketAdapter(requireContext(), it.data.cryptoCurrencyList)
        }

        val adapter = TopLossGainPagerAdapter(this)
        binding.contentViewPager.adapter = adapter
        binding.contentViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 0) {
                    binding.topGainIndicator.visibility = View.VISIBLE
                    binding.topLoseIndicator.visibility = View.GONE
                } else {
                    binding.topGainIndicator.visibility = View.GONE
                    binding.topLoseIndicator.visibility = View.VISIBLE

                }
            }
        })
        TabLayoutMediator(binding.tabLayout, binding.contentViewPager) { tab, position ->
            var title = if (position == 0) {
                "Top Gainers"
            } else {
                "Top Losers"
            }
            tab.text = title
        }.attach()


    }


}













/*
    private fun getTopCurrencyList() {
        lifecycleScope.launch(Dispatchers.IO) {
            val res = ApiUtilities.getInstance().create(ApiUtilities.ApiInterface::class.java).getMarketData()
            withContext(Dispatchers.Main) {
                binding.topCurrencyRecyclerView.adapter =
                    TopMarketAdapter(requireContext(), res.body()!!.data.cryptoCurrencyList)
            }
            Log.d("Test", "getTopCurrencyList: ${res.body()!!.data.cryptoCurrencyList}")
        }

    }
     */












