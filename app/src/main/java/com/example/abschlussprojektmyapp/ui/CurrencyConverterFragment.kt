package com.example.abschlussprojektmyapp.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.abschlussprojektmyapp.MainViewModel
import com.example.abschlussprojektmyapp.R
import com.example.abschlussprojektmyapp.databinding.FragmentCurrencyConverterBinding

class CurrencyConverterFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentCurrencyConverterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurrencyConverterBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backStackCurrencyConverter.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
        binding.close.setOnClickListener {
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


        val currencies = arrayOf(
            "USD", "EUR", "GBP",
            "AED", "AFN", "ALL",
            "AMD", "ANG", "AOA",
            "ARS", "AUD", "AWG",
            "AZN", "BAM", "BBD",
            "BDT", "BGN", "BHD",
            "BIF", "BMD", "BND",
            "BOB", "BRL", "BSD",
            "BTN", "BWP", "BYN",
            "BZD", "CAD", "CDF",
            "CHF", "CLP", "CNY",
            "COP", "CRC", "CUP",
            "CVE", "CZK", "DJF",
            "DKK", "DOP", "DZD",
            "EGP", "ERN", "ETB",
            "FJD", "FKP", "FOK",
            "GEL", "GGP", "GHS",
            "GIP", "GMD", "GNF",
            "GTQ", "GYD", "HKD",
            "HNL",
            "HRK", "HTG","HUF",
            "IDR", "ILS", "IMP",
            "INR", "IQD", "IRR",
            "ISK", "JEP", "JMD",
            "JOD", "JPY", "KES",
            "KGS", "KHR", "KID",
            "KMF", "KRW", "KWD",
            "KYD", "KZT", "LAK",
            "LBP", "LKR", "LRD",
            "LSL", "LYD", "MAD",
            "MDL", "MGA", "MKD",
            "MMK", "MNT", "MOP",
            "MRU", "MUR", "MVR",
            "MWK", "MXN", "MYR",
            "MZN", "NAD", "NGN",
            "NIO", "NOK", "NPR",
            "NZD", "OMR", "PAB",
            "PEN", "PGK", "PHP",
            "PKR", "PLN", "PYG",
            "QAR", "RON", "RSD",
            "RUB", "RWF", "SAR",
            "SBD", "SCR", "SDG",
            "SEK", "SGD", "SHP",
            "SLE", "SLL", "SOS",
            "SRD", "SSP", "STN",
            "SYP", "SZL", "THB",
            "TJS", "TMT", "TND",
            "TOP", "TRY", "TTD",
            "TVD", "TWD", "TZS",
            "UAH", "UGX", "USD",
            "UYU", "UZS", "VES",
            "VND", "VUV", "WST",
            "XAF", "XCD", "XDR",
            "XOF", "XPF", "YER",
            "ZAR", "ZMW", "ZWL"
        )
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, currencies)
        binding.fromCurrencySpinner.adapter = adapter
        binding.toCurrencySpinner.adapter = adapter
        binding.convertButton.setOnClickListener {
            val fromCurrency = binding.fromCurrencySpinner.selectedItem.toString()
            val toCurrency = binding.toCurrencySpinner.selectedItem.toString()
            val amountToConvert = binding.amountToConvertEditText.text.toString().toDouble()
            viewModel.getExchangeRates()
            viewModel.exchangeRates.observe(viewLifecycleOwner) { exchangeRates ->
                val fromRate = exchangeRates.conversion_rates!![fromCurrency] ?: 1.0
                val toRate = exchangeRates.conversion_rates!![toCurrency] ?: 1.0
                val convertedAmount = amountToConvert * (toRate / fromRate)
                updateUIWithConvertedAmount(
                    amountToConvert,
                    fromCurrency,
                    convertedAmount,//.roundToInt(),
                    toCurrency
                )
            }
        }
    }
    private fun updateUIWithConvertedAmount(
        amountToConvert: Double,
        fromCurrency: String,
        convertedAmount: Double,
        toCurrency: String
    ) {
        val resultText = "$amountToConvert $fromCurrency is $convertedAmount $toCurrency"
        binding.resultTextView.text = resultText
        Toast.makeText(requireContext(), resultText, Toast.LENGTH_SHORT).show()
    }
}

















