package com.example.abschlussprojektmyapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.abschlussprojektmyapp.MainViewmodel
import com.example.abschlussprojektmyapp.adapter.TopLossGainPagerAdapter
import com.example.abschlussprojektmyapp.adapter.TopMarketAdapter
import com.example.abschlussprojektmyapp.data.Repository
import com.example.abschlussprojektmyapp.data.remote.Api
import com.example.abschlussprojektmyapp.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private val viewModel: MainViewmodel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        //getTopCurrencyList()

        setTabCurrencyList()

        return binding.root
    }

    private fun setTabCurrencyList() {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMarketData()

        viewModel.market.observe(viewLifecycleOwner) {
            binding.topCurrencyRecyclerView.adapter =
                TopMarketAdapter(requireContext(), it.data.cryptoCurrencyList)
        }


        /*private fun getTopCurrencyList() {
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


        /*

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            viewModel.getTopCurrencyList()

            viewModel.setTabCurrencyList()

        }

         */

    }
}


/*


/**
     * Wird aufgerufen, wenn die zugehörige Activity erstellt wurde. Hier werden die UI-Elemente initialisiert und Daten geladen.
     * @param view Die View des Fragments.
     * @param savedInstanceState Wenn das Fragment neu erstellt wird, enthält dieser Parameter den gespeicherten Zustand.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

 */









