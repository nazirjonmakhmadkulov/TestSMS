package com.nazirjon.testsms.ui.code

import android.os.Bundle
import android.os.CountDownTimer
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
import com.nazirjon.testsms.databinding.CodeFragmentBinding
import com.nazirjon.testsms.utils.SharedPreference
import com.nazirjon.testsms.utils.isInternetAvailable


class CodeFragment : Fragment() {
    companion object {
        fun newInstance() = CodeFragment()
    }
    private lateinit var fragmentViewModel: CodeFragmentViewModel

    private var fragmentBinding: CodeFragmentBinding? = null
    private val binding get() = fragmentBinding!!

    private lateinit var sharedPreference: SharedPreference

    private lateinit var phone_number: String

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        fragmentViewModel = ViewModelProvider(this).get(CodeFragmentViewModel::class.java)

        fragmentBinding = CodeFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        sharedPreference = SharedPreference(requireContext())

        val bundle = this.arguments
        if (bundle != null) {
            phone_number = bundle.getString("phone")!!
        }

        object : CountDownTimer(15000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.disCode.setText("Повторно отправить СМС с кодом можно будет через " + millisUntilFinished / 1000 + " секунд")
            }
            override fun onFinish() {
                binding.disCode.setText("")
                binding.btNext.isEnabled = true
            }
        }.start()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btNext.setOnClickListener { view: View ->
            if (isInternetAvailable(requireContext())) {
                binding.progressBar.isVisible = true
                if (binding.otpView.text!!.isNotEmpty()) {
                    fragmentViewModel.getauthClients(phone_number, binding.otpView.text.toString())
                    fragmentViewModel.token.observe(viewLifecycleOwner, Observer {
                        binding.progressBar.isVisible = false
                        if (it.isNotEmpty()) {
                            Log.d(
                                    "token ",
                                    " ${it}"
                            )
                            sharedPreference.saveTokenValue(it.toString())
                            view.findNavController().navigate(R.id.infoFragment)

                        }
                    })
                } else {
                    Log.d(
                            "CodeFragment",
                            "Error code ${binding.otpView.text.toString()}"
                    )
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