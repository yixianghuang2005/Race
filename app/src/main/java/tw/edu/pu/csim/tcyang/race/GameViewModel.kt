package tw.edu.pu.csim.tcyang.race

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameViewModel : ViewModel(){

    var screenWidthPx by mutableStateOf(0f)
        private set

    var screenHeightPx by mutableStateOf(0f)
        private set

    var gameRunning by mutableStateOf(false)

    var circleX by mutableStateOf(0f)

    var circleY by mutableStateOf(0f)

    var playerName by mutableStateOf("黃義祥")
        private set

    private var gameLoopJob: Job? = null



    var score by mutableStateOf(0)
        private set



    // 設定螢幕寬度與高度
    fun SetGameSize(w: Float, h: Float) {
        screenWidthPx = w
        screenHeightPx = h
    }

    fun StartGame() {
        gameRunning = true
        // 回到初使位置
        circleX = 100f
        circleY = screenHeightPx - 100f
        // 遊戲開始時，重設分數
        score = 0

        // 啟動新的 Job，並儲存起來以便停止
        gameLoopJob?.cancel() // 取消任何舊的 Job
        gameLoopJob = viewModelScope.launch {
            while (gameRunning) { // 每0.1秒循環
                delay(100)
                circleX += 10

                // 處理邊界碰撞
                if (circleX >= screenWidthPx - 100f){
                    circleX = 100f
                    // 需求：碰到右邊邊界,分數+1
                    score += 1
                }
            }
        }
    }
    fun MoveCircle(x: Float, y: Float) {
        circleX += x
        circleY += y
    }



}