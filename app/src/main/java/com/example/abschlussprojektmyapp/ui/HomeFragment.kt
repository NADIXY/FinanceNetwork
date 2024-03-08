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

            /*

            val itemTouchHelper = ItemTouchHelper(object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        val position = viewHolder.adapterPosition
                        binding.topNewsRecyclerView.adapter?.notifyItemRemoved(position)

                    //viewModel.newsList.value?.articles?.removeAt(position)

                }
            })
            itemTouchHelper.attachToRecyclerView(binding.topNewsRecyclerView)

             */

            viewModel.getMarketData()
            viewModel.market.observe(viewLifecycleOwner) {
                binding.topCurrencyRecyclerView.adapter =
                    TopMarketAdapter(it.data.cryptoCurrencyList, viewModel, requireContext())
            }

            val adapter = TopLossGainPagerAdapter(this)
            binding.contentViewPager.adapter = adapter

        }

    }
}

























