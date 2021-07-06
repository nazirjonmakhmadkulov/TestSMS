package com.nazirjon.testsms.ui.splash

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.nazirjon.currencyvalue.utils.SharedPreference
import com.nazirjon.currencyvalue.utils.isInternetAvailable
import com.nazirjon.testsms.R
import com.nazirjon.testsms.databinding.InfoFragmentBinding
import com.nazirjon.testsms.databinding.SplashFragmentBinding


class SplashFragment : Fragment() {
    companion object {
        fun newInstance() = SplashFragment()
    }
    private lateinit var fragmentViewModel: SplashFragmentViewModel

    private var fragmentBinding:  SplashFragmentBinding? = null
    private val binding get() = fragmentBinding!!

    private lateinit var sharedPreference: SharedPreference

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        fragmentViewModel = ViewModelProvider(this).get(SplashFragmentViewModel::class.java)

        fragmentBinding = SplashFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        sharedPreference = SharedPreference(requireContext())

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isInternetAvailable(requireContext())) {
            if (sharedPreference.getTokenValue().isNotEmpty()) {
                fragmentViewModel.getInfo(sharedPreference.getTokenValue())
                fragmentViewModel.infoResponse.observe(viewLifecycleOwner, Observer {
                    if (it.status.isNotEmpty()) {
                        Log.d(
                            "status ",
                            " ${it}"
                        )
                        view.findNavController().navigate(R.id.infoFragment)
                    } else {
                        view.findNavController().navigate(R.id.mainFragment)
                    }
                })
            }else{
                view.findNavController().navigate(R.id.mainFragment)
            }
        }else{
            val toast = Toast.makeText(requireContext(), context?.getString(R.string.internet_info), Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentBinding = null
    }
}