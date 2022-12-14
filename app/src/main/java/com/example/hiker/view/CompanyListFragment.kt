package com.example.hiker.view

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hiker.HikerApplication
import com.example.hiker.R
import com.example.hiker.adapter.CompanyListAdapter
import com.example.hiker.adapter.CompanyListCallBack
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
class CompanyListFragment : Fragment(),CompanyListCallBack {

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
    private lateinit var deleteImg:ImageView
    private lateinit var hikerMap:HashMap<Int,HikeEntity>
    private lateinit var materialDeleteAlert: MaterialAlertDialogBuilder
    private lateinit var companyListAdapter : CompanyListAdapter
    private var counter = 0
    var backPressedTime: Long = 0
    var hikeArrayList : ArrayList<HikeEntity> = ArrayList<HikeEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        counter = 0
        // Inflate the layout for this fragment
        companyListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_company_list, container, false);
        hikeViewModel = ViewModelProvider(this,MainViewModelFactory(HikerApplication.getRepositoryInstance()!!))[MainViewModel::class.java]
        hikerMap = HashMap()
        materialDeleteAlert = MaterialAlertDialogBuilder(context!!)
        materialAlertDialogBuilder = MaterialAlertDialogBuilder(context!!)
        return companyListBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        context!!.toast("Click on + icon to Add Hikes")
        rv = companyListBinding.recyclerView
        deleteImg = companyListBinding.menuDelete
        deleteImg.visibility = View.INVISIBLE
        val addButton =  companyListBinding.addButton
        val txtView = companyListBinding.noHikesTxt
        rv.layoutManager = LinearLayoutManager(context)
        hikeArrayList.clear()
        companyListBinding.menuDelete.setOnClickListener {
            showDeleteAlert()
        }
        hikeViewModel.getHikes().observe(this, Observer {
            if (it.isNotEmpty()){
                txtView.visibility = View.GONE
                rv.visibility = View.VISIBLE
                val n = counter
                for(item in it){
                    hikeArrayList.add(item)
                }
                if(hikeArrayList.size==1){
                    context!!.toast("click on individual list to check the Detailed View")
                }
                companyListAdapter = CompanyListAdapter(context!!,hikeArrayList,this)
                rv.adapter = companyListAdapter
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
            for(item in hikeArrayList){
              if(item.company_name.toLowerCase().equals(company.toLowerCase())){
                  errorMessage = "Company Name is duplicate, please add another"
                  showingMessage(errorMessage,"#ff0000")
                  return@setOnClickListener
              }
            }
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

    private fun showDeleteAlert() {
        val alert = materialDeleteAlert.create()
        materialDeleteAlert.setMessage("Do you really want to delete this hiker ?")
        materialDeleteAlert.setPositiveButton(android.R.string.yes){ dialog, which ->
            companyListAdapter.deleteHikerInAdapter()
        }
        materialDeleteAlert.setNegativeButton(android.R.string.no) { dialog, which ->
            alert.dismiss()
        }
        materialDeleteAlert.show()
    }

    override fun showDeleteIcon() {
        companyListBinding.menuDelete.visibility = View.VISIBLE
    }

    override fun HideDeleteIcon() {
        companyListBinding.menuDelete.visibility = View.GONE
    }

     override fun deleteHiker(deleteHikes: ArrayList<Int>) {
            hikeViewModel.deleteHiker(deleteHikes)
        }

    override fun navigateToCompanyShowComponent(holderPosition: Int, hikeEntity: HikeEntity?) {
        val bundle = Bundle()
        bundle.putParcelable("hikeEntity",hikeEntity)
        findNavController().navigate(R.id.action_companyListFragment_to_showComponentListFragment,bundle)
    }

    fun Context.toast(message: CharSequence) =
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

}

