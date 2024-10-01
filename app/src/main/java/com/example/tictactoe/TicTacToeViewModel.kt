import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TicTacToeViewModel : ViewModel() {
    // 게임 보드 상태를 저장하는 LiveData
    private val _board = MutableLiveData<Array<IntArray>>(Array(3) { IntArray(3) { 0 } })
    val board: LiveData<Array<IntArray>> get() = _board

    // 현재 플레이어 상태 (1 = X, 2 = O)
    private val _currentPlayer = MutableLiveData(1)
    val currentPlayer: LiveData<Int> get() = _currentPlayer

    // 승리자 상태 (null = 없음, 1 = X 승리, 2 = O 승리, 0 = 무승부)
    private val _winner = MutableLiveData<Int?>(null)
    val winner: LiveData<Int?> get() = _winner

    private val boardHistory = mutableListOf<Array<IntArray>>()
    private val playerHistory = mutableListOf<Int>()

    init {
        resetBoard()
    }

    // 플레이어가 수를 둘 때 호출되는 함수
    fun makeMove(row: Int, col: Int) {
        val currentBoard = _board.value
        currentBoard?.let {
            if (it[row][col] == 0 && _winner.value == null) {
                it[row][col] = _currentPlayer.value ?: 1
                _board.value = it // 보드 상태 업데이트

                saveCurrentBoardState() // 보드 상태 저장
                
                // 승리 조건 확인
                if (checkWinner()) {
                    _winner.value = _currentPlayer.value // 현재 플레이어가 승리
                } else if (isBoardFull()) {
                    _winner.value = 0 // 무승부
                } else {
                    switchPlayer() // 플레이어 변경
                }
            }
        }
    }

    // 플레이어를 변경하는 함수
    private fun switchPlayer() {
        _currentPlayer.value = if (_currentPlayer.value == 1) 2 else 1
    }

    // 게임을 리셋하는 함수
    fun resetBoard() {
        _board.value = Array(3) { IntArray(3) { 0 } }
        _currentPlayer.value = 1
        _winner.value = null
        boardHistory.clear()
        playerHistory.clear()
    }

    // 승리 조건 확인 함수
    private fun checkWinner(): Boolean {
        val b = _board.value!!
        // 가로, 세로 확인
        for (i in 0..2) {
            if (b[i][0] != 0 && b[i][0] == b[i][1] && b[i][1] == b[i][2]) return true
            if (b[0][i] != 0 && b[0][i] == b[1][i] && b[1][i] == b[2][i]) return true
        }
        // 대각선 확인
        if (b[0][0] != 0 && b[0][0] == b[1][1] && b[1][1] == b[2][2]) return true
        if (b[0][2] != 0 && b[0][2] == b[1][1] && b[1][1] == b[2][0]) return true
        return false
    }

    // 보드가 가득 찼는지 확인하는 함수
    private fun isBoardFull(): Boolean {
        return _board.value!!.all { row -> row.all { cell -> cell != 0 } }
    }

    // 되돌아가기 함수
    fun undoToTurn(turnIndex: Int) {
        if (turnIndex in boardHistory.indices) {
            _board.value = boardHistory[turnIndex]
            _currentPlayer.value = playerHistory[turnIndex]

            boardHistory.subList(turnIndex + 1, boardHistory.size).clear()
            playerHistory.subList(turnIndex + 1, playerHistory.size).clear()

            _winner.value = null
        }
    }
    
    // 보드 상태를 복사하는 함수
    private fun saveCurrentBoardState() {
        val currentBoard = _board.value ?: return
        val boardCopy = Array(3) { IntArray(3) { 0 } }
        for (i in 0..2) {
            for (j in 0..2) {
                boardCopy[i][j] = currentBoard[i][j]
            }
        }
        boardHistory.add(boardCopy)
        playerHistory.add(_currentPlayer.value ?: 1)

        Log.d("TicTacToe", "Board history updated: $boardHistory")
    }


    fun getBoardState(): Array<IntArray> {
        return _board.value ?: Array(3) { IntArray(3) { 0 } }
    }

    fun getBoardStateHistory(): List<Array<IntArray>> {
        return boardHistory
    }
}
