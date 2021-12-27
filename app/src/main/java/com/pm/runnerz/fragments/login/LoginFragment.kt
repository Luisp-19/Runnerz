package com.pm.runnerz.fragments.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pm.runnerz.R
import com.pm.runnerz.utils.Utils.Companion.hideKeyboard

class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_login, container, false)

        /*view.btn_submit.setOnClickListener {
            hideKeyboard()
            signin(view)
        }*/

        return view
    }

}