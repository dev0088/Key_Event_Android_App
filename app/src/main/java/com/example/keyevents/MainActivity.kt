package com.example.keyevents

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.InputDevice
import android.view.KeyEvent
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    private  lateinit var txtHistories: AppCompatTextView
    private lateinit var btnClear : AppCompatButton
    private lateinit var container: LinearLayout
    private var historyStr =""
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtHistories= findViewById(R.id.txt_histories)
        container = findViewById(R.id.container)

        btnClear = findViewById(R.id.btn_clear)
        txtHistories.setOnKeyListener { _, keyCode, event ->
            var keyCodeHex = String.format("0x%08X",keyCode)
            var color =""
            when(event.action){
                KeyEvent.ACTION_DOWN->{
                    color="00FF00"
                }
                KeyEvent.ACTION_UP->{
                    color ="FF0000"
                }
            }
            historyStr += "<font color= '#$color'>$keyCodeHex</font><br>"
            val sb = StringBuilder()
            sb.append(historyStr)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                txtHistories.setText( Html.fromHtml(sb.toString(),Html.FROM_HTML_MODE_COMPACT))

            }else{
                @Suppress("DEPRECATION")
                txtHistories.setText( Html.fromHtml(sb.toString()))
            }
            txtHistories.movementMethod = LinkMovementMethod.getInstance()
            return@setOnKeyListener true
        }
        txtHistories.setOnTouchListener { _, event ->
            if (event.source == InputDevice.SOURCE_MOUSE){
                var color =""
                when(event.action){
                    KeyEvent.ACTION_DOWN->{
                        color="00FF00"
                    }
                    KeyEvent.ACTION_UP->{
                        color ="FF0000"
                    }
                }
                historyStr += "<font color= '#$color'>${event.buttonState}</font><br>"
                val sb = StringBuilder()
                sb.append(historyStr)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    txtHistories.setText( Html.fromHtml(sb.toString(),Html.FROM_HTML_MODE_COMPACT))

                }else{
                    @Suppress("DEPRECATION")
                    txtHistories.setText( Html.fromHtml(sb.toString()))
                }

                return@setOnTouchListener true

            }
            return@setOnTouchListener false
        }
        btnClear.setOnClickListener {
            historyStr=""
            txtHistories.setText( historyStr)
        }
    }
}