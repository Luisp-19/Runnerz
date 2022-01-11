package com.pm.runnerz.fragments.update_run

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.pm.runnerz.R
import com.pm.runnerz.api_runnerz.dto.RunDto
import com.pm.runnerz.api_runnerz.retrofit.ServiceBuilder
import com.pm.runnerz.api_runnerz.runz.RunApi
import com.pm.runnerz.utils.Utils.Companion.getToken
import com.pm.runnerz.utils.Utils.Companion.hideKeyboard
import com.pm.runnerz.utils.Utils.Companion.somethingWentWrong
import com.pm.runnerz.utils.Utils.Companion.unauthorized
import kotlinx.android.synthetic.main.fragment_add_run.*
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateRunFragment : Fragment() {

    private val args by navArgs<UpdateRunFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update_run, container, false)

        setHasOptionsMenu(true)

        view.updateRunningName.setText(args.currentRunAPI.name_corrida)
        view.updateRunningData.setText(args.currentRunAPI.data_corrida)
        view.updateRunningDuration.setText(args.currentRunAPI.duration_corrida)
        view.updateRunningKms.setText(args.currentRunAPI.kms_corrida)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_running, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        hideKeyboard()

        if (item.itemId == R.id.menu_deleterun) {
            deleteRun()
        }

        if (item.itemId == R.id.menu_addrun) {
            updateRun()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun updateRun() {
        if (TextUtils.isEmpty(updateRunningName.text.toString())
            || TextUtils.isEmpty(updateRunningData.text.toString())
            || TextUtils.isEmpty(updateRunningDuration.text.toString())
            || TextUtils.isEmpty(updateRunningKms.text.toString())
        ) {
            Toast.makeText(
                requireContext(),
                getString(R.string.running_check),
                Toast.LENGTH_SHORT
            )
                .show()
        } else {
            val request = ServiceBuilder.buildService(RunApi::class.java)
            val call = request.updateRun(
                token = "Bearer ${getToken()}",
                id = args.currentRunAPI.id,
                name_corrida = addRunName.text.toString(),
                data_corrida = addRunData.text.toString(),
                duration_corrida = addRunDuration.text.toString(),
                kms_corrida = addRunKms.text.toString()
            )

            call.enqueue(object : Callback<RunDto> {
                override fun onResponse(call: Call<RunDto>, response: Response<RunDto>) {
                    if (response.isSuccessful) {
                        val report: RunDto = response.body()!!

                        if (report.status == "OK") {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.update_run_success),
                                Toast.LENGTH_LONG
                            ).show()
                            findNavController().navigate(R.id.action_updateRunFragment_to_runListFragment)
                        } else {
                            Toast.makeText(
                                requireContext(), getString(
                                    resources.getIdentifier(
                                        report.message, "string",
                                        context?.packageName
                                    )
                                ), Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        if (response.code() == 401) {
                            unauthorized(navigatonHandlder = {
                                findNavController().navigate(R.id.action_updateRunFragment_to_loginFragment)
                            })
                        } else {
                            somethingWentWrong()
                        }
                    }
                }

                override fun onFailure(call: Call<RunDto>, t: Throwable) {
                    somethingWentWrong()
                }
            })
        }
    }

    private fun deleteRun() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->

            val request = ServiceBuilder.buildService(RunApi::class.java)
            val call = request.deleteRun(
                token = "Bearer ${getToken()}",
                id = args.currentRunAPI.id
            )

            call.enqueue(object : Callback<RunDto> {
                override fun onResponse(call: Call<RunDto>, response: Response<RunDto>) {
                    if (response.isSuccessful) {
                        val report: RunDto = response.body()!!

                        if (report.status == "OK") {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.delete_run_succes),
                                Toast.LENGTH_SHORT
                            ).show()
                            findNavController().navigate(R.id.action_updateRunFragment_to_runListFragment)
                        } else {
                            Toast.makeText(
                                requireContext(), getString(
                                    resources.getIdentifier(
                                        report.message, "string",
                                        context?.packageName
                                    )
                                ), Toast.LENGTH_LONG
                            ).show()
                        }

                    } else {

                        if (response.code() == 401) {
                            unauthorized(navigatonHandlder = {
                                findNavController().navigate(R.id.action_updateRunFragment_to_loginFragment)
                            })
                        } else {
                            somethingWentWrong()
                        }
                    }
                }

                override fun onFailure(call: Call<RunDto>, t: Throwable) {
                    somethingWentWrong()
                }
            })
        }
        builder.setNegativeButton(getString(R.string.no)) { _, _ -> }
        builder.setTitle(getString(R.string.delete_run))
        builder.setMessage(getString(R.string.question_run_delete))
        builder.create().show()
    }

}