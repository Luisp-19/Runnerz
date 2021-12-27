package com.pm.runnerz.fragments.add

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pm.runnerz.R
import com.pm.runnerz.data.entities.Running
import com.pm.runnerz.data.viewmodel.RunningViewModel
import com.pm.runnerz.utils.Utils.Companion.hideKeyboard
import kotlinx.android.synthetic.main.fragment_add.*

class AddFragment : Fragment() {

    private lateinit var mProductViewModel: RunningViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        setHasOptionsMenu(true)

        mProductViewModel = ViewModelProvider(this).get(RunningViewModel::class.java)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_running, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        hideKeyboard()

        if (item.itemId == R.id.menu_addrun) {
            addCorrida()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun addCorrida() {
        if (!isValid()) {
            return Toast.makeText(
                requireContext(),
                getString(R.string.empty_run),
                Toast.LENGTH_LONG
            ).show()
        } /*else{
            Toast.makeText(requireContext(),
                getString(R.string.running_check),
            Toast.LENGTH_LONG
            ).show()
        }*/

        val addRunning = Running(
            0,
            addRunningName.text.toString(),
            addRunningData.text.toString(),
            addRunningDuration.text.toString(),
            addRunningKms.text.toString()
        )

        mProductViewModel.addRunning(addRunning)

        Toast.makeText(
            requireContext(),
            getString(R.string.succes_add_run),
            Toast.LENGTH_LONG
        ).show()

        findNavController().navigate(R.id.action_add_to_listFragment)
    }

    private fun isValid(): Boolean {
        return !(TextUtils.isEmpty(addRunningName.text.toString()) &&
                TextUtils.isEmpty(addRunningData.text.toString()) &&
                TextUtils.isEmpty(addRunningDuration.text.toString()) &&
                TextUtils.isEmpty(addRunningKms.text.toString()))
    }
}