
function searchplayer(event) {
    var player = document.getElementById('player-search').value;
    var sql_vide = "select * from player;";
    var sql = "select * from player where surname = '"+player+"' or first_name = '"+player+"';";
    if (player == ''){
        //Executer sql_vide
    } else {
        //Executer sql
    }
}

function searchclub(event) {
    var club = document.getElementById('club-search').value;
    var sql_vide = "select * from club;";
    var sql = "select * from club where title = '"+club+"';";
    if (club == ''){
        //Executer sql_vide
    } else {
        //Executer sql
    }
}

