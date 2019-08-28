package com.aski.industrialrevolution.askishopfloormanagement

import android.app.Dialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView
import java.text.SimpleDateFormat
import java.util.*


class ScanningBarcodeActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private var mScannerView: ZXingScannerView? = null
    lateinit var connectionClass: ConnectionClass

    //private lateinit var dept:String
    //private lateinit var mc:String
    //private lateinit var problem:String

    var testtimestamp:Long? = null
    var creationDate: Int? = null
    private lateinit var asal:String

    var mc:String? = null
    var mc2:String? = null
    var id:String? = null
    var dept:String? = null
    var problem:String? = null
    var timestampini:Long? = null
    var partandon:String? = null
    var linesaatini:String? = null
    var stationsaatini:String? = null

    var shiftsaatini:Int = 0
    var key:String = ""
    var key2:String = ""
    var shift:Int = 0
    var shiftdata:Int = 0
    var jam:Int = 0
    var menit:Int = 0

    lateinit var datetimedata:String
    lateinit var timeshiftdata:String

    lateinit var tulisanTVNEW: TextView
    lateinit var msgTV:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_scanning_barcode)
        mScannerView = findViewById<ZXingScannerView>(R.id.scanner)

        val date = Date()

        asal = getIntent().getStringExtra("asal")

        mc = getIntent().getStringExtra("mc")

        mc2 = getIntent().getStringExtra("mc2")

        problem = getIntent().getStringExtra("problem")
        dept = getIntent().getStringExtra("dept")
        partandon = getIntent().getStringExtra("partandon")
        shiftsaatini = getIntent().getIntExtra("shift",0)

        val formatter = SimpleDateFormat("yyyyMMdd")
        val formatter2 = SimpleDateFormat("HH")
        val formatter3 = SimpleDateFormat("mm")

        timeshiftdata = formatter2.format(date)
        datetimedata = formatter.format(date)

        val timeshift:String = "$timeshiftdata"
        shiftdata = timeshift.toInt()
        //setting shift
        if (shiftdata>=6&&shiftdata<=16){
            shift=1
        }

        else if (shiftdata>=16&&shiftdata<=6){
            shift=2
        }
        try {
            linesaatini = mc2!!.substringAfter("LINE ").substringBefore(" STATION")
            stationsaatini = mc2!!.substringAfterLast("STATION ")
        val dateshift :String = "$datetimedata$linesaatini$stationsaatini$shiftdata$shiftsaatini"
        key = dateshift.toString()
        val dateshift2 :String = "$datetimedata$linesaatini$stationsaatini$shiftsaatini"
        key2 = dateshift2.toString()
        }catch (ex:Exception){
            Toast.makeText(this, "$ex $linesaatini $stationsaatini $mc2", Toast.LENGTH_LONG).show()
        }

        Log.i("tanggal","$datetimedata")
        Log.i("jam","$timeshiftdata")
        Log.i("datedata","$date")

        connectionClass = ConnectionClass()

        tulisanTVNEW = findViewById<TextView>(R.id.tulisanTVNEW)
        msgTV = findViewById<TextView>(R.id.msgTV)

        //mc = getIntent().getStringExtra("mc")
        //problem = getIntent().getStringExtra("problem")
        //dept = getIntent().getStringExtra("dept")

        if ((asal=="scan1")||(asal=="scan2")||(asal=="scan3")||(asal=="scan4")||(asal=="scan5")||(asal=="scan6")||
            (asal=="scanandon")||(asal=="scanmcrej")||(asal=="scanmcsetting")){
            tulisanTVNEW.text = "Scan QR CODE pada MC untuk menyelesaikan"
        }
        else if ((asal.contains("MC",false))||(asal=="andon")){
            tulisanTVNEW.text = "Scan QR CODE pada TAGID anda untuk menyelesaikan"
        }
        else if ((asal=="scanpart")||(asal=="scanpartandon")){
            tulisanTVNEW.text = "Scan QR CODE pada BOX KOMPONEN untuk menyelesaikan"
        }

        //Toast.makeText(this, "$key2", Toast.LENGTH_LONG).show()
        //Toast.makeText(this, "$shiftsaatini $asal", Toast.LENGTH_LONG).show()
    }

    public override fun onResume() {
        super.onResume()
        mScannerView!!.setResultHandler(this)
        mScannerView!!.startCamera()
    }

    public override fun onPause() {
        super.onPause()
        mScannerView!!.stopCamera()
    }

    override fun onStop() {
        finish()
        super.onStop()
    }

    override fun handleResult(rawResult: Result) {
        Log.v("TAG", rawResult.getText()) // Prints scan results
        Log.v("TAG", rawResult.getBarcodeFormat().toString())

        /*val builder = AlertDialog.Builder(this)
        builder.setTitle("Scan Result")
        builder.setMessage(rawResult.getText())
        val alert1 = builder.create()
        alert1.show()*/


        //Tulis task disini
        var dialog = Dialog(this, android.R.style.Theme_Translucent_NoTitleBar)
        var z = ""
        if ((asal =="scan1")&&rawResult.getText()==mc2)
        {
            val launch4 = Intent(this, ScanningBarcodeActivity::class.java)
            launch4.putExtra("asal","MCON1")
            launch4.putExtra("mc",rawResult.getText())
            launch4.putExtra("mc2","$mc2")
            launch4.putExtra("shift", shiftsaatini)
            //Toast.makeText(this, "$key2", Toast.LENGTH_LONG).show()
            startActivity(launch4)
        }
        else if ((asal =="scan3")&&rawResult.getText()==mc2)
        {
            val launch4 = Intent(this, ScanningBarcodeActivity::class.java)
            launch4.putExtra("asal","MCON3")
            launch4.putExtra("mc",rawResult.getText())
            launch4.putExtra("mc2","$mc2")
            launch4.putExtra("shift",shiftsaatini)
            //Toast.makeText(this, "$key2", Toast.LENGTH_LONG).show()
            startActivity(launch4)
        }
        else if ((asal =="scan5")&&rawResult.getText()==mc2)
        {
            val launch4 = Intent(this, ScanningBarcodeActivity::class.java)
            launch4.putExtra("asal","MCON5")
            launch4.putExtra("mc",rawResult.getText())
            launch4.putExtra("mc2","$mc2")
            launch4.putExtra("shift",shiftsaatini)
            //Toast.makeText(this, "$key2", Toast.LENGTH_LONG).show()
            startActivity(launch4)
        }
        else if ((asal =="scan2")&&rawResult.getText()==mc2)
        {
            val launch3 = Intent(this, ScanningBarcodeActivity::class.java)
            launch3.putExtra("asal","MCOFF2")
            launch3.putExtra("mc",rawResult.getText())
            launch3.putExtra("mc2","$mc2")
            launch3.putExtra("shift",shiftsaatini)
            //Toast.makeText(this, "$key2", Toast.LENGTH_LONG).show()
            startActivity(launch3)
        }
        else if ((asal =="scan4")&&rawResult.getText()==mc2)
        {
            val launch4 = Intent(this, ScanningBarcodeActivity::class.java)
            launch4.putExtra("asal","MCOFF4")
            launch4.putExtra("mc",rawResult.getText())
            launch4.putExtra("mc2","$mc2")
            launch4.putExtra("shift",shiftsaatini)
            //Toast.makeText(this, "$key2", Toast.LENGTH_LONG).show()
            startActivity(launch4)
        }
        else if ((asal =="scan6")&&rawResult.getText()==mc2)
        {
            val launch4 = Intent(this, ScanningBarcodeActivity::class.java)
            launch4.putExtra("asal","MCOFF6")
            launch4.putExtra("mc",rawResult.getText())
            launch4.putExtra("mc2","$mc2")
            launch4.putExtra("shift",shiftsaatini)
            //Toast.makeText(this, "$key2", Toast.LENGTH_LONG).show()
            startActivity(launch4)
        }
        else if ((asal =="andon")&&rawResult.getText().contains("ID",false)){
            val dataidcard = rawResult.getText().substringAfter("FN:").substringBefore("\n")

            val view = this.layoutInflater.inflate(R.layout.progress, null)
            dialog.setContentView(view)
            dialog.setCancelable(false)
            dialog.show()

            try {
                val keykey = FirebaseDatabase.getInstance().getReference().child("andon").child(dept!!).push().key
                //FirebaseDatabase.getInstance().getReference().child("andonswitch").setValue(0)
                //FirebaseDatabase.getInstance().getReference().child("buzzer").setValue(0)
                FirebaseDatabase.getInstance().getReference().child("andon").child(dept!!).child(keykey!!).child("key").setValue(keykey)
                FirebaseDatabase.getInstance().getReference().child("andon").child(dept!!).child(keykey!!).child("part").setValue(partandon)
                FirebaseDatabase.getInstance().getReference().child("andon").child(dept!!).child(keykey!!).child("start").setValue(ServerValue.TIMESTAMP)
                FirebaseDatabase.getInstance().getReference().child("andon").child(dept!!).child(keykey!!).child("mc").setValue(mc)
                FirebaseDatabase.getInstance().getReference().child("andon").child(dept!!).child(keykey!!).child("problem").setValue(problem)
                FirebaseDatabase.getInstance().getReference().child("andon").child(dept!!).child(keykey!!).child("issuedby").setValue(dataidcard)
                    .addOnSuccessListener {
                        z = "Silahkan menunggu, $dept akan datang ke MC."
                    }.addOnFailureListener {
                        z = "Error $it"
                    }.addOnCompleteListener {
                        dialog.dismiss()
                        Toast.makeText(this, "$z", Toast.LENGTH_LONG).show()
                        finish()
                    }
            } catch (ex: java.lang.Exception) {
                Toast.makeText(this, "$ex", Toast.LENGTH_LONG).show()
                dialog.dismiss()
            }
            Toast.makeText(this, "$z", Toast.LENGTH_LONG).show()
        }
        else if ((asal =="scanandon")&&rawResult.getText().contains("MC",false)){
            val launch4 = Intent(this, AndonActivity::class.java)
            launch4.putExtra("asal","andonafterscan")
            launch4.putExtra("mc",rawResult.getText())
            startActivity(launch4)
        }
        else if ((asal =="scanpart")&&rawResult.getText().contains("PART",false)){
            val launch4 = Intent()
            launch4.putExtra("part",rawResult.getText())
            setResult(RESULT_OK,launch4)
            finish()

            // Create object of SharedPreferences.
            /*val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
            //now get Editor
            val editor = sharedPref.edit()
            //put your value
            editor.putString("part",rawResult.getText())
            //commits your edits
            editor.commit()
            finish()*/

        }
        else if ((asal =="scanpartandon")&&rawResult.getText().contains("PART",false)){
            val launch4 = Intent()
            launch4.putExtra("part",rawResult.getText())
            setResult(RESULT_OK,launch4)
            finish()

            // Create object of SharedPreferences.
            /*val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
            //now get Editor
            val editor = sharedPref.edit()
            //put your value
            editor.putString("part",rawResult.getText())
            //commits your edits
            editor.commit()
            finish()*/
        }
        else if ((asal =="scanmcrej")&&rawResult.getText().contains("MC",false)){
            val launch4 = Intent()
            launch4.putExtra("mesin",rawResult.getText())
            setResult(RESULT_OK,launch4)
            finish()
        }else if ((asal =="scanmcrej")&&rawResult.getText().contains("MC",false)){
            val launch4 = Intent()
            launch4.putExtra("mesin",rawResult.getText())
            setResult(RESULT_OK,launch4)
            finish()
        }
        else if ((asal.contains("MCON",false))&&rawResult.getText().contains("ID",false)){
            val dataidcard = rawResult.getText().substringAfter("FN:").substringBefore("\n")
            id = dataidcard
            //set start stop mesin
            try {
                Toast.makeText(this, "$key2", Toast.LENGTH_LONG).show()
                linesaatini = mc!!.substringAfter("LINE ").substringBefore(" STATION")
                stationsaatini = mc!!.substringAfterLast("STATION ")

                if (stationsaatini=="1&2"&&id!!.contains("4054")||id!!.contains("8077")||id!!.contains("8032")||id!!.contains("8018")||id!!.contains("8105")||id!!.contains("8038")||id!!.contains("8013")){
                    FirebaseDatabase.getInstance().getReference().child("$mc").setValue(1)
                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("$asal").child("jam").setValue(ServerValue.TIMESTAMP)
                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("$asal").child("id").setValue(id)
                    Douploadreport(this,asal).execute()
                }else if (stationsaatini=="3&4"&&id!!.contains("4054")||id!!.contains("8081")||id!!.contains("8008")||id!!.contains("8015")||id!!.contains("8101")||id!!.contains("8014")||id!!.contains("8016")){
                    FirebaseDatabase.getInstance().getReference().child("$mc").setValue(1)
                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("$asal").child("jam").setValue(ServerValue.TIMESTAMP)
                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("$asal").child("id").setValue(id)
                    Douploadreport(this,asal).execute()
                }else if (stationsaatini=="5"&&id!!.contains("4054")||id!!.contains("8079")||id!!.contains("8033")||id!!.contains("8009")||id!!.contains("8109")||id!!.contains("8023")||id!!.contains("8017")){
                    FirebaseDatabase.getInstance().getReference().child("$mc").setValue(1)
                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("$asal").child("jam").setValue(ServerValue.TIMESTAMP)
                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("$asal").child("id").setValue(id)
                    Douploadreport(this,asal).execute()
                }else if (stationsaatini=="6"&&id!!.contains("4054")||id!!.contains("8075")||id!!.contains("8030")||id!!.contains("8034")||id!!.contains("8102")||id!!.contains("8025")||id!!.contains("8037")){
                    FirebaseDatabase.getInstance().getReference().child("$mc").setValue(1)
                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("$asal").child("jam").setValue(ServerValue.TIMESTAMP)
                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("$asal").child("id").setValue(id)
                    Douploadreport(this,asal).execute()
                }else if (stationsaatini=="7"&&id!!.contains("4054")||id!!.contains("8080")||id!!.contains("8022")||id!!.contains("8039")||id!!.contains("8104")||id!!.contains("8036")||id!!.contains("8026")){
                    FirebaseDatabase.getInstance().getReference().child("$mc").setValue(1)
                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("$asal").child("jam").setValue(ServerValue.TIMESTAMP)
                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("$asal").child("id").setValue(id)
                    Douploadreport(this,asal).execute()
                }else if (stationsaatini=="8"&&id!!.contains("4054")||id!!.contains("8076")||id!!.contains("8004")||id!!.contains("8019")||id!!.contains("8106")||id!!.contains("8012")||id!!.contains("8027")){
                    FirebaseDatabase.getInstance().getReference().child("$mc").setValue(1)
                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("$asal").child("jam").setValue(ServerValue.TIMESTAMP)
                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("$asal").child("id").setValue(id)
                    Douploadreport(this,asal).execute()
                }else if (stationsaatini=="QCL"&&id!!.contains("4054")||id!!.contains("8073")||id!!.contains("8001")||id!!.contains("8007")||id!!.contains("8103")||id!!.contains("8028")||id!!.contains("8021")){
                    FirebaseDatabase.getInstance().getReference().child("$mc").setValue(1)
                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("$asal").child("jam").setValue(ServerValue.TIMESTAMP)
                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("$asal").child("id").setValue(id)
                    Douploadreport(this,asal).execute()
                }else{
                    msgTV.text = "ID Anda Tidak Terdaftar pada Station Ini."
                    mScannerView!!.resumeCameraPreview(this)
                }
            }catch (ex:Exception){
                Toast.makeText(this, "$ex", Toast.LENGTH_LONG).show()
            }
        }
        else if ((asal.contains("MCOFF",false))&&rawResult.getText().contains("ID",false)){
            val dataidcard = rawResult.getText().substringAfter("FN:").substringBefore("\n")
            id = dataidcard
            try {
                //Toast.makeText(this, "$key2", Toast.LENGTH_LONG).show()
                linesaatini = mc!!.substringAfter("LINE ").substringBefore(" STATION")
                stationsaatini = mc!!.substringAfterLast("STATION ")

                if (stationsaatini=="1&2"&&id!!.contains("4054")||id!!.contains("8077")||id!!.contains("8032")||id!!.contains("8018")||id!!.contains("8105")||id!!.contains("8038")||id!!.contains("8013")){
                    FirebaseDatabase.getInstance().getReference().child("$mc").setValue(0)
                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("$asal").child("jam").setValue(ServerValue.TIMESTAMP)
                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("$asal").child("id").setValue(id)
                    Douploadreport(this,asal).execute()
                }else if (stationsaatini=="3&4"&&id!!.contains("4054")||id!!.contains("8081")||id!!.contains("8008")||id!!.contains("8015")||id!!.contains("8101")||id!!.contains("8014")||id!!.contains("8016")){
                    FirebaseDatabase.getInstance().getReference().child("$mc").setValue(0)
                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("$asal").child("jam").setValue(ServerValue.TIMESTAMP)
                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("$asal").child("id").setValue(id)
                    Douploadreport(this,asal).execute()
                }else if (stationsaatini=="5"&&id!!.contains("4054")||id!!.contains("8079")||id!!.contains("8033")||id!!.contains("8009")||id!!.contains("8109")||id!!.contains("8023")||id!!.contains("8017")){
                    FirebaseDatabase.getInstance().getReference().child("$mc").setValue(0)
                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("$asal").child("jam").setValue(ServerValue.TIMESTAMP)
                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("$asal").child("id").setValue(id)
                    Douploadreport(this,asal).execute()
                }else if (stationsaatini=="6"&&id!!.contains("4054")||id!!.contains("8075")||id!!.contains("8030")||id!!.contains("8034")||id!!.contains("8102")||id!!.contains("8025")||id!!.contains("8037")){
                    FirebaseDatabase.getInstance().getReference().child("$mc").setValue(0)
                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("$asal").child("jam").setValue(ServerValue.TIMESTAMP)
                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("$asal").child("id").setValue(id)
                    Douploadreport(this,asal).execute()
                }else if (stationsaatini=="7"&&id!!.contains("4054")||id!!.contains("8080")||id!!.contains("8022")||id!!.contains("8039")||id!!.contains("8104")||id!!.contains("8036")||id!!.contains("8026")){
                    FirebaseDatabase.getInstance().getReference().child("$mc").setValue(0)
                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("$asal").child("jam").setValue(ServerValue.TIMESTAMP)
                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("$asal").child("id").setValue(id)
                    Douploadreport(this,asal).execute()
                }else if (stationsaatini=="8"&&id!!.contains("4054")||id!!.contains("8076")||id!!.contains("8004")||id!!.contains("8019")||id!!.contains("8106")||id!!.contains("8012")||id!!.contains("8027")){
                    FirebaseDatabase.getInstance().getReference().child("$mc").setValue(0)
                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("$asal").child("jam").setValue(ServerValue.TIMESTAMP)
                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("$asal").child("id").setValue(id)
                    Douploadreport(this,asal).execute()
                }else if (stationsaatini=="QCL"&&id!!.contains("4054")||id!!.contains("8073")||id!!.contains("8001")||id!!.contains("8007")||id!!.contains("8103")||id!!.contains("8028")||id!!.contains("8021")){
                    FirebaseDatabase.getInstance().getReference().child("$mc").setValue(0)
                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("$asal").child("jam").setValue(ServerValue.TIMESTAMP)
                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("$asal").child("id").setValue(id)
                    Douploadreport(this,asal).execute()
                }else{
                    msgTV.text = "ID Anda Tidak Terdaftar pada Station Ini."
                    mScannerView!!.resumeCameraPreview(this)
                }
            }catch (ex:Exception){
                Toast.makeText(this, "$ex", Toast.LENGTH_LONG).show()
            }
        }
        else if (asal =="scanpart"){
            msgTV.text = "QR CODE yang anda scan salah, Silahkan Scan QR CODE Komponen."
            mScannerView!!.resumeCameraPreview(this)
        }
        else if ((asal.contains("MC",false))||(asal.contains("andon",false))){
            msgTV.text = "QR CODE yang anda scan salah, Silahkan Scan QR CODE pada ID CARD anda."
            mScannerView!!.resumeCameraPreview(this)
        }
        else{
            msgTV.text = "QR CODE yang anda scan salah, Silahkan Scan QR CODE pada Mesin."
            mScannerView!!.resumeCameraPreview(this)
        }

    }

    inner class Douploadreport(var activity: ScanningBarcodeActivity, var checkintime:String) : AsyncTask<String, String, String>() {

        var dialog = Dialog(activity,android.R.style.Theme_Translucent_NoTitleBar)

        var variablecheckin:String? = null
        var variabletable:String? = null
        private var z = ""
        internal var isSuccess = false

        override fun onPreExecute() {
            val view = activity.layoutInflater.inflate(R.layout.progress,null)
            dialog.setContentView(view)
            dialog.setCancelable(false)
            dialog.show()
            //setDialog(true)
            //progressDialog.setMessage("Loading...")
            //progressDialog.show()
        }

        override fun doInBackground(vararg params: String): String {
            try {
                val con = connectionClass.CONN()
                if (checkintime=="MCON1"){
                    variablecheckin="in_shift"
                    variabletable="1in"
                }
                if (checkintime=="MCOFF2"){
                    variablecheckin="out_1st_break"
                    variabletable="2out"
                }
                if (checkintime=="MCON3"){
                    variablecheckin="in_1st_break"
                    variabletable="3in"
                }
                if (checkintime=="MCOFF4"){
                    variablecheckin="out_2nd_break"
                    variabletable="4out"
                }
                if (checkintime=="MCON5"){
                    variablecheckin="in_2nd_break"
                    variabletable="5in"
                }
                if (checkintime=="MCOFF6"){
                    variablecheckin="out_shift"
                    variabletable="6out"
                }
                if (con == null) {
                    z = "Please check your internet connection"
                } else {
                    //val query = "INSERT INTO checksheetpainting(kunci,date,shift,timestamp,mp_id,line,cat_expired,rawpart_visual,loading_hangerdansubjig,waping_solvent,waping_lap,settingroom1_temp,airblow1_airpressure,matic_undercoat_balancingangin,matic_undercoat_settinggun_atomize,matic_undercoat_settinggun_pattern,matic_undercoat_settinggun_paint,settingroom2_temp,matic_topcoat_balancingangin,matic_topcoat_settinggun_atomize,matic_topcoat_settinggun_pattern,matic_topcoat_settinggun_paint,settingroom3_temp,bakingoven_temp) VALUES ($key,$datetimedata,$shift,$timestamp,'$mpid','$line','$maticcat','$maticrawpart','$matichanger','$maticwapingsolvent','$maticwapinglap',$maticsettingtemp1,$maticairpress1,'$bamatic1','$maticsettingatomize1','$maticsettingpattern1','$maticsettingpaint1',$maticsettingtemp2,'$bamatic2','$maticsettingatomize2','$maticsettingpattern2','$maticsettingpaint2',$maticsettingtemp3,$maticoventemp)"

                    testtimestamp = System.currentTimeMillis() + 25200000
                    //Log.i("timestampini","${testtimestamp}")
                    linesaatini = mc!!.substringAfter("LINE ").substringBefore(" STATION")
                    stationsaatini = mc!!.substringAfterLast("STATION ")
                    val query = "INSERT INTO $variabletable(kunci,mesin,line,station,mp_id,tanggal,shift,$variablecheckin) VALUES ('$key','$mc','$linesaatini','$stationsaatini','$id',$datetimedata,$shift,$testtimestamp)"
                    val stmt = con.createStatement()
                    stmt.executeUpdate(query)

                    z = "Upload successfull"
                    isSuccess = true
                }
            } catch (ex: Exception) {

                /*if(ex.toString().contains("duplicate",true)){
                    try {
                        val con = connectionClass.CONN()
                        if (checkintime=="MCON1"){
                            variablecheckin="in_shift"
                        }
                        if (checkintime=="MCOFF2"){
                            variablecheckin="out_1st_break"
                        }
                        if (checkintime=="MCON3"){
                            variablecheckin="in_1st_break"
                        }
                        if (checkintime=="MCOFF4"){
                            variablecheckin="out_2nd_break"
                        }
                        if (checkintime=="MCON5"){
                            variablecheckin="in_2nd_break"
                        }
                        if (checkintime=="MCOFF6"){
                            variablecheckin="out_shift"
                        }
                        if (con == null) {
                            z = "Please check your internet connection"
                        } else {
                            //val query = "INSERT INTO checksheetpainting(kunci,date,shift,timestamp,mp_id,line,cat_expired,rawpart_visual,loading_hangerdansubjig,waping_solvent,waping_lap,settingroom1_temp,airblow1_airpressure,matic_undercoat_balancingangin,matic_undercoat_settinggun_atomize,matic_undercoat_settinggun_pattern,matic_undercoat_settinggun_paint,settingroom2_temp,matic_topcoat_balancingangin,matic_topcoat_settinggun_atomize,matic_topcoat_settinggun_pattern,matic_topcoat_settinggun_paint,settingroom3_temp,bakingoven_temp) VALUES ($key,$datetimedata,$shift,$timestamp,'$mpid','$line','$maticcat','$maticrawpart','$matichanger','$maticwapingsolvent','$maticwapinglap',$maticsettingtemp1,$maticairpress1,'$bamatic1','$maticsettingatomize1','$maticsettingpattern1','$maticsettingpaint1',$maticsettingtemp2,'$bamatic2','$maticsettingatomize2','$maticsettingpattern2','$maticsettingpaint2',$maticsettingtemp3,$maticoventemp)"

                            testtimestamp = System.currentTimeMillis() + 25200000
                            //Log.i("timestampini","${testtimestamp}")
                            val query = "UPDATE checkin SET $variablecheckin=$testtimestamp WHERE kunci='$key'"
                            val stmt = con.createStatement()
                            stmt.executeUpdate(query)

                            z = "Upload successfull"
                            isSuccess = true
                        }
                    } catch (ez:Exception){
                        z = "ERRROR : $ez"
                    }
                }else {*/
                    isSuccess = false
                    z = "ERROR : $ex"
                //}
            }
            return z
        }

        override fun onPostExecute(s: String) {
            //setDialog(false)

            dialog.dismiss()
            Toast.makeText(this@ScanningBarcodeActivity, "" + z, Toast.LENGTH_LONG).show()
            mScannerView!!.resumeCameraPreview(this@ScanningBarcodeActivity)

            if(isSuccess)
            {
                //FirebaseDatabase.getInstance().getReference().child("andon").child("waiting").child("Moldshop").child(kunci).removeValue()

                this@ScanningBarcodeActivity.finish()
            }
            //progressDialog.hide()
        }
    }


}