package com.example.abschlussprojektmyapp.ui

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.example.abschlussprojektmyapp.MainViewModel
import com.example.abschlussprojektmyapp.R
import com.example.abschlussprojektmyapp.adapter.NewsAdapter
import com.example.abschlussprojektmyapp.adapter.TopLossGainPagerAdapter
import com.example.abschlussprojektmyapp.adapter.TopMarketAdapter
import com.example.abschlussprojektmyapp.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                //Uri bezieht sich auf das Bild
                viewModel.uploadProfilePicture(uri)
            }
        }

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

        viewModel.selectedItem.observe(viewLifecycleOwner) {
            binding.imageView8.load(it.urlToImage)
        }

        viewModel.newsList.observe(viewLifecycleOwner) {
            binding.topNewsRecyclerView.adapter = NewsAdapter(it.articles, viewModel)

        }

        viewModel.getMarketData()

        viewModel.market.observe(viewLifecycleOwner)
        {

            binding.topCurrencyRecyclerView.adapter =
                TopMarketAdapter(requireContext(), it.data.cryptoCurrencyList)
        }

        binding.button2.setOnClickListener {
            viewModel.getMarketData()

        }

        val adapter = TopLossGainPagerAdapter(this)
        binding.contentViewPager.adapter = adapter
        binding.contentViewPager.registerOnPageChangeCallback(
            object :
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
        TabLayoutMediator(binding.tabLayout, binding.contentViewPager)
        { tab, position ->
            var title = if (position == 0) {
                "Top Gainers"
            } else {
                "Top Losers"
            }
            tab.text = title
        }.attach()


    }
}



















