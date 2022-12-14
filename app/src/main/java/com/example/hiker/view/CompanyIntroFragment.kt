package com.example.hiker.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.hiker.R
import com.example.hiker.databinding.FragmentCompanyIntroBinding
import com.example.hiker.databinding.FragmentCompanyListBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CompanyIntroFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CompanyIntroFragment : BottomSheetDialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var companyIntroBinding : FragmentCompanyIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        companyIntroBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_company_intro, container, false);

        return companyIntroBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val calculateSalaryButton = companyIntroBinding.preview
        val companyName = companyIntroBinding.editTextTextPersonName5
        val location = companyIntroBinding.editTextTextPersonName6
        val ctc = companyIntroBinding.editTextTextPersonName7
        calculateSalaryButton.setOnClickListener {
            val companyNameValue = companyName.text.toString()
            val locationValue = location.text.toString()
            val ctcValue = ctc.text.toString()
            val bundle = Bundle()
            bundle.putString("company_name",companyNameValue)
            bundle.putString("location",locationValue)
            bundle.putString("ctc",ctcValue)
            findNavController().navigate(R.id.action_companyListFragment_to_salaryComponentPreviewFragment,bundle,null)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CompanyIntroFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CompanyIntroFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}