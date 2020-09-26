package jp.techacademy.masaaki.kabe.autoslideshowapp

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.pm.PackageManager
import android.provider.MediaStore
import android.content.ContentUris
import android.os.Build
import android.os.Handler
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity(){


    private val PERMISSION_REQUEST_CODE=100
    var aa=1
    var bb=0

    private var mTimer:Timer?=null
    private var mHandler= Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                getContentsInfo()
            } else {
                requestPermissions(
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST_CODE
                )
            }
        } else {
            getContentsInfo()
        }

        getContentsInfo1()

        button1.setOnClickListener {
            getContentsInfo2()
        }

        button2.setOnClickListener {
            getContentsInfo3()
        }


        button3.setOnClickListener {
            autoslide_main()
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_REQUEST_CODE->
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    getContentsInfo()
                }
        }
    }

    private fun getContentsInfo(){
        val resolver=contentResolver
        val cursor=resolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            null
        )

        if(cursor!!.moveToFirst()){
                val fieldIndex=cursor.getColumnIndex(MediaStore.Images.Media._ID)
                val id=cursor.getLong(fieldIndex)
                val imageUri=ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,id)
                imageView.setImageURI(imageUri)

        }
        cursor.close()
    }

    private fun getContentsInfo1(){
        val resolver=contentResolver
        val cursor=resolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            null
        )

        if(cursor!!.moveToFirst()){
            do {bb=bb+1
                val fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID)
                val id = cursor.getLong(fieldIndex)
                val imageUri =
                    ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
            }while(cursor.moveToNext())
        }
        Log.d("kotlintest","bb:"+bb.toString())
       cursor.close()
    }

    private fun getContentsInfo2(){
        val resolver=contentResolver
        val cursor=resolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            null
        )

        if (aa>=bb) {
            aa = 0
            aa = aa + 1
        }else{
            aa=aa+1
        }

        if(cursor!!.move(aa)){
                val fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID)
                val id = cursor.getLong(fieldIndex)
                val imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
            imageView.setImageURI(imageUri)
                Log.d("kotlintest", "URI:" + imageUri.toString())
                Log.d("kotlintest", "aa:" + aa.toString())
            }
        cursor.close()
        }



    private fun getContentsInfo3(){
        val resolver=contentResolver
        val cursor=resolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            null
        )

        if (aa<=1) {
            aa = 8
            aa = aa-1
        }else{
            aa=aa-1
        }

        if(cursor!!.move(aa)){
            val fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID)
            val id = cursor.getLong(fieldIndex)
            val imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
            imageView.setImageURI(imageUri)
            Log.d("kotlintest", "URI:" + imageUri.toString())
            Log.d("kotlintest", "aa:" + aa.toString())
        }
        cursor.close()
    }



    private fun autoslide_main() {

        if (button3.text == "再生") {
            button3.text = "停止"
            autoslide_sub()
            button1.isClickable = false
            button2.isClickable = false
        }else if (button3.text=="停止"){
            button3.text = "再生"
            button1.isClickable = true
            button2.isClickable = true
            if(mTimer!=null){
                    mTimer!!.cancel() }
            mTimer=null
        }else{
            button3.text = "停止"
            button1.isClickable = false
            button2.isClickable = false
            autoslide_sub()
        }

        }

    private fun autoslide_sub() {
        if (mTimer == null) {
            mTimer = Timer()
            mTimer!!.schedule(object:TimerTask(){
                override fun run() {
                    mHandler.post {
                        val resolver = contentResolver
                        val cursor = resolver.query(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            null,
                            null,
                            null,
                            null
                        )

                        if (aa >= bb) {
                            aa = 0
                            aa = aa + 1
                        } else {
                            aa = aa + 1
                        }

                        if (cursor!!.move(aa)) {
                            val fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID)
                            val id = cursor.getLong(fieldIndex)
                            val imageUri = ContentUris.withAppendedId(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                id
                            )
                            imageView.setImageURI(imageUri)
                            Log.d("kotlintest", "URI:" + imageUri.toString())
                            Log.d("kotlintest", "aa:" + aa.toString())
                        }
                        cursor.close()
                    }
                }
            }, 2000, 2000)


        }
    }









}
