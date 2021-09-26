package com.ahmed.contactbook.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ahmed.contactbook.BaseFragment
import com.ahmed.contactbook.databinding.HomeFragmentBinding

class HomeFragment : BaseFragment<HomeFragmentBinding>() {


    private lateinit var viewModel: HomeViewModel
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> HomeFragmentBinding
        get() = HomeFragmentBinding::inflate

    override fun setupOnViewCreated(view: View) {
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }


}