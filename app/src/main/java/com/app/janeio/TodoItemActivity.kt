package com.app.janeio

import Janeio.R
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import com.app.janeio.database.TodoItemDBHelper
import com.app.janeio.model.TodoItem
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random
import kotlin.random.nextInt

class TodoItemActivity : AppCompatActivity() {
    private lateinit var save_btn : Button
    private lateinit var title_text: EditText
    private lateinit var note_text: EditText
    lateinit var todoDBHelper: TodoItemDBHelper
    private lateinit var picker: MaterialTimePicker
    private lateinit var datePicker:MaterialDatePicker<Long>
    private  lateinit var calendar: Calendar
    lateinit var dateView: TextView
    lateinit var timeView:TextView
    lateinit var toolbar: Toolbar
    lateinit var selectTimeBtn: Button
    lateinit var selectDateBtn:Button
    lateinit var deleteBtn:ImageButton
    lateinit var alarmManager:AlarmManager
    lateinit var selectedDate : Date
    var item = TodoItem(null,"","","","", 0)
    var hasPickedTime = false
    var hasPickedDate = false
    var requestCode = 0



    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // set action bar

        setContentView(R.layout.activity_todo_item)
        createNotificationChannel()
        toolbar = findViewById(R.id.back_toolbar) as androidx.appcompat.widget.Toolbar
        setSupportActionBar(toolbar)


        //set ui data
        save_btn = findViewById(R.id.save_btn) as Button
        title_text = findViewById(R.id.title)as EditText
        note_text = findViewById(R.id.note) as EditText
        dateView = findViewById(R.id.date_view) as TextView
        timeView = findViewById(R.id.time_view) as TextView
        selectDateBtn = findViewById(R.id.selectDate) as Button
        selectTimeBtn = findViewById(R.id.selectTime) as Button
        deleteBtn = findViewById(R.id.delete_btn) as ImageButton
        calendar = Calendar.getInstance()
        //initialize date pickers
//        picker = MaterialTimePicker.Builder()
//            .setTimeFormat(TimeFormat.CLOCK_12H)
//            .setHour(12)
//            .setMinute(0)
//            .setTitleText("Select Due Date")
//            .build()
//        datePicker = MaterialDatePicker.Builder.datePicker().build()


        //listener
        save_btn.setOnClickListener{

            saveItem()
        }

        //fill items with data from previous activity if there is any
        fillItems()

