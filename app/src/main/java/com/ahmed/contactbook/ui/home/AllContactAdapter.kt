package com.ahmed.contactbook.ui.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ahmed.contactbook.R
import com.ahmed.contactbook.data.model.GetContact
import com.ahmed.contactbook.databinding.ContactsListBinding
import com.ahmed.contactbook.utils.ContactBookPreferences
import com.ahmed.contactbook.utils.SharedKeyEnum
import java.util.*

class AllContactAdapter(
    private val list: ArrayList<GetContact>,
    private val viewModel: HomeViewModel
) :
    RecyclerView.Adapter<AllContactAdapter.ViewHolder>() {
    inner class ViewHolder(binding: ContactsListBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvName = binding.tvName
        val tvChar = binding.tvChar
        val layout = binding.layout

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ContactsListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemList = list[position]
        holder.tvName.text = itemList.name
        holder.tvChar.text = "${itemList.name[0]}${itemList.name[1]}".toUpperCase(Locale.ROOT)
        val bundle = bundleOf("contact" to itemList)

        holder.layout.setOnClickListener {

            it.findNavController()
                .navigate(R.id.action_homeFragment_to_contactProfileFragment, bundle)

        }

        holder.layout.setOnLongClickListener {
            val token =
                "Bearer " + ContactBookPreferences().getString(SharedKeyEnum.TOKEN.toString())
            val builder = AlertDialog.Builder(it.context)
            builder.setMessage("Do you want to Delete this contact?")
                .setPositiveButton("Yes") { _, _ ->
                    viewModel.deleteContact(itemList.id.toString())
                    list.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeRemoved(position, list.size)

                }.setNegativeButton("No") { _, _ -> }
            builder.show()
            true
        }

    }

    override fun getItemCount(): Int = list.size


}