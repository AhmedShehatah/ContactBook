package com.ahmed.contactbook.ui.contactProfile

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Spinner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmed.contactbook.BaseFragment
import com.ahmed.contactbook.R
import com.ahmed.contactbook.data.model.GetContact
import com.ahmed.contactbook.data.model.Phone
import com.ahmed.contactbook.data.model.UpdateContact
import com.ahmed.contactbook.databinding.ContactProfileFragmentBinding
import com.ahmed.contactbook.utils.ChechInternetConnection
import com.ahmed.contactbook.utils.ContactBookPreferences
import com.ahmed.contactbook.utils.SharedKeyEnum
import com.ahmed.contactbook.utils.toast

class ContactProfileFragment : BaseFragment<ContactProfileFragmentBinding>(), ProfileListener {
    private lateinit var navController: NavController
    private lateinit var viewModel: ContactProfileViewModel
    private lateinit var normalBackground: Drawable
    var enabled = false
    private lateinit var contact: GetContact
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ContactProfileFragmentBinding
        get() = ContactProfileFragmentBinding::inflate

    override fun setupOnViewCreated(view: View) {
        viewModel = ViewModelProvider(this).get(ContactProfileViewModel::class.java)
        contact = arguments?.getParcelable("contact")!!
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

        binding.recyclerPhones.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerPhones.adapter =
            PhonesAdapter(binding, contact.phones)
        binding.etName.setText(contact.name)
        binding.etNotes.setText(if (contact.notes.isNullOrEmpty()) "Notes" else contact.notes)
        binding.etEmail.setText(if (contact.email.isNullOrEmpty()) "Email" else contact.email)
        binding.apply {
            etName.background = null
            etEmail.background = null
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
                etNotes.background = normalBackground
                etName.isEnabled = true
                etEmail.isEnabled = true
                etNotes.isEnabled = true

                for(i in contact.phones.indices){
                    val view = binding.recyclerPhones.getChildAt(i)
                    val phoneNumber = view.findViewById<EditText>(R.id.text_phone_number)
                    val phoneType = view.findViewById<Spinner>(R.id.text_phone_type)
                    phoneNumber.isEnabled = true
                    phoneType.isEnabled = true

                }

            }

        } else {
            binding.btnEdit.setImageResource(R.drawable.edit)
            binding.apply {
                etName.background = null
                etEmail.background = null
                etNotes.background = null
                etName.isEnabled = false
                etEmail.isEnabled = false
                etNotes.isEnabled = false
                for(i in contact.phones.indices){
                    val view = binding.recyclerPhones.getChildAt(i)
                    val phoneNumber = view.findViewById<EditText>(R.id.text_phone_number)
                    val phoneType = view.findViewById<Spinner>(R.id.text_phone_type)
                    phoneNumber.isEnabled = false
                    phoneType.isEnabled = false

                }

            }
        }
    }


    private fun updateUser() {
        val user = UpdateContact(
            if (binding.etEmail.text.toString() == contact.email) null else binding.etEmail.text.toString(),
            if (binding.etName.text.toString() == contact.name) null else binding.etName.text.toString(),
            if (binding.etNotes.text.toString() == contact.notes) null else binding.etNotes.text.toString(),

            )
        val token = "Bearer " + ContactBookPreferences().getString(SharedKeyEnum.TOKEN.toString())
        contact.id.let { viewModel.updateUser(token, it.toString(), user) }


    }

    private fun updatePhone() {
        val token = "Bearer " + ContactBookPreferences().getString(SharedKeyEnum.TOKEN.toString())

        for (i in contact.phones.indices) {
            val view = binding.recyclerPhones.getChildAt(i)
            val phoneNumber = view.findViewById<EditText>(R.id.text_phone_number)
            val phoneType = view.findViewById<Spinner>(R.id.text_phone_type)

            val phone = Phone(
                phoneType.selectedItemPosition + 1,
                phoneNumber.text.toString()
            )
            viewModel.updatePhone(token, contact.phones[i].id.toString(), phone)
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