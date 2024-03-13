package com.example.abschlussprojektmyapp.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.abschlussprojektmyapp.MainViewModel
import com.example.abschlussprojektmyapp.R
import com.example.abschlussprojektmyapp.adapter.MarketAdapter
import com.example.abschlussprojektmyapp.adapter.TopBusinessNewsAdapter
import com.example.abschlussprojektmyapp.adapter.TopMarketAdapter
import com.example.abschlussprojektmyapp.databinding.FragmentHomeBinding
import java.util.Timer
import java.util.TimerTask

class HomeFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding
    private var currentPageCurrency = 0
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

        binding.account.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }

        viewModel.getBusinessNews()
        viewModel.newsList.observe(viewLifecycleOwner) {
            binding.topNewsRecyclerView.adapter = TopBusinessNewsAdapter(it.articles, viewModel)
        }

        viewModel.getMarketData()
        viewModel.market.observe(viewLifecycleOwner) {

            binding.topCurrencyRecyclerView.adapter =
                TopMarketAdapter(it.data.cryptoCurrencyList, viewModel, requireContext())
            startAutoScrollCurrency()

            binding.topGainLoseRecyclerView.adapter =
                MarketAdapter(it.data.cryptoCurrencyList, viewModel, requireContext())

            binding.button3.setOnClickListener {
                viewModel.getMarketData()
            }

        }

        binding.menu.setOnClickListener {
            showPopupMenu(it)
        }

    }

    private fun startAutoScrollCurrency() {
        timerCurrency = Timer()
        timerCurrency?.schedule(object : TimerTask() {
            override fun run() {
                activity?.runOnUiThread {
                    if (currentPageCurrency == viewModel.market.value?.data?.cryptoCurrencyList?.size) {
                        currentPageCurrency = 0
                    }
                    binding.topCurrencyRecyclerView.smoothScrollToPosition(currentPageCurrency++)
                }
            }
        }, 0, 5000) // Change the interval as needed for currency RecyclerView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timerCurrency?.cancel()
    }

    private fun showPopupMenu(view: View) {
        val popup = PopupMenu(requireContext(), view)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(
            R.menu.home_menu,
            popup.menu
        )
        popup.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.impressum -> {
                    findNavController().navigate(R.id.impressumFragment)
                    true

                }
                R.id.close -> {
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setTitle("Close")
                    builder.setMessage("Do you really want to close?")
                    builder.setPositiveButton("Yes") { dialog, which ->
                        activity?.finish()
                        Toast.makeText(requireContext(), "closed", Toast.LENGTH_SHORT).show()
                    }
                    builder.setNegativeButton("Cancel") { dialog, which -> }
                    val dialog: AlertDialog = builder.create()
                    dialog.show()

                    true
                }
                else -> true
            }
        }
        popup.show()
    }
}