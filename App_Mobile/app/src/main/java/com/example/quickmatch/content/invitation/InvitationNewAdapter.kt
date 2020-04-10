package com.example.quickmatch.content.invitation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quickmatch.databinding.ListInvitationNewItemBinding
import com.example.quickmatch.network.InvitationObject
import com.example.quickmatch.network.PlayerMeetObject

class InvitationNewAdapter(val clickListener: InvitationNewClickListener)
    : ListAdapter<PlayerMeetObject, InvitationNewAdapter.ViewHolder>(InvitationNewDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class ViewHolder private constructor(val binding: ListInvitationNewItemBinding): RecyclerView.ViewHolder(binding.root) {

        /* bind view values to the datas */
        fun bind(item: PlayerMeetObject, clickListener: InvitationNewClickListener) {
            binding.invitation = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        /* inflate layout here to encapsulate, onCreateViewHolder has nothing to know about inflation */
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListInvitationNewItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }
}

class InvitationNewDiffCallback : DiffUtil.ItemCallback<PlayerMeetObject>() {
    override fun areItemsTheSame(oldItem: PlayerMeetObject, newItem: PlayerMeetObject): Boolean {
        return oldItem.playerId == newItem.playerId && oldItem.meetId == newItem.meetId
    }

    override fun areContentsTheSame(oldItem: PlayerMeetObject, newItem: PlayerMeetObject): Boolean {
        return oldItem == newItem
    }


}
class InvitationNewClickListener(val clickListener: (invitationId: Int?) -> Unit) {
    fun onClick(invitation: PlayerMeetObject) {
        return clickListener(invitation.meetId)
    }
}