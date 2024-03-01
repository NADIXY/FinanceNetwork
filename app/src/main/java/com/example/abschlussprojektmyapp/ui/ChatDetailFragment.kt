package com.example.abschlussprojektmyapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.abschlussprojektmyapp.MainViewModel
import com.example.abschlussprojektmyapp.adapter.MessageAdapter
import com.example.abschlussprojektmyapp.data.model.Message
import com.example.abschlussprojektmyapp.databinding.FragmentChatDetailBinding

class ChatDetailFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentChatDetailBinding
    private val args: ChatDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chatId = args.chatId

        viewModel.getMessageRef(chatId).addSnapshotListener { value, error ->
            if(error == null){
                val messageList : List<Message> = value!!.toObjects(Message::class.java)

                val adapter = MessageAdapter(messageList, viewModel.auth.currentUser!!.uid)
                binding.messagesRV.adapter = adapter
            }
        }

        binding.sendBTN.setOnClickListener {
            val message = binding.messageET.text.toString()
            binding.messageET.setText("")
            viewModel.addMessageToChat(message, chatId)
        }

    }

}
