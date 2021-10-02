package com.ahmed.contactbook.ui.contactProfile

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ahmed.contactbook.BaseFragment
import com.ahmed.contactbook.R
import com.ahmed.contactbook.data.model.Phone
import com.ahmed.contactbook.data.model.UpdateContact
import com.ahmed.contactbook.databinding.ContactProfileFragmentBinding
import com.ahmed.contactbook.ui.addContact.AddListener
import com.ahmed.contactbook.utils.ChechInternetConnection
import com.ahmed.contactbook.utils.ContactBookPreferences
import com.ahmed.contactbook.utils.SharedKeyEnum
import com.ahmed.contactbook.utils.toast

class ContactProfileFragment : BaseFragment<ContactProfileFragmentBinding>(), AddListener {
    private lateinit var navController: NavController
    private lateinit var viewModel: ContactProfileViewModel
    private lateinit var normalBackground: Drawable
    private var enabled = false
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ContactProfileFragmentBinding
        get() = ContactProfileFragmentBinding::inflate

    override fun setupOnViewCreated(view: View) {
        viewModel = ViewModelProvider(this).get(ContactProfileViewModel::class.java)
        viewModel.addListener = this
        navController = Navigation.findNavController(view)
        normalBackground = binding.etEmail.background
        setData()
        binding.btnEdit.setOnClickListener {
            setEditTextState()
            if (!enabled) {
                updateUser()
                updatePhone()
            }
        }

    }

    private fun setData() {
        binding.etName.setText(arguments?.getString("name"))
        binding.etPhone.setText(arguments?.getString("phone"))
        binding.etNotes.setText(arguments?.getString("notes"))
        binding.etEmail.setText(arguments?.getString("email"))
        binding.apply {
            etName.background = null
            etEmail.background = null
            etPhone.background = null
            etNotes.background = null

        }
    }

    private fun setEditTextState() {
        enabled = !enabled
        if (enabled) {
            binding.btnEdit.setImageResource(R.drawable.check)
            binding.apply {
                etName.background = normalBackground
                etEmail.background = normalBackground
                etPhone.background = normalBackground
                etNotes.background = normalBackground
                etName.isEnabled = true
                etPhone.isEnabled = true
                etEmail.isEnabled = true
                etNotes.isEnabled = true

            }

        } else {
            binding.btnEdit.setImageResource(R.drawable.edit)
            binding.apply {
                etName.background = null
                etEmail.background = null
                etPhone.background = null
                etNotes.background = null
                etName.isEnabled = false
                etPhone.isEnabled = false
                etEmail.isEnabled = false
                etNotes.isEnabled = false

            }
        }
    }


    private fun updateUser() {

        val user = UpdateContact(
            if (binding.etEmail.text.toString() == arguments?.getString("email")) null else binding.etEmail.text.toString(),
            if (binding.etName.text.toString() == arguments?.getString("name")) null else binding.etName.text.toString(),
            if (binding.etNotes.text.toString() == arguments?.getString("notes")) null else binding.etNotes.text.toString(),

            )
        val token = "Bearer " + ContactBookPreferences().getString(SharedKeyEnum.TOKEN.toString())
        arguments?.getString("position")?.let { viewModel.updateUser(token, it, user) }


    }

    private fun updatePhone() {
        val token = "Bearer " + ContactBookPreferences().getString(SharedKeyEnum.TOKEN.toString())
        arguments?.getString("phone_id")
            ?.let {
                if (binding.etPhone.text.toString() != arguments?.getString("phone")) {
                    val phone = Phone(1, binding.etPhone.text.toString())
                    viewModel.updatePhone(token, it, phone)
                }
            }
    }

    override fun onStarted() {
        showProgressDialog(requireContext())
    }

    override fun onSuccess() {
        hideProgressDialog()
        requireContext().toast("saved successfully")
    }

    override fun onFailure(msg: String) {
        hideProgressDialog()
        requireContext().toast(msg)
    }

    override fun isConnection(): Boolean {
        return ChechInternetConnection(requireContext()).isConnection()
    }
}