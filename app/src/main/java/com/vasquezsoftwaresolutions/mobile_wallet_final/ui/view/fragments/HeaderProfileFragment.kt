package com.vasquezsoftwaresolutions.mobile_wallet_final.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vasquezsoftwaresolutions.mobile_wallet_final.R
import com.vasquezsoftwaresolutions.mobile_wallet_final.databinding.FragmentHeaderProfileBinding


/**
 * A simple [Fragment] subclass.
 * Use the [HeaderProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HeaderProfileFragment : Fragment() {
    private var _binding: FragmentHeaderProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHeaderProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
}