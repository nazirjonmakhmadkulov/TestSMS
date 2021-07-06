package com.nazirjon.testsms.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.desiredsoftware.currencywatcher.data.api.RemoteService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainFragmentViewModel : ViewModel() {

    var smsResult: MutableLiveData<String> = MutableLiveData()
    private val remoteService: RemoteService = RemoteService()

    // Метод получить status
    fun getSMSCodeClient(phone: String): Disposable {
        val res = remoteService.serviceBuilder.requestSMSCodeClient(phone)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ smsRes ->
                smsResult.value = smsRes.status
                Log.d(
                    "MainFragmentViewModel",
                    "Success called in the getSMSCodeClient API method for the date requested: ${smsRes.status}"
                )
            },
                { throwable ->
                    Log.d(
                        "MainFragmentViewModel",
                        "onError called in the getSMSCodeClient API method: ${throwable.printStackTrace()}"
                    )
                })
        return res
    }
}