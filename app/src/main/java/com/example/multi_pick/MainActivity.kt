package com.example.multi_pick


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    lateinit var btn: Button
    val dataobject = ArrayList<itemViewModel>()
    val PICK_IMAGES_CODE=0
    lateinit var recyclerAdapter: RecyclerItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recycleview)
        recyclerView.isNestedScrollingEnabled = false
        btn = findViewById(R.id.button)
        recyclerView.setItemViewCacheSize(50)

        Log.d("Tag", "Adapter and RecyclerView set up")
        recyclerAdapter = RecyclerItemViewModel(this, dataobject)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recyclerAdapter

        btn.setOnClickListener {
            Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show()
            pick_image_intent()
            Log.d("Tag", "Button Clicked")
        }


    }

    fun pick_image_intent(){
        val intent= Intent()
        intent.type="image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true)
        intent.action=Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"Select Image(s)"),PICK_IMAGES_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==PICK_IMAGES_CODE){
            if(resultCode== Activity.RESULT_OK){
                if(data!!.clipData!=null){
                    val count=data.clipData!!.itemCount
                    var imageurl: Uri?
                    for(i in 0 until  count){
                         imageurl=data.clipData!!.getItemAt(i).uri
                        Log.d("Tag", "Image URI: $imageurl")
                        dataobject.add(itemViewModel(imageurl))
                    }
                    recyclerAdapter.notifyDataSetChanged()
                }
                else{
                    val imageuri=data.data
                }
            }
        }
    }
}
