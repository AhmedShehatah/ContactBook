package com.ahmed.contactbook.ui.contactProfile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahmed.contactbook.data.model.GetPhone
import com.ahmed.contactbook.databinding.ContactProfileFragmentBinding
import com.ahmed.contactbook.databinding.PhoneShowBinding

class PhonesAdapter(
    private val contactProfile: ContactProfileFragmentBinding,
    private val list: List<GetPhone>
) :
    RecyclerView.Adapter<PhonesAdapter.ViewHolder>() {
    inner class ViewHolder(binding: PhoneShowBinding) : RecyclerView.ViewHolder(binding.root) {
        val phoneNumber = binding.textPhoneNumber
        val phoneType = binding.textPhoneType
        val container = binding.linearLayoutPhonesContainer

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PhoneShowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val phone = list[position]
        holder.phoneNumber.setText(phone.value)

        holder.phoneType.isEnabled = false
        when (phone.type_id) {
            1 -> holder.phoneType.setSelection(0)
            2 -> holder.phoneType.setSelection(1)
            3 -> holder.phoneType.setSelection(2)
            4 -> holder.phoneType.setSelection(3)
        }


    }

    override fun getItemCount(): Int = list.size

}