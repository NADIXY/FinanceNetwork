package com.example.abschlussprojektmyapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.abschlussprojektmyapp.MainViewmodel
import com.example.abschlussprojektmyapp.Profile
import com.example.abschlussprojektmyapp.R
import com.example.abschlussprojektmyapp.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private val viewmodel: MainViewmodel by activityViewModels()
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logoutBTN.setOnClickListener {
            viewmodel.logout()
        }

        viewmodel.user.observe(viewLifecycleOwner){
            if(it != null){
                //binding.userTV.text = it.uid
            } else {
                findNavController().navigate(R.id.loginFragment)
            }
        }

        viewmodel.profileRef.addSnapshotListener{    snapshot, error ->

            val profile = snapshot?.toObject(Profile::class.java)
            binding.userTV.text = profile?.isPremium.toString()
        }

    }

}

