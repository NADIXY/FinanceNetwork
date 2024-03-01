package com.example.abschlussprojektmyapp.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import com.example.abschlussprojektmyapp.MainViewModel
import com.example.abschlussprojektmyapp.adapter.ChatAdapter
import com.example.abschlussprojektmyapp.data.model.Chat
import com.example.abschlussprojektmyapp.databinding.FragmentChatListBinding

class ChatListFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentChatListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*

        //region testcode

        //Später noch über UI ausgewählt
        val userId = "qqShJpe2K6XNW6f9sMrDtlms5U72"
        viewModel.createChat(userId)

        viewModel.addMessageToChat("Hallo Firebase", "5EsAeuMmAJcchCghq6Mq")

        //endregion

         */

        viewModel.chatsRef.addSnapshotListener { value, error ->

            if (error == null) {

                val chatList: List<Pair<String, Chat>> = value!!.documents.map {
                    Pair(
                        it.id,
                        it.toObject(Chat::class.java)!!
                    )
                }

                val filteredChatList = chatList.filter {
                    it.second.userList.contains(viewModel.auth.currentUser!!.uid)
                }

                val adapter = ChatAdapter(filteredChatList)
                binding.chatsRV.adapter = adapter
            }
        }

        binding.createChatBTN.setOnClickListener {

            val dialogBuilder = AlertDialog.Builder(requireContext())

            val editText = EditText(requireContext())
            dialogBuilder.setView(editText)
            dialogBuilder.setPositiveButton("Chat erstellen") { _, _ ->

                val id = editText.text.toString()
                viewModel.createChat(id)

            }
            dialogBuilder.setNegativeButton("Abbrechen") { _, _ ->

            }
            dialogBuilder.show()

        }
    }

}