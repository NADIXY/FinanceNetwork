package com.example.abschlussprojektmyapp.adapter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.abschlussprojektmyapp.MainViewModel
import com.example.abschlussprojektmyapp.data.model.Note
import com.example.abschlussprojektmyapp.databinding.ItemNoteBinding
import java.text.SimpleDateFormat

/**

 * Der Adapter kümmert sich um das Erstellen der Listeneinträge
 */
class NoteAdapter(
    private val dataset: List<Note>,
    private val viewModel: MainViewModel
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val item = dataset[position]

        var dateFormatParse = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        var date = dateFormatParse.parse(item.publishedAt)

        var dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var date2 = dateFormat.format(date)


        holder.binding.tvNoteName.text = date2.toString()
        holder.binding.tvNoteText.text = item.title
        holder.binding.tvNoteText3.text = item.author



        val hCard = holder.binding.cvNote

        // ScaleY = Stretchen oben unten           AnimationsTyp, Start, Ende
        val animator = ObjectAnimator.ofFloat(hCard, View.SCALE_Y, 0f, 1f)
        animator.duration = 800
        animator.start()

        holder.binding.cvNote.setOnClickListener {
            showDeleteAlertDialog(item,holder.itemView.context )

            // RotationY = object horizontal rotieren                  Start, Ende
            val rotator = ObjectAnimator.ofFloat(hCard, View.ROTATION_Y, 0f, 360f)
            rotator.duration = 600

            // Animationen abspielen.
            val set = AnimatorSet()
            set.playTogether(rotator)
            set.start()
        }

    }

    /**

     * Zeigt einen Lösch-AlertDialog für die übergebene Notiz an.
     * @param note Die zu löschende Notiz
     * @param context Der Kontext, in dem der AlertDialog angezeigt werden soll
     */
    private fun showDeleteAlertDialog(note: Note, context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Delete")
        builder.setMessage("Do you really want to delete this note?")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton("Yes") { dialogInterface, which ->
            viewModel.deleteNote(note)
            Toast.makeText(context, "The note has been deleted", Toast.LENGTH_LONG)
                .show()
            dialogInterface.dismiss()
        }

        builder.setNeutralButton("Cancel") { dialogInterface, which ->
            Toast.makeText(context, "Deletion process aborted", Toast.LENGTH_LONG)
                .show()
            dialogInterface.dismiss()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

}
