<template>
  <div>
    <div v-if="isSignedIn">
      <!-- ///////////////////////////////////////////////////////////////// -->
        <div class="my_card">
          <div class="titre">Statistiques globales</div>

          <hr>

          <div class="para1">Nom</div>
          <div class="para1">Prénom</div>
          <div class="para1">Nombre de but(s)</div>
          <div class="para1">Nombre de but(s) encaissé(s)</div>
          <div class="para1">Nombre de match joué(s)</div>
          <div class="para1">Nombre de victoire(s)</div>

          <br>

          <div class="para2">{{ playerToShow.surname }}</div>
          <div class="para2">{{ playerToShow.first_name }}</div>
          <div class="para2">{{ playerToShow.scored_goals }}</div>
          <div class="para2">{{ playerToShow.conceded_goals }}</div>
          <div class="para2">{{ playerToShow.matches_played }}</div>
          <div class="para2">{{ playerToShow.victories }}</div>

        </div>
      <!-- ///////////////////////////////////////////////////////////////// -->
      <div class="my_card">
        <div class="titre">Statistiques de vos derniers matchs</div>

        <hr><br>

        <div v-if="playerStatToShow.length != 0">
          <div class="para3">Date</div>
          <div class="para3">Lieux</div>
          <div class="para3">Nombre de but(s)</div>
          <div class="para3">Nombre de but(s) encaissé(s)</div>
          <div class="para3">Résultat du match</div>

          <div v-for="row in playerStatToShowPage" :key="row.id">
            <hr>

            <div class="para4">{{ new Date(row.precise_date).toLocaleDateString('fr-FR') }} ({{ new Date(row.precise_date).toLocaleTimeString('fr-FR') }})</div>
            <div class="para4">{{ row.location }}</div>
            <div class="para4">{{ row.scored_goals }}</div>
            <div class="para4">{{ row.conceded_goals }}</div>
            <div v-if="row.won" class="para4">Victoire</div>
            <div v-else class="para4">Défaite</div>
          </div>

          <br>

          <div class="pages">
            <div class="numeric_icon" v-on:click="page_left()"><v-icon color="black">mdi-chevron-left</v-icon></div>
            <!-- <div class="numeric_icon" v-if="this.playerStatPage > "><v-icon color="black">mdi-dots-horizontal</v-icon></div> -->
            <div style="display: inline-block;" v-for="index in this.playerStatPageMax" :key="index">
              <div class="numeric_icon" v-on:click="page(index)">{{ index }}</div>
            </div>
            <div class="numeric_icon" v-on:click="page_right()"><v-icon color="black">mdi-chevron-right</v-icon></div>
          </div>
        </div>

        <div v-else>
          <hr>
          <div class="para1">Vous n'avez pas encore joué de match</div>
        </div>
      </div>
      <!-- ///////////////////////////////////////////////////////////////// -->
    </div>
    <WorkInProgress v-else></WorkInProgress>
  </div>
</template>

<style>
  .pages{
    text-align: center;
  }
  .numeric_icon{
    margin: 2px;
    display: inline-block;
    background-color: white;
    color: black;
    border-radius: 5px;
    width: 30px;
    height: 30px;
  }
  .my_card{
    margin-top: 1%;
    margin-left: 2%;
    margin-right: 2%;
    margin-bottom: 1%;
    background-color: black;
    border-radius: 5px;
    box-shadow: 0px 4px 8px #AAA;
  }
  .titre{
    text-align: center;
    color: white;
    font-size: 200%;
    margin: 5px;
  }
  .para1, .para2, .para3, .para4{
    display: inline-block;
    width: 16%;
    overflow: hidden;
    text-overflow: ellipsis;
    margin-top: 5px;
    margin-left: 5px;
  }
  .para1, .para2{
    width: 16%;
  }
  .para3, .para4{
    width: 19%;
  }
  .para1, .para3{
    font-weight: bold;
    color: white;
  }
  .para2, .para4{
    color: #CCC;
    font-size: 120%;
  }
</style>

<script>
import store from "../store";
import axios from "axios";
import router from "../router";
import WorkInProgress from "./WorkInProgress";

export default {
  components: {
    WorkInProgress
  },
  data() {
    return {
      playerToShow: {},
      playerStatToShow: {},
      playerStatNbPageMenu: 5, // si nombre plus grand que 4, utilisation des ..., ex les btn du bas : " < 1 ... 5 6 7 8 9 ... 18 > "
      playerStatPage: 1,
      playerStatPageMax: 1,
      playerStatNbRowPerPage: 5,
      playerStatNbRow: 0,
      playerStatToShowPage: [],
    };
  },
  async created() {
    const player = await axios
      .get(
        "https://dbcontrol.quickmatch.fr/dbcontrol/api/v1/players/id" + this.id,
        {
          responseType: "json"
        }
      )
      .catch(e => {
        if (this.isSignedIn()){
          alert("Une erreur s'est produite, nous allons rafraichir la page, si le problème persiste, quittez la page");
          this.$router.go();
        }
      });
    this.playerToShow = player.data;
    const playerStat = await axios
      .get(
        "https://dbcontrol.quickmatch.fr/dbcontrol/api/v1/players/stat" + this.id,
        {
          responseType: "json"
        }
      )
      .catch(e => {
        if (this.isSignedIn()){
          alert("Une erreur s'est produite, nous allons rafraichir la page, si le problème persiste, quittez la page");
          this.$router.go();
        }
      });
    this.playerStatToShow = playerStat.data.rows;
    this.playerStatNbRow = playerStat.data.rowCount;
    this.playerStatPageMax = Math.floor((playerStat.data.rowCount -1) / this.playerStatNbRowPerPage) + 1;
    for (let i = 0; i < Math.min(this.playerStatNbRowPerPage, this.playerStatNbRow); i++){
      this.playerStatToShowPage.push(this.playerStatToShow[i]);
    }
  },
  computed: {
    isSignedIn() {
      store.dispatch("isSignedIn");
      return store.getters.isSignedIn;
    },
    id() {
      store.dispatch("setID");
      return store.getters.id;
    }
  },
  methods: {
    page_left(){
      if (this.playerStatPage != 1){
        this.page(this.playerStatPage - 1);
      }
    },
    page_right(){
      if (this.playerStatPage != this.playerStatPageMax){
        this.page(this.playerStatPage + 1);
      }
    },
    page(n){
      this.playerStatPage = n;
      let len = this.playerStatToShowPage.length
      for (let i = 0; i < len; i++){
        this.playerStatToShowPage.pop();
        console.log(this.playerStatToShowPage);
      }
      for (let i = (this.playerStatPage - 1) * this.playerStatNbRowPerPage; i < Math.min(this.playerStatNbRow, this.playerStatPage * this.playerStatNbRowPerPage); i++){
        this.playerStatToShowPage.push(this.playerStatToShow[i]);
        console.log(this.playerStatToShowPage);
      }
    }
  },
};
</script>
