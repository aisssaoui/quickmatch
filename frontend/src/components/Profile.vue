<template>
  <div>
    <br /><br />
    <v-card class="mx-auto" max-width="800" tile>
      <v-list-item three-line>
        <v-list-item-content>
          <v-list-item-title class="display-2">{{
            playerToShow.pseudo
          }}</v-list-item-title>
          <v-list-item-subtitle
            >Joueur professionnel des girondins le 100 !!
          </v-list-item-subtitle>
        </v-list-item-content>

        <v-list-item-avatar size="80" color="purple">
          <span class="white--text headline text-uppercase">{{
            playerToShow.pseudo
          }}</span>
        </v-list-item-avatar>
      </v-list-item>

      <v-card-actions>
        <v-btn text color="deep-purple accent-4"
          >Editer mon profil <v-icon> mdi-pencil-outline</v-icon></v-btn
        >
      </v-card-actions>
    </v-card>
    <br />
    <v-card class="mx-auto" max-width="800" tile>
      <v-list-item two-line>
        <v-list-item-content>
          <v-list-item-title class="font-weight-bold">Nom</v-list-item-title>
          <v-list-item-subtitle class="headline">{{
            playerToShow.surname
          }}</v-list-item-subtitle>
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
          <v-list-item-title class="font-weight-bold"
            >Adresse mail</v-list-item-title
          >
          <v-list-item-subtitle class="headline">
            {{ playerToShow.mail_address }}
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>

      <v-list-item two-line>
        <v-list-item-content>
          <v-list-item-title class="font-weight-bold"
            >Numéro de téléphone</v-list-item-title
          >
          <v-list-item-subtitle class="headline">
            {{ playerToShow.phone_number }}
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>

      <!-- <v-list-item two-line>
        <v-list-item-content>
          <v-list-item-title class="font-weight-bold"
            >Buts marqués</v-list-item-title
          >
          <v-list-item-subtitle class="headline">
            {{ playerToShow.scored_goals }}
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>

      <v-list-item two-line>
        <v-list-item-content>
          <v-list-item-title class="font-weight-bold"
            >Buts encaissés</v-list-item-title
          >
          <v-list-item-subtitle class="headline">
            {{ playerToShow.conceded_goals }}
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>

      <v-list-item two-line>
        <v-list-item-content>
          <v-list-item-title class="font-weight-bold"
            >Matchs joués</v-list-item-title
          >
          <v-list-item-subtitle class="headline">
            {{ playerToShow.matches_played }}
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>

      <v-list-item two-line>
        <v-list-item-content>
          <v-list-item-title class="font-weight-bold"
            >Nombre de victoires</v-list-item-title
          >
          <v-list-item-subtitle class="headline">
            {{ playerToShow.victories }}
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item> -->
    </v-card>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      id: this.$route.params.id,
      playerToShow: {}
    };
  },

  async created() {
    const player = await axios.get(
      "http://localhost:3000/dbcontrol/api/v1/players/id" + this.id,
      {
        responseType: "json"
      }
    );
    this.playerToShow = player.data;
  }
};
</script>
