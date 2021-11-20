package com.pm.runnerz.fragments

import android.app.AlertDialog
import android.opengl.ETC1.isValid
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.pm.runnerz.R
import com.pm.runnerz.data.entities.Running
import com.pm.runnerz.data.viewmodel.RunningViewModel
import com.pm.runnerz.utils.Utils.Companion.hideKeyboard
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import java.nio.file.Files.delete

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mProductViewModel: RunningViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mProductViewModel = ViewModelProvider(this).get(RunningViewModel::class.java)

        view.updateRunningName.setText(args.currenRun.name)

        setHasOptionsMenu(true)

        return view;
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_running, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        hideKeyboard()

        if (item.itemId == R.id.menu_addrun) {
            updateRunning()
        }

        if (item.itemId == R.id.menu_deleterun) {
            deleteRunning()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun updateRunning() {
        if (!isValid()) {
            return Toast.makeText(
                requireContext(),
                getString(R.string.empty_run),
                Toast.LENGTH_LONG
            ).show()
        }
        val running = Running(args.currenRun.id, updateRunningName.text.toString())

        mProductViewModel.updateRunning(running)

        Toast.makeText(
            requireContext(),
            getString(R.string.update_run_success),
            Toast.LENGTH_LONG
        ).show()
        findNavController().navigate(R.id.action_updateFragment_to_listFragment)
    }

    private fun deleteRunning() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            mProductViewModel.deleteRunning(args.currenRun)
            Toast.makeText(
                requireContext(),
                getString(R.string.delete_run_succes),
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton(getString(R.string.no)) { _, _ -> }
        builder.setTitle(getString(R.string.delete))
        builder.setMessage(getString(R.string.question_run_delete))
        builder.create().show()
    }

    private fun isValid(): Boolean {
        return !TextUtils.isEmpty(updateRunningName.text.toString())
    }
}