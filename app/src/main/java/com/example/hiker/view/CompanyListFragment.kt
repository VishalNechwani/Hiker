package com.example.hiker.view

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils.isEmpty
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hiker.HikerApplication
import com.example.hiker.R
import com.example.hiker.adapter.CompanyListAdapter
import com.example.hiker.databinding.FragmentCompanyListBinding
import com.example.hiker.model.HikeEntity
import com.example.hiker.viewmodel.MainViewModel
import com.example.hiker.viewmodel.MainViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

//enum class



/**
 * A simple [Fragment] subclass.
 * Use the [CompanyListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CompanyListFragment : Fragment() {

    private lateinit var companyListBinding : FragmentCompanyListBinding
    private lateinit var hikeViewModel : MainViewModel
    private lateinit var customAlertDialogView : View
    private lateinit var companyNameField : TextInputLayout
    private lateinit var expectedCTCField : TextInputLayout
    private lateinit var currentCTCField : TextInputLayout
    private lateinit var materialAlertDialogBuilder: MaterialAlertDialogBuilder
    private lateinit var rv:RecyclerView
    private lateinit var companyNameValue: TextInputEditText
    private lateinit var currentCtcValue:TextInputEditText
    private lateinit var expectedCtcValue:TextInputEditText
    private lateinit var errorMessage:String
    private lateinit var error_message_textview:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        companyListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_company_list, container, false);
        hikeViewModel = ViewModelProvider(this,MainViewModelFactory(HikerApplication.getRepositoryInstance()!!))[MainViewModel::class.java]
        return companyListBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        rv = companyListBinding.recyclerView
        val hikeNoListLayout = companyListBinding.noHikeListLayout
        val addButton =  companyListBinding.addButton
        val txtView = companyListBinding.noHikesTxt
        hikeNoListLayout.visibility = View.VISIBLE
        materialAlertDialogBuilder = MaterialAlertDialogBuilder(context!!)

        rv.layoutManager = LinearLayoutManager(context)
        hikeViewModel.getHikes().observe(this, Observer {
            if (it.isNotEmpty()){
                txtView.visibility = View.GONE
                rv.visibility = View.VISIBLE
                val customAdapter = CompanyListAdapter(it)
                rv.adapter = customAdapter
            }else{
                rv.visibility = View.GONE
                txtView.visibility = View.VISIBLE
            }
        })

        addButton.setOnClickListener {

        customAlertDialogView = LayoutInflater.from(context)
                .inflate(R.layout.companyintroalert, null, false)

            launchCustomAlertDialog()
        }


    }


    private fun launchCustomAlertDialog() {

        companyNameField  = customAlertDialogView.findViewById<TextInputLayout>(R.id.company_name)
        expectedCTCField = customAlertDialogView.findViewById<TextInputLayout>(R.id.expected_ctc)
        currentCTCField = customAlertDialogView.findViewById<TextInputLayout>(R.id.current_ctc)
        companyNameValue = customAlertDialogView.findViewById<TextInputEditText>(R.id.company_name_edit_text_alert)
        currentCtcValue = customAlertDialogView.findViewById<TextInputEditText>(R.id.current_ctc_edit_text_alert)
        expectedCtcValue = customAlertDialogView.findViewById<TextInputEditText>(R.id.expected_ctc_edit_text_alert)
        error_message_textview = customAlertDialogView.findViewById<TextView>(R.id.error_message)
        val inHandTextView = customAlertDialogView.findViewById<TextView>(R.id.calculate_in_hand_Text)
        val cancelTextView = customAlertDialogView.findViewById<TextView>(R.id.cancel)
        materialAlertDialogBuilder.setView(customAlertDialogView)
        val alertDialog = materialAlertDialogBuilder.create()

        inHandTextView.setOnClickListener {
            val company = companyNameField.editText?.text.toString()
            val expectedCtc = expectedCTCField.editText?.text.toString()
            val currentCtc = currentCTCField.editText?.text.toString()
            val regexCompany = regexCompany(company)
            if(!regexCompany){
                showingMessage(errorMessage,"#ff0000")
                return@setOnClickListener
            }
            if(!isCurrentNull(currentCtc)){
                showingMessage(errorMessage,"#ff0000")
                return@setOnClickListener
            }
            if(!isExpectedNull(expectedCtc)){
                showingMessage(errorMessage,"#ff0000")
                return@setOnClickListener
            }
            error_message_textview.text = ""
            val bundle = Bundle()
            bundle.putString("company_name",company)
            bundle.putString("location",expectedCtc)
            bundle.putString("ctc",currentCtc)
            findNavController().navigate(R.id.action_companyListFragment_to_salaryComponentPreviewFragment,bundle,null)
            alertDialog.dismiss()
        }

        cancelTextView.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
        companyNameField.requestFocus()
    }

    private fun showingMessage(errorMessage:String,color:String){
        error_message_textview.setTextColor(Color.parseColor(color))
        hikeViewModel.isCompanyNameProper = false
        error_message_textview.text = errorMessage
        error_message_textview.visibility = View.VISIBLE
    }

    private fun isCurrentNull(currentCtc: String): Boolean {
        if(currentCtc.isEmpty()){
            errorMessage = "Please Enter your current CTC"
            return false
        }
        return true
    }

    private fun isExpectedNull(expectedCtc: String): Boolean {
        if(expectedCtc.isEmpty()){
            errorMessage = "Please Enter your Hike Amount"
            return false
        }
        return true
    }

    private fun regexCompany(company: String): Boolean {
        val regex = Regex("[a-zA-Z0-9\\s]+$")
        val matched = regex.containsMatchIn(input = company)
        if(company.length<20 && matched){
            return true
        }
        errorMessage = "Error: contains only a-z, A-Z, 0-9"
        return false
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CompanyListFragment.
         */
        // TODO: Rename and change t
        //  ypes and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CompanyListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}