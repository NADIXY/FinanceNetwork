package com.example.abschlussprojektmyapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.abschlussprojektmyapp.MainViewModel
import com.example.abschlussprojektmyapp.R
import com.example.abschlussprojektmyapp.adapter.BusinessNewsAdapter
import com.example.abschlussprojektmyapp.databinding.FragmentNewsBinding

class NewsFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getBusinessNews()

        binding.backStackNews.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }

        binding.rvBusinessNews.setOnClickListener {
            viewModel.getBusinessNews()
        findNavController().navigate(R.id.newsDetailFragment)
    }
        viewModel.newsList.observe(viewLifecycleOwner){
            Log.d("Test_Api","$it")

            binding.rvBusinessNews.adapter = BusinessNewsAdapter(it.articles, viewModel)

        }

    }
}

