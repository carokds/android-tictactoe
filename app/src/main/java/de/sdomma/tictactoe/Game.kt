package de.sdomma.tictactoe

class Game {
    private val board: Array<Array<Player?>> = arrayOf(
        arrayOf(null, null, null),
        arrayOf(null, null, null),
        arrayOf(null, null, null)
    )

    private var currentPlayer: Player = Player.ONE

    var gameStatus: GameStatus = GameStatus.RUNNING
        private set

    fun isValidInput(x: Int, y: Int): Boolean =
        x in board.indices && y in board[x].indices && board[x][y] == null

    fun makeMove(x: Int, y: Int) {
        board[x][y] = currentPlayer
    }

    fun checkBoard(board: Array<Array<Player?>>) {
        val resultWin = mutableListOf<List<Player?>>().apply {
            for (i in board.indices) {
                add(listOf(board[i][0], board[i][1], board[i][2]))
                add(listOf(board[0][i], board[1][i], board[2][i]))
            }
            add(listOf(board[0][0], board[1][1], board[2][2]))
            add(listOf(board[0][2], board[1][1], board[2][0]))
        }.any { possibility -> possibility.all { it == currentPlayer } }

        if (resultWin) {
            gameStatus = GameStatus.WON
            return
        }

        val resultDraw = board.flatten().all { it != null }

        if (resultDraw) {
            gameStatus = GameStatus.DRAW
        }
    }
}

enum class Player(val sign: String) {
    ONE("X"), TWO("O")
}

enum class GameStatus {
    RUNNING, WON, DRAW
}