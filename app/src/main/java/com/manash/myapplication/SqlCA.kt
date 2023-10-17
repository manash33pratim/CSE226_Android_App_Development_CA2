package com.manash.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog



class SqlCA : AppCompatActivity() {
    private lateinit var id: EditText
    private lateinit var name: TextView
    private lateinit var people: EditText
    private lateinit var days: EditText
    private lateinit var scroll: ScrollView

    private lateinit var submitData: Button
    private lateinit var updateData: Button
    private lateinit var deleteData: Button

    private lateinit var kerala: ImageButton
    private lateinit var himachal: ImageButton
    private lateinit var agra: ImageButton
    private lateinit var ladakh: ImageButton
    private lateinit var varansi: ImageButton
    private lateinit var goa: ImageButton

    private lateinit var getId: TextView
    private lateinit var getName: TextView
    private lateinit var getPerson: TextView
    private lateinit var getDays: TextView
    private lateinit var getAmount: TextView

   var money=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.your_holiday_planner)

        id = findViewById(R.id.Name)
        name = findViewById(R.id.Place)
        people = findViewById(R.id.EtAge)
        days = findViewById(R.id.EtSalary)
        getAmount=findViewById(R.id.total)

        scroll=findViewById(R.id.scrollView)


        submitData = findViewById(R.id.submitData)

        updateData = findViewById(R.id.updateData)
        deleteData = findViewById(R.id.deleteData)

        getId = findViewById(R.id.displayId)
        getName = findViewById(R.id.displayName)
        getPerson = findViewById(R.id.displayAge)
        getDays = findViewById(R.id.displaySalary)

        ladakh=findViewById(R.id.leh)
        varansi=findViewById(R.id.varansi)
        himachal=findViewById(R.id.himachal)
        kerala=findViewById(R.id.kerala)
        agra=findViewById(R.id.agara)
        goa=findViewById(R.id.goa)


        ladakh.setOnClickListener {
            name.setText("Ladakh")
            money=5999/3
        }
        varansi.setOnClickListener {
            name.setText("Varansi")
            money=6999/4
        }
        himachal.setOnClickListener {
            name.setText("Himachal")
            money=5499/3
        }
        kerala.setOnClickListener {
            name.setText("Kerala")
            money=7999/4

        }
        agra.setOnClickListener {
            name.setText("Agra")
            money=4999/3

        }
        goa.setOnClickListener {
            name.setText("Goa")
            money=7499/3
        }



        submitData.setOnClickListener {

            if(id.text.isEmpty() || name.text.isEmpty() || people.text.isEmpty()|| days.text.isEmpty() ){

                Toast.makeText(this, "Please fill out all required fields", Toast.LENGTH_SHORT).show()
            }else {

                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder
                    .setMessage("Do you want to save this")
                    .setTitle("Please Confirm")
                    .setPositiveButton("Yes") { dialog, which ->
                        val db = SqlCAhelper(this, null)

                        val dataId = id.text.toString()
                        val dataPlace = name.text.toString()
                        val dataPersion = people.text.toString()
                        val dataDays = days.text.toString()
                        val dataAmount = (money * dataDays.toInt()) * dataPersion.toInt()

                        db.addData(dataId, dataPlace, dataPersion, dataDays, dataAmount)
                        Toast.makeText(this, "$dataPlace added to database", Toast.LENGTH_SHORT).show()

                        id.text.clear()
                        name.text=""
                        people.text.clear()
                        days.text.clear()
                        money = 0
                        displayData(getId, getName, getPerson, getDays, getAmount)

                    }
                    .setNegativeButton("Cancel") { dialog, which ->

                    }

                val dialog: AlertDialog = builder.create()
                dialog.show()


            }
        }



        updateData.setOnClickListener {

            val db = SqlCAhelper(this, null)

            val dataId = id.text.toString()
            val dataPlace = name.text.toString()
            val dataPersion = people.text.toString()
            val dataDays = days.text.toString()
            val dataAmount=(money*dataDays.toInt())*dataPersion.toInt()

            db.updateData(dataId, dataPlace, dataPersion, dataDays, dataAmount)
            Toast.makeText(this, "$dataId updated to database", Toast.LENGTH_SHORT).show()

            id.text.clear()
            name.text=""
            people.text.clear()
            days.text.clear()
            money=0
            displayData(getId, getName, getPerson, getDays, getAmount)
        }
        deleteData.setOnClickListener {


            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder
                .setMessage("Are You sure you want to delete this")
                .setTitle("ALART")
                .setPositiveButton("Confirm") { dialog, which ->
                    val db = SqlCAhelper(this, null)

                    val dataId = id.text.toString()

                    db.deleteData(dataId)
                    Toast.makeText(this, "$dataId deleted from database", Toast.LENGTH_SHORT).show()

                    id.text.clear()
                    name.text=""
                    people.text.clear()
                    days.text.clear()
                    money=0
                    displayData(getId, getName, getPerson, getDays,getAmount)
                }
                .setNegativeButton("Cancel") { dialog, which ->

                }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }
    @SuppressLint("Range")
    private fun displayData(
        tvId: TextView,
        tvPlace: TextView,
        tvPeople: TextView,
        tvDays: TextView,
        tvAmount: TextView
    ) {
        val db = SqlCAhelper(this, null)
            val cursor = db.getData()
        tvId.text = "Id\n\n"
        tvPlace.text = "Place\n\n"
        tvPeople.text = "No.People\n\n"
        tvDays.text = "Days\n\n"
        tvAmount.text="Amount\n\n"

        cursor!!.moveToFirst()
        tvId.append(
            cursor.getString(cursor.getColumnIndex(SqlCAhelper.ID_COL))
                    + "\n"
        )
        tvPlace.append(
            cursor.getString(cursor.getColumnIndex(SqlCAhelper.PLACE_COL))
                    + "\n"
        )
        tvPeople.append(
            cursor.getString(cursor.getColumnIndex(SqlCAhelper.PEOPLE_COL))
                    + "\n"
        )
        tvDays.append(
            cursor.getString(cursor.getColumnIndex(SqlCAhelper.DAYS_COL))
                    + "\n"
        )
        tvAmount.append(
            cursor.getString(cursor.getColumnIndex(SqlCAhelper.TOTAL_AMOUNT))
                    + "\n"
        )

        while (cursor.moveToNext()) {
            tvId.append(
                cursor.getString(cursor.getColumnIndex(SqlCAhelper.ID_COL))
                        + "\n"
            )
            tvPlace.append(
                cursor.getString(cursor.getColumnIndex(SqlCAhelper.PLACE_COL))
                        + "\n"
            )
            tvPeople.append(
                cursor.getString(cursor.getColumnIndex(SqlCAhelper.PEOPLE_COL))
                        + "\n"
            )
            tvDays.append(
                cursor.getString(cursor.getColumnIndex(SqlCAhelper.DAYS_COL))
                        + "\n"
            )
            tvAmount.append(
                cursor.getString(cursor.getColumnIndex(SqlCAhelper.TOTAL_AMOUNT))
                        + "\n"
            )

        }
        cursor.close()
    }
}