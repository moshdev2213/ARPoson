package com.example.arposon

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.view.isGone
import dev.romainguy.kotlin.math.Float3
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.node.ArModelNode

class ARViewer : AppCompatActivity() {
    private lateinit var sceneView: ArSceneView

    private lateinit var btnExit:Button
    private lateinit var btnPlace:Button
    private lateinit var imageView9:ImageView
    private lateinit var imageView10:ImageView
    private lateinit var imageView11:ImageView
    private lateinit var imageView12:ImageView
    private lateinit var cvHome:CardView

    private val modelNodes: MutableList<ArModelNode> = mutableListOf() // Store multiple model nodes
    private lateinit var gLBPath: String
    private lateinit var modelNode: ArModelNode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arviewer)

        // In Activity's onCreate() for instance this transparents the background
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w: Window = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
//            val flags = window.decorView.systemUiVisibility
//            window.decorView.systemUiVisibility = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        sceneView = findViewById(R.id.sceneViewID)
        cvHome = findViewById(R.id.cvHome)
        btnExit  = findViewById(R.id.btnExit)
        btnPlace  = findViewById(R.id.btnPlace)
        imageView9 = findViewById(R.id.imageView9)
        imageView10 = findViewById(R.id.imageView10)
        imageView11 = findViewById(R.id.imageView11)
        imageView12 = findViewById(R.id.imageView12)

        cvHome.setOnClickListener {
            val intent = Intent(this@ARViewer, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        btnExit.setOnClickListener {
            clearModels()

        }
        btnPlace.setOnClickListener {
            anchorIT()
        }
        imageView9.setOnClickListener {
            setGLBPath("models/FabConvert.com_uploads_files_3925115_vesak+lanten.glb")
            placeModal()
            btnPlace.visibility = View.VISIBLE
        }
        imageView10.setOnClickListener {
            setGLBPath("models/chaithyatemple.glb")
            placeModal()
            btnPlace.visibility = View.VISIBLE
        }
        imageView11.setOnClickListener {
            setGLBPath("models/vesak_lanterns.glb")
            placeModal()
            btnPlace.visibility = View.VISIBLE
        }
        imageView12.setOnClickListener {
            setGLBPath("models/Deer.glb")
            placeModal()
            btnPlace.visibility = View.VISIBLE
        }

    }

    private fun placeModal() {
        modelNode = ArModelNode().apply {
            loadModelGlbAsync(gLBPath) {
                sceneView.planeRenderer.isVisible = true

            }
            onAnchorChanged = {
//                btn.isGone
            }
            position = Float3(0f, 0f, 0f) // Adjust the position as needed
            scale = Float3(0.2f, 0.2f, 0.2f) // Adjust the scale as needed
        }
        sceneView.addChild(modelNode)
        modelNodes.add(modelNode)
        sceneView.planeRenderer.isVisible = false
    }
    private fun setGLBPath(path: String) {
        this.gLBPath = path
    }
    private fun anchorIT(){
        modelNode.anchor()
    }
    private fun clearModels() {
        for (modelNode in modelNodes) {
            sceneView.removeChild(modelNode)
        }
        modelNodes.clear()
    }
}