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

        viewModel.crypto.observe(viewLifecycleOwner) {

            binding.topGainLoseRecyclerView.adapter =
                TopMarketAdapter(requireContext(), it)

            binding.topGainLoseRecyclerView.adapter =
                MarketAdapter(requireContext(), it)

            val position = requireArguments().getInt("position")
        }
    }
    /*

    private fun getMarketData() {
        val position = requireArguments().getInt("position")
        lifecycleScope.launch(Dispatchers.IO) {

            val res = ApiUtilities.getInstance().create(ApiUtilities.ApiInterface::class.java).getMarketData()

            if (res.body() != null) {

                withContext(Dispatchers.Main) {
                    val dataItem = res.body()!!.data.cryptoCurrencyList

                    Collections.sort(dataItem) { o1, o2 ->
                        (o2.quotes[0].percentChange24h.toInt())
                            .compareTo(o1.quotes[0].percentChange24h.toInt())

                    }
                    val list = ArrayList<CryptoCurrency>()

                    if (position == 0) {
                        list.clear()
                        for (i in 0..9) {
                            list.add(dataItem[i])
                        }

                        binding.topGainLoseRecyclerView.adapter =
                            MarketAdapter(requireContext(), list)

                    } else {
                        list.clear()
                        for (i in 0..9) {
                            list.add(dataItem[dataItem.size - 1 - i])
                        }

                        binding.topGainLoseRecyclerView.adapter =
                            MarketAdapter(requireContext(), list)
                    }
                }
            }
        }

    }

     */
}

//Option:
/*
    // Diese Funktion ruft Marktdaten ab
    private fun getMarketData() {
        val position = requireArguments().getInt("position")

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = Repository(Api).getMarketData()

                if (response.isSuccessful) {
                    val dataItem = response.body()?.data?.cryptoCurrencyList ?: emptyList()

                    withContext(Dispatchers.Main) {
                        dataItem.sortedByDescending { it.quotes[0].percentChange24h.toInt() }

                        val list = ArrayList<CryptoCurrency>()
                        if (position == 0) {
                            list.addAll(dataItem.take(10))
                        } else {
                            list.addAll(dataItem.takeLast(10))
                        }

                        binding.topGainLoseRecyclerView.adapter = MarketAdapter(requireContext(), list)
                    }
                } else {
                    // Handle error
                    Log.e("TopLossGainFragment", "Failed to get market data: ${response.message()}")
                }
            } catch (e: Exception) {
                // Handle exception
                Log.e("TopLossGainFragment", "Error getting market data: $e")
            }
        }
    }
}
 */



