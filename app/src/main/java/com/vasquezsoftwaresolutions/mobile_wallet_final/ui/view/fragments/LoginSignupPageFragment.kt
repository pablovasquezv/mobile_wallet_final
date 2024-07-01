package com.vasquezsoftwaresolutions.mobile_wallet_final.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.vasquezsoftwaresolutions.mobile_wallet_final.R
import com.vasquezsoftwaresolutions.mobile_wallet_final.databinding.FragmentLoginSingnupPageBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class LoginSignupPageFragment : Fragment() {

    private var _binding: FragmentLoginSingnupPageBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginSingnupPageBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tieneCuenta.setOnClickListener {
            findNavController().navigate(R.id.action_loginSignUpFragment_to_loginFragment)
        }

        binding.creaCuenta.setOnClickListener {
            findNavController().navigate(R.id.action_loginSignUpFragment_to_signUpFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}