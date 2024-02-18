package com.example.abschlussprojektmyapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.abschlussprojektmyapp.R
import com.example.abschlussprojektmyapp.databinding.FragmentCurrencyConverterBinding

class CurrencyConverterFragment : Fragment() {

    private lateinit var viewModel: CurrencyConverterFragment
    private lateinit var binding: FragmentCurrencyConverterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurrencyConverterBinding.inflate(layoutInflater)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backStackCurrencyConverter.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
        binding.logout.setOnClickListener {
            activity?.finish()
        }


        //viewModel.getExchangeRates(baseCurrency) // API-Anfrage für Wechselkurse



        binding.convertButton.setOnClickListener {
            val baseCurrency = binding.baseCurrencyEditText.text.toString()
            val fromCurrency = binding.fromCurrencySpinner.selectedItem.toString()
            val toCurrency = binding.toCurrencySpinner.selectedItem.toString()

            val amountToConvert = 100.0 // Beispielbetrag zum Umrechnen

            //viewModel.getExchangeRates(baseCurrency) // API-Anfrage für Wechselkurse

            //viewModel.exchangeRates.observe(viewLifecycleOwner, Observer { exchangeRates ->
                //val fromRate = exchangeRates.rates[fromCurrency] ?: 1.0 // Standardrate auf 1.0 setzen, falls nicht verfügbar
                //val toRate = exchangeRates.rates[toCurrency] ?: 1.0

                //val convertedAmount = amountToConvert * (toRate / fromRate)

                //updateUIWithConvertedAmount(amountToConvert, fromCurrency, convertedAmount, toCurrency)
            //})
        }
    }

}





