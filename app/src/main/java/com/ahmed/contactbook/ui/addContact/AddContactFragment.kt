package com.ahmed.contactbook.ui.addContact

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ahmed.contactbook.BaseFragment
import com.ahmed.contactbook.R
import com.ahmed.contactbook.data.model.Phone
import com.ahmed.contactbook.databinding.AddContactFragmentBinding
import com.ahmed.contactbook.databinding.PhoneViewBinding
import com.ahmed.contactbook.utils.ChechInternetConnection
import com.ahmed.contactbook.utils.toast

class AddContactFragment : BaseFragment<AddContactFragmentBinding>(), AddListener {
    private lateinit var navController: NavController
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> AddContactFragmentBinding
        get() = AddContactFragmentBinding::inflate
    private lateinit var viewModel: AddContactViewModel
    private lateinit var phoneView: PhoneViewBinding
    private val phones = ArrayList<PhoneViewBinding>()

    override fun setupOnViewCreated(view: View) {
        viewModel = ViewModelProvider(this).get(AddContactViewModel::class.java)
        navController = Navigation.findNavController(view)
        viewModel.addListener = this

        addView()
        binding.btnAdd.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val note = binding.etNotes.text.toString().trim()
            val name = binding.etName.text.toString().trim()
            val phones = getPhones()
            viewModel.validateData(name, phones, note, email)
        }
        binding.imageAddPhone.setOnClickListener { addView() }
        binding.imageRemovePhone.setOnClickListener { removeView() }
    }

    override fun onStarted() {
        showProgressDialog(requireContext())
    }

    override fun onSuccess() {
        hideProgressDialog()
        requireContext().toast("succeeded")
        navController.navigate(R.id.action_addContactFragment_to_homeFragment)


    }

    override fun onFailure(msg: String) {
        hideProgressDialog()
        requireContext().toast(msg)
    }

    override fun isConnection(): Boolean {
        return ChechInternetConnection(requireContext()).isConnection()
    }


    override fun addView() {
        phoneView = PhoneViewBinding.inflate(layoutInflater)
        binding.linearLayoutPhonesContainer.addView(phoneView.root)
        phones.add(phoneView)
    }

    override fun removeView() {
        binding.linearLayoutPhonesContainer.removeView(phoneView.root)
        phones.remove(phoneView)
    }

    override fun getPhones(): List<Phone> {
        val phoneList = ArrayList<Phone>()

        for (phone in phones) {
            phoneList.add(
                Phone(
                    phone.spinner.selectedItemPosition + 1,
                    phone.etPhoneNumber.text.toString()
                )
            )
        }
        return phoneList
    }
}