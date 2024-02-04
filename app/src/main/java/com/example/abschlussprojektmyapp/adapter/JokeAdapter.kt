package com.example.abschlussprojektmyapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abschlussprojektmyapp.data.Joke
import com.example.abschlussprojektmyapp.databinding.ListItemJokeBinding

class JokeAdapter(

) : RecyclerView.Adapter<JokeAdapter.ItemViewHolder>() {

    private var dataset = listOf<Joke>()

    /**
     * der ViewHolder umfasst die View uns stellt einen Listeneintrag dar
     */
    inner class ItemViewHolder(val binding: ListItemJokeBinding) :
        RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<Joke>) {
        dataset = newList
        notifyDataSetChanged()
    }

    /**
     * hier werden neue ViewHolder erstellt
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            ListItemJokeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    /**
     * hier findet der Recyclingprozess statt
     * die vom ViewHolder bereitgestellten Parameter erhalten die Information des Listeneintrags
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val joke = dataset[position]

        holder.binding.tvSetup.text= joke.setup
        holder.binding.tvDelivery.visibility = View.VISIBLE

        holder.binding.btnShowDelivery.setOnClickListener {
            holder.binding.tvDelivery.visibility = View.VISIBLE
            holder.binding.btnShowDelivery.visibility = View.INVISIBLE
            holder.binding.tvDelivery.text= joke.delivery
        }
    }

    /**
     * damit der LayoutManager weiß, wie lang die Liste ist
     */
    override fun getItemCount(): Int {
        return dataset.size
    }
}
