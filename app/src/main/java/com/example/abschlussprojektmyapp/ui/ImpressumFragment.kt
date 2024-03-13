package com.example.abschlussprojektmyapp.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.abschlussprojektmyapp.MainViewModel
import com.example.abschlussprojektmyapp.R
import com.example.abschlussprojektmyapp.databinding.FragmentImpressumBinding

class ImpressumFragment : Fragment() {

    private lateinit var binding: FragmentImpressumBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImpressumBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Button um zurück zum SignInFragment zu navigieren
        binding.btBackToHome.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
    }
}