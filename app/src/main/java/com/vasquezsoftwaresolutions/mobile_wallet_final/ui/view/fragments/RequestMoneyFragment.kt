package com.vasquezsoftwaresolutions.mobile_wallet_final.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.vasquezsoftwaresolutions.mobile_wallet_final.R
import com.vasquezsoftwaresolutions.mobile_wallet_final.databinding.FragmentRequestMoneyBinding
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.local.database.WalletDataBase
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.retrofit.RetrofitHelper
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.repository.TransactionRepository
import com.vasquezsoftwaresolutions.mobile_wallet_final.utils.SharedPreferencesManager
import com.vasquezsoftwaresolutions.mobile_wallet_final.utils.TransactionFetcher
import com.vasquezsoftwaresolutions.mobile_wallet_final.viewmodel.LoginViewModel
import com.vasquezsoftwaresolutions.mobile_wallet_final.viewmodel.TransactionViewModel
import com.vasquezsoftwaresolutions.mobile_wallet_final.viewmodel.ViewModelFactory


/**
 * A simple [Fragment] subclass.
 * Use the [RequestMoneyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RequestMoneyFragment : Fragment() {
    private var _binding: FragmentRequestMoneyBinding? = null
    private val binding get() = _binding!!

    private val sharedPreferencesManager by lazy { SharedPreferencesManager(requireContext()) }
    private val appDatabase by lazy {
        WalletDataBase
        .getDatabase(requireContext())
    }

    private val transactionRepository by lazy {
        TransactionRepository(appDatabase.transactionDao(), RetrofitHelper.apiService)
    }

    private val transactionFetcher by lazy {
        TransactionFetcher(transactionRepository)
    }
    private val transactionViewModel: TransactionViewModel by viewModels {
        ViewModelFactory(
            sharedPreferencesManager,
            transactionFetcher
        )
    }
    private val loginViewModel: LoginViewModel by viewModels {
        ViewModelFactory(
            sharedPreferencesManager,
            transactionFetcher
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRequestMoneyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupHeaderFragment()

        binding.ingresarBtn.setOnClickListener {
            val type = "topup"
            val concept = binding.conceptoIngreso.text.toString()
            val amount = binding.montoIngreso.text.toString().toLong()

            transactionViewModel.depositarOtransferir(type, concept, amount)
        }

        observeViewModels()
    }

    private fun observeViewModels() {
        transactionViewModel.transactionResult.observe(viewLifecycleOwner) { result ->
            if (result) {
                val token = sharedPreferencesManager.getAuthToken()
                if (token != null) {
                    loginViewModel.getUserAccountsDetails(token)
                    loginViewModel.accountDetailsUpdated.observe(viewLifecycleOwner) { isUpdated ->
                        if (isUpdated) {
                            Toast.makeText(context, "Depósito realizado correctamente", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_requestMoneyFragment_to_homePageFragment)
                        } else {
                            Toast.makeText(context, "Error al actualizar el saldo", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(context, "Token no disponible", Toast.LENGTH_SHORT).show()
                }
            } else {
                transactionViewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
                    Toast.makeText(context, "Error en el depósito: $errorMessage", Toast.LENGTH_SHORT).show()
                })
            }
        }
    }

    private fun setupHeaderFragment() {
        val fragment = TransactionHeaderFragment()
        childFragmentManager.beginTransaction()
            .replace(R.id.user_container, fragment)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}