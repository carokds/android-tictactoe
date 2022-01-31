package de.sdomma.tictactoe.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import de.sdomma.tictactoe.Game
import de.sdomma.tictactoe.GameStatus
import de.sdomma.tictactoe.Player
import de.sdomma.tictactoe.R
import de.sdomma.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        const val PLAYER_ONE_COUNTER_KEY = "playerOneCounterKey"
        const val PLAYER_TWO_COUNTER_KEY = "playerTwoCounterKey"
        const val DRAW_COUNTER_KEY = "drawCounterKey"
        const val BOARD_KEY = "boardKey"
        const val CURRENT_PLAYER_KEY = "currentPlayer"
    }

    private lateinit var binding: ActivityMainBinding

    private var myGame = Game()
    private var inputFields: MutableList<ImageView>? = null

    private lateinit var currentPlayerTV: TextView
    private lateinit var winningMessageTV: TextView
    private lateinit var btnReset: Button

    private lateinit var counterOneTV: TextView
    private var counterOne: Int = 0
        set(value) {
            field = value
            counterOneTV.text = value.toString()
        }

    private lateinit var counterTwoTV: TextView
    private var counterTwo: Int = 0
        set(value) {
            field = value
            counterTwoTV.text = value.toString()
        }

    private lateinit var counterDrawTV: TextView
    private var counterDraw: Int = 0
        set(value) {
            field = value
            counterDrawTV.text = value.toString()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        val view = binding.root
        setContentView(view)


        counterOneTV = binding.counterOne
        counterTwoTV = binding.counterTwo
        counterDrawTV = binding.counterDraw
        winningMessageTV = binding.winningMessageTv


        inputFields = mutableListOf(
            binding.input0,
            binding.input1,
            binding.input2,
            binding.input3,
            binding.input4,
            binding.input5,
            binding.input6,
            binding.input7,
            binding.input8
        ).apply {
            this.forEachIndexed { index, iv ->
                iv.setOnClickListener {
                    //  (2 / 3 = 0) (2 % 3 = 2)
                    dothething(iv, index / 3, index % 3)
                }
            }
        }

        binding.currentPlayerTv.text = "Your turn Player ${myGame.currentPlayer}"

        binding.btnReset.setOnClickListener {
            resetGame()
        }

    }

    private fun dothething(
        inputField: ImageView,
        x: Int,
        y: Int
    ) {
        // Game is running AND valid input (Field is not already taken)
//            if (myGame.gameStatus != GameStatus.RUNNING || !myGame.isValidInput(0, 0)) return@setOnClickListener
        if (myGame.gameStatus == GameStatus.RUNNING && myGame.isValidInput(x, y)) {

            // Make move on the gameboard
            myGame.makeMove(x, y)

            // Show move on the UI
            inputField.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    if (myGame.currentPlayer == Player.ONE) {
                        // Set icon for player one
                        R.drawable.ic_player_one
                    } else {
                        // Set icon for player two
                        R.drawable.ic_player_two
                    }, null
                )
            )

            // Checkboard for win or Draw
            myGame.checkBoard(myGame.board)

            if (myGame.gameStatus == GameStatus.WON) {
                binding.winningMessageTv.apply {
                    text = "You Won Player ${myGame.currentPlayer}"
                    visibility = View.VISIBLE
                }

                if (myGame.currentPlayer == Player.ONE) {
                    counterOne++
                } else {
                    counterTwo++
                }
                // Solution 1
//                btnReset.visibility = View.VISIBLE
            }

            if (myGame.gameStatus == GameStatus.DRAW) {
                binding.winningMessageTv.apply {
                    text = "Draw"
                    visibility = View.VISIBLE
                }

                counterDraw++
//                btnReset.visibility = View.VISIBLE
            }

            // Solution 2
//            if (myGame.gameStatus == GameStatus.WON || myGame.gameStatus == GameStatus.DRAW) {
//                btnReset.visibility = View.VISIBLE
//            }

            // Solution 3
//            if (myGame.gameStatus != GameStatus.RUNNING) {
//                btnReset.visibility = View.VISIBLE
//            }

            // Solution 4 - 5
            binding.btnReset.visibility =
                if (myGame.gameStatus != GameStatus.RUNNING) View.VISIBLE else View.INVISIBLE

            // Solution 5 - 3
//            btnReset.isVisible = myGame.gameStatus != GameStatus.RUNNING

            // Switch Player
            myGame.switchPlayer()
            binding.currentPlayerTv.text = "Your turn Player ${myGame.currentPlayer}"
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(PLAYER_ONE_COUNTER_KEY, counterOne)
        outState.putInt(PLAYER_TWO_COUNTER_KEY, counterTwo)
        outState.putInt(DRAW_COUNTER_KEY, counterDraw)

        outState.putSerializable(BOARD_KEY, myGame.board)
        outState.putSerializable(CURRENT_PLAYER_KEY, myGame.currentPlayer)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        counterOne = savedInstanceState.getInt(PLAYER_ONE_COUNTER_KEY)
        counterTwo = savedInstanceState.getInt(PLAYER_TWO_COUNTER_KEY)
        counterDraw = savedInstanceState.getInt(DRAW_COUNTER_KEY)
        myGame.board = savedInstanceState.getSerializable(BOARD_KEY) as Array<Array<Player?>>
        myGame.currentPlayer = savedInstanceState.getSerializable(CURRENT_PLAYER_KEY) as Player
        restoreBoard()

    }

    private fun restoreBoard() {
        myGame.board.flatten().forEachIndexed { position, player ->

            if (player == Player.ONE) {
                // Set icon for player one
                inputFields?.get(position)?.let {
                    it.setImageResource(R.drawable.ic_player_one)
                }
            } else if (player == Player.TWO) {
                // Set icon for player two
                inputFields?.get(position)?.let {
                    it.setImageResource(R.drawable.ic_player_two)
                }
            }
        }
    }


    private fun resetGame() {
        inputFields?.forEach {
            it.setImageDrawable(null)
        }

        myGame = Game()

        binding.btnReset.visibility = View.INVISIBLE
        binding.winningMessageTv.visibility = View.INVISIBLE

        binding.currentPlayerTv.text = "Your turn Player ${myGame.currentPlayer}"
    }


}

