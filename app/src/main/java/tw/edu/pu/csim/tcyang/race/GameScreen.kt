package tw.edu.pu.csim.tcyang.race

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun GameScreen(message: String,gameViewModel: GameViewModel) {

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Yellow)
    ){
        Canvas (modifier = Modifier.fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume() // 告訴系統已經處理了這個事件
                    gameViewModel.MoveCircle( dragAmount.x, dragAmount.y)
                }
            }

        ) {
            drawCircle(
                color = Color.Red,
                radius = 100f,
                center = Offset(gameViewModel.circleX, gameViewModel.circleY)
            )


        }
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            // 1. 顯示作者姓名
            Text(
                text = "作者: ${gameViewModel.playerName}",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black
            )
            // 2. 顯示分數
            Text(
                text = "分數: ${gameViewModel.score}",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black,
                modifier = Modifier.padding(top = 4.dp)
            )
            // 3. 重新加入原本顯示尺寸的 Text
            Text(
                text = message + gameViewModel.screenWidthPx.toString() + "*"
                        + gameViewModel.screenHeightPx.toString(),
                color = Color.Black,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) {
            // 只保留「遊戲開始」按鈕
            Button(onClick = {
                if (!gameViewModel.gameRunning) {
                    gameViewModel.StartGame()
                }
            }, enabled = !gameViewModel.gameRunning){
                Text("遊戲開始")
            }

        }


    }
}




