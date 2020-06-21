package com.example.githubprofiles.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.githubprofiles.R
import com.example.githubprofiles.model.ProfilesResponse
import kotlinx.android.synthetic.main.fragment_profile_details.*

class ProfileDetailsFragment : Fragment() {

    private lateinit var profile: ProfilesResponse

    companion object {

        private const val TAG = "ProfileDetailsFragment"

        fun newInstance(profile : ProfilesResponse) = ProfileDetailsFragment().apply {
            arguments = Bundle().apply { putParcelable("Profile", profile) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if(arguments!=null) {
            if(arguments?.getParcelable<ProfilesResponse>("Profile")!=null)
            profile = arguments?.let { it.getParcelable<ProfilesResponse>("Profile")!! }!!
        }

        if(::profile.isInitialized){
            setProfileDetails(profile)
        }
    }

    private fun setProfileDetails(profile: ProfilesResponse) {
        Glide.with(activity)
            .load(profile.owner?.avatarUrl)
            .into(profileImage)
        profileId.text = getString(R.string.id) + " " + profile.id.toString()
        profileNodeId.text = getString(R.string.node_id) + " " + profile.nodeId
        profileName.text = getString(R.string.name) + " " + profile.name
        profileFullName.text = getString(R.string.full_name) + " " + profile.fullName
        loginAs.text = getString(R.string.login_as) + " " + profile.owner?.login
        description.text = profile.description
        gitHubProfileLink.text = profile.owner?.htmlUrl
        gitHubRepoLink.text = profile.htmlUrl
    }
}