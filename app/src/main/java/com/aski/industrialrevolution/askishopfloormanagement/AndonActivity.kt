package com.aski.industrialrevolution.askishopfloormanagement

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class AndonActivity : AppCompatActivity() {
    lateinit var mcTVAndon:TextView
    lateinit var komponenandonET:EditText
    lateinit var dept:String
    lateinit var mc:String
    var problemsaatini:String? = null

    lateinit var spinnerproblem:Spinner
    var myKomponen:String? = null

    lateinit var asal:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_andon)

        mcTVAndon = findViewById<TextView>(R.id.mcTVAndon)
        komponenandonET = findViewById<EditText>(R.id.komponenandonET)
        spinnerproblem = findViewById<Spinner>(R.id.spinnerproblem)

        mc = getIntent().getStringExtra("mc")

        mcTVAndon.text = mc
        komponenandonET.setText(myKomponen)

        ArrayAdapter.createFromResource(this,R.array.linestop,R.layout.spinner_itemini).also { arrayAdapter ->
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerproblem.adapter = arrayAdapter
        }

        spinnerproblem.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                problemsaatini = p0?.getItemAtPosition(p2).toString()
                //Toast.makeText(this@AndonActivity, "$problemsaatini", Toast.LENGTH_LONG).show()
            }

        }
    }

    override fun onResume() {
        super.onResume()
        komponenandonET.setText(myKomponen)
    }

    override fun onStop(){
        super.onStop()
        //finish()
    }

    fun onscanpartandon(view: View){
        asal = "scanpartandon"
        DoScanPart(4)
    }

    fun goPE(view: View){
        try {
            if (problemsaatini != "N/A") {
                dept = "PE"
                DoAndon()
            }
        }catch (ex: Exception){
            Toast.makeText(this, "$ex", Toast.LENGTH_LONG).show()
        }
    }

    fun goENG(view: View){
        if(problemsaatini!="N/A") {
            dept = "ENG"
            DoAndon()
        }
    }

    fun goQUALITY(view: View){
        if(problemsaatini!="N/A") {
            dept = "QA"
            DoAndon()
        }
    }

    fun goPPIC(view: View) {
        if(problemsaatini!="N/A") {
            dept = "PPIC"
            DoAndon()
        }
    }

    fun goMS(view: View) {
        if(problemsaatini!="N/A") {
            dept = "MS"
            DoAndon()
        }
    }


    private fun DoAndon (){

        val launch4 = Intent(this, ScanningBarcodeActivity::class.java)
        launch4.putExtra("asal","andon")
        launch4.putExtra("mc", mc)
        launch4.putExtra("problem", problemsaatini)
        launch4.putExtra("dept", dept)
        launch4.putExtra("partandon", komponenandonET!!.text.toString())
        try {
            startActivity(launch4)
        }catch (ex:Exception){
            Toast.makeText(this, "$ex", Toast.LENGTH_LONG).show()
        }
    }

    fun DoScanPart (code:Int){
        val launch4 = Intent(this, ScanningBarcodeActivity::class.java)
        launch4.putExtra("asal",asal)
        try {
            startActivityForResult(launch4,code)
        }catch (ex:Exception){
            Toast.makeText(this, "$ex", Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 4){
            if (resultCode == Activity.RESULT_OK) {
                myKomponen = data!!.getStringExtra("part")
            }
        }
    }


}