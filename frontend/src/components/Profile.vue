<template>
  <div>
    <div v-if="isSignedIn">
      <br />
      <br />
      <v-card class="mx-auto" max-width="800" tile>
        <v-list-item three-line>
          <v-list-item-content>
            <v-list-item-title class="display-2">
              {{
              playerToShow.pseudo
              }}
            </v-list-item-title>
            <v-list-item-subtitle>{{playerToShow.bio}}</v-list-item-subtitle>
          </v-list-item-content>

          <v-list-item-avatar size="160">
            <img id="avatar" />
          </v-list-item-avatar>
        </v-list-item>

        <v-card-actions>
          <v-btn text color="deep-purple accent-4" :to="'/EditProfile/' + id">
            Editer mon profil
            <v-icon>mdi-pencil-outline</v-icon>
          </v-btn>
        </v-card-actions>
      </v-card>
      <br />
      <v-card class="mx-auto" max-width="800" tile>
        <v-list-item two-line>
          <v-list-item-content>
            <v-list-item-title class="font-weight-bold">Nom</v-list-item-title>
            <v-list-item-subtitle class="headline">
              {{
              playerToShow.surname
              }}
            </v-list-item-subtitle>
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
            <v-list-item-title class="font-weight-bold">Adresse mail</v-list-item-title>
            <v-list-item-subtitle class="headline">{{ playerToShow.mail_address }}</v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>

        <v-list-item two-line>
          <v-list-item-content>
            <v-list-item-title class="font-weight-bold">Numéro de téléphone</v-list-item-title>
            <v-list-item-subtitle class="headline">{{ playerToShow.phone_number }}</v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
      </v-card>
    </div>
    <WorkInProgress v-else></WorkInProgress>
  </div>
</template>

<script>
import axios from "axios";
import WorkInProgress from "./WorkInProgress";
import store from "../store";

export default {
  components: {
    WorkInProgress
  },
  data() {
    return {
      id: this.$route.params.id,
      playerToShow: {}
    };
  },

  async created() {
    const player = await axios.get(
      "http://fama6831.odns.fr/dbcontrol/api/v1/players/id" + this.id,
      {
        responseType: "json"
      }
    );
    this.playerToShow = player.data;
    document.getElementById("avatar").src = this.playerToShow.avatar;
  },
  computed: {
    isSignedIn() {
      store.dispatch("isSignedIn");
      return store.getters.isSignedIn;
    }
  }
};
</script>
