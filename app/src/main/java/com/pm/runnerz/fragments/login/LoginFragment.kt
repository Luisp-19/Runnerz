package com.pm.runnerz.fragments.login

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.pm.runnerz.R
import com.pm.runnerz.api_runnerz.dao.UserDao
import com.pm.runnerz.api_runnerz.retrofit.ServiceBuilder
import com.pm.runnerz.api_runnerz.runz.UsersApi
import com.pm.runnerz.utils.Utils.Companion.hideKeyboard
import com.pm.runnerz.utils.Utils.Companion.somethingWentWrong
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_login, container, false)

        view.btn_login.setOnClickListener {
            hideKeyboard()
            signin(view)
        }

        return view
    }

    private fun signin(view: View) {
        if (TextUtils.isEmpty(login_username.text.toString()) || TextUtils.isEmpty(login_password.text.toString())) {
            Toast.makeText(
                requireContext(),
                getString(R.string.fill_user_password),
                Toast.LENGTH_LONG
            ).show()
        } else {
            signinRun(view)
        }
    }

    private fun signinRun(view: View) {
        val request = ServiceBuilder.buildService(UsersApi::class.java)
        val call = request.signin(login_username.text.toString(), login_password.text.toString())

        llProgressBar.bringToFront()
        llProgressBar.visibility = View.VISIBLE

        call.enqueue(object : Callback<UserDao> {
            override fun onResponse(call: Call<UserDao>, response: Response<UserDao>) {

                llProgressBar.visibility = View.GONE

                if (response.isSuccessful) {
                    val userDao: UserDao = response.body()!!

                    if (userDao.status == "OK") {
                        setUserSettings(userDao)
                        findNavController().navigate(R.id.action_loginFragment_to_runListFragment)
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.welcome) + " " + userDao.user.first().name,
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.wrong_username_password),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    somethingWentWrong()
                }
            }

            override fun onFailure(call: Call<UserDao>, t: Throwable) {
                llProgressBar.visibility = View.GONE
                somethingWentWrong()
            }
        })
    }

    fun setUserSettings(userDao: UserDao) {
        val preferences = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
        preferences.edit().putString("token", userDao.token).apply()
        preferences.edit().putString("user_id", userDao.user.first().id.toString()).apply()
        preferences.edit().putString("user_name", userDao.user.first().name).apply()
    }

}