package com.aski.industrialrevolution.askishopfloormanagement

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var fragobj: RejectKomponenFragment? = null
    lateinit var asal:String
    var myString:String? = "N/A"
    var myMC:String? = "N/A"
    var myMCnow:String? = "N/A"
    var myMC2:String? = "N/A"


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId){

            R.id.navigation_startstop -> {
                replaceFragment(StartStopFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_rejectkomponen -> {
                replacetext()
                replaceFragment(RejectKomponenFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_andon -> {
                replaceFragment(AndonFragment())
                return@OnNavigationItemSelectedListener true
            }

        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        replaceFragment(StartStopFragment())
        fragobj = RejectKomponenFragment()


    }

    override fun onResume() {
        super.onResume()

        val mSharedPreference = PreferenceManager.getDefaultSharedPreferences(baseContext)
        myMC2 = mSharedPreference.getString("MC", "N/A")

    }

    fun getMyData(): String? {
        return myString
    }

    fun getMyMesin(): String? {
        return myMC
    }

    fun getMyMesinall(): String? {
        return myMCnow
    }

    private fun replaceFragment(fragment:Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container,fragment)
        fragmentTransaction.commit()
    }

    private fun replacetext(){
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        //fragment.update("test")
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container,RejectKomponenFragment())
        fragmentTransaction.commit()
        /*try {
            var testtext = findViewById<TextView?>(R.id.partnumberTV)
            testtext!!.text = "testing"
        }catch (ex: Exception){
            Toast.makeText(this, "$ex", Toast.LENGTH_LONG).show()
        }*/
    }

    /*fun onscan1(view: View){
        asal = "scan1"
        try {
            DoUpload()
        }catch (err:Exception){
            Toast.makeText(this, "$err", Toast.LENGTH_LONG).show()
        }
    }*/

    fun onscan2(view: View){
        asal = "scan2"
        DoUpload()
    }

    fun onscan3(view: View){
        asal = "scan3"
        DoUpload()
    }

    fun onscan4(view: View){
        asal = "scan4"
        DoUpload()
    }

    fun onscan5(view: View){
        asal = "scan5"
        DoUpload()
    }

    fun onscan6(view: View){
        asal = "scan6"
        DoUpload()
    }

    fun onscanMCREJ(view: View){
        asal = "scanmcrej"
        DoResult(2)
    }

    fun onscanPART(view: View){
        asal = "scanpart"
        DoResult(1)
    }

    fun onscanmcstart(view: View){
        asal = "scanmcrej"
        DoResult(2)
    }
    fun goAndon(view:View){
        asal = "scanandon"
        DoUpload()
    }

    fun goAndon2(view:View){
        asal = "scanandon2"
        DoUpload()
    }

    fun onscanMCSetting(view: View){
        asal = "scanmcrej"
        DoResult(2)
    }



    /*fun goUploadReject(){
        var z = ""

        try {
            //Log.i("qty :","${fragobj!!.getQTY()}")
            //fragobj!!.progresson()
            val keykey = FirebaseDatabase.getInstance().getReference().child("reject").child("open").push().key
            FirebaseDatabase.getInstance().getReference().child("reject").child("open").child(keykey!!).child("key").setValue(keykey)
            FirebaseDatabase.getInstance().getReference().child("reject").child("open").child(keykey!!).child("start").setValue(ServerValue.TIMESTAMP)
            FirebaseDatabase.getInstance().getReference().child("reject").child("open").child(keykey!!).child("mc").setValue(myMC)
            FirebaseDatabase.getInstance().getReference().child("reject").child("open").child(keykey!!).child("part").setValue(myString)
            FirebaseDatabase.getInstance().getReference().child("reject").child("open").child(keykey!!).child("jenis_NG").setValue(fragobj!!.getNG())
            FirebaseDatabase.getInstance().getReference().child("reject").child("open").child(keykey!!).child("qty").setValue(fragobj!!.getQTY())
                .addOnSuccessListener {
                    z = "Rejection Telah di Input,Part NG dapat ditukar setelah ada judgement oleh Quality Team."
                }.addOnFailureListener {
                    z = "Error $it"
                }.addOnCompleteListener {
                    //fragobj!!.progressoff()
                    Toast.makeText(this, "$z", Toast.LENGTH_LONG).show()
                    finish()
                }
        } catch (ex: java.lang.Exception) {
            Toast.makeText(this, "$ex", Toast.LENGTH_LONG).show()
            //fragobj!!.progressoff()
        }
        Toast.makeText(this, "$z", Toast.LENGTH_LONG).show()
    }*/

    fun DoUpload (){
        val launch4 = Intent(this, ScanningBarcodeActivity::class.java)
        launch4.putExtra("asal",asal)
        launch4.putExtra("mc2",myMC)
        try {
            startActivity(launch4)
        }catch (ex:Exception){
            Toast.makeText(this, "$ex", Toast.LENGTH_LONG).show()
        }
    }

    fun DoResult (code:Int){
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
        if (requestCode == 1){
            if (resultCode == Activity.RESULT_OK) {
                myString = data!!.getStringExtra("part")
            }
        }
        if (requestCode == 2){
            if (resultCode == Activity.RESULT_OK) {
                myMC = data!!.getStringExtra("mesin")
            }
        }
        if (requestCode == 3){
            if (resultCode == Activity.RESULT_OK) {
                myMCnow = data!!.getStringExtra("mesin")
            }
        }
    }
}

