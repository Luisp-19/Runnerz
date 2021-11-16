package com.pm.runnerz.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pm.runnerz.R
import com.pm.runnerz.data.viewmodel.RunningViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment() {

    private  lateinit var mRunningViewModel: RunningViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

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

        view.btnAddProductFromList.setOnClickListener() {
            findNavController().navigate(R.id.action_listFragment_to_add2)
        }

        return view
    }
}