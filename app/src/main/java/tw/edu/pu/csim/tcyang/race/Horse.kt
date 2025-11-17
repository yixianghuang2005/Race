package tw.edu.pu.csim.tcyang.race

class Horse (n : Int) {
    var HorseX = 0
    var HorseY = 100 + 320 * n
    var HorseNumber = 0

    fun Run (){
        //賽馬圖片處理
        HorseNumber ++
        if(HorseNumber > 3 ){
            HorseNumber = 0
        }

        HorseX += (10 ..30).random()
    }
 }