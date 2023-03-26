package com.example.dicegame

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import java.util.Random

class DiceScreen : AppCompatActivity() {

    // ImageViews for human's dice
    private lateinit var humanImg1: ImageView
    private lateinit var humanImg2: ImageView
    private lateinit var humanImg3: ImageView
    private lateinit var humanImg4: ImageView
    private lateinit var humanImg5: ImageView

    // ImageViews for computer's dice
    private lateinit var computerImg1: ImageView
    private lateinit var computerImg2: ImageView
    private lateinit var computerImg3: ImageView
    private lateinit var computerImg4: ImageView
    private lateinit var computerImg5: ImageView

    // Radiobuttons for human's dice
    private lateinit var rbtnH1: RadioButton
    private lateinit var rbtnH2: RadioButton
    private lateinit var rbtnH3: RadioButton
    private lateinit var rbtnH4: RadioButton
    private lateinit var rbtnH5: RadioButton

    // Radiobuttons for computer's dice
    private lateinit var rbtnC1: RadioButton
    private lateinit var rbtnC2: RadioButton
    private lateinit var rbtnC3: RadioButton
    private lateinit var rbtnC4: RadioButton
    private lateinit var rbtnC5: RadioButton

    // Counters for the current round
    private var rollCount = 0
    private var humanRound = 0
    private  var computerRound = 0


    private var humanSum = 0
    private var computerSum = 0

    var humanWins = 0
    var computerWins = 0



    // Editable field for the target score
    lateinit var targetText: Editable

    // TextViews for displaying scores
    private lateinit var humanScore: TextView
    private lateinit var computerScore: TextView
    private lateinit var targetScore: TextView

    // Buttons for throwing the dice and scoring
    private lateinit var btnRoll: Button
    private lateinit var btnScore: Button

    // Define maps of ImageViews and RadioButtons for human and computer dices
    private var humanDices : Map<ImageView, RadioButton> = mutableMapOf()
    private var computerDices : Map<ImageView, RadioButton> = mutableMapOf()


    // Define lists to keep track of the current dice and selected dice for each player
    private val currentDiceH = mutableListOf<ImageView>()
    private val currentDiceC = mutableListOf<ImageView>()
    private var currentSelectedH= mutableListOf<ImageView>()
    private var currentSelectedC= mutableListOf<ImageView>()

