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
import coil.load
import com.example.abschlussprojektmyapp.MainViewmodel
import com.example.abschlussprojektmyapp.data.model.Profile
import com.example.abschlussprojektmyapp.R
import com.example.abschlussprojektmyapp.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private val viewModel: MainViewmodel by activityViewModels()
    private lateinit var binding: FragmentProfileBinding

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) {uri: Uri? ->
        if (uri != null) {
            //Uri bezieht sich auf das Bild
            viewModel.uploadProfilePicture(uri)
        }
    }

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

        binding.continueBTN.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)

        }

        viewModel.user.observe(viewLifecycleOwner){
            if(it != null){
                binding.userTV.text = it.uid
            } else {
                findNavController().navigate(R.id.loginFragment)
            }
        }

        viewModel.profileRef.addSnapshotListener{    snapshot, error ->

            val profile = snapshot?.toObject(Profile::class.java)!!
            binding.userTV.text = profile.isPremium.toString()
            if(profile.profilePicture != ""){
                binding.imageView.load(profile.profilePicture)
            }
        }
        binding.imageView.setOnClickListener {
            getContent.launch("image/*")
        }

    }

}

