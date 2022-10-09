package com.example.hiker.view

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hiker.R
import com.example.hiker.adapter.SalaryComponentAdapter
import com.example.hiker.databinding.FragmentSalaryComponentPreviewBinding
import android.content.DialogInterface
import android.graphics.Color
import android.icu.text.NumberFormat
import android.os.AsyncTask
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.hiker.HikerApplication
import com.example.hiker.model.HikeEntity
import com.example.hiker.utils.Component
import com.example.hiker.viewmodel.MainViewModel
import com.example.hiker.viewmodel.MainViewModelFactory
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "company_name"
private const val ARG_PARAM2 = "location"
private const val ARG_PARAM3 = "ctc"

/**
 * A simple [Fragment] subclass.
 * Use the [SalaryComponentPreviewFragment.newInstance] factory method to
 * create an instance of this f
 * ragment.
 */
enum class REGIME{
    OLD_TAX_REGIME,
    NEW_TAX_REGIME
}

class SalaryComponentPreviewFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var companyName: String? = null
    private var ctc: Int = 0  //this is expected
    private var currentCtc: Int = 0  // this is current
    private lateinit var salaryComponentBinding: FragmentSalaryComponentPreviewBinding
    private lateinit var hikeViewModel : MainViewModel
    private lateinit var rv: RecyclerView
    private lateinit var compAdapter: SalaryComponentAdapter
    private lateinit var customAlertDialogView : View
    private lateinit var arrComponent : ArrayList<Component>
    private lateinit var  progressCircle : ProgressBar
    private var taxesNew : Int = 0
    private var taxesOld : Int = 0
    private var salaryNew : Int = 0
    private var salaryOld : Int = 0
    private lateinit var errorMessage:String
    private lateinit var error_message_textview:TextView
    //salary variables
    var basicPay = 0
    var hra = 0
    var pf = 0
    var gratuity = 0
    var medicalInsurance = 0
    var professionalTax = 0
    var otherAllowances = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            companyName = it.getString(ARG_PARAM1)
            ctc = Integer.parseInt(it.getString(ARG_PARAM2)!!)
            currentCtc = Integer.parseInt(it.getString(ARG_PARAM3)!!)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        salaryComponentBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_salary_component_preview, container, false
        );
        hikeViewModel = ViewModelProvider(this,
            MainViewModelFactory(HikerApplication.getRepositoryInstance()!!)
        )[MainViewModel::class.java]
        return salaryComponentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        customAlertDialogView = LayoutInflater.from(context)
            .inflate(R.layout.companyintroalert, null, false)
        rv = salaryComponentBinding.recyclerView
        progressCircle = salaryComponentBinding.progressBar
        progressCircle.visibility = View.GONE
        val finalInHand = salaryComponentBinding.finalInHandButton
        val addComponentButton = salaryComponentBinding.addComponentButton
        basicPay = (ctc.times(30)).div(100)
        hra = (basicPay.times(50)).div(100)
        pf = (basicPay.times(12)).div(100)
        gratuity = ((basicPay.times(4.81)).div(100)).toInt()
        medicalInsurance = 7200
        professionalTax = 2400
        otherAllowances = (ctc - (basicPay + hra + (2*pf) + gratuity-medicalInsurance-professionalTax))
        val ctcString = ctc.toString()
        val hraString = hra.toString()
        val basicPayString = basicPay.toString()
        val otherAllowancesString = otherAllowances.toString()
        val pfString = pf.toString()
        val gratuityString = gratuity.toString()
        val medicalInsuranceStr = "7200"
        val professionalTaxStr = "2400"
        arrComponent = ArrayList<Component>();
        arrComponent.add(Component("Basic Pay", basicPayString, true,false))
        arrComponent.add(Component("HRA", hraString, true,false))
        arrComponent.add(Component("Other Allowances", otherAllowancesString, true,false))
        arrComponent.add(Component("PF", pfString, false,false))
        arrComponent.add(Component("Gratuity", gratuityString, false,false))
        arrComponent.add(Component("Medical Insurance",medicalInsuranceStr,false,false))
        arrComponent.add(Component("Professional Tax",professionalTaxStr,false,false))
        compAdapter = SalaryComponentAdapter(arrComponent)
        rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv.adapter = compAdapter
        addComponentButton.setOnClickListener {
            showingAlert()
        }
        finalInHand.setOnClickListener {
            calculateFinal(arrComponent)
        }
    }

    private fun taxCalculation(taxableIncome:Int,regime:REGIME):Int {

        var taxPercent = 0
        if (regime == REGIME.NEW_TAX_REGIME) {
            //tax as per new regime
            if(taxableIncome<500000) {
                taxPercent = 0
            }
            else if (taxableIncome >= 500000 && taxableIncome < 750000) {
                taxPercent = 10
            } else if (taxableIncome >= 750000 && taxableIncome < 1000000) {
                taxPercent = 15
            } else if (taxableIncome >= 1000000 && taxableIncome < 1250000) {
                taxPercent = 20
            } else if (taxableIncome >= 1250000 && taxableIncome < 1500000) {
                taxPercent = 25
            } else {
                taxPercent = 30
            }

        } else {
            //tax as per old regime
            if(taxableIncome<500000) {
                taxPercent = 0
            }
            else if (taxableIncome >= 500000 && taxableIncome < 750000) {
                taxPercent = 10
            } else if (taxableIncome >= 750000 && taxableIncome < 1000000) {
                taxPercent = 20
            } else {
                taxPercent = 30
            }
        }
        return taxPercent
    }

    private fun calculateFinal(arrComponent: ArrayList<Component>){
        //calculating final salary means inHand
        if(!isComponentsEqualToCtc(arrComponent,ctc)){
            return
        }
        val regimeNew = REGIME.NEW_TAX_REGIME
        val regimeOld = REGIME.OLD_TAX_REGIME
        var i = 0
        //calculation
        val gross_salary = ctc - pf - gratuity
        var taxableIncome = ctc - gratuity
        val taxPercentagesNew = taxCalculation(taxableIncome,regimeNew)
        val taxPercentagesOld = taxCalculation(taxableIncome,regimeOld)
        taxesNew = ((taxableIncome-500000) * taxPercentagesNew)/100
        taxesOld = ((taxableIncome-500000) * taxPercentagesOld)/100
        salaryNew = (gross_salary - taxesNew - pf - professionalTax)/12
        salaryOld = (gross_salary - taxesOld - pf - professionalTax)/12
        val builder = AlertDialog.Builder(context)
        val inflater = layoutInflater
        val finalInhandLayout = inflater.inflate(R.layout.final_in_hand_alert, null)
        val viewTaxNew = finalInhandLayout.findViewById<MaterialTextView>(R.id.taxnew)
        val viewTaxOld = finalInhandLayout.findViewById<MaterialTextView>(R.id.taxold)
        val viewSalaryNew = finalInhandLayout.findViewById<MaterialTextView>(R.id.inhandnew)
        val viewSalaryOld = finalInhandLayout.findViewById<MaterialTextView>(R.id.inhandold)
        builder.setView(finalInhandLayout)
        viewTaxNew.text = currencyFormat(taxesNew.toString())
        viewTaxOld.text = currencyFormat(taxesOld.toString())
        viewSalaryNew.text = currencyFormat(salaryNew.toString())
        viewSalaryOld.text = currencyFormat(salaryOld.toString())
        with(builder){
            setPositiveButton("Save", DialogInterface.OnClickListener {
                    dialog, id -> saveButton()
            })
            setNegativeButton("Cancel", DialogInterface.OnClickListener(function = cancelClick))
            show()
        }
    }

    private fun isComponentsEqualToCtc(arrComponent: ArrayList<Component>, ctc: Int): Boolean {
        var isSame = true
        var value = 0
        for(component in arrComponent){
            value += component.valuer.toInt()
        }
        if(value != ctc){
            isSame = false
        }
        return isSame
    }

    private fun saveButton() {
        val hike = HikeEntity(0,companyName!!,arrComponent,currentCtc.toString(),ctc.toString(),salaryNew.toString(),salaryOld.toString(),taxesOld.toString(),taxesNew.toString())
        AsyncTaskForSave().execute(hike)
    }

    val cancelClick = { dialog: DialogInterface, which: Int ->
        dialog.dismiss()
    }

    fun currencyFormat(value:String):String{
        val format = NumberFormat.getCurrencyInstance(Locale("en","in"))
        val formatted = format.format(BigDecimal(value))
        return formatted.substring(0,formatted.length-3)
    }

    private fun showingAlert() {
        val builder = AlertDialog.Builder(context)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.component_add_alert, null)
        val name = dialogLayout.findViewById<EditText>(R.id.component_add_edit_text)
        var select_layout = dialogLayout.findViewById<AutoCompleteTextView>(R.id.filled_exposed_dropdown)
        val value = dialogLayout.findViewById<EditText>(R.id.component_value_edit_text)
        val addComponentTxtView = dialogLayout.findViewById<TextView>(R.id.add_component_button)
        val alertDialog = builder.create()
        error_message_textview = customAlertDialogView.findViewById<TextView>(R.id.error_message)
        //adding click to text views
        var arrAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            context!!,android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.component))
        select_layout.setAdapter(arrAdapter)
        val position = arrAdapter.getPosition("Part of salary like allowances...")
        select_layout.setSelection(position)
        builder.setView(dialogLayout).show()
        addComponentTxtView.setOnClickListener {
            val comName = name.text.toString()
            val comValue = value.text.toString()
            if(comName.isEmpty()){
                errorMessage = "Component Name cannot be null"
                showingMessage(errorMessage,"#ff0000")
                return@setOnClickListener
            }
            if(!regexComponentName(comName)){
                errorMessage = "Component Name cannot be null"
                showingMessage(errorMessage,"#ff0000")
                return@setOnClickListener
            }
            if(comValue.isEmpty()){
                errorMessage =  "Component value is empty please add some value"
                showingMessage(errorMessage,"#ff0000")
                return@setOnClickListener
            }
            if (hikeViewModel.addComponentRedundentList.contains(comName.toLowerCase())){
                errorMessage = "Component is already added..."
                showingMessage(errorMessage,"#ff0000")
                return@setOnClickListener
            }
            compAdapter.adapterUpdate(Component(comName,comValue,true,false))
            hikeViewModel.addComponentRedundentList.add(comName.toLowerCase())
            alertDialog.dismiss()
        }
      }

    private fun regexComponentName(value: String): Boolean {
        val regex = Regex("[a-zA-Z\\s]+$")
        val matched = regex.containsMatchIn(input = value)
        if(value.length<20 && matched){
            return true
        }
        errorMessage = "Error: contains only a-z, A-Z"
        return false
    }

    private fun showingMessage(errorMessage:String,color:String){
        error_message_textview.setTextColor(Color.parseColor(color))
        hikeViewModel.isComponentAdd = false
        error_message_textview.text = errorMessage
        error_message_textview.visibility = View.VISIBLE
    }



    inner class AsyncTaskForSave: AsyncTask<HikeEntity,Void,Long>() {

        override fun onPreExecute() {
            super.onPreExecute()
            progressCircle.visibility = View.VISIBLE
            CoroutineScope(Main).launch {
                delay(8000)
                return@launch
            }
        }

        override fun doInBackground(vararg params: HikeEntity?): Long {
           return hikeViewModel.savingData(params[0]!!)
        }

        override fun onPostExecute(result: Long?) {
            super.onPostExecute(result)
            if(result!!>0){
                progressCircle.visibility = View.GONE
                findNavController().navigate(R.id.action_salaryComponentPreviewFragment_to_companyListFragment,null,null)
            }else{

            }

        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SalaryComponentPreviewFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SalaryComponentPreviewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}