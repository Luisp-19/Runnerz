package com.pm.runnerz.fragments.runz.add

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.pm.runnerz.R
import com.pm.runnerz.api_runnerz.dao.RunDao
import com.pm.runnerz.api_runnerz.retrofit.ServiceBuilder
import com.pm.runnerz.api_runnerz.runz.RunApi
import com.pm.runnerz.utils.Utils.Companion.getToken
import com.pm.runnerz.utils.Utils.Companion.getUserIdInSession
import com.pm.runnerz.utils.Utils.Companion.hideKeyboard
import com.pm.runnerz.utils.Utils.Companion.somethingWentWrong
import com.pm.runnerz.utils.Utils.Companion.unauthorized
import kotlinx.android.synthetic.main.fragment_add_run.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddRunFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_run, container, false)

        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add_run, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        hideKeyboard()

        if (item.itemId == R.id.menu_addrun_webhost) {
            addRun()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun addRun() {
        if (TextUtils.isEmpty(addRunName.text.toString())
            || TextUtils.isEmpty(addRunData.text.toString())
            || TextUtils.isEmpty(addRunDuration.text.toString())
            || TextUtils.isEmpty(addRunKms.text.toString())) {

            Toast.makeText(
                requireContext(),
                getString(R.string.running_check),
                Toast.LENGTH_LONG
            )
                .show()
        } else {
            llProgressBar.bringToFront()
            llProgressBar.visibility = View.VISIBLE

            val run = ServiceBuilder.buildService(RunApi::class.java)
            val call = run.createRun(
                token = "Bearer ${getToken()}",
                users_id = getUserIdInSession(),
                name = addRunName.text.toString(),
                data = addRunData.text.toString(),
                duration = addRunDuration.text.toString(),
                kms = addRunKms.text.toString()
            )

            call.enqueue(object : Callback<RunDao> {
                override fun onResponse(call: Call<RunDao>, response: Response<RunDao>) {
                    llProgressBar.visibility = View.GONE

                    if (response.isSuccessful) {
                        val run: RunDao = response.body()!!

                        if (run.status == "OK") {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.add_run),
                                Toast.LENGTH_LONG
                            ).show()
                            findNavController().navigate(R.id.action_addRunFragment_to_runListFragment)
                        } else {
                            Toast.makeText(
                                requireContext(), getString(
                                    resources.getIdentifier(
                                        run.message, "string",
                                        context?.packageName
                                    )
                                ), Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        if (response.code() == 401) {
                            unauthorized(navigatonHandlder = {
                                findNavController().navigate(R.id.action_addRunFragment_to_loginFragment)
                            })
                        } else {
                            somethingWentWrong()
                        }
                    }
                }

                override fun onFailure(call: Call<RunDao>, t: Throwable) {
                    llProgressBar.visibility = View.GONE
                    somethingWentWrong()
                }
            })
        }
    }
}