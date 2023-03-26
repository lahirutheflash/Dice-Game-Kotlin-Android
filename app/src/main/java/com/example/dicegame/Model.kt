package com.example.dicegame

import android.text.Editable
import android.widget.ImageView
import androidx.lifecycle.ViewModel

class Model : ViewModel() {
    var currentSelectedC: MutableList<ImageView> = mutableListOf()
    var currentSelectedH: MutableList<ImageView> = mutableListOf()
    var humanScore: String = "Human - "
    var computerScore: String = "Computer - "
    var targetScore: String = "Target - "

    var rollCount = 0
    var humanRound = 0
    var computerRound = 0
    var humanSum = 0
    var computerSum = 0

    var targetText = 101

}