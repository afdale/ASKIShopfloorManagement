package com.aski.industrialrevolution.askishopfloormanagement

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import android.content.Intent
import android.graphics.Color
import android.media.Image
import android.net.Uri
import android.os.AsyncTask
import android.widget.*
import androidx.fragment.app.ListFragment
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import kotlinx.android.synthetic.main.fragment_reject_komponen.*
import kotlin.Exception
import android.widget.ArrayAdapter
import android.widget.Spinner
import kotlinx.android.synthetic.main.fragment_reject_komponen.view.*
import android.widget.CompoundButton
import com.aski.industrialrevolution.askishopfloormanagement.R.id.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class RejectKomponenFragment : Fragment(),View.OnClickListener {
    override fun onClick(p0: View?) {
        try {
            if (m.text.toString()!="N/A"&&qtyET.text.toString().isNotEmpty()){
                goUploadReject()
            }
            else{
                Toast.makeText(activity, "Silahkan lengkapi data yang masih kosong.", Toast.LENGTH_LONG).show()
            }
        }catch (ex:Exception){
            Toast.makeText(activity, "$ex", Toast.LENGTH_LONG).show()
        }
    }

    var myDataFromActivity:String? = null
    var myMcData:String? = null

    lateinit var connectionClass: ConnectionClass

    lateinit var spinnerpart:Spinner
    lateinit var spinnerpartproc:Spinner
    lateinit var spinnerreject:Spinner
    lateinit var spinnerkodepart:Spinner
    lateinit var m:TextView
    lateinit var komptv:TextView
    lateinit var proctv:TextView
    lateinit var rejectBTN:ImageButton
    lateinit var dialog: Dialog
    lateinit var qtyET:EditText
    lateinit var root:View
    lateinit var pilihanbtn:Switch

    lateinit var komponenngprocesscontainer :Array<String>

    var z = ""
    //var strtext:String? = null
    var partnow:String? = null
    var rejectnow:String? = null
    var kodenow:String? = null

    var stationnowcontainer:String? = null
    var subtype:String? = null
    var rejecttipe:Boolean = false

    var testtimestamp:Long? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        try {
            connectionClass = ConnectionClass()

            root = inflater.inflate(R.layout.fragment_reject_komponen, container, false)
            pilihanbtn = root.findViewById<Switch>(R.id.pilihanbtn) as Switch
            spinnerpart = root.findViewById<Spinner>(R.id.spinnerpart) as Spinner
            spinnerpartproc = root.findViewById<Spinner>(R.id.spinnerpartproc) as Spinner
            spinnerreject = root.findViewById<Spinner>(R.id.spinnerreject) as Spinner
            spinnerkodepart = root.findViewById<Spinner>(R.id.spinnerkodepart) as Spinner
            m = root.findViewById<TextView>(R.id.mcTVRej) as TextView
            komptv = root.findViewById<TextView>(R.id.komptv) as TextView
            proctv = root.findViewById<TextView>(R.id.proctv) as TextView
            dialog = Dialog(activity!!, android.R.style.Theme_Translucent_NoTitleBar)
            val view = inflater.inflate(R.layout.progress, null)
            dialog.setContentView(view)
            dialog.setCancelable(false)

            rejectBTN = root.findViewById(R.id.rejectBTN) as ImageButton
            qtyET = root.findViewById(R.id.qtyET) as EditText

            rejectBTN.setOnClickListener(this)
            // Get the string array
            val activity = activity as MainActivity
            val myDataFromActivity = activity!!.getMyData()
            myMcData = activity!!.getMyMesin()
            //var strtext = arguments?.getString("part")

            m.setText(myMcData)

            spinnerpartproc.setVisibility(View.GONE)

            komptv.setTextColor(Color.GREEN)
            proctv.setTextColor(Color.BLACK)

            //Pilihan Reject
            ArrayAdapter.createFromResource(activity!!,R.array.rejectkomponen,R.layout.spinner_part).also { arrayAdapter ->
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerreject.adapter = arrayAdapter
            }

            //Pilihan Kodepart
            ArrayAdapter.createFromResource(activity!!,R.array.kodepart,R.layout.spinner_part).also { arrayAdapter ->
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerkodepart.adapter = arrayAdapter
            }

            if (myMcData!!.contains("ASSYMIRROR",false)&&myMcData!!.contains("STATION 1&2")){
                ArrayAdapter.createFromResource(activity!!,R.array.komponen1n2,R.layout.spinner_part).also { arrayAdapter ->
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerpart.adapter = arrayAdapter
                }
            }else if (myMcData!!.contains("ASSYMIRROR",false)&&myMcData!!.contains("STATION 3&4")){
                ArrayAdapter.createFromResource(activity!!,R.array.komponen3n4,R.layout.spinner_part).also { arrayAdapter ->
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerpart.adapter = arrayAdapter
                }
            }else if (myMcData!!.contains("ASSYMIRROR",false)&&myMcData!!.contains("STATION 5")){
                ArrayAdapter.createFromResource(activity!!,R.array.komponen5,R.layout.spinner_part).also { arrayAdapter ->
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerpart.adapter = arrayAdapter
                }
            }else if (myMcData!!.contains("ASSYMIRROR",false)&&myMcData!!.contains("STATION 6")){
                ArrayAdapter.createFromResource(activity!!,R.array.komponen6,R.layout.spinner_part).also { arrayAdapter ->
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerpart.adapter = arrayAdapter
                }
            }else if (myMcData!!.contains("ASSYMIRROR",false)&&myMcData!!.contains("STATION 7")){
                ArrayAdapter.createFromResource(activity!!,R.array.komponen7,R.layout.spinner_part).also { arrayAdapter ->
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerpart.adapter = arrayAdapter
                }
            }else if (myMcData!!.contains("ASSYMIRROR",false)&&myMcData!!.contains("STATION 8")){
                ArrayAdapter.createFromResource(activity!!,R.array.komponenall,R.layout.spinner_part).also { arrayAdapter ->
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerpart.adapter = arrayAdapter
                }
            }else if (myMcData!!.contains("ASSYMIRROR",false)&&myMcData!!.contains("STATION QCL")){
                ArrayAdapter.createFromResource(activity!!,R.array.komponenall,R.layout.spinner_part).also { arrayAdapter ->
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerpart.adapter = arrayAdapter
                }
            }else{
                ArrayAdapter.createFromResource(activity!!,R.array.komponenall,R.layout.spinner_part).also { arrayAdapter ->
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerpart.adapter = arrayAdapter
                }
            }

            pilihanbtn.setOnCheckedChangeListener { compoundButton, b ->
                if (b==true){
                    rejecttipe=b
                    spinnerpartproc.setVisibility(View.VISIBLE)
                    spinnerpart.setVisibility(View.GONE)
                    komptv.setTextColor(Color.BLACK)
                    proctv.setTextColor(Color.GREEN)

                    //var komponenngprocess :Array<String> = resources.getStringArray(R.array.komponen1)
                    //komponenngprocesscontainer = komponenngprocess
                    //Toast.makeText(activity, "${komponenngprocesscontainer.size}", Toast.LENGTH_LONG).show()

                    ArrayAdapter.createFromResource(activity!!,R.array.rejectproses,R.layout.spinner_part).also { arrayAdapter ->
                        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spinnerreject.adapter = arrayAdapter
                    }
                    rejectnow=="N/A"
                }
                else if (b==false){
                    rejecttipe=b
                    spinnerpartproc.setVisibility(View.GONE)
                    spinnerpart.setVisibility(View.VISIBLE)
                    komptv.setTextColor(Color.GREEN)
                    proctv.setTextColor(Color.BLACK)

                    ArrayAdapter.createFromResource(activity!!,R.array.rejectkomponen,R.layout.spinner_part).also { arrayAdapter ->
                        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spinnerreject.adapter = arrayAdapter
                    }
                    rejectnow=="N/A"
                }
            }

            spinnerpart.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    partnow = p0?.getItemAtPosition(p2).toString()
                    //Toast.makeText(activity, "$partnow", Toast.LENGTH_LONG).show()
                }
            }

            spinnerreject.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    rejectnow = p0?.getItemAtPosition(p2).toString()
                    Toast.makeText(activity, "$rejectnow", Toast.LENGTH_LONG).show()
                }
            }

            spinnerkodepart.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    kodenow = p0?.getItemAtPosition(p2).toString()
                    //Toast.makeText(activity, "$kodenow", Toast.LENGTH_LONG).show()

                    try {
                        if (kodenow!!.contains	("C00",false))	{ subtype=	"LOW STSL" }
                        else if (kodenow!!.contains	("B60",false))	{	subtype=	"LOW STSL"	}
                        else if (kodenow!!.contains	("C10",false))	{	subtype=	"MID STSL"	}
                        else if (kodenow!!.contains	("B70",false))	{	subtype=	"MID STSL"	}
                        else if (kodenow!!.contains	("C20",false))	{	subtype=	"HIGH"	}
                        else if (kodenow!!.contains	("B80",false))	{	subtype=	"HIGH"	}
                        else if (kodenow!!.contains	("C30",false))	{	subtype=	"LOW STSL"	}
                        else if (kodenow!!.contains	("B90",false))	{	subtype=	"LOW STSL"	}
                        else if (kodenow!!.contains	("C50",false))	{	subtype=	"LOW STSL"	}
                        else if (kodenow!!.contains	("A  ",false))	{	subtype=	"LOW STSL"	}
                        else if (kodenow!!.contains	("C80",false))	{	subtype=	"HIGH"	}
                        else if (kodenow!!.contains	("C40",false))	{	subtype=	"HIGH"	}
                        else if (kodenow!!.contains	("D30",false))	{	subtype=	"HIGH"	}
                        else if (kodenow!!.contains	("C90",false))	{	subtype=	"HIGH"	}
                        else if (kodenow!!.contains	("B20",false))	{	subtype=	"LOW NON STSL"	}
                        else if (kodenow!!.contains	("B30",false))	{	subtype=	"MID NON STSL"	}
                        else if (kodenow!!.contains	("E00",false))	{	subtype=	"LOW NON STSL"	}
                        else if (kodenow!!.contains	("E10",false))	{	subtype=	"LOW NON STSL"	}
                        else if (kodenow!!.contains	("E30",false))	{	subtype=	"MID NON STSL"	}
                        else if (kodenow!!.contains	("E60",false))	{	subtype=	"MID STSL"	}
                        else if (kodenow!!.contains	("E70",false))	{	subtype=	"HIGH"	}
                        else if (kodenow!!.contains	("E80",false))	{	subtype=	"LOW STSL"	}
                        else if (kodenow!!.contains	("E90",false))	{	subtype=	"LOW STSL"	}
                        else if (kodenow!!.contains	("F00",false))	{	subtype=	"MID STSL"	}
                        else if (kodenow!!.contains	("B  ",false))	{	subtype=	"MID NON STSL"	}
                        else if (kodenow!!.contains	("C  ",false))	{	subtype=	"LOW STSL"	}
                        else if (kodenow!!.contains	("D  ",false))	{	subtype=	"LOW STSL"	}
                        else if (kodenow!!.contains	("D50",false))	{	subtype=	"LOW NON STSL"	}
                        else if (kodenow!!.contains	("D60",false))	{	subtype=	"MID NON STSL"	}
                        else if (kodenow!!.contains	("D80",false))	{	subtype=	"LOW NON STS"	}
                        else if (kodenow!!.contains	("D90",false))	{	subtype=	"LOW NON STSL"	}
                        else if (kodenow!!.contains	("E  ",false))	{	subtype=	"MID STSL"	}
                        else if (kodenow!!.contains	("E40",false))	{	subtype=	"MID STSL"	}
                        else if (kodenow!!.contains	("E50",false))	{	subtype=	"HIGH"	}


                    }catch (ex:Exception){
                        Toast.makeText(activity, "$ex", Toast.LENGTH_LONG).show()
                    }
                }
            }

        }catch (ex:Exception){
            Toast.makeText(activity, "$ex", Toast.LENGTH_LONG).show()
        }
        return root
    }

    override fun onResume() {
        super.onResume()
        try {
            val activity = activity as MainActivity
            myDataFromActivity = activity!!.getMyData()
            myMcData = activity!!.getMyMesin()
            m.setText(myMcData)

            if (myMcData!!.contains("ASSYMIRROR",false)&&myMcData!!.contains("STATION 1&2")){
                ArrayAdapter.createFromResource(activity!!,R.array.komponen1n2,R.layout.spinner_part).also { arrayAdapter ->
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerpart.adapter = arrayAdapter
                }
            }else if (myMcData!!.contains("ASSYMIRROR",false)&&myMcData!!.contains("STATION 3&4")){
                ArrayAdapter.createFromResource(activity!!,R.array.komponen3n4,R.layout.spinner_part).also { arrayAdapter ->
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerpart.adapter = arrayAdapter
                }
            }else if (myMcData!!.contains("ASSYMIRROR",false)&&myMcData!!.contains("STATION 5")){
                ArrayAdapter.createFromResource(activity!!,R.array.komponen5,R.layout.spinner_part).also { arrayAdapter ->
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerpart.adapter = arrayAdapter
                }
            }else if (myMcData!!.contains("ASSYMIRROR",false)&&myMcData!!.contains("STATION 6")){
                ArrayAdapter.createFromResource(activity!!,R.array.komponen6,R.layout.spinner_part).also { arrayAdapter ->
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerpart.adapter = arrayAdapter
                }
            }else if (myMcData!!.contains("ASSYMIRROR",false)&&myMcData!!.contains("STATION 7")){
                ArrayAdapter.createFromResource(activity!!,R.array.komponen7,R.layout.spinner_part).also { arrayAdapter ->
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerpart.adapter = arrayAdapter
                }
            }else if (myMcData!!.contains("ASSYMIRROR",false)&&myMcData!!.contains("STATION 8")){
                ArrayAdapter.createFromResource(activity!!,R.array.komponenall,R.layout.spinner_part).also { arrayAdapter ->
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerpart.adapter = arrayAdapter
                }
            }else if (myMcData!!.contains("ASSYMIRROR",false)&&myMcData!!.contains("STATION QCL")){
                ArrayAdapter.createFromResource(activity!!,R.array.komponenall,R.layout.spinner_part).also { arrayAdapter ->
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerpart.adapter = arrayAdapter
                }
            }else{
                ArrayAdapter.createFromResource(activity!!,R.array.komponenall,R.layout.spinner_part).also { arrayAdapter ->
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerpart.adapter = arrayAdapter
                }
            }
        }catch (ex:Exception){
            Toast.makeText(activity, "$ex", Toast.LENGTH_LONG).show()
        }
    }


    fun getQTY():String {
        return qtyET.text.toString()
    }



    fun goUploadReject(){

        try {
            //Log.i("qty :","${fragobj!!.getQTY()}")
            val qtycontainer = qtyET.text.toString()
            dialog.show()
            if(kodenow=="N/A"||rejectnow=="N/A"||myMcData=="N/A"||qtycontainer==""){
                Toast.makeText(activity, "Silahkan isi kolom yang masih kosong.", Toast.LENGTH_LONG).show()
            }
            else if (myMcData!!.contains("ASSYMIRROR",false)&&myMcData!!.contains("STATION 1&2")&&rejecttipe==true){
                if(subtype=="HIGH"){
                    var komponenngprocess :Array<String> = resources.getStringArray(R.array.highproses2)
                    komponenngprocesscontainer = komponenngprocess
                    for (i in 0..komponenngprocesscontainer.size-1){
                        Firebaseuploaddata(i)
                    }
                    Douploadreport(1).execute()
                }
                else if(subtype=="MID STSL"){
                    var komponenngprocess :Array<String> = resources.getStringArray(R.array.midstslproses2)
                    komponenngprocesscontainer = komponenngprocess
                    for (i in 0..komponenngprocesscontainer.size-1){
                        Firebaseuploaddata(i)
                    }
                    Douploadreport(1).execute()
                }
                else if(subtype=="MID NON STSL"){
                    var komponenngprocess :Array<String> = resources.getStringArray(R.array.midnonstslproses2)
                    komponenngprocesscontainer = komponenngprocess
                    for (i in 0..komponenngprocesscontainer.size-1){
                        Firebaseuploaddata(i)
                    }
                    Douploadreport(1).execute()
                }
                else if(subtype=="LOW STSL"){
                    var komponenngprocess :Array<String> = resources.getStringArray(R.array.lowstslproses2)
                    komponenngprocesscontainer = komponenngprocess
                    for (i in 0..komponenngprocesscontainer.size-1){
                        Firebaseuploaddata(i)
                    }
                    Douploadreport(1).execute()
                }
                else if(subtype=="LOW NON STSL"){
                    var komponenngprocess :Array<String> = resources.getStringArray(R.array.lownonstslproses2)
                    komponenngprocesscontainer = komponenngprocess
                    for (i in 0..komponenngprocesscontainer.size-1){
                        Firebaseuploaddata(i)
                    }
                    Douploadreport(1).execute()
                }
                else if (subtype==null){
                    dialog.dismiss()
                    Toast.makeText(activity!!, "Silahkan isi Kode Part", Toast.LENGTH_LONG).show()
                }
            }else if (myMcData!!.contains("ASSYMIRROR",false)&&myMcData!!.contains("STATION 3&4")&&rejecttipe==true){
                if(subtype=="HIGH"){
                    var komponenngprocess :Array<String> = resources.getStringArray(R.array.highproses4)
                    komponenngprocesscontainer = komponenngprocess
                    for (i in 0..komponenngprocesscontainer.size-1){
                        Firebaseuploaddata(i)
                    }
                    Douploadreport(1).execute()
                }
                else if(subtype=="MID STSL"){
                    var komponenngprocess :Array<String> = resources.getStringArray(R.array.midstslproses4)
                    komponenngprocesscontainer = komponenngprocess
                    for (i in 0..komponenngprocesscontainer.size-1){
                        Firebaseuploaddata(i)
                    }
                    Douploadreport(1).execute()
                }
                else if(subtype=="MID NON STSL"){
                    var komponenngprocess :Array<String> = resources.getStringArray(R.array.midnonstslproses4)
                    komponenngprocesscontainer = komponenngprocess
                    for (i in 0..komponenngprocesscontainer.size-1){
                        Firebaseuploaddata(i)
                    }
                    Douploadreport(1).execute()
                }
                else if(subtype=="LOW STSL"){
                    var komponenngprocess :Array<String> = resources.getStringArray(R.array.lowstslproses4)
                    komponenngprocesscontainer = komponenngprocess
                    for (i in 0..komponenngprocesscontainer.size-1){
                        Firebaseuploaddata(i)
                    }
                    Douploadreport(1).execute()
                }
                else if(subtype=="LOW NON STSL"){
                    var komponenngprocess :Array<String> = resources.getStringArray(R.array.lownonstslproses4)
                    komponenngprocesscontainer = komponenngprocess
                    for (i in 0..komponenngprocesscontainer.size-1){
                        Firebaseuploaddata(i)
                    }
                    Douploadreport(1).execute()
                }
                else if (subtype==null){
                    dialog.dismiss()
                    Toast.makeText(activity!!, "Silahkan isi Kode Part", Toast.LENGTH_LONG).show()
                }
            }else if (myMcData!!.contains("ASSYMIRROR",false)&&myMcData!!.contains("STATION 5")&&rejecttipe==true){
                if(subtype=="HIGH"){
                    var komponenngprocess :Array<String> = resources.getStringArray(R.array.highproses5)
                    komponenngprocesscontainer = komponenngprocess
                    for (i in 0..komponenngprocesscontainer.size-1){
                        Firebaseuploaddata(i)
                    }
                    Douploadreport(1).execute()
                }
                else if(subtype=="MID STSL"){
                    var komponenngprocess :Array<String> = resources.getStringArray(R.array.midstslproses5)
                    komponenngprocesscontainer = komponenngprocess
                    for (i in 0..komponenngprocesscontainer.size-1){
                        Firebaseuploaddata(i)
                    }
                    Douploadreport(1).execute()
                }
                else if(subtype=="MID NON STSL"){
                    var komponenngprocess :Array<String> = resources.getStringArray(R.array.midnonstslproses5)
                    komponenngprocesscontainer = komponenngprocess
                    for (i in 0..komponenngprocesscontainer.size-1){
                        Firebaseuploaddata(i)
                    }
                    Douploadreport(1).execute()
                }
                else if(subtype=="LOW STSL"){
                    var komponenngprocess :Array<String> = resources.getStringArray(R.array.lowstslproses5)
                    komponenngprocesscontainer = komponenngprocess
                    for (i in 0..komponenngprocesscontainer.size-1){
                        Firebaseuploaddata(i)
                    }
                    Douploadreport(1).execute()
                }
                else if(subtype=="LOW NON STSL"){
                    var komponenngprocess :Array<String> = resources.getStringArray(R.array.lownonstslproses5)
                    komponenngprocesscontainer = komponenngprocess
                    for (i in 0..komponenngprocesscontainer.size-1){
                        Firebaseuploaddata(i)
                    }
                    Douploadreport(1).execute()
                }
                else if (subtype==null){
                    dialog.dismiss()
                    Toast.makeText(activity!!, "Silahkan isi Kode Part", Toast.LENGTH_LONG).show()
                }
            }else if (myMcData!!.contains("ASSYMIRROR",false)&&myMcData!!.contains("STATION 6")&&rejecttipe==true){
                if(subtype=="HIGH"){
                    var komponenngprocess :Array<String> = resources.getStringArray(R.array.highproses6)
                    komponenngprocesscontainer = komponenngprocess
                    for (i in 0..komponenngprocesscontainer.size-1){
                        Firebaseuploaddata(i)
                    }
                    Douploadreport(1).execute()
                }
                else if(subtype=="MID STSL"){
                    var komponenngprocess :Array<String> = resources.getStringArray(R.array.midstslproses6)
                    komponenngprocesscontainer = komponenngprocess
                    for (i in 0..komponenngprocesscontainer.size-1){
                        Firebaseuploaddata(i)
                    }
                    Douploadreport(1).execute()
                }
                else if(subtype=="MID NON STSL"){
                    var komponenngprocess :Array<String> = resources.getStringArray(R.array.midnonstslproses6)
                    komponenngprocesscontainer = komponenngprocess
                    for (i in 0..komponenngprocesscontainer.size-1){
                        Firebaseuploaddata(i)
                    }
                    Douploadreport(1).execute()
                }
                else if(subtype=="LOW STSL"){
                    var komponenngprocess :Array<String> = resources.getStringArray(R.array.lowstslproses6)
                    komponenngprocesscontainer = komponenngprocess
                    for (i in 0..komponenngprocesscontainer.size-1){
                        Firebaseuploaddata(i)
                    }
                    Douploadreport(1).execute()
                }
                else if(subtype=="LOW NON STSL"){
                    var komponenngprocess :Array<String> = resources.getStringArray(R.array.lownonstslproses6)
                    komponenngprocesscontainer = komponenngprocess
                    for (i in 0..komponenngprocesscontainer.size-1){
                        Firebaseuploaddata(i)
                    }
                    Douploadreport(1).execute()
                }
                else if (subtype==null){
                    dialog.dismiss()
                    Toast.makeText(activity!!, "Silahkan isi Kode Part", Toast.LENGTH_LONG).show()
                }
            }else if (myMcData!!.contains("ASSYMIRROR",false)&&myMcData!!.contains("STATION 7")&&rejecttipe==true){
                if(subtype=="HIGH"){
                    var komponenngprocess :Array<String> = resources.getStringArray(R.array.highproses7)
                    komponenngprocesscontainer = komponenngprocess
                    for (i in 0..komponenngprocesscontainer.size-1){
                        Firebaseuploaddata(i)
                    }
                    Douploadreport(1).execute()
                }
                else if(subtype=="MID STSL"){
                    var komponenngprocess :Array<String> = resources.getStringArray(R.array.midstslproses7)
                    komponenngprocesscontainer = komponenngprocess
                    for (i in 0..komponenngprocesscontainer.size-1){
                        Firebaseuploaddata(i)
                    }
                    Douploadreport(1).execute()
                }
                else if(subtype=="MID NON STSL"){
                    var komponenngprocess :Array<String> = resources.getStringArray(R.array.midnonstslproses7)
                    komponenngprocesscontainer = komponenngprocess
                    for (i in 0..komponenngprocesscontainer.size-1){
                        Firebaseuploaddata(i)
                    }
                    Douploadreport(1).execute()
                }
                else if(subtype=="LOW STSL"){
                    var komponenngprocess :Array<String> = resources.getStringArray(R.array.lowstslproses7)
                    komponenngprocesscontainer = komponenngprocess
                    for (i in 0..komponenngprocesscontainer.size-1){
                        Firebaseuploaddata(i)
                    }
                    Douploadreport(1).execute()
                }
                else if(subtype=="LOW NON STSL"){
                    var komponenngprocess :Array<String> = resources.getStringArray(R.array.lownonstslproses7)
                    komponenngprocesscontainer = komponenngprocess
                    for (i in 0..komponenngprocesscontainer.size-1){
                        Firebaseuploaddata(i)
                    }
                    Douploadreport(1).execute()
                }
                else if (subtype==null){
                    dialog.dismiss()
                    Toast.makeText(activity!!, "Silahkan isi Kode Part", Toast.LENGTH_LONG).show()
                }
            }else if (myMcData!!.contains("ASSYMIRROR",false)&&myMcData!!.contains("STATION 8")&&rejecttipe==true){
                if(subtype=="HIGH"){
                    var komponenngprocess :Array<String> = resources.getStringArray(R.array.highproses7)
                    komponenngprocesscontainer = komponenngprocess
                    for (i in 0..komponenngprocesscontainer.size-1){
                        Firebaseuploaddata(i)
                    }
                    Douploadreport(1).execute()
                }
                else if(subtype=="MID STSL"){
                    var komponenngprocess :Array<String> = resources.getStringArray(R.array.midstslproses7)
                    komponenngprocesscontainer = komponenngprocess
                    for (i in 0..komponenngprocesscontainer.size-1){
                        Firebaseuploaddata(i)
                    }
                    Douploadreport(1).execute()
                }
                else if(subtype=="MID NON STSL"){
                    var komponenngprocess :Array<String> = resources.getStringArray(R.array.midnonstslproses7)
                    komponenngprocesscontainer = komponenngprocess
                    for (i in 0..komponenngprocesscontainer.size-1){
                        Firebaseuploaddata(i)
                    }
                    Douploadreport(1).execute()
                }
                else if(subtype=="LOW STSL"){
                    var komponenngprocess :Array<String> = resources.getStringArray(R.array.lowstslproses7)
                    komponenngprocesscontainer = komponenngprocess
                    for (i in 0..komponenngprocesscontainer.size-1){
                        Firebaseuploaddata(i)
                    }
                    Douploadreport(1).execute()
                }
                else if(subtype=="LOW NON STSL"){
                    var komponenngprocess :Array<String> = resources.getStringArray(R.array.lownonstslproses7)
                    komponenngprocesscontainer = komponenngprocess
                    for (i in 0..komponenngprocesscontainer.size-1){
                        Firebaseuploaddata(i)
                    }
                    Douploadreport(1).execute()
                }
                else if (subtype==null){
                    dialog.dismiss()
                    Toast.makeText(activity!!, "Silahkan isi Kode Part", Toast.LENGTH_LONG).show()
                }
            }else if (myMcData!!.contains("ASSYMIRROR",false)&&myMcData!!.contains("STATION QCL")&&rejecttipe==true){
                if(subtype=="HIGH"){
                    var komponenngprocess :Array<String> = resources.getStringArray(R.array.highproses7)
                    komponenngprocesscontainer = komponenngprocess
                    for (i in 0..komponenngprocesscontainer.size-1){
                        Firebaseuploaddata(i)
                    }
                    Douploadreport(1).execute()
                }
                else if(subtype=="MID STSL"){
                    var komponenngprocess :Array<String> = resources.getStringArray(R.array.midstslproses7)
                    komponenngprocesscontainer = komponenngprocess
                    for (i in 0..komponenngprocesscontainer.size-1){
                        Firebaseuploaddata(i)
                    }
                    Douploadreport(1).execute()
                }
                else if(subtype=="MID NON STSL"){
                    var komponenngprocess :Array<String> = resources.getStringArray(R.array.midnonstslproses7)
                    komponenngprocesscontainer = komponenngprocess
                    for (i in 0..komponenngprocesscontainer.size-1){
                        Firebaseuploaddata(i)
                    }
                    Douploadreport(1).execute()
                }
                else if(subtype=="LOW STSL"){
                    var komponenngprocess :Array<String> = resources.getStringArray(R.array.lowstslproses7)
                    komponenngprocesscontainer = komponenngprocess
                    for (i in 0..komponenngprocesscontainer.size-1){
                        Firebaseuploaddata(i)
                    }
                    Douploadreport(1).execute()
                }
                else if(subtype=="LOW NON STSL"){
                    var komponenngprocess :Array<String> = resources.getStringArray(R.array.lownonstslproses7)
                    komponenngprocesscontainer = komponenngprocess
                    for (i in 0..komponenngprocesscontainer.size-1){
                        Firebaseuploaddata(i)
                    }
                    Douploadreport(1).execute()
                }
                else if (subtype==null){
                    dialog.dismiss()
                    Toast.makeText(activity!!, "Silahkan isi Kode Part", Toast.LENGTH_LONG).show()
                }
            }else if (myMcData!!.contains("ASSYMIRROR",false)&&rejecttipe==false){
                val keykey = FirebaseDatabase.getInstance().getReference().child("reject").child("open").push().key
                FirebaseDatabase.getInstance().getReference().child("reject").child("open").child(keykey!!).child("key").setValue(keykey)
                FirebaseDatabase.getInstance().getReference().child("reject").child("open").child(keykey!!).child("start").setValue(ServerValue.TIMESTAMP)
                FirebaseDatabase.getInstance().getReference().child("reject").child("open").child(keykey!!).child("mc").setValue(myMcData)
                FirebaseDatabase.getInstance().getReference().child("reject").child("open").child(keykey!!).child("part").setValue(partnow)
                FirebaseDatabase.getInstance().getReference().child("reject").child("open").child(keykey!!).child("jenis_NG").setValue(rejectnow)
                FirebaseDatabase.getInstance().getReference().child("reject").child("open").child(keykey!!).child("kode").setValue(kodenow)
                FirebaseDatabase.getInstance().getReference().child("reject").child("open").child(keykey!!).child("qty").setValue(qtyET.text.toString().toInt())
                    .addOnSuccessListener {
                        z = "Rejection Telah di Input,Part NG akan ditukar oleh PPIC."
                    }.addOnFailureListener {
                        z = "Error $it"
                    }.addOnCompleteListener {
                        dialog.dismiss()
                        Toast.makeText(activity, "$z", Toast.LENGTH_LONG).show()
                    }

                Douploadreport(78322).execute()
            }

        } catch (ex: Exception) {
            Toast.makeText(activity, "$ex", Toast.LENGTH_LONG).show()
            dialog.dismiss()
        }
        Toast.makeText(activity, "$z", Toast.LENGTH_LONG).show()
    }

    private fun Firebaseuploaddata (hitungangka: Int){
        try {
            val keykey = FirebaseDatabase.getInstance().getReference().child("reject").child("open").push().key
            FirebaseDatabase.getInstance().getReference().child("reject").child("open").child(keykey!!).child("key").setValue(keykey)
            FirebaseDatabase.getInstance().getReference().child("reject").child("open").child(keykey!!).child("start").setValue(ServerValue.TIMESTAMP)
            FirebaseDatabase.getInstance().getReference().child("reject").child("open").child(keykey!!).child("mc").setValue(myMcData)
            FirebaseDatabase.getInstance().getReference().child("reject").child("open").child(keykey!!).child("part").setValue(komponenngprocesscontainer[hitungangka])
            FirebaseDatabase.getInstance().getReference().child("reject").child("open").child(keykey!!).child("jenis_NG").setValue(rejectnow)
            FirebaseDatabase.getInstance().getReference().child("reject").child("open").child(keykey!!).child("kode").setValue(kodenow)
            FirebaseDatabase.getInstance().getReference().child("reject").child("open").child(keykey!!).child("qty").setValue(qtyET.text.toString().toInt())
                .addOnSuccessListener {
                    z = "Rejection Telah di Input,Part NG akan ditukar oleh PPIC."
                }.addOnFailureListener {
                    z = "Error $it"
                }.addOnCompleteListener {
                    dialog.dismiss()
                    Toast.makeText(activity, "$z", Toast.LENGTH_LONG).show()
                }
        }catch (ex:Exception){
            Toast.makeText(activity!!, "$ex", Toast.LENGTH_LONG).show()
        }

    }

    inner class Douploadreport(var angkaangka:Int) : AsyncTask<String, String, String>() {

        var variablecheckin:String? = null
        private var z1 = ""
        internal var isSuccess = false

        override fun onPreExecute() {
            dialog.show()
            //setDialog(true)
            //progressDialog.setMessage("Loading...")
            //progressDialog.show()
        }

        override fun doInBackground(vararg params: String): String {
            try {
                val con = connectionClass.CONN()
                if (con == null) {
                    z1 = "Please check your internet connection"
                } else {
                    testtimestamp = System.currentTimeMillis() + 25200000
                    //Log.i("timestampini","${testtimestamp}")
                    val linesaatini = myMcData!!.substringAfter("LINE ").substringBefore(" STATION")
                    val station = myMcData!!.substringAfterLast("STATION ")
                    //val query = "INSERT INTO checkin(kunci,mesin,mp_id,tanggal,shift,$variablecheckin) VALUES ($key,'$mc','$id',$datetimedata,$shift,$testtimestamp)"
                    if (angkaangka==78322){
                        val query = "INSERT INTO rejection(waktu,kodepart,mc,line,station,part,rejection,qty) VALUES ($testtimestamp,'$kodenow','$myMcData','$linesaatini','$station','$partnow','$rejectnow',${qtyET.text.toString()})"
                        val stmt = con.createStatement()
                        stmt.executeUpdate(query)
                    }
                    else {
                        for (i in 0..komponenngprocesscontainer.size-1){
                            val query = "INSERT INTO rejection(waktu,kodepart,mc,line,station,part,rejection,qty) VALUES ($testtimestamp,'$kodenow','$myMcData','$linesaatini','$station','${komponenngprocesscontainer[i]}','$rejectnow',${qtyET.text.toString()})"
                            val stmt = con.createStatement()
                            stmt.executeUpdate(query)
                        }
                    }
                    z1 = "Upload successfull"
                    isSuccess = true
                }
            } catch (ex: Exception) {
                z1 = ex.toString()
            }
            return z1
        }

        override fun onPostExecute(s: String) {
            //setDialog(false)
            dialog.dismiss()
            Toast.makeText(activity!!, "" + z1, Toast.LENGTH_LONG).show()

            if(isSuccess)
            {
                //FirebaseDatabase.getInstance().getReference().child("andon").child("waiting").child("Moldshop").child(kunci).removeValue()
                Toast.makeText(activity!!, "" + z1, Toast.LENGTH_LONG).show()
            }
            //progressDialog.hide()
        }
    }

}