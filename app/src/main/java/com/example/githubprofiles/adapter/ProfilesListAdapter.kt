package com.example.githubprofiles.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubprofiles.R
import com.example.githubprofiles.fragments.ProfileDetailsFragment
import com.example.githubprofiles.fragments.ProfilesListFragment
import com.example.githubprofiles.listener.AdapterClickListener
import com.example.githubprofiles.model.ProfilesResponse

class ProfilesListAdapter constructor(private val listener: AdapterClickListener): RecyclerView.Adapter<ProfilesListAdapter.ProfilesViewHolder>() {

    public var profiles: MutableList<ProfilesResponse> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfilesViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.activity_profiles_list, parent, false)
        return ProfilesViewHolder(view)
    }

    override fun getItemCount(): Int = profiles.size

    override fun onBindViewHolder(holder: ProfilesViewHolder, position: Int) {
        holder.bind(profiles[position])
    }

    inner class ProfilesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val profileImage: ImageView = itemView.findViewById(R.id.profileImage)
        private val profileName: TextView = itemView.findViewById(R.id.profileName)
        private val profileNodeId: TextView = itemView.findViewById(R.id.profileNodeId)
        private val profileFullName: TextView = itemView.findViewById(R.id.profileFullName)
        private val cardViewLayout: CardView = itemView.findViewById(R.id.cardViewLayout)

        fun bind(profilesResponse: ProfilesResponse) {

            Glide.with(itemView.context)
                .load(profilesResponse.owner?.avatarUrl)
                .into(profileImage)

            profileName.setText(profilesResponse.name)
            profileNodeId.setText(profilesResponse.id.toString())
            profileFullName.setText(profilesResponse.fullName)

            cardViewLayout.setOnClickListener {
                listener.onAdapterClick(adapterPosition)
            }
        }
    }
}