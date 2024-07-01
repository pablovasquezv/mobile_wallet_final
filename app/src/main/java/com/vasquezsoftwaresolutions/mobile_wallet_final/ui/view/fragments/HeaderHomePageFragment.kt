package com.vasquezsoftwaresolutions.mobile_wallet_final.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vasquezsoftwaresolutions.mobile_wallet_final.R
import com.vasquezsoftwaresolutions.mobile_wallet_final.databinding.FragmentHeaderHomePageBinding
import com.vasquezsoftwaresolutions.mobile_wallet_final.utils.SharedPreferencesManager

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HeaderHomePageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HeaderHomePageFragment : Fragment() {
    private var _binding: FragmentHeaderHomePageBinding? = null
    private val binding get() = _binding!!

    private val sharedPreferencesManager by lazy { SharedPreferencesManager(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHeaderHomePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHeaderData()
    }

    override fun onResume() {
        super.onResume()
        setHeaderData() // Actualizar los datos del encabezado cada vez que el fragmento sea visible
    }

    private fun setHeaderData() {
        val user = sharedPreferencesManager.getUser()
        val getSaldo = sharedPreferencesManager.getSaldo()
        val firstName = user?.firstName ?: "Usuario"
        binding.saludo.text = "Hola $firstName"
        binding.saldo.text = "Saldo: $$getSaldo"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}