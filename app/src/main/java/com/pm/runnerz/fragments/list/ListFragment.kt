package com.pm.runnerz.fragments.list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pm.runnerz.R
import com.pm.runnerz.data.viewmodel.RunningViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment() {

    private lateinit var mRunningViewModel: RunningViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        setHasOptionsMenu(true)

        // Recyclerview
        val adapter = ListAdapter()
        val recyclerView = view.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // UserViewModel
        mRunningViewModel = ViewModelProvider(this).get(RunningViewModel::class.java)
        mRunningViewModel.readAllRunnings.observe(viewLifecycleOwner, { products ->
            adapter.setData(products)
        })

        view.btnAddRunFromList.setOnClickListener() {
            findNavController().navigate(R.id.action_listFragment_to_add)
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_login, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.menu_login) {
            openlogin()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun openlogin() {
        findNavController().navigate(R.id.action_listFragment_to_loginFragment)
    }

}