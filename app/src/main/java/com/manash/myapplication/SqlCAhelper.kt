package com.manash.myapplication
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqlCAhelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {


        companion object {


            private const val DATABASE_NAME = "CSE226ca1"


            private const val DATABASE_VERSION = 1

            private const val TABLE_NAME = "my_Table2"

            const val ID_COL = "id"

            const val PLACE_COL = "place"

            const val PEOPLE_COL = "people_count"

            const val DAYS_COL = "days"

            const val TOTAL_AMOUNT= "amount"
        }

        override fun onCreate(db: SQLiteDatabase) {


            val query = ("CREATE TABLE $TABLE_NAME " +
                    "($ID_COL TEXT, $PLACE_COL TEXT, $PEOPLE_COL TEXT, $DAYS_COL TEXT, $TOTAL_AMOUNT TEXT)")


            db.execSQL(query)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {


            db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
            onCreate(db)
        }


        fun addData(id: String, place: String, people: String, days: String, amount: Int) {

            val values = ContentValues()


            values.put(ID_COL, id)
            values.put(PLACE_COL, place)
            values.put(PEOPLE_COL, people)
            values.put(DAYS_COL, days)
            values.put(TOTAL_AMOUNT, amount)


            val db = this.writableDatabase

            db.insert(TABLE_NAME, null, values)


            db.close()
        }

        fun updateData(id: String, place: String, people: String, days: String, amount: Int) {

            val db = this.writableDatabase
            val values = ContentValues()

            values.put(PLACE_COL, place)
            values.put(PEOPLE_COL, people)
            values.put(DAYS_COL, days)
            values.put(TOTAL_AMOUNT, amount)
            db.update(TABLE_NAME, values, "ID=?", arrayOf(id))


            db.close()
        }

        fun getData(): Cursor? {

            val db = this.readableDatabase


            return db.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY $ID_COL", null)
        }

        fun deleteData(id: String) {

            val db = this.writableDatabase
            db.delete(TABLE_NAME, "ID=?", arrayOf(id))
            db.close()
        }
    }