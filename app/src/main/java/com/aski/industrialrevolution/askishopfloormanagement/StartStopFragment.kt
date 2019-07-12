package com.aski.industrialrevolution.askishopfloormanagement


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.Image
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
//import com.google.android.gms.flags.impl.SharedPreferencesFactory.getSharedPreferences
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_start_stop.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.Exception
import android.R.id.button1
import android.R.id.button1
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.widget.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class StartStopFragment : Fragment(), View.OnClickListener, TextWatcher {

    override fun afterTextChanged(p0: Editable?) {

    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        textbefore = p0.toString()
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        textafter = p0.toString()
        if (textbefore!=textafter){
            s1.text = "N/A"
            s2.text = "N/A"
            s3.text = "N/A"
            s4.text = "N/A"
            s5.text = "N/A"
            s6.text = "N/A"

            s1a.text = "ID N/A"
            s2a.text = "ID N/A"
            s3a.text = "ID N/A"
            s4a.text = "ID N/A"
            s5a.text = "ID N/A"
            s6a.text = "ID N/A"
        }
    }

    override fun onClick(v: View) {

        when (v.id) {
            R.id.scan1 -> {
                if (myMcnow=="N/A"){
                    Toast.makeText(activity, "Silahkan Scan QR CODE pada Station Anda Terlebih Dahulu.", Toast.LENGTH_LONG).show()
                }
                else if (s1.text.toString()=="N/A"){
                    asal = "scan1"
                    DoUploadnow()
                }
                else{
                    Toast.makeText(activity, "Anda Telah Melakukan Check-in.", Toast.LENGTH_LONG).show()
                }
            }
            R.id.scan2 -> {
                if (myMcnow=="N/A"){
                    Toast.makeText(activity, "Silahkan Scan QR CODE pada Station Anda Terlebih Dahulu.", Toast.LENGTH_LONG).show()
                }
                else if (s1.text.toString()=="N/A"){
                    Toast.makeText(activity, "Silahkan Lakukan Check-in/Check-out Secara Berurutan.", Toast.LENGTH_LONG).show()
                }
                else if (s2.text.toString()=="N/A"&&s1.text.toString()!="N/A"){
                    asal = "scan2"
                    DoUploadnow()
                }else{
                    Toast.makeText(activity, "Anda Telah Melakukan Check-in.", Toast.LENGTH_LONG).show()
                }
            }
            R.id.scan3 -> {
                if (myMcnow=="N/A"){
                    Toast.makeText(activity, "Silahkan Scan QR CODE pada Station Anda Terlebih Dahulu.", Toast.LENGTH_LONG).show()
                }
                else if (s1.text.toString()=="N/A"||s2.text.toString()=="N/A"){
                    Toast.makeText(activity, "Silahkan Lakukan Check-in/Check-out Secara Berurutan.", Toast.LENGTH_LONG).show()
                }
                else if (s3.text.toString()=="N/A"&&s2.text.toString()!="N/A"&&s1.text.toString()!="N/A"){
                    asal = "scan3"
                    DoUploadnow()
                }else{
                    Toast.makeText(activity, "Anda Telah Melakukan Check-in.", Toast.LENGTH_LONG).show()
                }
            }
            R.id.scan4 -> {
                if (myMcnow=="N/A"){
                    Toast.makeText(activity, "Silahkan Scan QR CODE pada Station Anda Terlebih Dahulu.", Toast.LENGTH_LONG).show()
                }else if (s1.text.toString()=="N/A"||s2.text.toString()=="N/A"||s3.text.toString()=="N/A"){
                    Toast.makeText(activity, "Silahkan Lakukan Check-in/Check-out Secara Berurutan.", Toast.LENGTH_LONG).show()
                }
                else if (s4.text.toString()=="N/A"&&s3.text.toString()!="N/A"&&s2.text.toString()!="N/A"&&s1.text.toString()!="N/A"){
                    asal = "scan4"
                    DoUploadnow()
                }else{
                    Toast.makeText(activity, "Anda Telah Melakukan Check-in.", Toast.LENGTH_LONG).show()
                }
            }
            R.id.scan5 -> {
                if (myMcnow=="N/A"){
                    Toast.makeText(activity, "Silahkan Scan QR CODE pada Station Anda Terlebih Dahulu.", Toast.LENGTH_LONG).show()
                }else if (s1.text.toString()=="N/A"||s2.text.toString()=="N/A"||s3.text.toString()=="N/A"||s4.text.toString()=="N/A"){
                    Toast.makeText(activity, "Silahkan Lakukan Check-in/Check-out Secara Berurutan.", Toast.LENGTH_LONG).show()
                }
                else if (s5.text.toString()=="N/A"&&s4.text.toString()!="N/A"&&s3.text.toString()!="N/A"&&s2.text.toString()!="N/A"&&s1.text.toString()!="N/A"){
                    asal = "scan5"
                    DoUploadnow()
                }else{
                    Toast.makeText(activity, "Anda Telah Melakukan Check-in.", Toast.LENGTH_LONG).show()
                }
            }
            R.id.scan6 -> {
                if (myMcnow=="N/A"){
                    Toast.makeText(activity, "Silahkan Scan QR CODE pada Station Anda Terlebih Dahulu.", Toast.LENGTH_LONG).show()
                }else if (s1.text.toString()=="N/A"||s2.text.toString()=="N/A"||s3.text.toString()=="N/A"||s4.text.toString()=="N/A"||s5.text.toString()=="N/A"){
                    Toast.makeText(activity, "Silahkan Lakukan Check-in/Check-out Secara Berurutan.", Toast.LENGTH_LONG).show()
                }
                else if (s6.text.toString()=="N/A"&&s5.text.toString()!="N/A"&&s4.text.toString()!="N/A"&&s3.text.toString()!="N/A"&&s2.text.toString()!="N/A"&&s1.text.toString()!="N/A"){
                    asal = "scan6"
                    DoUploadnow()
                }else{
                    Toast.makeText(activity, "Anda Telah Melakukan Check-in.", Toast.LENGTH_LONG).show()
                }
            }
        }
        // CODE TO DO
    }

    lateinit var asal:String
    var shiftsaatini:Int=0

    var textbefore:String="N/A"
    var textafter:String="N/A"
    lateinit var v:View
    lateinit var s1: TextView
    lateinit var s2: TextView
    lateinit var s3: TextView
    lateinit var s4: TextView
    lateinit var s5: TextView
    lateinit var s6: TextView

    lateinit var s1a: TextView
    lateinit var s2a: TextView
    lateinit var s3a: TextView
    lateinit var s4a: TextView
    lateinit var s5a: TextView
    lateinit var s6a: TextView

    lateinit var shift2tv: TextView
    lateinit var shift3tv: TextView

    lateinit var mesinTVstart:TextView
    lateinit var linesaatini:String
    lateinit var stationsaatini:String
    lateinit var datetimedata:String
    lateinit var timeshiftdata:String

    lateinit var scan1btn:ImageButton
    lateinit var scan2btn:ImageButton
    lateinit var scan3btn:ImageButton
    lateinit var scan4btn:ImageButton
    lateinit var scan5btn:ImageButton
    lateinit var scan6btn:ImageButton

    var statussaatini:Boolean = false

    lateinit var pilihanbtn2: Switch

    lateinit var scan1:ImageButton

    var key2:String = ""

    var prefs: SharedPreferences? = null

    var myMcnow:String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_start_stop, container, false)

        scan1btn = v.findViewById<ImageButton>(R.id.scan1) as ImageButton
        scan2btn = v.findViewById<ImageButton>(R.id.scan2) as ImageButton
        scan3btn = v.findViewById<ImageButton>(R.id.scan3) as ImageButton
        scan4btn = v.findViewById<ImageButton>(R.id.scan4) as ImageButton
        scan5btn = v.findViewById<ImageButton>(R.id.scan5) as ImageButton
        scan6btn = v.findViewById<ImageButton>(R.id.scan6) as ImageButton

        s1 = v.findViewById<TextView>(R.id.awalshiftTV) as TextView
        s2 = v.findViewById<TextView>(R.id.before1stbreakTV) as TextView
        s3 = v.findViewById<TextView>(R.id.after1stbreakTV) as TextView
        s4 = v.findViewById<TextView>(R.id.before2ndbreakTV) as TextView
        s5 = v.findViewById<TextView>(R.id.after2ndbreakTV) as TextView
        s6 = v.findViewById<TextView>(R.id.pulangTV) as TextView

        s1a = v.findViewById<TextView>(R.id.awalshiftTV2) as TextView
        s2a = v.findViewById<TextView>(R.id.before1stbreakTV2) as TextView
        s3a = v.findViewById<TextView>(R.id.after1stbreakTV3) as TextView
        s4a = v.findViewById<TextView>(R.id.before2ndbreakTV2) as TextView
        s5a = v.findViewById<TextView>(R.id.after2ndbreakTV2) as TextView
        s6a = v.findViewById<TextView>(R.id.pulangTV2) as TextView

        pilihanbtn2 = v.findViewById<Switch>(R.id.pilihanbtn2) as Switch
        shift2tv = v.findViewById<TextView>(R.id.textView44) as TextView
        shift3tv = v.findViewById<TextView>(R.id.textView48) as TextView

        scan1btn.setOnClickListener(this)
        scan2btn.setOnClickListener(this)
        scan3btn.setOnClickListener(this)
        scan4btn.setOnClickListener(this)
        scan5btn.setOnClickListener(this)
        scan6btn.setOnClickListener(this)

        mesinTVstart = v.findViewById<TextView>(R.id.textView24)
        val activity = activity as MainActivity
        myMcnow = activity!!.getMyMesin()

        mesinTVstart.setText(myMcnow)

        try {
            /*val date = Date()
            val formatter = SimpleDateFormat("yyyyMMdd")
            datetimedata = formatter.format(date)

            linesaatini = myMcnow!!.substringAfter("LINE ").substringBefore(" STATION")
            stationsaatini = myMcnow!!.substringAfterLast("STATION ")
            val dateshift2 :String = "$datetimedata$linesaatini$stationsaatini"
            key2 = dateshift2.toString()*/
            shiftsaatini=2
            val date = Date()
            val formatter = SimpleDateFormat("yyyyMMdd")
            datetimedata = formatter.format(date)

            linesaatini = myMcnow!!.substringAfter("LINE ").substringBefore(" STATION")
            stationsaatini = myMcnow!!.substringAfterLast("STATION ")
            val dateshift2 :String = "$datetimedata$linesaatini$stationsaatini$shiftsaatini"
            key2 = dateshift2.toString()

            Toast.makeText(activity, "$key2", Toast.LENGTH_LONG).show()
        }catch (ex:Exception){
            Toast.makeText(activity, "$ex", Toast.LENGTH_LONG).show()
        }
        pilihanbtn2.setOnCheckedChangeListener { compoundButton, b ->
            try {
                if (b==false){
                    statussaatini==b
                    shift2tv.setTextColor(Color.GREEN)
                    shift3tv.setTextColor(Color.BLACK)
                    shiftsaatini=2
                    val date = Date()
                    val formatter = SimpleDateFormat("yyyyMMdd")
                    datetimedata = formatter.format(date)

                    linesaatini = myMcnow!!.substringAfter("LINE ").substringBefore(" STATION")
                    stationsaatini = myMcnow!!.substringAfterLast("STATION ")
                    val dateshift2 :String = "$datetimedata$linesaatini$stationsaatini$shiftsaatini"
                    key2 = dateshift2.toString()

                    Toast.makeText(activity, "$key2", Toast.LENGTH_LONG).show()

                    s1.text = "N/A"
                    s2.text = "N/A"
                    s3.text = "N/A"
                    s4.text = "N/A"
                    s5.text = "N/A"
                    s6.text = "N/A"

                    s1a.text = "ID N/A"
                    s2a.text = "ID N/A"
                    s3a.text = "ID N/A"
                    s4a.text = "ID N/A"
                    s5a.text = "ID N/A"
                    s6a.text = "ID N/A"

                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("MCON1").child("jam")
                        .addListenerForSingleValueEvent(object: ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                            }

                            override fun onDataChange(p0: DataSnapshot) {
                                try{
                                    val t = p0.getValue(Long::class.java)
                                    //val iniwaktu:Long = t!!
                                    val yourmilliseconds = System.currentTimeMillis()
                                    val sdf = SimpleDateFormat("HH:mm")
                                    val resultdate = Date(t!!)
                                    s1.text = sdf.format(resultdate)
                                }catch (ex:Exception){
                                    //Toast.makeText(activity, "$ex $key2", Toast.LENGTH_LONG).show()
                                }
                            }
                        })
                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("MCON1").child("id")
                        .addListenerForSingleValueEvent(object: ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                            }

                            override fun onDataChange(p0: DataSnapshot) {
                                try{
                                    val siz = p0.getValue(String::class.java)
                                    if (siz!=null){ s1a.setText("$siz") }
                                }catch (ex:Exception){
                                    Toast.makeText(activity, "$ex $key2", Toast.LENGTH_LONG).show()
                                }
                            }
                        })

                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("MCOFF2").child("jam")
                        .addListenerForSingleValueEvent(object: ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                            }

                            override fun onDataChange(p0: DataSnapshot) {
                                try{
                                    val t = p0.getValue(Long::class.java)
                                    //val iniwaktu:Long = t!!
                                    val yourmilliseconds = System.currentTimeMillis()

                                    val sdf = SimpleDateFormat("HH:mm")
                                    val resultdate = Date(t!!)
                                    s2.text = sdf.format(resultdate)
                                }catch (ex:Exception){
                                    //Toast.makeText(activity, "$ex $key2", Toast.LENGTH_LONG).show()
                                }
                            }
                        })
                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("MCOFF2").child("id")
                        .addListenerForSingleValueEvent(object: ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                            }

                            override fun onDataChange(p0: DataSnapshot) {
                                try{
                                    val siz = p0.getValue(String::class.java)
                                    if (siz!=null){ s2a.setText("$siz") }
                                }catch (ex:Exception){
                                    Toast.makeText(activity, "$ex $key2", Toast.LENGTH_LONG).show()
                                }
                            }
                        })

                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("MCON3").child("jam")
                        .addListenerForSingleValueEvent(object: ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                            }

                            override fun onDataChange(p0: DataSnapshot) {
                                try{
                                    val t = p0.getValue(Long::class.java)
                                    //val iniwaktu:Long = t!!
                                    val yourmilliseconds = System.currentTimeMillis()

                                    val sdf = SimpleDateFormat("HH:mm")
                                    val resultdate = Date(t!!)
                                    s3.text = sdf.format(resultdate)
                                }catch (ex:Exception){
                                    //Toast.makeText(activity, "$ex $key2", Toast.LENGTH_LONG).show()
                                }
                            }
                        })
                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("MCON3").child("id")
                        .addListenerForSingleValueEvent(object: ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                            }

                            override fun onDataChange(p0: DataSnapshot) {
                                try{
                                    val siz = p0.getValue(String::class.java)
                                    if (siz!=null){ s3a.setText("$siz") }
                                }catch (ex:Exception){
                                    Toast.makeText(activity, "$ex $key2", Toast.LENGTH_LONG).show()
                                }
                            }
                        })

                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("MCOFF4").child("jam")
                        .addListenerForSingleValueEvent(object: ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                            }

                            override fun onDataChange(p0: DataSnapshot) {
                                try{
                                    val t = p0.getValue(Long::class.java)
                                    //val iniwaktu:Long = t!!
                                    val yourmilliseconds = System.currentTimeMillis()

                                    val sdf = SimpleDateFormat("HH:mm")
                                    val resultdate = Date(t!!)
                                    s4.text = sdf.format(resultdate)
                                }catch (ex:Exception){
                                    //Toast.makeText(activity, "$ex $key2", Toast.LENGTH_LONG).show()
                                }
                            }
                        })
                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("MCOFF4").child("id")
                        .addListenerForSingleValueEvent(object: ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                            }

                            override fun onDataChange(p0: DataSnapshot) {
                                try{
                                    val siz = p0.getValue(String::class.java)
                                    if (siz!=null){ s4a.setText("$siz") }
                                }catch (ex:Exception){
                                    Toast.makeText(activity, "$ex $key2", Toast.LENGTH_LONG).show()
                                }
                            }
                        })

                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("MCON5").child("jam")
                        .addListenerForSingleValueEvent(object: ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                            }

                            override fun onDataChange(p0: DataSnapshot) {
                                try{
                                    val t = p0.getValue(Long::class.java)
                                    //val iniwaktu:Long = t!!
                                    val yourmilliseconds = System.currentTimeMillis()

                                    val sdf = SimpleDateFormat("HH:mm")
                                    val resultdate = Date(t!!)
                                    s5.text = sdf.format(resultdate)
                                }catch (ex:Exception){
                                    //Toast.makeText(activity, "$ex $key2", Toast.LENGTH_LONG).show()
                                }
                            }
                        })
                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("MCON5").child("id")
                        .addListenerForSingleValueEvent(object: ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                            }

                            override fun onDataChange(p0: DataSnapshot) {
                                try{
                                    val siz = p0.getValue(String::class.java)
                                    if (siz!=null){ s5a.setText("$siz") }
                                }catch (ex:Exception){
                                    Toast.makeText(activity, "$ex $key2", Toast.LENGTH_LONG).show()
                                }
                            }
                        })

                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("MCOFF6").child("jam")
                        .addListenerForSingleValueEvent(object: ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                            }

                            override fun onDataChange(p0: DataSnapshot) {
                                try{
                                    val t = p0.getValue(Long::class.java)
                                    //val iniwaktu:Long = t!!
                                    val yourmilliseconds = System.currentTimeMillis()

                                    val sdf = SimpleDateFormat("HH:mm")
                                    val resultdate = Date(t!!)
                                    s6.text = sdf.format(resultdate)
                                }catch (ex:Exception){
                                    //Toast.makeText(activity, "$ex $key2", Toast.LENGTH_LONG).show()
                                }
                            }
                        })
                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("MCOFF6").child("id")
                        .addListenerForSingleValueEvent(object: ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                            }

                            override fun onDataChange(p0: DataSnapshot) {
                                try{
                                    val siz = p0.getValue(String::class.java)
                                    if (siz!=null){s6a.setText("$siz")}
                                }catch (ex:Exception){
                                    Toast.makeText(activity, "$ex $key2", Toast.LENGTH_LONG).show()
                                }
                            }
                        })
                }else if (b==true){
                    statussaatini==b
                    shift2tv.setTextColor(Color.BLACK)
                    shift3tv.setTextColor(Color.GREEN)
                    shiftsaatini=3
                    val date = Date()
                    val formatter = SimpleDateFormat("yyyyMMdd")
                    datetimedata = formatter.format(date)

                    linesaatini = myMcnow!!.substringAfter("LINE ").substringBefore(" STATION")
                    stationsaatini = myMcnow!!.substringAfterLast("STATION ")
                    val dateshift2 :String = "$datetimedata$linesaatini$stationsaatini$shiftsaatini"
                    key2 = dateshift2.toString()
                    Toast.makeText(activity, "$key2", Toast.LENGTH_LONG).show()

                    s1.text = "N/A"
                    s2.text = "N/A"
                    s3.text = "N/A"
                    s4.text = "N/A"
                    s5.text = "N/A"
                    s6.text = "N/A"

                    s1a.text = "ID N/A"
                    s2a.text = "ID N/A"
                    s3a.text = "ID N/A"
                    s4a.text = "ID N/A"
                    s5a.text = "ID N/A"
                    s6a.text = "ID N/A"

                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("MCON1").child("jam")
                        .addListenerForSingleValueEvent(object: ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                            }

                            override fun onDataChange(p0: DataSnapshot) {
                                try{
                                    val t = p0.getValue(Long::class.java)
                                    //val iniwaktu:Long = t!!
                                    val yourmilliseconds = System.currentTimeMillis()
                                    val sdf = SimpleDateFormat("HH:mm")
                                    val resultdate = Date(t!!)
                                    s1.text = sdf.format(resultdate)
                                }catch (ex:Exception){
                                    //Toast.makeText(activity, "$ex $key2", Toast.LENGTH_LONG).show()
                                }
                            }
                        })
                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("MCON1").child("id")
                        .addListenerForSingleValueEvent(object: ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                            }

                            override fun onDataChange(p0: DataSnapshot) {
                                try{
                                    val siz = p0.getValue(String::class.java)
                                    if (siz!=null){ s1a.setText("$siz") }
                                }catch (ex:Exception){
                                    Toast.makeText(activity, "$ex $key2", Toast.LENGTH_LONG).show()
                                }
                            }
                        })

                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("MCOFF2").child("jam")
                        .addListenerForSingleValueEvent(object: ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                            }

                            override fun onDataChange(p0: DataSnapshot) {
                                try{
                                    val t = p0.getValue(Long::class.java)
                                    //val iniwaktu:Long = t!!
                                    val yourmilliseconds = System.currentTimeMillis()

                                    val sdf = SimpleDateFormat("HH:mm")
                                    val resultdate = Date(t!!)
                                    s2.text = sdf.format(resultdate)
                                }catch (ex:Exception){
                                    //Toast.makeText(activity, "$ex $key2", Toast.LENGTH_LONG).show()
                                }
                            }
                        })
                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("MCOFF2").child("id")
                        .addListenerForSingleValueEvent(object: ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                            }

                            override fun onDataChange(p0: DataSnapshot) {
                                try{
                                    val siz = p0.getValue(String::class.java)
                                    if (siz!=null){ s2a.setText("$siz") }
                                }catch (ex:Exception){
                                    Toast.makeText(activity, "$ex $key2", Toast.LENGTH_LONG).show()
                                }
                            }
                        })

                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("MCON3").child("jam")
                        .addListenerForSingleValueEvent(object: ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                            }

                            override fun onDataChange(p0: DataSnapshot) {
                                try{
                                    val t = p0.getValue(Long::class.java)
                                    //val iniwaktu:Long = t!!
                                    val yourmilliseconds = System.currentTimeMillis()

                                    val sdf = SimpleDateFormat("HH:mm")
                                    val resultdate = Date(t!!)
                                    s3.text = sdf.format(resultdate)
                                }catch (ex:Exception){
                                    //Toast.makeText(activity, "$ex $key2", Toast.LENGTH_LONG).show()
                                }
                            }
                        })
                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("MCON3").child("id")
                        .addListenerForSingleValueEvent(object: ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                            }

                            override fun onDataChange(p0: DataSnapshot) {
                                try{
                                    val siz = p0.getValue(String::class.java)
                                    if (siz!=null){ s3a.setText("$siz") }
                                }catch (ex:Exception){
                                    Toast.makeText(activity, "$ex $key2", Toast.LENGTH_LONG).show()
                                }
                            }
                        })

                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("MCOFF4").child("jam")
                        .addListenerForSingleValueEvent(object: ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                            }

                            override fun onDataChange(p0: DataSnapshot) {
                                try{
                                    val t = p0.getValue(Long::class.java)
                                    //val iniwaktu:Long = t!!
                                    val yourmilliseconds = System.currentTimeMillis()

                                    val sdf = SimpleDateFormat("HH:mm")
                                    val resultdate = Date(t!!)
                                    s4.text = sdf.format(resultdate)
                                }catch (ex:Exception){
                                    //Toast.makeText(activity, "$ex $key2", Toast.LENGTH_LONG).show()
                                }
                            }
                        })
                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("MCOFF4").child("id")
                        .addListenerForSingleValueEvent(object: ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                            }

                            override fun onDataChange(p0: DataSnapshot) {
                                try{
                                    val siz = p0.getValue(String::class.java)
                                    if (siz!=null){ s4a.setText("$siz") }
                                }catch (ex:Exception){
                                    Toast.makeText(activity, "$ex $key2", Toast.LENGTH_LONG).show()
                                }
                            }
                        })

                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("MCON5").child("jam")
                        .addListenerForSingleValueEvent(object: ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                            }

                            override fun onDataChange(p0: DataSnapshot) {
                                try{
                                    val t = p0.getValue(Long::class.java)
                                    //val iniwaktu:Long = t!!
                                    val yourmilliseconds = System.currentTimeMillis()

                                    val sdf = SimpleDateFormat("HH:mm")
                                    val resultdate = Date(t!!)
                                    s5.text = sdf.format(resultdate)
                                }catch (ex:Exception){
                                    //Toast.makeText(activity, "$ex $key2", Toast.LENGTH_LONG).show()
                                }
                            }
                        })
                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("MCON5").child("id")
                        .addListenerForSingleValueEvent(object: ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                            }

                            override fun onDataChange(p0: DataSnapshot) {
                                try{
                                    val siz = p0.getValue(String::class.java)
                                    if (siz!=null){ s5a.setText("$siz") }
                                }catch (ex:Exception){
                                    Toast.makeText(activity, "$ex $key2", Toast.LENGTH_LONG).show()
                                }
                            }
                        })

                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("MCOFF6").child("jam")
                        .addListenerForSingleValueEvent(object: ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                            }

                            override fun onDataChange(p0: DataSnapshot) {
                                try{
                                    val t = p0.getValue(Long::class.java)
                                    //val iniwaktu:Long = t!!
                                    val yourmilliseconds = System.currentTimeMillis()

                                    val sdf = SimpleDateFormat("HH:mm")
                                    val resultdate = Date(t!!)
                                    s6.text = sdf.format(resultdate)
                                }catch (ex:Exception){
                                    //Toast.makeText(activity, "$ex $key2", Toast.LENGTH_LONG).show()
                                }
                            }
                        })
                    FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("MCOFF6").child("id")
                        .addListenerForSingleValueEvent(object: ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                            }

                            override fun onDataChange(p0: DataSnapshot) {
                                try{
                                    val siz = p0.getValue(String::class.java)
                                    if (siz!=null){s6a.setText("$siz")}
                                }catch (ex:Exception){
                                    Toast.makeText(activity, "$ex $key2", Toast.LENGTH_LONG).show()
                                }
                            }
                        })
                }
            }catch (ex:Exception){

                Toast.makeText(activity, "$ex", Toast.LENGTH_LONG).show()
            }

        }
        return v
    }

    override fun onResume() {
        super.onResume()
        val activity = activity as MainActivity
        myMcnow = activity!!.getMyMesin()
        mesinTVstart.setText(myMcnow)
        try {
            mesinTVstart.addTextChangedListener(this)
            val date = Date()
            val formatter = SimpleDateFormat("yyyyMMdd")
            datetimedata = formatter.format(date)

            linesaatini = myMcnow!!.substringAfter("LINE ").substringBefore(" STATION")
            stationsaatini = myMcnow!!.substringAfterLast("STATION ")
            val dateshift2 :String = "$datetimedata$linesaatini$stationsaatini$shiftsaatini"
            key2 = dateshift2.toString()

            FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("MCON1").child("jam")
                .addValueEventListener(object: ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        try{
                            val t = p0.getValue(Long::class.java)
                            //val iniwaktu:Long = t!!
                            val yourmilliseconds = System.currentTimeMillis()
                            val sdf = SimpleDateFormat("HH:mm")
                            val resultdate = Date(t!!)
                            s1.text = sdf.format(resultdate)
                        }catch (ex:Exception){
                            //Toast.makeText(activity, "$ex $key2", Toast.LENGTH_LONG).show()
                        }
                    }
                })
            FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("MCON1").child("id")
                .addValueEventListener(object: ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        try{
                            val siz = p0.getValue(String::class.java)
                            if (siz!=null){ s1a.setText("$siz") }
                        }catch (ex:Exception){
                            Toast.makeText(activity, "$ex $key2", Toast.LENGTH_LONG).show()
                        }
                    }
                })

            FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("MCOFF2").child("jam")
                .addValueEventListener(object: ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        try{
                            val t = p0.getValue(Long::class.java)
                            //val iniwaktu:Long = t!!
                            val yourmilliseconds = System.currentTimeMillis()

                            val sdf = SimpleDateFormat("HH:mm")
                            val resultdate = Date(t!!)
                            s2.text = sdf.format(resultdate)
                        }catch (ex:Exception){
                            //Toast.makeText(activity, "$ex $key2", Toast.LENGTH_LONG).show()
                        }
                    }
                })
            FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("MCOFF2").child("id")
                .addValueEventListener(object: ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        try{
                            val siz = p0.getValue(String::class.java)
                            if (siz!=null){ s2a.setText("$siz") }
                        }catch (ex:Exception){
                            Toast.makeText(activity, "$ex $key2", Toast.LENGTH_LONG).show()
                        }
                    }
                })

            FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("MCON3").child("jam")
                .addValueEventListener(object: ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        try{
                            val t = p0.getValue(Long::class.java)
                            //val iniwaktu:Long = t!!
                            val yourmilliseconds = System.currentTimeMillis()

                            val sdf = SimpleDateFormat("HH:mm")
                            val resultdate = Date(t!!)
                            s3.text = sdf.format(resultdate)
                        }catch (ex:Exception){
                            //Toast.makeText(activity, "$ex $key2", Toast.LENGTH_LONG).show()
                        }
                    }
                })
            FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("MCON3").child("id")
                .addValueEventListener(object: ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        try{
                            val siz = p0.getValue(String::class.java)
                            if (siz!=null){ s3a.setText("$siz") }
                        }catch (ex:Exception){
                            Toast.makeText(activity, "$ex $key2", Toast.LENGTH_LONG).show()
                        }
                    }
                })

            FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("MCOFF4").child("jam")
                .addValueEventListener(object: ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        try{
                            val t = p0.getValue(Long::class.java)
                            //val iniwaktu:Long = t!!
                            val yourmilliseconds = System.currentTimeMillis()

                            val sdf = SimpleDateFormat("HH:mm")
                            val resultdate = Date(t!!)
                            s4.text = sdf.format(resultdate)
                        }catch (ex:Exception){
                            //Toast.makeText(activity, "$ex $key2", Toast.LENGTH_LONG).show()
                        }
                    }
                })
            FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("MCOFF4").child("id")
                .addValueEventListener(object: ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        try{
                            val siz = p0.getValue(String::class.java)
                            if (siz!=null){ s4a.setText("$siz") }
                        }catch (ex:Exception){
                            Toast.makeText(activity, "$ex $key2", Toast.LENGTH_LONG).show()
                        }
                    }
                })

            FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("MCON5").child("jam")
                .addValueEventListener(object: ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        try{
                            val t = p0.getValue(Long::class.java)
                            //val iniwaktu:Long = t!!
                            val yourmilliseconds = System.currentTimeMillis()

                            val sdf = SimpleDateFormat("HH:mm")
                            val resultdate = Date(t!!)
                            s5.text = sdf.format(resultdate)
                        }catch (ex:Exception){
                            //Toast.makeText(activity, "$ex $key2", Toast.LENGTH_LONG).show()
                        }
                    }
                })
            FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("MCON5").child("id")
                .addValueEventListener(object: ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        try{
                            val siz = p0.getValue(String::class.java)
                            if (siz!=null){ s5a.setText("$siz") }
                        }catch (ex:Exception){
                            Toast.makeText(activity, "$ex $key2", Toast.LENGTH_LONG).show()
                        }
                    }
                })

            FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("MCOFF6").child("jam")
                .addValueEventListener(object: ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        try{
                            val t = p0.getValue(Long::class.java)
                            //val iniwaktu:Long = t!!
                            val yourmilliseconds = System.currentTimeMillis()

                            val sdf = SimpleDateFormat("HH:mm")
                            val resultdate = Date(t!!)
                            s6.text = sdf.format(resultdate)
                        }catch (ex:Exception){
                            //Toast.makeText(activity, "$ex $key2", Toast.LENGTH_LONG).show()
                        }
                    }
                })
            FirebaseDatabase.getInstance().getReference().child("startstop").child("$key2").child("MCOFF6").child("id")
                .addValueEventListener(object: ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        try{
                            val siz = p0.getValue(String::class.java)
                            if (siz!=null){s6a.setText("$siz")}
                        }catch (ex:Exception){
                            Toast.makeText(activity, "$ex $key2", Toast.LENGTH_LONG).show()
                        }
                    }
                })

            //FirebaseDatabase.getInstance().getReference().child("andon").child("$key2").child("MCON1").child("jam").setValue(ServerValue.TIMESTAMP)
            //FirebaseDatabase.getInstance().getReference().child("andon").child("$key2").child("MCON1").child("id").setValue(id)


        }catch (ex:Exception){
            Toast.makeText(activity, "$ex", Toast.LENGTH_LONG).show()
        }
    }

    fun DoUploadnow (){
        val launch4 = Intent(activity, ScanningBarcodeActivity::class.java)
        launch4.putExtra("asal",asal)
        launch4.putExtra("mc2",myMcnow)
        launch4.putExtra("shift",shiftsaatini)
        Toast.makeText(activity, "$shiftsaatini", Toast.LENGTH_LONG).show()
        try {
            startActivity(launch4)
        }catch (ex:Exception){
            Toast.makeText(activity, "$ex", Toast.LENGTH_LONG).show()
        }
    }
}
