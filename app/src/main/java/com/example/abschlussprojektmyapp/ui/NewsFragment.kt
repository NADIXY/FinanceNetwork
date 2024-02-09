package com.example.abschlussprojektmyapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.abschlussprojektmyapp.MainViewmodel
import com.example.abschlussprojektmyapp.adapter.BusinessNewsAdapter
import com.example.abschlussprojektmyapp.databinding.FragmentNewsBinding

class NewsFragment : Fragment() {

    private val viewModel: MainViewmodel by activityViewModels()
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


        viewModel.newsList.observe(viewLifecycleOwner){
            Log.d("Test_Api","$it")

            binding.rvstatus.adapter = BusinessNewsAdapter(it.articles, viewModel)


        }


    }
}