    // Late-initialize the ViewModel
    private lateinit var viewModel : Model


    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dice_screen)

        viewModel =ViewModelProvider(this).get(Model::class.java)


        // Human dice ImageViews
        humanImg1 = findViewById<ImageView>(R.id.hdice1)
        humanImg2 = findViewById<ImageView>(R.id.hdice2)
        humanImg3 = findViewById<ImageView>(R.id.hdice3)
        humanImg4 = findViewById<ImageView>(R.id.hdice4)
        humanImg5 = findViewById<ImageView>(R.id.hdice5)

        // Computer dice ImageViews
        computerImg1=findViewById<ImageView>(R.id.cdice1)
        computerImg2=findViewById<ImageView>(R.id.cdice2)
        computerImg3=findViewById<ImageView>(R.id.cdice3)
        computerImg4=findViewById<ImageView>(R.id.cdice4)
        computerImg5=findViewById<ImageView>(R.id.cdice5)

        // Human dice RadioButtons
        rbtnH1 = findViewById<RadioButton>(R.id.rbtnH1)
        rbtnH2 = findViewById<RadioButton>(R.id.rbtnH2)
        rbtnH3 = findViewById<RadioButton>(R.id.rbtnH3)
        rbtnH4 = findViewById<RadioButton>(R.id.rbtnH4)
        rbtnH5 = findViewById<RadioButton>(R.id.rbtnH5)

        // Computer dice RadioButtons
        rbtnC1 = findViewById<RadioButton>(R.id.rbtnC1)
        rbtnC2 = findViewById<RadioButton>(R.id.rbtnC2)
        rbtnC3 = findViewById<RadioButton>(R.id.rbtnC3)
        rbtnC4 = findViewById<RadioButton>(R.id.rbtnC4)
        rbtnC5 = findViewById<RadioButton>(R.id.rbtnC5)


        // Dice images for human and computer
        val diceImageH1 = ImageView(this)
        val diceImageH2 = ImageView(this)
        val diceImageH3 = ImageView(this)
        val diceImageH4 = ImageView(this)
        val diceImageH5 = ImageView(this)
        val diceImageH6 = ImageView(this)

        val diceImageC1 = ImageView(this)
        val diceImageC2 = ImageView(this)
        val diceImageC3 = ImageView(this)
        val diceImageC4 = ImageView(this)
        val diceImageC5 = ImageView(this)
        val diceImageC6 = ImageView(this)


        // Set dice images for human and computer ImageViews
        diceImageH1.setImageResource(R.drawable.dice1)
        diceImageH2.setImageResource(R.drawable.dice2)
        diceImageH3.setImageResource(R.drawable.dice3)
        diceImageH4.setImageResource(R.drawable.dice4)
        diceImageH5.setImageResource(R.drawable.dice5)
        diceImageH6.setImageResource(R.drawable.dice6)


        diceImageC1.setImageResource(R.drawable.dice1)
        diceImageC2.setImageResource(R.drawable.dice2)
        diceImageC3.setImageResource(R.drawable.dice3)
        diceImageC4.setImageResource(R.drawable.dice4)
        diceImageC5.setImageResource(R.drawable.dice5)
        diceImageC6.setImageResource(R.drawable.dice6)


        // Create and set tags for dice images
        diceImageH1.tag = 1
        diceImageH2.tag = 2
        diceImageH3.tag = 3
        diceImageH4.tag = 4
        diceImageH5.tag = 5
        diceImageH6.tag = 6

        diceImageC1.tag = 1
        diceImageC2.tag = 2
        diceImageC3.tag = 3
        diceImageC4.tag = 4
        diceImageC5.tag = 5
        diceImageC6.tag = 6

        // Add dice images to lists
        currentDiceH.addAll(listOf(diceImageH1,diceImageH2,diceImageH3,diceImageH4,diceImageH5,diceImageH6))
        currentDiceC.addAll(listOf(diceImageC1,diceImageC2,diceImageC3,diceImageC4,diceImageC5,diceImageC6))

        humanScore = findViewById<TextView>(R.id.humanScore)
        computerScore = findViewById<TextView>(R.id.computerScore)
        targetScore = findViewById<TextView>(R.id.targetScore)

        btnRoll= findViewById<Button>(R.id.btnRoll)
        btnScore= findViewById<Button>(R.id.btnScore)

        val rbtns = arrayOf(
            rbtnH1,rbtnH2,rbtnH3,rbtnH4,rbtnH5,
            rbtnC1,rbtnC2,rbtnC3,rbtnC4,rbtnC5)

        val rbtnsState = BooleanArray(rbtns.size)


        // Create an AlertDialog Builder object with the current context
        val targetSet = AlertDialog.Builder(this)
        targetSet.setTitle("Set the Target")


        val inputScore = EditText(this)

        // Set the input type of the EditText to number
        inputScore.inputType = InputType.TYPE_CLASS_NUMBER
        inputScore.setText("101")
        targetSet.setView(inputScore)

        targetSet.setPositiveButton("OK")
        {dialogBox, which ->
            targetText =inputScore.text
            targetScore.text = "Target - $targetText"

           viewModel.targetScore = "${viewModel.targetText}"
        }

        // When the button is clicked, cancel the dialog box
        targetSet.setNegativeButton("Cancel")
        {dialogBox, which -> dialogBox.cancel() }

        val window = targetSet.create()
        window.show()

        // Iterate through radio buttons array
        for (i in rbtns.indices){
            val rBtn = rbtns[i]
            rBtn.isChecked = rbtnsState[i]

            // Set click listener for radio button
            rBtn.setOnClickListener{

                rbtnsState[i] = !rbtnsState[i]
                rBtn.isChecked = rbtnsState[i]
            }
        }

        // Create maps for human and computer dices
        humanDices = mutableMapOf(
            humanImg1 to rbtnH1,
            humanImg2 to rbtnH2,
            humanImg3 to rbtnH3,
            humanImg4 to rbtnH4,
            humanImg5 to rbtnH5,)

        computerDices = mutableMapOf(
            computerImg1 to rbtnC1,
            computerImg2 to rbtnC2,
            computerImg3 to rbtnC3,
            computerImg4 to rbtnC4,
            computerImg5 to rbtnC5,
        )



