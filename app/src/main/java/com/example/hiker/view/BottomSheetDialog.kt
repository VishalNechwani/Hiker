package com.example.hiker.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.hiker.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText


class BottomSheetDialog : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(
            R.layout.fragment_company_intro,
            container, false
        )
        return view
    }
    
    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val calculateSalaryButton = view.findViewById<Button>(R.id.preview)
        val companyName = view.findViewById<TextInputEditText>(R.id.editTextTextPersonName5)
        val location = view.findViewById<TextInputEditText>(R.id.editTextTextPersonName6)
        val ctc = view.findViewById<TextInputEditText>(R.id.editTextTextPersonName7)
        calculateSalaryButton.setOnClickListener {
            this.dismiss()
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
    
    
    
    
    
}