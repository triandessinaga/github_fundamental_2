package com.example.submissionakhirfundamentalandroid.presentation.activities.content.detail.content


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionakhirfundamentalandroid.R
import com.example.submissionakhirfundamentalandroid.data.remote.UserResponse
import com.example.submissionakhirfundamentalandroid.data.repository.DetailRepositoryImpl
import com.example.submissionakhirfundamentalandroid.databinding.FragmentFollowBinding
import com.example.submissionakhirfundamentalandroid.databinding.ItemUserBinding
import com.example.submissionakhirfundamentalandroid.domain.usecase.FollowerUseCase
import com.example.submissionakhirfundamentalandroid.domain.usecase.FollowingUseCase
import com.example.submissionakhirfundamentalandroid.presentation.activities.adapter.UserAdapter


import com.example.submissionakhirfundamentalandroid.presentation.activities.content.home.HomeFragment
import com.example.submissionakhirfundamentalandroid.presentation.base.BaseFragment
import com.example.submissionakhirfundamentalandroid.utilities.network.RequestClient
import com.example.submissionakhirfundamentalandroid.utilities.stateHandler.Resource

class FollowFragment : BaseFragment() {

    companion object {
        const val ARG_POSITION = "position"
        const val USERNAME_KEY = "username_key"
        const val FOLLOW_SLUG = "follow"
    }

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    private lateinit var userAdapter: UserAdapter<UserResponse>
    private val lists = ArrayList<UserResponse>()
    private val requestClient: RequestClient = RequestClient()
    private val detailRepository = DetailRepositoryImpl(requestClient)
    private val followingUseCase = FollowingUseCase(detailRepository)
    private val followerUseCase = FollowerUseCase(detailRepository)
    private val viewModel: FollowViewModel by viewModels { FollowViewModelFactory(followingUseCase = followingUseCase, followerUseCase = followerUseCase) }

    private var position: Int = 0
    private lateinit var username: String
    private var content = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(HomeFragment.USERNAME_KEY).toString()
        }

        if (position == 1){
            content = getString(R.string.label_follower)
            showFollower()
            viewModel.getFollowerUser(username)

        }else {
            content = getString(R.string.label_following)
            showFollowing()
            viewModel.getFollowingUser(username)
        }
    }

    private fun setupRecyclerView() {
        binding.rvContentFollow.layoutManager = LinearLayoutManager(activity)
        userAdapter = UserAdapter(FOLLOW_SLUG, lists)
        binding.rvContentFollow.adapter = userAdapter
        binding.rvContentFollow.itemAnimator = DefaultItemAnimator()
        userAdapter.notifyDataSetChanged()
    }

    private fun showFollowing(){
        viewModel.followingDataResult.observe(viewLifecycleOwner){resource ->
            when(resource){
                is Resource.Loading -> {}
                is Resource.Success -> {
                    if (resource.data != null){
                        isVisibleView(View.GONE, binding.placeholderFollowRoot)
                        if (resource.data.isEmpty()){
                            isVisibleView(View.GONE, binding.rvContentFollow)
                            isVisibleView(View.VISIBLE, binding.txtErrorResult)
                            binding.txtErrorResult.text = getString(R.string.label_content_is_empty, content)
                        }else {
                            isVisibleView(View.GONE, binding.txtErrorResult)
                            isVisibleView(View.VISIBLE, binding.rvContentFollow)
                            userAdapter.setDataUser(resource.data)
                        }
                    }
                }
                is Resource.Error -> {
                    showSnackBarWithAction(R.color.color_error, resource.message.toString(), null) {}
                }
            }
        }
    }

    private fun showFollower(){
        viewModel.followerDataResult.observe(viewLifecycleOwner){resource ->
            when(resource){
                is Resource.Loading -> {
                    binding.placeholderFollowRoot.isShimmerStarted
                    isVisibleView(View.GONE, binding.rvContentFollow)
                }
                is Resource.Success -> {
                    if (resource.data != null){
                        isVisibleView(View.GONE, binding.placeholderFollowRoot)
                        if (resource.data.isEmpty()){
                            isVisibleView(View.VISIBLE, binding.txtErrorResult)
                            isVisibleView(View.GONE, binding.rvContentFollow)
                            binding.txtErrorResult.text = getString(R.string.label_content_is_empty, content)
                        }else {
                            isVisibleView(View.GONE, binding.txtErrorResult)
                            isVisibleView(View.VISIBLE, binding.rvContentFollow)
                            userAdapter.setDataUser(resource.data)
                        }
                    }
                }
                is Resource.Error -> { showSnackBarWithAction(R.color.color_error, resource.message.toString(), null) {} }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        binding.placeholderFollowRoot.isShimmerStarted
    }

}

class FollowViewModelFactory(
    private val followingUseCase: FollowingUseCase,
    private val followerUseCase: FollowerUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FollowViewModel(
            followingUseCase = followingUseCase,
            followerUseCase = followerUseCase
        ) as T
    }
}