package com.ahmed.contactbook.ui.home

import android.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmed.contactbook.BaseFragment
import com.ahmed.contactbook.R
import com.ahmed.contactbook.data.model.GetContact
import com.ahmed.contactbook.databinding.HomeFragmentBinding
import com.ahmed.contactbook.utils.ChechInternetConnection
import com.ahmed.contactbook.utils.ContactBookPreferences
import com.ahmed.contactbook.utils.SharedKeyEnum
import com.ahmed.contactbook.utils.toast
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : BaseFragment<HomeFragmentBinding>(), HomeListener {


    private lateinit var viewModel: HomeViewModel
    private lateinit var navController: NavController
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> HomeFragmentBinding
        get() = HomeFragmentBinding::inflate

    override fun setupOnViewCreated(view: View) {
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.homeListener = this
        navController = Navigation.findNavController(view)
        binding.rvAllContacts.layoutManager = LinearLayoutManager(requireContext())


        binding.btnAdd.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_addContactFragment)

        }
        binding.ivMenu.setOnClickListener {
            logout()
        }
        lifecycleScope.launch {
            viewModel.getContact()
            viewModel.contacts.observe(this@HomeFragment, {
                if (it.isNotEmpty()) {
                    binding.rvAllContacts.adapter = AllContactAdapter(it, viewModel)
                    binding.tvNoThing.visibility = View.GONE
                } else binding.tvNoThing.visibility = View.VISIBLE
            })
        }
        onKeyListener()
    }

    override fun onStarted() {
        showProgressDialog(requireContext())
    }

    override fun onSuccess() {
        hideProgressDialog()
    }

    override fun onDeleted() {
        hideProgressDialog()
        viewModel.getContact()
        lifecycleScope.launch {
            viewModel.contacts.observe(this@HomeFragment, {
                if (it.isNotEmpty()) {

                    binding.tvNoThing.visibility = View.GONE
                } else {
                    binding.tvNoThing.visibility = View.VISIBLE

                }
                binding.rvAllContacts.adapter = AllContactAdapter(it, viewModel)
            })
        }
    }

    override fun onFailure(msg: String) {
        hideProgressDialog()
        requireContext().toast("Can't Load Contact Try Again")

    }

    override fun isConnection(): Boolean {
        return ChechInternetConnection(requireContext()).isConnection()
    }


    private fun logout() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Do You want to Logout?")
            .setPositiveButton("Yes") { _, _ ->
                ContactBookPreferences().removeString(SharedKeyEnum.TOKEN.toString())
                navController.navigate(R.id.action_homeFragment_to_loginFragment)
            }.setNegativeButton("No") { _, _ ->

            }
        builder.show()
    }

    private fun search(name: String) {

        viewModel.contacts.observe(this, {
            val itemList = ArrayList<GetContact>()
            for (item in it) {
                if (item.name.toLowerCase(Locale.ROOT).contains(name.toLowerCase(Locale.ROOT)))
                    itemList.add(item)
            }


            if (itemList.isEmpty())
                binding.tvNoThing.visibility = View.VISIBLE
            else
                binding.tvNoThing.visibility = View.GONE


            binding.rvAllContacts.adapter =
                AllContactAdapter(itemList as List<GetContact>, viewModel)
        })
    }

    private fun onKeyListener() {
        binding.etSearch.clearFocus()
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                search(s.toString())
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                search(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }


}