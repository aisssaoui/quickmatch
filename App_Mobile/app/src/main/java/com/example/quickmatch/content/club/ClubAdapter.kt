package com.example.quickmatch.content.club

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quickmatch.R
import com.example.quickmatch.network.ClubObject
import com.example.quickmatch.utils.FormatUtils

class ClubAdapter : RecyclerView.Adapter<ClubAdapter.ViewHolder>() {
    var data = listOf<ClubObject>() // datas list displayed by the recycler view
    set(value) { // setter to make recycler view know when datas to display change
        field = value
        notifyDataSetChanged()
    }

    /* give knowledge of datas size */
    override fun getItemCount() = data.size

    /* how data should be binded with the viewholder */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    /* how to create a new viewholder */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    /* custom view holder based on the custom item layout */
    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        /* view in the layout */
        private val clubName: TextView = itemView.findViewById(R.id.club_name)
        private val clubPrivacy: TextView = itemView.findViewById(R.id.club_privacy)
        private val clubCreationDate: TextView = itemView.findViewById(R.id.club_creation_date)
        private val joinClubIcon: ImageView = itemView.findViewById(R.id.join_icon)

        /* bind view values to the datas */
        fun bind(item: ClubObject) {
            clubName.text = item.name
            clubPrivacy.text = if (item.private) "Privé" else "Public"
            clubCreationDate.text = itemView.context.getString(R.string.created) + " " + FormatUtils.parseDateToJJMMAAAA(item.creationDate!!)
            joinClubIcon.setImageResource(R.drawable.ic_group_add_green_48dp)
        }

        /* inflate layout here to encapsulate, onCreateViewHolder has nothing to know about inflation */
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.list_clubs_item, parent, false)
                return ViewHolder((view))
            }
        }
    }


}