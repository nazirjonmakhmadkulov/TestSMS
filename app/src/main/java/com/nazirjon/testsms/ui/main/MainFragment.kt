package com.nazirjon.testsms.ui.main

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.nazirjon.testsms.R
import com.nazirjon.testsms.databinding.MainFragmentBinding
import com.nazirjon.testsms.utils.SharedPreference
import com.nazirjon.testsms.utils.digitsOnly
import com.nazirjon.testsms.utils.isInternetAvailable


class MainFragment : Fragment() {
    companion object {
        fun newInstance() = MainFragment()
    }
    private lateinit var fragmentViewModel: MainFragmentViewModel

    private var fragmentBinding : MainFragmentBinding? = null
    private val binding get() = fragmentBinding!!

    private lateinit var sharedPreference: SharedPreference

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View {

        fragmentViewModel = ViewModelProvider(this).get(MainFragmentViewModel::class.java)

        fragmentBinding = MainFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        sharedPreference = SharedPreference(requireContext())


        return view
    }

    @Suppress("DEPRECATION")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.btNext.setOnClickListener { view: View ->
            if (isInternetAvailable(requireContext())) {
                if (binding.phone.text!!.isNotEmpty() && binding.phone.text!!.length == 16) {
                    binding.progressBar.isVisible = true
                    fragmentViewModel.getSMSCodeClient(binding.phone.text.toString().digitsOnly())
                    fragmentViewModel.smsResult.observe(viewLifecycleOwner, Observer {
                        binding.progressBar.isVisible = false
                        if (it.isNotEmpty()) {
                            Log.d(
                                    "smsResult ",
                                    " ${it}"
                            )
                            val bundle = Bundle()
                            bundle.putString("phone", binding.phone.text.toString().digitsOnly())
                            view.findNavController().navigate(R.id.codeFragment, bundle)
                        }else {
                            Log.d(
                                    "MainFragment",
                                    "Error phone ${binding.phone.text.toString()}"
                            )
                        }
                    })
                } else {
                    val toast = Toast.makeText(requireContext(), "Укажите правильный номер телефона", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                }
            }else{
                val toast = Toast.makeText(requireContext(), context?.getString(R.string.internet_info), Toast.LENGTH_LONG)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentBinding = null
    }
}