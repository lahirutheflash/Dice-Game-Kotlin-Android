//Video Link : https://drive.google.com/drive/folders/1wv9IU1Hs9ZXE1G59 Pv4ppBsC1bWg1JJO?usp=share_link

package com.example.dicegame

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.PopupMenu
import android.widget.PopupWindow
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newGameButton = findViewById<Button>(R.id.newGamebtn)

        newGameButton.setOnClickListener {
            val intent = Intent(this, DiceScreen::class.java)
            startActivity(intent)

        }

        val aboutButton:Button = findViewById<Button>(R.id.aboutbtn)

        aboutButton.setOnClickListener {
            val alert = AlertDialog.Builder(this)
            val inflater = this.layoutInflater

            val view = inflater.inflate(R.layout.aboutpopup, null)
            alert.setView(view)
            val dialog = alert.create()
            dialog.show()
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }



    }

}
