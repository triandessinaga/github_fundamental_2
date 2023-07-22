package com.example.submissionakhirfundamentalandroid.presentation.activities.content.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.submissionakhirfundamentalandroid.R
import com.example.submissionakhirfundamentalandroid.data.repository.DetailRepositoryImpl
import com.example.submissionakhirfundamentalandroid.databinding.FragmentDetailBinding
import com.example.submissionakhirfundamentalandroid.domain.usecase.DetailUserUseCase
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator
import com.papero.gituser.presentation.activities.adapter.ContentDetailUserAdapter
import com.example.submissionakhirfundamentalandroid.presentation.activities.content.home.HomeFragment
import com.example.submissionakhirfundamentalandroid.presentation.base.BaseFragment
import com.example.submissionakhirfundamentalandroid.utilities.network.RequestClient
import com.example.submissionakhirfundamentalandroid.utilities.stateHandler.Resource

class DetailFragment : BaseFragment() {

    private lateinit var detailAdapter: ContentDetailUserAdapter

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val requestClient: RequestClient = RequestClient()
    private val detailRepository = DetailRepositoryImpl(requestClient)
    private val detailUserUseCase = DetailUserUseCase(detailRepository)
    private val viewModel: DetailViewModel by viewModels { DetailViewModelFactory(detailUserUseCase) }

    private var totalFollowers = 0
    private var totalFollowing = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailAdapter = ContentDetailUserAdapter(parentFragmentManager, lifecycle)
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNavigationView?.visibility = View.GONE

        getSelectedUser()

        viewModel.detailResult.observe(viewLifecycleOwner){resource ->
            val data = resource.data
            when(resource){
                is Resource.Loading -> {
                    binding.placeholderTopContent.isShimmerStarted
                }
                is Resource.Success -> {

                    if (data?.avatarUrl != null){
                        Glide.with(this)
                            .load(resource.data.avatarUrl)
                            .centerCrop()
                            .into(binding.profileImage)
                        binding.txtJob.isSelected = true
                        binding.txtLocation.isSelected = true
                        binding.txtName.text = (if (data.name == null) getString(R.string.label_content_is_empty, "Name") else  data.name).toString()
                        binding.txtUsername.text =
                            (if (data.username == null) getString(R.string.label_content_is_empty, "Username") else  data.username).toString()
                        binding.txtJob.text = (if (data.company == null) getString(R.string.label_content_is_empty, "Company") else  data.company).toString()
                        binding.txtLocation.text = (if (data.location == null) getString(R.string.label_content_is_empty, "Location") else  data.location).toString()

                        totalFollowers = resource.data.followers!!
                        totalFollowing = resource.data.following!!

                        binding.placeholderTopContent.visibility = View.GONE
                        binding.topRoot.visibility = View.VISIBLE
                    }
                    setupTabLayout()
                }
                is Resource.Error -> {}
            }
        }


    }

    private fun setupTabLayout(){
        val tabLayout = binding.bottomContent.tabLayout
        val vpContent = binding.bottomContent.vpContent

        vpContent.adapter = detailAdapter

        val labelTabLayout = resources.getStringArray(R.array.label_tab_exams)
        TabLayoutMediator(tabLayout, vpContent){tab, position ->
            when(position){
                0 -> { tab.text = String.format(labelTabLayout[0], totalFollowers.toString())}
                else -> {tab.text = String.format(labelTabLayout[1], totalFollowing.toString())}
            }

        }.attach()
    }

    private fun getSelectedUser(){
        val username = arguments?.getString(HomeFragment.USERNAME_KEY)
        if (username != null) {
            viewModel.getDetailUser(username)
            detailAdapter.username = username
        }
    }

    override fun onStart() {
        super.onStart()
        binding.placeholderTopContent.isShimmerStarted
    }
}

class DetailViewModelFactory(
    private val detailUserUseCase: DetailUserUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(
            detailUserUseCase
        ) as T
    }
}