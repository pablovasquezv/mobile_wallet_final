package com.vasquezsoftwaresolutions.mobile_wallet_final.ui.view.fragments

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.vasquezsoftwaresolutions.mobile_wallet_final.R
import com.vasquezsoftwaresolutions.mobile_wallet_final.databinding.FragmentSplashBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        val view = binding.root
        val splashScreenDuration = resources.getInteger(R.integer.splash_screen_duration).toLong()

        // Configura el ImageView como invisible inicialmente
        binding.splashImage.visibility = View.INVISIBLE

        // Agregar un retraso antes de iniciar la animación
        val animationStartDelay = 500L // 500 ms de retraso antes de iniciar la animación

        view.postDelayed({
            // Hacer visible el ImageView justo antes de iniciar la animación
            binding.splashImage.visibility = View.VISIBLE
            // Inicializa la animación
            val animatedVectorDrawable = binding.splashImage.drawable as AnimatedVectorDrawable
            animatedVectorDrawable.start()
        }, animationStartDelay)

        // Posterga la transición a la siguiente actividad después de splashScreenDuration milisegundos
        view.postDelayed({
            findNavController().navigate(R.id.navigate_splashFragment_to_LoginSignupFragment)
        }, splashScreenDuration)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}