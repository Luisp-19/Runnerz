package com.pm.runnerz.fragments

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
        //hideKeyboard()

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
        }

        val running = Running(0, addRunningName.text.toString())

        mProductViewModel.addRunning(running)

        Toast.makeText(
            requireContext(),
            getString(R.string.succes_add_run),
            Toast.LENGTH_LONG
        ).show()

        findNavController().navigate(R.id.action_add2_to_listFragment)
    }

    private fun isValid(): Boolean {
        return !TextUtils.isEmpty(addRunningName.text.toString())
    }
}