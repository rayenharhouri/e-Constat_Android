package com.example.econstat_android.ViewModel

import android.Manifest
import android.content.ClipData
import android.content.ClipDescription
import android.content.ContentValues
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.DragEvent
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.econstat_android.R
import android.widget.*
import com.example.econstat_android.fragments.homeFragment

class Drawer : AppCompatActivity() {
    private lateinit var Capture: ImageButton
    private lateinit var carb1: View
    private lateinit var carb2: View
    private lateinit var carb3: View
    private lateinit var carb4: View
    private lateinit var carb5: View
    private lateinit var cara1: View
    private lateinit var cara2: View
    private lateinit var cara3: View
    private lateinit var cara4: View
    private lateinit var cara5: View
    private lateinit var crash1: View
    private lateinit var crash2: View
    private lateinit var crash3: View
    private lateinit var right: View
    private lateinit var left: View
    private lateinit var truck1: View
    private lateinit var truck2: View
    private lateinit var truck3: View
    private lateinit var half1: View
    private lateinit var half2: View
    private lateinit var half3: View
    private lateinit var half4: View


    private lateinit var ll: GridLayout
    private lateinit var ll2: GridLayout
    private lateinit var ll3: GridLayout
    private lateinit var ll4: GridLayout
    private lateinit var ll5: GridLayout
    private lateinit var ll6: GridLayout
    private lateinit var ll7: GridLayout
    private lateinit var ll8: GridLayout
    private lateinit var ll9: GridLayout
    private lateinit var ll10: GridLayout
    private lateinit var ll11: GridLayout
    private lateinit var ll12: GridLayout
    private lateinit var ll13: GridLayout
    private lateinit var ll14: GridLayout
    private lateinit var ll15: GridLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE


        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            PackageManager.PERMISSION_GRANTED
        )
        ////////images cars
        carb1 = findViewById(R.id.carb1)
        carb2 = findViewById(R.id.carb2)
        carb3 = findViewById(R.id.carb3)
        carb4 = findViewById(R.id.carb4)
        carb5 = findViewById(R.id.carb5)

        cara1 = findViewById(R.id.cara1)
        cara2 = findViewById(R.id.cara2)
        cara3 = findViewById(R.id.cara3)
        cara4 = findViewById(R.id.cara4)
        cara5 = findViewById(R.id.cara5)

        crash1 = findViewById(R.id.crash1)
        crash2 = findViewById(R.id.crash2)
        crash3 = findViewById(R.id.crash3)

        right = findViewById(R.id.right1)
        left = findViewById(R.id.left1)

        truck1 = findViewById(R.id.truck1)
        truck2 = findViewById(R.id.truck2)
        truck3 = findViewById(R.id.truck3)

        half1 = findViewById(R.id.half1)
        half2 = findViewById(R.id.half2)
        half3 = findViewById(R.id.half3)
        half4 = findViewById(R.id.half4)