//        btnRoll.setOnClickListener {
//            diceRoll(currentDiceH,humanDices,currentSelectedH)
//            diceRoll(currentDiceC,computerDices,currentSelectedC)
//            rollCount++
//
//            when(rollCount){
//                1 -> {
//                    btnRoll.text = "Reroll(2)"
//                }
//
//                2 -> {
//                    btnRoll.text = "Reroll(1)"
//                }
//
//                else -> {
//                    btnRoll.isEnabled =  false
//                    btnScore.isPressed = true
//                    btnScore.performClick()
//                    btnRoll.text = "Throw"
//
//                    Handler(Looper.getMainLooper()).postDelayed({
//                        btnRoll.text = "Throw"
//                        rollCount = 0
//                        btnRoll.isEnabled = true
//                        btnScore.isPressed = false },
//                        1000)
//                    btnRoll.text = "Throw(0)"
//                    }
//                }
//            }


        // Set the OnClickListener for the Roll button
        btnRoll.setOnClickListener {

            rollCount++
            btnScore.isEnabled = true

            // Roll the human dices
            diceRoll(currentDiceH, humanDices, currentSelectedH)

            // If the human has reached the target score and their score equals the computer's score
            if (humanSum >= targetText.toString().toInt() && humanSum == computerSum) {
                rollCount = 4
                diceRoll(currentDiceC, computerDices, currentSelectedC)
                btnRoll.isEnabled = false
                btnScore.isPressed = true
                btnScore.performClick()
                btnRoll.text = "Throw"

                // Set the Roll button text to "Throw" and delay the execution of the following code by 1 second
                Handler(Looper.getMainLooper()).postDelayed(
                    {
                        // Reset the Roll button text, roll count, and enable the Roll button
                        btnRoll.text = "Throw"
                        rollCount = 0
                        btnRoll.isEnabled = true
                        btnScore.isPressed = false
                    },
                    1000
                )
                // Set the Roll button text to "Throw(0)"
                btnRoll.text = "Throw(0)"


            } else {

                when (rollCount) {
                    1 -> {
                       diceRoll(currentDiceC,computerDices,currentSelectedC)
                        btnRoll.text = "Reroll(2)"
                    }

                    2 -> {
                        randomStratergy()
                        btnRoll.text = "Reroll(1)"
                    }

                    3 -> {
                        randomStratergy()
                        btnRoll.isEnabled = false
                        btnScore.isPressed = true
                        btnScore.performClick()
                        btnRoll.text = "Throw"

                        Handler(Looper.getMainLooper()).postDelayed({
                            btnRoll.text = "Throw"
                            rollCount= 0
                            btnRoll.isEnabled = true
                            btnScore.isPressed= false
                        },1000)
                        btnRoll.text = "Reroll (0)"
                    }
                }
            }
        }

        btnScore.setOnClickListener {

            rollCount = 0
            btnRoll.isEnabled = false

            Handler(Looper.getMainLooper()).postDelayed({
                btnRoll.text = "Throw"
                btnRoll.isEnabled= true
                reset()

                val alert = AlertDialog.Builder(this)
                val inflater = this.layoutInflater


                // determine if the human or computer won
                var isWon : Boolean = true

                if (computerSum >= targetText.toString().toInt() && computerSum > humanSum){
                    isWon = false
                    computerWins++
                    val view = inflater.inflate(R.layout.youlose, null)
                    alert.setView(view)
                    val dialog = alert.create()
                    dialog.show()
                    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    val wonOkButton : Button = view.findViewById(R.id.loserButton)
                    wonOkButton.setOnClickListener {
                        finish()
                        if (dialog != null && dialog.isShowing) {
                            dialog.dismiss()
                        }
                    }
            }
                else if (humanSum >= targetText.toString().toInt() && humanSum > computerSum){
                    isWon = true
                    humanWins++
                    val view = inflater.inflate(R.layout.youwon, null)
                    alert.setView(view)
                    val dialog = alert.create()
                    dialog.show()
                    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    val wonOkButton : Button = view.findViewById(R.id.winnerButton)
                    wonOkButton.setOnClickListener {
                        finish()
                        if (dialog != null && dialog.isShowing) {
                            dialog.dismiss()
                        }
                    }
                }

                // reset the round scores and current selections
                humanRound = 0
                computerRound = 0
                currentSelectedC.clear()
                currentSelectedH.clear()},
                1000)
            }
        }

    //responsible for rolling the dice and changing their images
    //It iterates through the computer's selected dice, and if a radio button is checked, it skips that dice
    private fun diceRoll(diceImgs: List<ImageView>, computerDices: Map<ImageView, RadioButton>, diceSet: MutableList <ImageView>){
        var counter = 0

        for ((dice, rBtn) in computerDices){
            if (rBtn.isChecked){
                counter++

                //Image won't change if the radio button is pressed
                continue
            }


            if (diceSet.size ==5){
                diceSet.removeAt(counter)
            }

            val randomImgs = diceImgs.random()
            val drawable = randomImgs.drawable
            dice.setImageDrawable(drawable)
            diceSet.add(counter, randomImgs)
            counter++
        }
    }




    private fun totalDice(diceImgs: List<ImageView>): Int{
        var total = 0
        for (imgView in diceImgs){
            val tag = imgView.tag
            if (tag!= null){
                 total+=tag.toString().toInt()
            }
        }

        return total

        }



    @SuppressLint("SetTextI18n")
    private fun reset(){
        computerRound = totalDice(currentSelectedC)
        humanRound = totalDice(currentSelectedH)

        viewModel.computerRound = totalDice(currentSelectedC)
        viewModel.humanRound = totalDice(currentSelectedH)

        computerSum += computerRound
        humanSum += humanRound

        viewModel.computerSum += computerRound
        viewModel.humanSum += humanRound

        humanScore.text = "Human - $humanSum"
        computerScore.text = "Computer - $computerSum"

        humanScore.text = "Human - ${viewModel.humanSum}"
        computerScore.text = "Computer - ${viewModel.computerSum}"





        val drawableIdCom = resources.getIdentifier("dice0", "drawable", packageName)
        val comHolderDrawable = resources.getDrawable(drawableIdCom, null)

        val drawableIdHum = resources.getIdentifier("dice0", "drawable", packageName)
        val humHolderDrawable = resources.getDrawable(drawableIdHum, null)

        for ((dice, rBtn) in humanDices) {
            dice.setImageDrawable(comHolderDrawable)
            rBtn.isChecked = false
        }

        for ((dice, rBtn) in computerDices) {
            dice.setImageDrawable(humHolderDrawable)
            rBtn.isChecked = false
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // Save the current values of the ViewModel's properties to the Bundle
        outState.putString("humanRoundScore", viewModel.humanScore)
        outState.putString("computerRoundScore", viewModel.computerScore)
        outState.putString("targetScore", viewModel.targetScore)

        outState.putInt("throwCounter", viewModel.rollCount)
        outState.putInt("roundSumHuman", viewModel.humanRound)
        outState.putInt("roundSumComputer", viewModel.computerRound)
        outState.putInt("totalSumHuman", viewModel.humanSum)
        outState.putInt("totalSumComputer", viewModel.computerSum)

        outState.putInt("target", viewModel.targetText)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        // Restore the values of the ViewModel's properties from the Bundle
        viewModel.humanScore = savedInstanceState.getString("humanRoundScore", "Human - ")
        viewModel.computerScore = savedInstanceState.getString("computerRoundScore", "Computer - ")
        viewModel.targetScore = savedInstanceState.getString("targetScore", "Target - ")

        rollCount = savedInstanceState.getInt("throwCounter")
        humanRound = savedInstanceState.getInt("roundSumHuman")
        computerRound = savedInstanceState.getInt("roundSumComputer")
        humanSum = savedInstanceState.getInt("totalSumHuman")
        computerSum = savedInstanceState.getInt("totalSumComputer")

        // Restore the value of the target score EditText
        val savedTarget = savedInstanceState.getInt("target").toString()
        targetText = savedTarget.toEditable()

        // Update the UI with the restored values
        humanScore.text = humanSum.toString()
        computerScore.text = computerSum.toString()
        targetScore.text = targetText
    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)




    private fun randomStratergy(){
        if (isReroll()){
            val set = generateRandomNum()
            for (number in set){
                computerDices.values.toList()[number].isChecked = true
            }
            diceRoll(currentDiceC, computerDices, currentSelectedC)
        }
    }

//    If the "isReroll" condition is true, the function generates a set of random numbers using the "generateRandomNum" function, and then iterates over each number in the set.
//    For each number, the function accesses the corresponding value from map "computerDices" using the "values.toList()[number]" expression.The purpose of this step is to mark a
//    particular dice as selected for re-rolling.
//    The function calls the "diceRoll" function, passing in several arguments including "currentDiceC", the "computerDices", and "currentSelectedC"
//    The "diceRoll" function rolls the selected dice again and updates the game state accordingly.
//    The strategy implemented here randomly select some of the computer's dice to re-roll, without any consideration for the current game
//    state or the player's own dice.






    private fun isReroll(): Boolean{
        return kotlin.random.Random.nextBoolean()
    }

    private fun generateRandomNum(): Set<Int>{
        val set = mutableSetOf<Int>()
        val size = kotlin.random.Random.nextInt(1,5)
        while (set.size < size){
            val randNum = kotlin.random.Random.nextInt(size+1)
            set.add(randNum)
        }
        return set
    }



}

//REFERENCES

//https://johncodeos.com/how-to-create-a-popup-window-in-android-using-kotlin/
//https://www.youtube.com/watch?v=BCSlZIUj18Y
//https://www.youtube.com/watch?v=Sf7nRdRe7sQ

