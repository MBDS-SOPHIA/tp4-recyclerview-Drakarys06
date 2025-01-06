package com.openclassrooms.magicgithub.ui.user_list

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.magicgithub.databinding.ItemListUserBinding
import com.openclassrooms.magicgithub.model.User
import com.openclassrooms.magicgithub.utils.UserDiffCallback
import java.util.*

class UserListAdapter(private val callback: Listener) : RecyclerView.Adapter<ListUserViewHolder>() {
    private var users: MutableList<User> = mutableListOf()

    interface Listener {
        fun onClickDelete(user: User)
        fun onUserStatusChanged(user: User)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUserViewHolder {
        val binding = ItemListUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListUserViewHolder, position: Int) {
        val user = users[position]
        holder.bind(user, callback)
        holder.itemView.setBackgroundColor(
            if (user.isActive) Color.WHITE else Color.rgb(255, 200, 200)
        )
    }

    override fun getItemCount(): Int = users.size

    fun updateList(newList: List<User>) {
        val diffResult = DiffUtil.calculateDiff(UserDiffCallback(newList.toList(), users))
        users = newList.toMutableList()
        diffResult.dispatchUpdatesTo(this)
    }

    fun toggleUserActiveStatus(position: Int) {
        users[position].isActive = !users[position].isActive
        callback.onUserStatusChanged(users[position])
        notifyItemChanged(position)
    }

    fun moveItem(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(users, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(users, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }
}