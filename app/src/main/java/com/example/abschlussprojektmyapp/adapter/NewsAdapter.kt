package com.example.abschlussprojektmyapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.abschlussprojektmyapp.R
import com.example.abschlussprojektmyapp.data.model.newsapi.Article
import com.example.abschlussprojektmyapp.databinding.TopNewsLayoutBinding

class NewsAdapter(
    private val dataset: List<Article>,
) : RecyclerView.Adapter<NewsAdapter.ItemViewHolder>() {

    /**
     * der ViewHolder umfasst die View uns stellt einen Listeneintrag dar
     */
    inner class ItemViewHolder(val binding: TopNewsLayoutBinding ) :
        RecyclerView.ViewHolder(binding.root)

    /**
     * hier werden neue ViewHolder erstellt
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            TopNewsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    /**
     * hier findet der Recyclingprozess statt
     * die vom ViewHolder bereitgestellten Parameter erhalten die Information des Listeneintrags
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val item = dataset[position]

        holder.binding.topNewsImageView.load(item.urlToImage) {

            error(android.R.drawable.ic_menu_info_details)
            placeholder(R.drawable.ic_launcher_background)
            //transformations(RoundedCornersTransformation(50f))

        }

        holder.binding.topNewsTitelTextView.text = item.title
        holder.binding.topNewsDataView.text = item.publishedAt
        holder.binding.topNewsDataView.text = item.author
        holder.binding.topNewsDataView.text = item.url
        holder.binding.topNewsDataView.text = item.content
        holder.binding.topNewsDataView.text = item.urlToImage

    }

    /**
     * damit der LayoutManager weiß, wie lang die Liste ist
     */
    override fun getItemCount(): Int {
        return dataset.size
    }
}