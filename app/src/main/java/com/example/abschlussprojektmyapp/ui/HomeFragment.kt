package com.example.abschlussprojektmyapp.ui

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.example.abschlussprojektmyapp.MainViewmodel
import com.example.abschlussprojektmyapp.R
import com.example.abschlussprojektmyapp.adapter.NewsAdapter
import com.example.abschlussprojektmyapp.adapter.TopLossGainPagerAdapter
import com.example.abschlussprojektmyapp.adapter.TopMarketAdapter
import com.example.abschlussprojektmyapp.data.model.Profile
import com.example.abschlussprojektmyapp.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    private val viewModel: MainViewmodel by activityViewModels()
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

        //getTopCurrencyList()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logoutBTN.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Log out")
            builder.setMessage("Do you really want to log out?")
            builder.setPositiveButton("Yes") { dialog, which ->
                viewModel.logout()
                Toast.makeText(requireContext(), "You are logged out", Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("Don't") { dialog, which -> }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        viewModel.user.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.userTV.text = it.uid
            } else {
                findNavController().navigate(R.id.loginFragment)
            }
        }

        viewModel.profileRef.addSnapshotListener { snapshot, error ->

            val profile = snapshot?.toObject(Profile::class.java)!!
            binding.userTV.text = profile.isPremium.toString()
            if (profile.profilePicture != "") {
                binding.imageView.load(profile.profilePicture)
            }
        }
        binding.imageView.setOnClickListener {
            getContent.launch("image/*")
        }

        viewModel.getBusinessNews()


        viewModel.newsList.observe(viewLifecycleOwner)
        {

            binding.topNewsRecyclerView.adapter = NewsAdapter(it.articles, viewModel)

        }

        viewModel.getMarketData()

        viewModel.market.observe(viewLifecycleOwner)
        {

            binding.topCurrencyRecyclerView.adapter =
                TopMarketAdapter(requireContext(), it.data.cryptoCurrencyList)
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


















