package com.vasquezsoftwaresolutions.mobile_wallet_final.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vasquezsoftwaresolutions.mobile_wallet_final.R
import com.vasquezsoftwaresolutions.mobile_wallet_final.databinding.FragmentLoginPageBinding
import com.vasquezsoftwaresolutions.mobile_wallet_final.databinding.FragmentLoginSingnupPageBinding
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.local.database.WalletDataBase
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.retrofit.RetrofitHelper
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.repository.TransactionRepository
import com.vasquezsoftwaresolutions.mobile_wallet_final.utils.SharedPreferencesManager
import com.vasquezsoftwaresolutions.mobile_wallet_final.utils.TransactionFetcher
import com.vasquezsoftwaresolutions.mobile_wallet_final.viewmodel.LoginViewModel
import com.vasquezsoftwaresolutions.mobile_wallet_final.viewmodel.ViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginPageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginPageFragment : Fragment() {

    private var _binding: FragmentLoginPageBinding? = null
    private val binding get() = _binding!!

    private val sharedPreferencesManager by lazy { SharedPreferencesManager(requireContext()) }

    private val appDatabase by lazy {
        WalletDataBase.getDatabase(requireContext())
    }

    private val transactionRepository by lazy {
        TransactionRepository(appDatabase.transactionDao(), RetrofitHelper.apiService)
    }

    private val transactionFetcher by lazy {
        TransactionFetcher(transactionRepository)
    }

    private val loginViewModel: LoginViewModel by viewModels {
        ViewModelFactory(sharedPreferencesManager, transactionFetcher)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginPageBinding.inflate(inflater, container, false)
        setEmailInput()
        return binding.root
    }

    /**
     * Set email input desde sharedpreferences
     */
    private fun setEmailInput() {
        binding.editTextEmail.setText(sharedPreferencesManager.getUser()?.email)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginBtn.setOnClickListener {
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()
            loginViewModel.login(email, password)
        }

        binding.tieneCuenta.setOnClickListener {
            findNavController().navigate(R.id.
                    action_loginPageFragment_to_singnupPageFragment)
        }

        loginViewModel.loginResult.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                findNavController().navigate(R.id.action_loginPageFragment_to_homePageFragment)
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.login_failed),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}