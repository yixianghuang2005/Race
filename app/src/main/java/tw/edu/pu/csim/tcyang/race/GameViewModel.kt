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
    var winningHorseNumber by mutableStateOf(0)
        private set

    val horses = mutableListOf<Horse>()




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

    //val horse = Horse()




    // 設定螢幕寬度與高度
    fun SetGameSize(w: Float, h: Float) {
        screenWidthPx = w
        screenHeightPx = h

        if (horses.isEmpty()){
            for ( i in 0..2){
                // 初始化馬匹時，給予編號 (1, 2, 3) 和初始位置
                horses.add(Horse(i + 1, 100 + 320 * i))
            }
        }
        // 確保在設定大小後，初始位置正確 (如果這是第一次設定)
        if (!gameRunning) {
            ResetHorses()
        }
    }

    fun ResetHorses() {
        for(horse in horses) {
            horse.HorseX = 0
            horse.HorseNumber = 0
        }
    }

    fun StartGame() {
        if (gameRunning) return
        // 回到初使位置
        gameRunning = true
        winningHorseNumber = 0 // 【新增】開始新遊戲時，重置獲勝編號
        ResetHorses() // 【新增】開始新遊戲時，重置馬匹位置

        circleX = 100f
        circleY = screenHeightPx - 100f
        // 遊戲開始時，重設分數
        score = 0

        // 啟動新的 Job，並儲存起來以便停止
        gameLoopJob?.cancel() // 取消任何舊的 Job
        gameLoopJob = viewModelScope.launch {
            val finishLineX = screenWidthPx - 300f
            while (true) { // 每0.1秒循環
                delay(100)
                circleX += 10
                if (circleX >= screenWidthPx - 100f){
                    circleX = 100f
                    score += 1
                }
                if (winningHorseNumber == 0) {
                    var winnerId = 0

                    for(horse in horses){
                        horse.Run()

                        // 判斷馬匹是否抵達終點
                        if(horse.HorseX >= finishLineX){
                            winnerId = horse.horseId
                            break
                        }
                    }

                    if (winnerId != 0) {
                        winningHorseNumber = winnerId // 設置獲勝者ID

                        // 【修改點 4】使用新的 launch 協程處理延遲重置
                        launch {
                            delay(2000) // 顯示獲勝訊息 2 秒
                            winningHorseNumber = 0 // 清除獲勝訊息
                            ResetHorses() // 重置所有馬匹位置，開始下一場比賽
                        }
                    }
                }


            }
        }
    }
    fun MoveCircle(x: Float, y: Float) {
        circleX += x
        circleX = circleX.coerceIn(50f, screenWidthPx - 50f)
        circleY += y
        circleY = circleY.coerceIn(50f, screenHeightPx - 50f)
    }



}