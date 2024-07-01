package com.vasquezsoftwaresolutions.mobile_wallet_final.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vasquezsoftwaresolutions.mobile_wallet_final.R
import com.vasquezsoftwaresolutions.mobile_wallet_final.databinding.FragmentHomePageBinding
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.local.database.WalletDataBase
import com.vasquezsoftwaresolutions.mobile_wallet_final.ui.view.adapter.TransactionAdapter
import com.vasquezsoftwaresolutions.mobile_wallet_final.utils.SharedPreferencesManager


/**
 * A simple [Fragment] subclass.
 * Use the [HomePageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomePageFragment : Fragment() {
    private var _binding: FragmentHomePageBinding? = null
    private val binding get() = _binding!!
    private val transactionAdapter = TransactionAdapter()
    private val transactionDao by lazy {
        WalletDataBase
            .getDatabase(requireContext()).transactionDao()
    }
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupHeaderFragment()
        setupRecyclerView()
        loadTransactions()
        sharedPreferencesManager = SharedPreferencesManager(requireContext())

        // Obtener saldo
        val saldoStr = sharedPreferencesManager.getSaldo()
        val saldo = saldoStr?.toFloatOrNull()

        // Se cambia el color del botón según el saldo
        if (saldo != null && saldo > 0) {
            binding.enviarDinero.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.verde) // Color habilitado

        } else {
            binding.enviarDinero.backgroundTintList = ContextCompat.getColorStateList(
                requireContext(),
                R.color.gris_claro
            ) // Color deshabilitado

        }

        binding.ingresarDinero.setOnClickListener {
            findNavController().navigate(R.id.action_homePageFragment_to_requestMoneyFragment,
                Bundle().apply {
                    putString("transaction_type", "topup")
                })
        }

        binding.enviarDinero.setOnClickListener {
            //Se verifica el saldo si es 0 se envia mensaje de que no se pueden enviar dinero.
            //Se prefiere mantener el click para mostrar mensaje al usuario
            if (saldo != null && saldo > 0) {
                findNavController().navigate(R.id.action_homePageFragment_to_sendMoneyFragment,
                    Bundle().apply {
                        putString("transaction_type", "payment")
                    })

            } else {
                Toast.makeText(
                    requireContext(),
                    "No se pueden hacer transferencias de una cuenta sin saldo",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        binding.headerContainerHomePage.profileImg.setOnClickListener {
            findNavController().navigate(R.id.action_homePageFragment_to_profilePageFragment)
        }

    }

    private fun setupRecyclerView() {
        binding.transactionsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = transactionAdapter
        }
    }

    private fun loadTransactions() {
        transactionDao.getAllTransactions().observe(viewLifecycleOwner, Observer { transactions ->
            transactions?.let {
                if (it.isEmpty()) {
                    binding.transactionsRecyclerView.visibility = View.GONE
                    binding.noTransactionsView.visibility = View.VISIBLE
                } else {
                    binding.transactionsRecyclerView.visibility = View.VISIBLE
                    binding.noTransactionsView.visibility = View.GONE
                    transactionAdapter.submitList(it)
                }
            }
        })
    }

    private fun setupHeaderFragment() {
        val fragment = HeaderHomePageFragment()
        childFragmentManager.beginTransaction()
            .replace(R.id.headerContainerHomePage, fragment)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}