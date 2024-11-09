package com.bignerdranch.andriod.criminalintent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.andriod.criminalintent.databinding.ListItemCrimeBinding
import com.bignerdranch.andriod.criminalintent.databinding.ListItemSeriousCrimeBinding
import com.bignerdranch.andriod.criminalintent.databinding.ListItemSeriousCrimeBinding.inflate

private const val VIEW_TYPE_NORMAL = 0
private const val VIEW_TYPE_SERIOUS = 1

class CrimeListAdapter(
    private val crimes: List<Crime>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if (crimes[position].requiresPolice) {
            VIEW_TYPE_SERIOUS
        } else {
            VIEW_TYPE_NORMAL
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_SERIOUS -> {
                val binding = inflate(inflater, parent, false)
                SeriousCrimeHolder(binding)
            }
            else -> {
                val binding = ListItemCrimeBinding.inflate(inflater, parent, false)
                CrimeHolder(binding)
            }
        }
    }

    override fun getItemCount() = crimes.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val crime = crimes[position]
        when (holder) {
            is CrimeHolder -> holder.bind(crime)
            is SeriousCrimeHolder -> holder.bind(crime)
        }
    }

    inner class CrimeHolder(
        private val binding: ListItemCrimeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(crime: Crime) {
            binding.crimeTitle.text = crime.title
            binding.crimeDate.text = crime.date.toString()
            binding.contactPoliceButton.visibility = View.GONE // Normal crimes do not need this button

            binding.root.setOnClickListener {
                Toast.makeText(
                    binding.root.context,
                    "${crime.title} clicked!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    inner class SeriousCrimeHolder(
        private val binding: ListItemSeriousCrimeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(crime: Crime) {
            binding.crimeTitle.text = crime.title
            binding.crimeDate.text = crime.date.toString()

            binding.contactPoliceButton.setOnClickListener {
                Toast.makeText(
                    binding.root.context,
                    "Contacting police for ${crime.title}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            binding.root.setOnClickListener {
                Toast.makeText(
                    binding.root.context,
                    "${crime.title} clicked!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
