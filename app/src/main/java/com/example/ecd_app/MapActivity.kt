package com.example.ecd_app

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecd_app.mapModels.Place
import com.example.ecd_app.mapModels.UserMap
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.*

const val EXTRA_USER_MAP = "EXTRA_USER_MAP"
const val EXTRA_MAP_TITLE= "EXTRA_MAP_TITLE"
const val FILE_NAME= "UserMaps.data"

private const val REQUEST_CODE = 1234


class MapActivity : AppCompatActivity() {

    private lateinit var userMapsList: MutableList<UserMap>
    private lateinit var mapAdapter : MapsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userMapsFromFile =  deserializeUserMaps(this@MapActivity)
        Toast.makeText(this@MapActivity, "${userMapsFromFile.size} current map size", Toast.LENGTH_LONG).show()
        userMapsList = generateSampleData().toMutableList()
        userMapsList.addAll(userMapsFromFile)
        userMapsList.removeAt(0)
        setContentView(R.layout.activity_map)


        val debugTv = findViewById<TextView>(R.id.tvDebug)
        debugTv.append("LIST\n")
        for (item in userMapsList){
            debugTv.append(item._title+"\n")
        }

        //set layout manager on the recycler view
        //set adapter on recycler view
        val rvMaps : RecyclerView = findViewById(R.id.rvMaps)
//        userMapsList = generateSampleData().toMutableList()
        rvMaps.layoutManager = LinearLayoutManager(this)
        mapAdapter = MapsAdapter(this, userMapsList, object: MapsAdapter.OnClickListener{
            override fun onItemClick(position: Int) {
                Toast.makeText(this@MapActivity,"hi from main", Toast.LENGTH_LONG).show()
                val intent = Intent(this@MapActivity, DisplayMapActivity::class.java)
                intent.putExtra(EXTRA_USER_MAP, userMapsList[position])
                startActivity(intent)
            }

        })
        rvMaps.adapter= mapAdapter

        val fab : FloatingActionButton = findViewById(R.id.fabCreateMap)
        fab.setOnClickListener(){
            Toast.makeText(this,"hi from fab", Toast.LENGTH_LONG).show()
            showAlertDialog()



        }


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode== REQUEST_CODE && resultCode== Activity.RESULT_OK){
            //come here
            val userMap = data?.getSerializableExtra(EXTRA_USER_MAP) as UserMap
            Toast.makeText(this, "we here", Toast.LENGTH_SHORT).show()
            userMapsList.add(userMap)
            mapAdapter.notifyItemInserted(userMapsList.size-1)
            serializeUserMaps(this, userMapsList)

        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun serializeUserMaps(context: Context, userMaps: List<UserMap>) {
        ObjectOutputStream(FileOutputStream(getDataFile(context))).use { it.writeObject(userMaps) }
        Log.d("Logging", "filecereal")

    }

    private fun deserializeUserMaps(context: Context) : List<UserMap> {
        val dataFile = getDataFile(context)
        if (!dataFile.exists()) {
            Log.d("Logging", "filenotfound")
            return emptyList()
        }
        ObjectInputStream(FileInputStream(dataFile)).use {
            return it.readObject() as List<UserMap> }
    }




    private fun showAlertDialog() {
        val mapFormView = LayoutInflater.from(this).inflate(R.layout.dialog_create_map, null)
        val dialog =
            AlertDialog.Builder(this)
                .setTitle("Map Title")
                .setView(mapFormView)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK",null).show()
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(){
            val mapTitle = mapFormView.findViewById<EditText>(R.id.etMapTitle).text.toString()
            if(mapTitle.trim().isEmpty()){
                Toast.makeText(this@MapActivity, "Please enter a map title", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            //nav
            val intent = Intent(this@MapActivity, CreateMapActivity::class.java)
            intent.putExtra(EXTRA_MAP_TITLE, mapTitle)
            startActivityForResult(intent, REQUEST_CODE)
            dialog.dismiss()
        }
    }





    private fun getDataFile(context: Context) : File {
        return File(context.filesDir, FILE_NAME)

    }

    private fun generateSampleData(): List<UserMap> {
        return listOf(
            UserMap(
                "Memories from BBP",
                listOf(
                    Place("Branner Hall", "Best dorm at Stanford", 37.426, -122.163),
                    Place("Gates CS building", "Many long nights in this basement", 37.430, -122.173),
                    Place("Pinkberry", "First date with my wife", 37.444, -122.170)
                )
            ))
    }
}