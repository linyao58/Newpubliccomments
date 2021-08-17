package com.example.newpubliccomments

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


import android.widget.Toast

class MyDatabaseHelper(val context: Context, name: String, version: Int) :
    SQLiteOpenHelper(context, name, null, version){

    private val createuser = "create table User (" + " id integer primary key autoincrement, " +
            " username text," + " usersex text," + " userpassword text, " + " userpholo text," +
            " useremail text, " + "  userimg blob)"

    private val createsynopsis = "create table Synopsis (" + " id integer primary key autoincrement, " +
            " userpholo text," + " S_synopsis text," + " S_img blob, " + " S_vedio blob," + " S_shenhe text)"

    private val createbusiness = "create table Business (" + " id integer primary key autoincrement, " +
            " B_name text, " + " B_shenhe text, " + " B_profile text," + " B_img blob," + " B_vedio blob)"

    private val createcommodity = "create table Commodity (" + " id integer primary key autoincrement, " +
            " B_id integer," + " C_name text," + " C_img blob," + " C_vedio blob, " + " C_shenhe text, " + " C_profile text)"

    private val createOrder = "create table Orders (" + " id integer primary key autoincrement, " +
            " B_id integer," + " C_id integer," + " O_pholo text," + " O_state text," + " B_name text," + " C_name text)"

    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL(createuser)
        db.execSQL(createsynopsis)
        db.execSQL(createbusiness)
        db.execSQL(createcommodity)
        db.execSQL(createOrder)
        Toast.makeText(context, "数据库创建成功", Toast.LENGTH_SHORT).show()

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        db.execSQL("drop table if exists User")
        db.execSQL("drop table if exists Synopsis")
        db.execSQL("drop table if exists Business")
        db.execSQL("drop table if exists Commodity")
        db.execSQL("drop table if exists Orders")
        onCreate(db)



        /*if (oldVersion <= 22){
            db.execSQL(createOrder)
        }*/

        /*if(oldVersion <= 19){

            db.execSQL("alter table Commodity add column C_profile text")
            db.execSQL("alter table Business add column B_profile text")
            db.execSQL("alter table Business add column B_img blob")
            db.execSQL("alter table Business add column B_vedio blob")
        }*/
    }
    }


