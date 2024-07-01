package com.vasquezsoftwaresolutions.mobile_wallet_final.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vasquezsoftwaresolutions.mobile_wallet_final.R
import com.vasquezsoftwaresolutions.mobile_wallet_final.databinding.ItemTransactionBinding
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.local.entity.TransactionEntity
import com.vasquezsoftwaresolutions.mobile_wallet_final.utils.toReadableDate

/**
 *@autor Pablo
 *@create 27-06-2024 14:43
 *@project mobile_wallet_final
 *@Version 1.0
 */
class TransactionAdapter : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    private var transactions: List<TransactionEntity> = emptyList()

    fun submitList(transactions: List<TransactionEntity>) {
        this.transactions = transactions
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding =
            ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(transactions[position])
    }

    override fun getItemCount(): Int = transactions.size

    inner class TransactionViewHolder(private val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(transaction: TransactionEntity) {
            with(binding) {
                nameTextView.text = transaction.concept
                dateTextView.text = transaction.date.toReadableDate()

                // Improved formatting clarity and conciseness
                amountTextView.text = root.context.getString(
                    R.string.transaction_amount_with_sign,
                    formatAmount(transaction.amount)
                )

                // Use when for enhanced readability and type safety
                val iconResource = when (transaction.type) {
                    "topup" -> R.drawable.request_icon_azul
                    else -> R.drawable.send_icon_amarillo
                }
                transactionTypeIcon.setImageResource(iconResource)
            }
        }

        fun formatAmount(amount: Long): String {
            val formattedAmount = String.format("%,d", amount) // Format with commas for thousands
            return if (amount >= 0) {
                "+$$formattedAmount" // Add plus sign and dollar symbol for positive amounts
            } else {
                "-$${formattedAmount.substring(1)}" // Add minus sign and dollar symbol for negative amounts
            }
        }
    }
}