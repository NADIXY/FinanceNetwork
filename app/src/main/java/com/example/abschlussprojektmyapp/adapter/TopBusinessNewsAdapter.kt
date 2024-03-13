package com.example.abschlussprojektmyapp.adapter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.abschlussprojektmyapp.MainViewModel
import com.example.abschlussprojektmyapp.R
import com.example.abschlussprojektmyapp.data.model.newsapi.Article
import com.example.abschlussprojektmyapp.databinding.TopNewsLayoutBinding

class TopBusinessNewsAdapter(
    private val dataset: List<Article>,
    viewModel: MainViewModel,
) : RecyclerView.Adapter<TopBusinessNewsAdapter.ItemViewHolder>() {

     // Der ViewHolder umfasst die View uns stellt einen Listeneintrag dar
    inner class ItemViewHolder(val binding: TopNewsLayoutBinding ) :
        RecyclerView.ViewHolder(binding.root)

     // Hier werden neue ViewHolder erstellt
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            TopNewsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

     // Hier findet der Recyclingprozess statt
     // die vom ViewHolder bereitgestellten Parameter erhalten die Information des Listeneintrags
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]

        val hCard = holder.binding.topNewsCardView

        // ScaleY = Stretchen oben unten           AnimationsTyp, Start, Ende
        val animator = ObjectAnimator.ofFloat(hCard, View.SCALE_Y, 0f, 1f)
        animator.duration = 800
        animator.start()

        holder.binding.topNewsCardView.setOnClickListener {

            // RotationY = object horizontal rotieren                  Start, Ende
            val rotator = ObjectAnimator.ofFloat(hCard, View.ROTATION_Y, 0f, 360f)
            rotator.duration = 600

            // Animationen abspielen.
            val set = AnimatorSet()
            set.playTogether(rotator)
            set.start()
        }

        if (item.urlToImage != null && item.urlToImage.isNotEmpty()) {
            holder.binding.topNewsImageView.visibility = View.VISIBLE
            holder.binding.topNewsImageView.load(item.urlToImage){
                error(R.drawable.spinner)
            }
        } else {
            holder.binding.topNewsImageView.visibility = View.GONE
            val layoutParams = holder.binding.topNewsCardView.layoutParams
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            holder.binding.topNewsCardView.layoutParams = layoutParams
        }

        holder.binding.topNewsTitelTextView.text = item.title
        holder.binding.topNewsAuthorView.text = item.author

        //Hier setzen wir per Click auf die CardView um ins
        //newsFragment navigieren zu können
        holder.binding.topNewsCardView.setOnClickListener {
            holder.itemView.findNavController().navigate(R.id.businessNewsFragment)
        }
    }

    /**
     * damit der LayoutManager weiß, wie lang die Liste ist
     */
    override fun getItemCount(): Int {
        return dataset.size
    }
}