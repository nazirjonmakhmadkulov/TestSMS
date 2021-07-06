package com.nazirjon.testsms.ui.info

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
import com.nazirjon.testsms.databinding.InfoFragmentBinding
import com.nazirjon.testsms.utils.SharedPreference
import com.nazirjon.testsms.utils.isInternetAvailable

class InfoFragment : Fragment() {
    companion object {
        fun newInstance() = InfoFragment()
    }
    private lateinit var fragmentViewModel: InfoFragmentViewModel

    private var fragmentBinding:  InfoFragmentBinding? = null
    private val binding get() = fragmentBinding!!

    private lateinit var sharedPreference: SharedPreference

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        fragmentViewModel = ViewModelProvider(this).get(InfoFragmentViewModel::class.java)

        fragmentBinding = InfoFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        sharedPreference = SharedPreference(requireContext())


        binding.logoutButton.setOnClickListener {
            sharedPreference.deleteToken()
            view.findNavController().navigate(R.id.mainFragment)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isInternetAvailable(requireContext())) {
            binding.progressBar.isVisible = true
            fragmentViewModel.getInfo(sharedPreference.getTokenValue())
            fragmentViewModel.infoResponse.observe(viewLifecycleOwner, Observer {
                binding.progressBar.isVisible = false
                if (it.status.isNotEmpty()) {
                    Log.d(
                            "status ",
                            " ${it}"
                    )
                    binding.name.text = it.name
                    binding.phone.text = it.phone_number
                    binding.email.text = it.email
                    binding.sex.text = it.sex ?: "Нет данные"
                    binding.birthDay.text = it.birth_day ?: "Нет данные"
                    binding.rating.text = it.rating.toString()
                }
            })
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