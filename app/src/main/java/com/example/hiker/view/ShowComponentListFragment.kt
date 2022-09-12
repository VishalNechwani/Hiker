package com.example.hiker.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hiker.R
import com.example.hiker.adapter.ComponentListAdapter
import com.example.hiker.databinding.FragmentCompanyListBinding
import com.example.hiker.databinding.FragmentShowComponentListBinding
import com.example.hiker.model.HikeEntity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ShowComponentListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShowComponentListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: HikeEntity? = null
    private lateinit var showComponentBinding : FragmentShowComponentListBinding
    private lateinit var componentRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getParcelable("hikeEntity")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        showComponentBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_show_component_list, container, false);

        return showComponentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        componentRecyclerView = showComponentBinding.componentRecyclerView
        componentRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        componentRecyclerView.adapter = ComponentListAdapter(param1?.component_arr!!)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ShowComponentListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ShowComponentListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}