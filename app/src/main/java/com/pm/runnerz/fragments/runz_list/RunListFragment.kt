package com.pm.runnerz.fragments.runz_list

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pm.runnerz.R
import com.pm.runnerz.api_runnerz.models.Run
import com.pm.runnerz.api_runnerz.retrofit.ServiceBuilder
import com.pm.runnerz.api_runnerz.runz.RunApi
import com.pm.runnerz.utils.Utils.Companion.getToken
import com.pm.runnerz.utils.Utils.Companion.getUserIdInSession
import com.pm.runnerz.utils.Utils.Companion.hideKeyboard
import com.pm.runnerz.utils.Utils.Companion.somethingWentWrong
import com.pm.runnerz.utils.Utils.Companion.unauthorized
import kotlinx.android.synthetic.main.fragment_run_list.*
import kotlinx.android.synthetic.main.fragment_run_list.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RunListFragment : Fragment() {
    private var _view: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_run_list, container, false)
        _view = view

        setHasOptionsMenu(true)

        getAndSetData(view)

        view.BtnAddNewRunFromRunsList.setOnClickListener() {
            findNavController().navigate(R.id.action_runListFragment_to_addRunFragment)
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_list_run, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        hideKeyboard()

        if (item.itemId == R.id.user_logout) {
            logout()
        }

        if (item.itemId == R.id.reports_list_refresh) {
            _view?.let { getAndSetData(it) }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun getAndSetData(view: View) {

        view.llProgressBarList.bringToFront()
        view.llProgressBarList.visibility = View.VISIBLE

        val adapter = RunListAdapter(getUserIdInSession())

        val recyclerView = view.recyclerview_runs_list
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val request = ServiceBuilder.buildService(RunApi::class.java)
        val call = request.getRunz(token = "Bearer ${getToken()}")

        call.enqueue(object : Callback<List<Run>> {
            override fun onResponse(call: Call<List<Run>>, response: Response<List<Run>>) {

                llProgressBarList.visibility = View.GONE

                if (response.isSuccessful) {
                    val runs: List<Run> = response.body()!!
                    adapter.setData(runs)
                } else {
                    if (response.code() == 401) {
                        unauthorized(navigatonHandlder = {
                            findNavController().navigate(R.id.action_runListFragment_to_loginFragment)
                        })
                    } else {
                        somethingWentWrong()
                    }
                }
            }

            override fun onFailure(call: Call<List<Run>>, t: Throwable) {
                llProgressBarList.visibility = View.GONE
                somethingWentWrong()
            }
        })
    }

    private fun logout() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            val preferences = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
            preferences.edit().putString("token", null).apply()
            findNavController().navigate(R.id.action_runListFragment_to_loginFragment)
        }
        builder.setNegativeButton(getString(R.string.no)) { _, _ -> }
        builder.setTitle(getString(R.string.logout))
        builder.setMessage(getString((R.string.logout_question)))
        builder.create().show()
    }
}