<template>
  <div>
    <div v-if="isSignedIn">
      <!-- ///////////////////////////////////////////////////////////////// -->
      <div id="stat_glob">
        <v-card max-width="800" dark color="#000">
          <v-list-item three-line>
            <v-list-item-content>
              <v-list-item-title class="display-2">Statistiques globales</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
          <hr>

          <v-list-item two-line>
            <v-list-item-content>
              <v-list-item-title class="font-weight-bold">Nom</v-list-item-title>
              <v-list-item-subtitle class="headline">{{ playerToShow.surname }}</v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>

          <v-list-item two-line>
            <v-list-item-content>
              <v-list-item-title class="font-weight-bold">Prénom</v-list-item-title>
              <v-list-item-subtitle class="headline">{{ playerToShow.first_name }}</v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>

          <v-list-item two-line>
            <v-list-item-content>
              <v-list-item-title class="font-weight-bold">Nombre de but(s)</v-list-item-title>
              <v-list-item-subtitle class="headline">{{ playerToShow.scored_goals }}</v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>

          <v-list-item two-line>
            <v-list-item-content>
              <v-list-item-title class="font-weight-bold">Nombre de but(s) encaissé(s)</v-list-item-title>
              <v-list-item-subtitle class="headline">{{ playerToShow.conceded_goals }}</v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>

          <v-list-item two-line>
            <v-list-item-content>
              <v-list-item-title class="font-weight-bold">Nombre de match joué(s)</v-list-item-title>
              <v-list-item-subtitle class="headline">{{ playerToShow.matches_played }}</v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>

          <v-list-item two-line>
            <v-list-item-content>
              <v-list-item-title class="font-weight-bold">Nombre de victoire(s)</v-list-item-title>
              <v-list-item-subtitle class="headline">{{ playerToShow.victories }}</v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
        </v-card>
      </div>
      <!-- ///////////////////////////////////////////////////////////////// -->
      <div id="stat_match">
        <v-card max-width="800" dark color="#000">
          <v-list-item three-line>
            <v-list-item-content>
              <v-list-item-title class="display-2">Statistiques de vos derniers match</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <div v-for="row in playerStatToShow" :key="row.id">
            <hr>

            <v-list-item two-line>
              <v-list-item-content>
                <v-list-item-title class="font-weight-bold">Résultat du match du {{ row.precise_date }} à : {{ row.location }}</v-list-item-title>
                <v-list-item-subtitle v-if="row.won" class="headline">victoire</v-list-item-subtitle>
                <v-list-item-subtitle v-else class="headline">défaite</v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>

            <v-list-item two-line>
              <v-list-item-content>
                <v-list-item-title class="font-weight-bold">Nombre de but(s)</v-list-item-title>
                <v-list-item-subtitle class="headline">{{ row.scored_goals }}</v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>

            <v-list-item two-line>
              <v-list-item-content>
                <v-list-item-title class="font-weight-bold">Nombre de but(s) encaissé(s)</v-list-item-title>
                <v-list-item-subtitle class="headline">{{ row.conceded_goals }}</v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>
          </div>
        </v-card>
      </div>
      <!-- ///////////////////////////////////////////////////////////////// -->
    </div>
    <WorkInProgress v-else></WorkInProgress>
  </div>
</template>

<style>
  #stat_glob{
    width: 45%;
    margin-top: 2%;
    margin-left: 2%;
    margin-bottom: 2%;
    float: left;
  }
  #stat_match{
    width: 45%;
    margin-top: 2%;
    margin-right: 2%;
    margin-bottom: 2%;
    float: right;
    height: 660px;
    overflow: scroll;
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
      playerStatToShow: {}
    };
  },
  async created() {
    const player = await axios.get(
      "https://dbcontrol.quickmatch.fr/dbcontrol/api/v1/players/id" + this.id,
      {
        responseType: "json"
      }
    );
    this.playerToShow = player.data;
    const playerStat = await axios.get(
      "https://dbcontrol.quickmatch.fr/dbcontrol/api/v1/players/stat" + this.id,
      {
        responseType: "json"
      }
    );
    this.playerStatToShow = playerStat.data.rows;
  },
  computed: {
    isSignedIn() {
      store.dispatch("isSignedIn");
      return store.getters.isSignedIn;
    },
    id() {
      store.dispatch("id");
      return store.getters.id;
    }
  }
};
</script>
