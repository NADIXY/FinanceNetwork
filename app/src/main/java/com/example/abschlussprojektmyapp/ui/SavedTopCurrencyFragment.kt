package com.example.abschlussprojektmyapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.abschlussprojektmyapp.MainViewModel
import com.example.abschlussprojektmyapp.adapter.SavedTopCurrencyAdapter
import com.example.abschlussprojektmyapp.databinding.FragmentSavedTopCurrencyBinding

class SavedTopCurrencyFragment : Fragment() {
    private lateinit var binding: FragmentSavedTopCurrencyBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedTopCurrencyBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMarketData()

        viewModel.savedTopCurrency.observe(viewLifecycleOwner) {
            binding.rvSavedTopCurrency.adapter = SavedTopCurrencyAdapter(it,viewModel)
        }

        // Der SnapHelper sorgt dafür, dass die RecyclerView immer auf das aktuelle List Item springt
        val helper: SnapHelper = PagerSnapHelper()
        helper.attachToRecyclerView(binding.rvSavedTopCurrency)

    }

}