        // date time button clkick listeners
        selectDateBtn.setOnClickListener {
            showDatePicker()
        }
        selectTimeBtn.setOnClickListener {
           showTimePicker()
        }
        deleteBtn.setOnClickListener {
            deleteItem()
        }

    }

    private fun deleteItem(){
        val intent = intent
        val id = intent.getStringExtra("item_id")

        if (id !="0" && id!=null){
            val todoDBHelper = TodoItemDBHelper(this)
            if(todoDBHelper.deleteItem(id) ){
                Toast.makeText(this, "Item Deleted", Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(this, "An error occured", Toast.LENGTH_SHORT).show()
            }
            cancelAlarm()
        }
    }


    // take in date data and set an alarm on the android OS schedule manager
    @RequiresApi(Build.VERSION_CODES.S)
    private fun setAlarm(itemID:Int){
//        calendar = Calendar.getInstance()

//        calendar[Calendar.HOUR_OF_DAY]= picker.hour
//        calendar[Calendar.MINUTE] = picker.minute
//        calendar[Calendar.SECOND] = 0
//        calendar[Calendar.MILLISECOND]= 0
//        calendar[Calendar.DAY_OF_MONTH] = selectedDate.day
//        calendar[Calendar.MONTH]= selectedDate.month+1
//        calendar[Calendar.YEAR]= selectedDate.year

        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReciever::class.java )
        intent.putExtra("item_id", itemID)
        val pendingIntent = PendingIntent.getBroadcast(this, requestCode ,intent, 0)
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
        Toast.makeText(this, "Alarm set successfully", Toast.LENGTH_SHORT).show()
//        Log.i("CAN SET EXACT", alarmManager.canScheduleExactAlarms().toString())
    }

    private fun cancelAlarm(){
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReciever::class.java )
        val pendingIntent = PendingIntent.getBroadcast(this, requestCode ,intent, 0)
        alarmManager.cancel(pendingIntent)
        Toast.makeText(this, "Alarm cancelled", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.back_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    fun fillItems(){
        val intent = intent
        val id = intent.getStringExtra("item_id")

        if (id !="0" && id!=null){
            val todoDBHelper = TodoItemDBHelper(this)
            item = todoDBHelper.readItem(id!!)[0]
            title_text.text.append(item.Title)
            note_text.text.append(item.Note)
            timeView.text = item.Time
            dateView.text = item.Date
            requestCode = item.Reqcode!!
        }

    }

    private fun showDatePicker(){
        datePicker = MaterialDatePicker.Builder.datePicker().build()
        datePicker.show(supportFragmentManager, "DatePicker")
        datePicker.addOnPositiveButtonClickListener {
            val dateFOrmatter = SimpleDateFormat("dd-MM-yy")
            selectedDate = Date(it)
            val date = dateFOrmatter.format(Date(it))
            dateView.text = date
            hasPickedDate = true
            calendar[Calendar.DAY_OF_MONTH] = selectedDate.day+1 //6
            calendar[Calendar.MONTH]= selectedDate.month //0
            calendar[Calendar.YEAR]= selectedDate.year+1900 //2023
            Log.i("XXXXX year" , selectedDate.year.toString())
        }
    }

    private fun showTimePicker(){
        picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Select Due Date")
            .build()



        picker.show(supportFragmentManager, "AlarmNotify")

        picker.addOnPositiveButtonClickListener{
            if(picker.hour > 12){
                timeView.text= String.format("%02d", picker.hour-12)+" : " +String.format("%02d", picker.minute)+" PM"
            }else{

                timeView.text= String.format("%02d", picker.hour)+" : " +String.format("%02d", picker.minute)+" AM"
            }


            calendar[Calendar.HOUR_OF_DAY]= picker.hour
            calendar[Calendar.MINUTE] = picker.minute
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND]= 0

            hasPickedTime = true
            Log.i("XXXXX minute " , calendar[Calendar.MINUTE].toString() +
                    " hour: "+calendar[Calendar.HOUR_OF_DAY].toString())
        }
    }



    @RequiresApi(Build.VERSION_CODES.S)
    public fun saveItem(){
        var newitemID:Long =0
        if (item.Id == null){
            requestCode = Random(85395).nextInt(10039,997330300)
            // create new item on db if there is non existing in this activity
            item = TodoItem(
                null,
                title_text.text.toString(),
                note_text.text.toString(),
                dateView.text.toString(),
                timeView.text.toString(),
                requestCode
            )
            todoDBHelper = TodoItemDBHelper(this)
//            todoDBHelper.dropDB()
            newitemID =todoDBHelper.insertTododItem(item)
            todoDBHelper.close()
            // set alarm after saving data. set alarm if date is selected
            //set alarm only when creating new item
            if(hasPickedDate && hasPickedTime){
                setAlarm(newitemID.toInt())
            }
            Toast.makeText(applicationContext, "Item Created", Toast.LENGTH_SHORT).show()

        }else{
            //updated item data
            var newItem = TodoItem(item.Id, title_text.text.toString(),
                note_text.text.toString(), dateView.text.toString(),timeView.text.toString(), requestCode )

            todoDBHelper = TodoItemDBHelper(this)
            todoDBHelper.insertTododItem(newItem)
            Toast.makeText(applicationContext, "Item Updated", Toast.LENGTH_SHORT).show()
        }
        finish()
    }


    //handle closing the page with back button
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var itemView = item.itemId
        when(itemView){
            R.id.back_menu ->{
              finish()
            }
        }
        return false
    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val name: CharSequence = "alarmappchannel"
            val description = "Channel for Alarm app"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val chanel = NotificationChannel("AlarmNotify", name, importance)
            chanel.description = description

            val notificationManager =  getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(chanel)
        }
    }


}