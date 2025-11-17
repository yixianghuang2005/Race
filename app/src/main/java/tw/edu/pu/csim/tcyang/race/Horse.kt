package tw.edu.pu.csim.tcyang.race

import kotlin.random.Random
class Horse (val horseId: Int, initialY: Int) {
    var HorseX = 0 // 變數宣告
    var HorseY = initialY
    var HorseNumber = 0

    fun Run (){
        //賽馬圖片處理
        HorseNumber ++
        if(HorseNumber > 3 ){
            HorseNumber = 0
        }

        // 使用 kotlin.random.Random
        HorseX += Random.nextInt(10, 31) // 生成 10 到 30 之間的隨機數
    }
}