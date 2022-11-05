package com.example.hiker.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hiker.HikerApplication
import com.example.hiker.R
import com.example.hiker.adapter.ComponentListAdapter
import com.example.hiker.databinding.FragmentCompanyListBinding
import com.example.hiker.databinding.FragmentShowComponentListBinding
import com.example.hiker.model.HikeEntity
import com.example.hiker.viewmodel.MainViewModel
import com.example.hiker.viewmodel.MainViewModelFactory

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
    private lateinit var backButtonImageView: ImageView
    private lateinit var hikeViewModel : MainViewModel
    private lateinit var salaryOld : TextView
    private lateinit var salaryNew : TextView
    private lateinit var taxOld : TextView
    private lateinit var taxNew : TextView
    //    all monthly symbols
    private lateinit var salaryOldSymbol : TextView
    private lateinit var salaryNewSymbol : TextView
    private lateinit var taxOldSymbol : TextView
    private lateinit var taxNewSymbol : TextView
    private lateinit var hikePercentageArea : TextView
    private lateinit var hikePercentageStr : String

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

        hikeViewModel = ViewModelProvider(this,
            MainViewModelFactory(HikerApplication.getRepositoryInstance()!!)
        )[MainViewModel::class.java]


        return showComponentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        componentRecyclerView = showComponentBinding.componentRecyclerView
        backButtonImageView = showComponentBinding.imageViewBack
        salaryNew = showComponentBinding.montlyIncome
        salaryOld = showComponentBinding.montlyIncomeOld
        taxNew = showComponentBinding.tax
        taxOld = showComponentBinding.taxOld
        hikePercentageArea =  showComponentBinding.hikePercentageValue
        hikePercentageStr = param1!!.hikeAmount
        hikePercentageArea.text = hikePercentageStr
        //       symbol
        salaryNewSymbol = showComponentBinding.montlyIncomeSymbol
        salaryOldSymbol = showComponentBinding.montlyIncomeSymbolOld
        taxNewSymbol = showComponentBinding.taxValueSymbol
        taxOldSymbol = showComponentBinding.taxValueSymbolOld
        //        setting text views
        salaryOld.text = hikeViewModel.currencyConversion(param1!!.inHandOld)
        salaryNew.text = hikeViewModel.currencyConversion(param1!!.inHandNew)
        taxOld.text = hikeViewModel.currencyConversion(param1!!.inRegimeOld)
        taxNew.text = hikeViewModel.currencyConversion(param1!!.inRegimeNew)
        componentRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        componentRecyclerView.adapter = ComponentListAdapter(param1?.component_arr!!)
        backButtonImageView.setOnClickListener {
         parentFragmentManager.popBackStack()
        }
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