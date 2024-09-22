package com.example.tictactoe

import TicTacToeViewModel
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: TicTacToeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.board.observe(this, Observer { board ->
                updateBoardUI(board)
        })
        viewModel.currentPlayer.observe(this, Observer { currentPlayer ->
                updateCurrentPlayerUI(currentPlayer)
        })
        viewModel.winner.observe(this, Observer { winner ->
            if (winner != null) {
                showWinnerMessage(winner)
            }
        })

        // 각 버튼에 클릭 리스너 설정
        val buttons = listOf(
                listOf(binding.button00, binding.button01, binding.button02),
                listOf(binding.button10, binding.button11, binding.button12),
                listOf(binding.button20, binding.button21, binding.button22)
        )

        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].setOnClickListener {
                    viewModel.makeMove(i, j)
                }
            }
        }

        // 리셋 버튼 클릭 리스너
        binding.resetButton.setOnClickListener {
            if (viewModel.winner.value != null) {
                // 게임이 끝났을 때 '한판 더' 클릭 시
                viewModel.resetBoard()
                binding.resetButton.text = "초기화" // 리셋 버튼 텍스트 변경
                enableButtons() // 버튼 활성화
            } else {
                // 게임 중일 때 리셋 로직
                viewModel.resetBoard()
                enableButtons() // 버튼 활성화
            }
        }
    }

    // 보드 UI를 업데이트하는 함수
    private fun updateBoardUI(board: Array<IntArray>) {
        val buttons = listOf(
                listOf(binding.button00, binding.button01, binding.button02),
                listOf(binding.button10, binding.button11, binding.button12),
                listOf(binding.button20, binding.button21, binding.button22)
        )

        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].text = when (board[i][j]) {
                    1 -> "X"
                    2 -> "O"
                    else -> ""
                }
                buttons[i][j].isEnabled = viewModel.winner.value == null
            }
        }
    }

    // 현재 플레이어 UI를 업데이트하는 함수
    private fun updateCurrentPlayerUI(currentPlayer: Int) {
        binding.currentPlayerText.text = "현재 플레이어: ${if (currentPlayer == 1) "X" else "O"}"
    }

    private fun showWinnerMessage(winner: Int?) {
        val message = when (winner) {
            1 -> "X 승리!"
            2 -> "O 승리!"
            0 -> "무승부!"
            else -> return
        }
        binding.currentPlayerText.text = message
        disableButtons() // 게임이 끝나면 버튼 비활성화
        binding.resetButton.text = "한판 더"
    }

    private fun disableButtons() {
        val buttons = listOf(
            listOf(binding.button00, binding.button01, binding.button02),
            listOf(binding.button10, binding.button11, binding.button12),
            listOf(binding.button20, binding.button21, binding.button22)
        )

        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].isEnabled = false
            }
        }
    }

    private fun enableButtons() {
        val buttons = listOf(
            listOf(binding.button00, binding.button01, binding.button02),
            listOf(binding.button10, binding.button11, binding.button12),
            listOf(binding.button20, binding.button21, binding.button22)
        )

        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].isEnabled = true
            }
        }
    }
}
