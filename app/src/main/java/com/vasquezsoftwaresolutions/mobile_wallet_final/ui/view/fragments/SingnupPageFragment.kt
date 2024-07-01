package com.vasquezsoftwaresolutions.mobile_wallet_final.ui.view.fragments

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.vasquezsoftwaresolutions.mobile_wallet_final.R
import com.vasquezsoftwaresolutions.mobile_wallet_final.databinding.FragmentSingnupPageBinding
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.local.database.WalletDataBase
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.model.UserModel
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.retrofit.RetrofitHelper
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.repository.TransactionRepository
import com.vasquezsoftwaresolutions.mobile_wallet_final.utils.SharedPreferencesManager
import com.vasquezsoftwaresolutions.mobile_wallet_final.utils.TransactionFetcher
import com.vasquezsoftwaresolutions.mobile_wallet_final.viewmodel.LoginViewModel
import com.vasquezsoftwaresolutions.mobile_wallet_final.viewmodel.SignUpViewModel
import com.vasquezsoftwaresolutions.mobile_wallet_final.viewmodel.ViewModelFactory


/**
 * A simple [Fragment] subclass.
 * Use the [SingnupPageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SingnupPageFragment : Fragment() {
    private var _binding: FragmentSingnupPageBinding? = null
    private val binding get() = _binding!!
    private lateinit var signUpViewModel: SignUpViewModel

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
        ViewModelFactory(
            sharedPreferencesManager,
            transactionFetcher
        )
    }

    /**
     * Esta variable se pasa como parametro al loginviewmodel para indicar en los metodos de
     * creacion de cuenta que se esta registrando un nuevo usuario
     */

    private var isSignUp = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        signUpViewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
        _binding = FragmentSingnupPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.creaCuenta.setOnClickListener {
            val user = getUserFromInputFields()
            user?.let {
                isSignUp = true
                signUpViewModel.signUp(it)
            } ?: run {
                Toast.makeText(
                    requireContext(),
                    "Por favor, completa todos los campos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        signUpViewModel.registerResult.observe(viewLifecycleOwner) { registerResult ->
            if (registerResult) {
                loginViewModel.login(
                    binding.includeSignupForm.editEmailInput.text.toString(),
                    binding.includeSignupForm.editPassInput.text.toString(),
                    isSignUp
                )
            } else {
                Toast.makeText(requireContext(), "Error en el registro", Toast.LENGTH_SHORT).show()
            }
        }

        signUpViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        loginViewModel.loginResult.observe(viewLifecycleOwner) { isSuccess ->
            if (!isSuccess) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.login_failed),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                findNavController().navigate(R.id.action_singnupPageFragment_to_homePageFragment)
            }
        }

        loginViewModel.createAccountResult.observe(viewLifecycleOwner) { success ->
            if (!success) {
                Toast.makeText(requireContext(), "Error creando cuenta", Toast.LENGTH_SHORT).show()
            }
        }

        loginViewModel.updateAccountResult.observe(viewLifecycleOwner) { updateAccountResult ->
            if (updateAccountResult) {
                findNavController().navigate(R.id.action_singnupPageFragment_to_homePageFragment)
            } else {
                Toast.makeText(requireContext(), "Error actualizando cuenta", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.tieneCuenta.setOnClickListener {
            findNavController().navigate(R.id.action_loginPageFragment_to_singnupPageFragment)
        }
    }

    private fun getUserFromInputFields(): UserModel? {
        val nombre = binding.includeSignupForm.editNombreInput.text.toString()
        val apellido = binding.includeSignupForm.editApellidoInput.text.toString()
        val email = binding.includeSignupForm.editEmailInput.text.toString()
        val password = binding.includeSignupForm.editPassInput.text.toString()
        val confirmPassword = binding.includeSignupForm.editConfirmPassInput.text.toString()

        // Devolver un objeto User con los datos ingresados por el usuario si son válidos
        return if (nombre.isNotEmpty() && apellido.isNotEmpty() && email.isNotEmpty() &&
            password.isNotEmpty() && confirmPassword.isNotEmpty() && password == confirmPassword
        ) {
            UserModel(nombre, apellido, email, password, 0, 0)
        } else {
            // Contraseñas no coinciden o algún campo está vacío, retornar null
            if (password != confirmPassword) {
                // Contraseñas no coinciden
                Toast.makeText(requireContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT)
                    .show()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(requireContext(), "Formato de correo incorrecto", Toast.LENGTH_SHORT)
                    .show()
            } else {
                // Alguno de los campos está vacío
                Toast.makeText(
                    requireContext(),
                    "Por favor, completa todos los campos",
                    Toast.LENGTH_SHORT
                ).show()
            }
            null
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}