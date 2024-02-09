package com.example.abschlussprojektmyapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.abschlussprojektmyapp.MainViewmodel
import com.example.abschlussprojektmyapp.R
import com.example.abschlussprojektmyapp.data.model.newsapi.Article
import com.example.abschlussprojektmyapp.databinding.ItemNewsBinding

class BusinessNewsAdapter(
    private val dataset: List<Article>,
    private val viewModel: MainViewmodel
) : RecyclerView.Adapter<BusinessNewsAdapter.ItemViewHolder>() {

        /**
         * der ViewHolder umfasst die View uns stellt einen Listeneintrag dar
         */
        inner class ItemViewHolder(val binding: ItemNewsBinding) :
            RecyclerView.ViewHolder(binding.root)

        /**
         * hier werden neue ViewHolder erstellt
         */
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val binding =
                ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ItemViewHolder(binding)
        }


        /**
         * hier findet der Recyclingprozess statt
         * die vom ViewHolder bereitgestellten Parameter erhalten die Information des Listeneintrags
         */
        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

            val item = dataset[position]

            holder.binding.titleTextView.text = item.title
            holder.binding.subtitleTextView.text = item.publishedAt
            holder.binding.tvStatusName.text = item.author
            //holder.binding.tvStatusName2.text = item.content

        }

        /**
         * damit der LayoutManager weiß, wie lang die Liste ist
         */
        override fun getItemCount(): Int {
            return dataset.size
        }

    }
