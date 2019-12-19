<template>
    <div v-if="isSignedIn">
      <v-text>vous n'êtes pas connecté</v-text>
    </div>

    <div v-else>
      <v-card class="mx-auto" max-width="800" tile>

        <v-list-item two-line>
          <v-list-item-content>
            <v-list-item-title class="font-weight-bold">Nom</v-list-item-title>
            <v-list-item-subtitle class="headline">
              {{ playerToShow.surname }}
            </v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>

        <v-list-item two-line>
          <v-list-item-content>
            <v-list-item-title class="font-weight-bold">Prénom</v-list-item-title>
            <v-list-item-subtitle class="headline">
              {{ playerToShow.first_name }}
            </v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>

        <v-list-item two-line>
          <v-list-item-content>
            <v-list-item-title class="font-weight-bold">Nombre de but(s)</v-list-item-title>
            <v-list-item-subtitle class="headline">
              {{ playerToShow.scored_goals }}
            </v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>

        <v-list-item two-line>
          <v-list-item-content>
            <v-list-item-title class="font-weight-bold">Nombre de but(s) encaissé(s)</v-list-item-title>
            <v-list-item-subtitle class="headline">
              {{ playerToShow.conceded_goals }}
            </v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>

        <v-list-item two-line>
          <v-list-item-content>
            <v-list-item-title class="font-weight-bold">Nombre de match joué(s)</v-list-item-title>
            <v-list-item-subtitle class="headline">
              {{ playerToShow.matches_played }}
            </v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>

        <v-list-item two-line>
          <v-list-item-content>
            <v-list-item-title class="font-weight-bold">Nombre de victoire(s)</v-list-item-title>
            <v-list-item-subtitle class="headline">
              {{ playerToShow.victories }}
            </v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>

        <hr>

        <div v-for="(row, index) in playerStatToShow" :key="row.id">
          <v-list-item two-line>
            <v-list-item-content>
              <v-list-item-title class="font-weight-bold">Résultat du match {{ index }}</v-list-item-title>
              <v-list-item-subtitle class="headline">
                {{ row.won }}
              </v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>

          <v-list-item two-line>
            <v-list-item-content>
              <v-list-item-title class="font-weight-bold">Nombre de but(s)</v-list-item-title>
              <v-list-item-subtitle class="headline">
                {{ row.scored_goals }}
              </v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>

          <v-list-item two-line>
            <v-list-item-content>
              <v-list-item-title class="font-weight-bold">Nombre de but(s) encaissé(s)</v-list-item-title>
              <v-list-item-subtitle class="headline">
                {{ row.conceded_goals }}
              </v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
        </div>

      </v-card>
    </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      email: this.$route.params.email,
      playerToShow: {},
      playerStatToShow: {}
    };
  },
  async created() {
    const player = await axios.get(
      "http://localhost:3000/dbcontrol/api/v1/players/ma" + this.email,
      {
        responseType: "json"
      }
    );
    this.playerToShow = player.data;

    const playerStat = await axios.get(
      "http://localhost:3000/dbcontrol/api/v1/players/ma_stat" + this.email,
      {
        responseType: "json"
      }
    );
    this.playerStatToShow = playerStat.data.rows;
  }
};
</script>
