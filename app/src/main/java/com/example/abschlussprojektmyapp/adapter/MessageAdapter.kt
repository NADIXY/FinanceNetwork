package com.example.abschlussprojektmyapp.adapter

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abschlussprojektmyapp.data.model.Message
import com.example.abschlussprojektmyapp.databinding.MessageItemBinding

class MessageAdapter (val dataset: List<Message>, val userId: String) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    inner class MessageViewHolder(val binding: MessageItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = MessageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val item = dataset[position]

        holder.binding.messageTV.text = item.content

        val myMessage: Boolean = (item.senderId == userId)

        if(myMessage){
            //Nachricht rechts
            holder.binding.messageOuterLL.gravity = Gravity.RIGHT
        } else {
            //Nachricht links
            holder.binding.messageOuterLL.gravity = Gravity.LEFT
        }

    }
}