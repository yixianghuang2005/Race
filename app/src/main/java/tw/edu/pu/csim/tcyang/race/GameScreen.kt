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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameScreen(message: String,gameViewModel: GameViewModel) {
    //val imageBitmap = ImageBitmap.imageResource(R.drawable.horse0)

    val imageBitmaps = listOf(
        ImageBitmap.imageResource(R.drawable.horse0),
        ImageBitmap.imageResource(R.drawable.horse1),
        ImageBitmap.imageResource(R.drawable.horse2),
        ImageBitmap.imageResource(R.drawable.horse3)
    )


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
            for( i in 0..2){
                drawImage(
                    image  = imageBitmaps[gameViewModel.horses[i].HorseNumber],
                    dstOffset = IntOffset(gameViewModel.horses[i].HorseX , gameViewModel.horses[i].HorseY),
                    dstSize = IntSize (width = 300,300 )
                )

            }




        }
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            // 1. 顯示作者姓名
            Text(
                text = "賽馬遊戲(作者: ${gameViewModel.playerName})",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black
            )
            // 2. 顯示分數
            /*Text(
                text = "分數: ${gameViewModel.score}",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black,
                modifier = Modifier.padding(top = 4.dp)
            )*/
            // 3. 重新加入原本顯示尺寸的 Text
           /* Text(
                text = message + gameViewModel.screenWidthPx.toString() + "*"
                        + gameViewModel.screenHeightPx.toString(),
                color = Color.Black,
                modifier = Modifier.padding(top = 4.dp)
            )*/
        }
        if (gameViewModel.winningHorseNumber != 0) {
            Text(
                text = "第${gameViewModel.winningHorseNumber}馬獲勝",
                fontSize = 48.sp, // 加大字體
                color = Color.Blue,
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(Color.White.copy(alpha = 0.8f)) // 增加背景以便清晰可見
                    .padding(16.dp)
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) {
            // 只保留「遊戲開始」按鈕
            Button(onClick = {
                gameViewModel.StartGame()
            }, enabled = !gameViewModel.gameRunning){
                // 【修改點 6】按鈕文字顯示
                Text(if (gameViewModel.gameRunning) "比賽進行中..." else "遊戲開始")
            }



        }


    }
}




