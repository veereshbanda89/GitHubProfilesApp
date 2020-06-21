package com.example.githubprofiles.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.githubprofiles.R
import com.example.githubprofiles.activities.HomeActivity
import com.example.githubprofiles.adapter.ProfilesListAdapter
import com.example.githubprofiles.constants.isConnectedToNetwork
import com.example.githubprofiles.listener.AdapterClickListener
import com.example.githubprofiles.model.ProfilesResponse
import com.example.githubprofiles.network.GitHubProfilesRepository
import kotlinx.android.synthetic.main.fragment_profiles_list.*

class ProfilesListFragment : Fragment(), AdapterClickListener {

    private lateinit var profileListAdapter: ProfilesListAdapter

    companion object {

        fun newInstance(): ProfilesListFragment {
            return ProfilesListFragment()
        }
    }

    override fun onAdapterClick(position: Int) {
        val profile = profileListAdapter.profiles[position]
        if (profile != null) {
            (activity as HomeActivity).showFragment(ProfileDetailsFragment.newInstance(profile))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profiles_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        progressBar.visibility = View.VISIBLE
        profileListAdapter = ProfilesListAdapter(this);
        profilesRecyclerView.adapter = profileListAdapter

        GitHubProfilesRepository.getProfiles(
            onSuccess = ::onProfilesFetched,
            onError = ::onError
        )
    }

    private fun onProfilesFetched(profiles: List<ProfilesResponse>?) {
        progressBar.visibility = View.GONE
        profiles?.run {
            profileListAdapter.profiles.clear()
            profileListAdapter.profiles.addAll(profiles)
            profileListAdapter.notifyDataSetChanged()
        }

    }

    private fun onError() {
        progressBar.visibility = View.GONE
        if (!activity!!.isConnectedToNetwork()) {
            Toast.makeText(
                context,
                getString(R.string.no_internet_connection),
                Toast.LENGTH_SHORT
            ).show()
        }else {
            Toast.makeText(activity, getString(R.string.error_fetch_profiles), Toast.LENGTH_SHORT)
                .show()
        }
    }
}