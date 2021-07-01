
var canvas = document.getElementById('canvas');
var ctn = canvas.getContext('2d');


var imgBlack = new Image();
imgBlack.src = '../images/blackchess.png';
var imgWhite = new Image();
imgWhite.src = '../images/whitechess.png';
var qs=qs2obj(location.href)
var detail={};
$.ajax({
    url:'./review',
    traditional:true,
    type:'post',
    dataType:'json',
    data:{id:id},
    success:function (data) {
       detail=data;
    }
});
var chessData = []; //var chessData = new Array(15)
var reg=/(\d{2})/;
var list=detail.split(reg);
init();
function play(e){
    writeChessData();
    review();
}
function qs2obj(url) {
    var qs = url.split("?")[1];
    var arr = [];
    var res = {};
    if(!qs) {
        // return res;
    } else {
        arr = qs.split("&");
        for(var i = 0, len = arr.length; i < len; i++) {
            var key = arr[i].split("=")[0];
            var val = arr[i].split("=")[1];
            res[key] = decodeURIComponent(val);
        }
    }
    return res;
}
function  writeChessData(){
    var bORw=1;
    for(var i=1;i<=list.length-4;i=i+4){
        chessData[parseInt(list[i],10)][parseInt(list[i+2],10)]=bORw;
        if(bORw==1){
            bORw++;
        }
        else{
            bORw--;
        }
    }
}
//初始化棋盘
function init() {
    for (var i = 0; i <= 640; i += 40) {
        //绘制横线
        ctn.beginPath();
        ctn.moveTo(0, i);
        ctn.lineTo(640, i);
        ctn.closePath();
        ctn.stroke();
        //绘制竖线
        ctn.beginPath();
        ctn.moveTo(i, 0);
        ctn.lineTo(i, 640);
        ctn.closePath();
        ctn.stroke();
    }
    for (var x = 0; x < 15; x++) {
        chessData[x] = [];
        for (var y = 0; y < 15; y++) {
            chessData[x][y] = 0;
        }
    }
    console.log(chessData);
}
//绘制单个棋子
function drawChess(chess, x, y) {

    if (x >= 0 && x < 15 && y >= 0 && y < 15) {
        if (chess == 1) {
            ctn.drawImage(imgWhite, x * 40 + 20, y * 40 + 20);
            // chessData[x][y] = 1;
        } else if(chess==2){
            ctn.drawImage(imgBlack, x * 40 + 20, y * 40 + 20);
            // chessData[x][y] = 2;
        }
    }
}
// function drawChess(chess, x, y) {
//     switch (chess) {
//         case 1:ctn.drawImage(imgWhite,x,y);break;
//         case 2:ctn.drawImage(imgBlack,x,y);break;
//     }
//
// }

function review() {
    for (var i = 0;i<14;i++){
        for (var j=0;j<14;j++)
        {
            drawChess(chessData[i][j],i,j);
            console.log(chessData[i][j]);
        }
    }
}