////////////////layout
        ll = findViewById(R.id.ll)
        ll2 = findViewById(R.id.ll2)
        ll3 = findViewById(R.id.ll3)
        ll4 = findViewById(R.id.ll4)
        ll5 = findViewById(R.id.ll5)
        ll6 = findViewById(R.id.ll6)
        ll7 = findViewById(R.id.ll7)
        ll8 = findViewById(R.id.ll8)
        ll9 = findViewById(R.id.ll9)
        ll10 = findViewById(R.id.ll10)
        ll11 = findViewById(R.id.ll11)
        ll12 = findViewById(R.id.ll12)
        ll13 = findViewById(R.id.ll13)
        ll14 = findViewById(R.id.ll14)
        ll15 = findViewById(R.id.ll15)
        Capture = findViewById(R.id.imageButtonCapture)


        ////////////////setOnDragListener
        ll.setOnDragListener(dragListener)
        ll2.setOnDragListener(dragListener)
        ll3.setOnDragListener(dragListener)
        ll4.setOnDragListener(dragListener)
        ll5.setOnDragListener(dragListener)
        ll6.setOnDragListener(dragListener)
        ll7.setOnDragListener(dragListener)
        ll8.setOnDragListener(dragListener)
        ll9.setOnDragListener(dragListener)
        ll10.setOnDragListener(dragListener)
        ll11.setOnDragListener(dragListener)
        ll12.setOnDragListener(dragListener)
        ll13.setOnDragListener(dragListener)
        ll14.setOnDragListener(dragListener)
        ll15.setOnDragListener(dragListener)


        ////////////////////////////////
        /*
            recyclerView = findViewById(R.id.rv_sketch)
            recyclerView.layoutManager = GridLayoutManager(this, 1)
            /////////////:

            val images = listOf(
                R.drawable.cara,
                R.drawable.carb,
                R.drawable.electric_car,
                R.drawable.carb,
                R.drawable.electric_car
            )
            adapter = ImageAdapter(images)
            recyclerView.adapter = adapter

            // recyclerView.adapter = ImageAdapter(listOf(R.drawable.cara, R.drawable.carb, R.drawable.electric_car))
            val callback = ImageItemTouchHelperCallback(adapter)
            val touchHelper = ItemTouchHelper(callback)
            touchHelper.attachToRecyclerView(recyclerView)
    */


        Capture.setOnClickListener {
            takeScreenshot(50, 380, 1920, 800)
           /* var intent = Intent(this, "win besh thez"::class.java)
            startActivity(intent)*/
        }
        carb1.setOnLongClickListener {
            val clipText = ""
            val item = ClipData.Item(clipText)
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(clipText, mimeTypes, item)
            val dragShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(data, dragShadowBuilder, it, 0)
            true

        }
        carb2.setOnLongClickListener {
            val clipText = ""
            val item = ClipData.Item(clipText)
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(clipText, mimeTypes, item)
            val dragShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(data, dragShadowBuilder, it, 0)
            true

        }
        carb3.setOnLongClickListener {
            val clipText = ""
            val item = ClipData.Item(clipText)
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(clipText, mimeTypes, item)
            val dragShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(data, dragShadowBuilder, it, 0)
            true

        }
        carb4.setOnLongClickListener {
            val clipText = ""
            val item = ClipData.Item(clipText)
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(clipText, mimeTypes, item)
            val dragShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(data, dragShadowBuilder, it, 0)
            true

        }



        cara1.setOnLongClickListener {
            val clipText = ""
            val item = ClipData.Item(clipText)
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(clipText, mimeTypes, item)
            val dragShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(data, dragShadowBuilder, it, 0)
            true

        }
        cara2.setOnLongClickListener {
            val clipText = ""
            val item = ClipData.Item(clipText)
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(clipText, mimeTypes, item)
            val dragShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(data, dragShadowBuilder, it, 0)
            true

        }
        cara3.setOnLongClickListener {
            val clipText = ""
            val item = ClipData.Item(clipText)
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(clipText, mimeTypes, item)
            val dragShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(data, dragShadowBuilder, it, 0)
            true

        }
        cara4.setOnLongClickListener {
            val clipText = ""
            val item = ClipData.Item(clipText)
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(clipText, mimeTypes, item)
            val dragShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(data, dragShadowBuilder, it, 0)
            true

        }


        crash1.setOnLongClickListener {
            val clipText = ""
            val item = ClipData.Item(clipText)
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(clipText, mimeTypes, item)
            val dragShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(data, dragShadowBuilder, it, 0)
            true

        }
        crash2.setOnLongClickListener {
            val clipText = ""
            val item = ClipData.Item(clipText)
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(clipText, mimeTypes, item)
            val dragShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(data, dragShadowBuilder, it, 0)
            true

        }


        truck1.setOnLongClickListener {
            val clipText = ""
            val item = ClipData.Item(clipText)
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(clipText, mimeTypes, item)
            val dragShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(data, dragShadowBuilder, it, 0)
            true

        }
        truck2.setOnLongClickListener {
            val clipText = ""
            val item = ClipData.Item(clipText)
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(clipText, mimeTypes, item)
            val dragShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(data, dragShadowBuilder, it, 0)
            true

        }

        half1.setOnLongClickListener {
            val clipText = ""
            val item = ClipData.Item(clipText)
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(clipText, mimeTypes, item)
            val dragShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(data, dragShadowBuilder, it, 0)
            true

        }

        half3.setOnLongClickListener {
            val clipText = ""
            val item = ClipData.Item(clipText)
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(clipText, mimeTypes, item)
            val dragShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(data, dragShadowBuilder, it, 0)
            true

        }

        right.setOnLongClickListener {
            val clipText = ""
            val item = ClipData.Item(clipText)
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(clipText, mimeTypes, item)
            val dragShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(data, dragShadowBuilder, it, 0)
            true

        }
        left.setOnLongClickListener {
            val clipText = ""
            val item = ClipData.Item(clipText)
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData("", mimeTypes, item)
            val dragShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(data, dragShadowBuilder, it, 0)
            true

        }


    }/////end onCreate


    val dragListener = View.OnDragListener { view, event ->
        when (event.action) {

            DragEvent.ACTION_DRAG_STARTED -> {
                event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                view.invalidate()
                true
            }
            DragEvent.ACTION_DRAG_LOCATION -> true
            DragEvent.ACTION_DRAG_EXITED -> {
                view.invalidate()
                true
            }
            DragEvent.ACTION_DROP -> {

                val item = event.clipData.getItemAt(0)
                val dragData = item.text
                Toast.makeText(this, dragData, Toast.LENGTH_SHORT).show()
                view.invalidate()
                val v = event.localState as View
                val owner = v.parent as ViewGroup
                owner?.removeView(v)
                if (view is GridLayout) {
                    // Add the dropped view to the GridLayout at the drop location
                    val layoutParams = GridLayout.LayoutParams()
                    layoutParams.columnSpec = GridLayout.spec(event.x.toInt())
                    layoutParams.rowSpec = GridLayout.spec(event.y.toInt())
                    layoutParams.width = 250 // set fixed width
                    layoutParams.height = 250 // set fixed height
                    view.addView(v, layoutParams)
                } else if (view is ImageView) {
                    // Add both the ImageView and the dropped view to the GridLayout
                    (view.parent as? ViewGroup)?.removeView(view)
                    ll.addView(view)
                    val layoutParams = GridLayout.LayoutParams()
                    layoutParams.columnSpec = GridLayout.spec(event.x.toInt())
                    layoutParams.rowSpec = GridLayout.spec(event.y.toInt())
                    layoutParams.width = 250 // set fixed width
                    layoutParams.height = 250 // set fixed height
                    ll.addView(v, layoutParams)

                }
                v.visibility = View.VISIBLE
                true
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                view.invalidate()
                true
            }

            else -> false


        }


    }


    private fun takeScreenshot(x: Int, y: Int, w: Int, h: Int) {
        val screenshotBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(screenshotBitmap)
        canvas.translate(-x.toFloat(), -y.toFloat())
        window.decorView.draw(canvas)

        val filename = "screenshot_${System.currentTimeMillis()}.png"
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, filename)
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(
                MediaStore.Images.Media.RELATIVE_PATH,
                "${Environment.DIRECTORY_PICTURES}/Screenshots"
            )
        }
        val contentResolver = contentResolver
        val uri =
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        if (uri != null) {
            val outputStream = contentResolver.openOutputStream(uri)
            screenshotBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream?.flush()
            outputStream?.close()
            Toast.makeText(this, "Sketch saved ", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Unable to save Sketch", Toast.LENGTH_SHORT).show()
        }
    }
}


// ${uri.toString()}