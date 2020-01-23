<template>
  <div>
    <div v-if="isSignedIn">
      <v-card class="mx-auto" max-width="800" tile>
        <v-list-item three-line>
          <v-list-item-content>
            <v-list-item-title class="display-2"
              >Liste des club</v-list-item-title
            >
          </v-list-item-content>
        </v-list-item>
      </v-card>

      <div v-for="row in playerStatToShow" :key="row.id">
        <v-list-item two-line>
          <v-list-item-content>
            <v-list-item-title class="font-weight-bold"
              >Nom du Club</v-list-item-title
            >
            <v-list-item-subtitle class="headline">{{
              row.club_name
            }}</v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>

        <v-list-item two-line>
          <v-list-item-content>
            <v-list-item-title class="font-weight-bold"
              >Date de création</v-list-item-title
            >
            <v-list-item-subtitle class="headline">{{
              row.creation_date
            }}</v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>

        <v-list-item two-line>
          <v-list-item-content>
            <v-list-item-title class="font-weight-bold"
              >Club privé</v-list-item-title
            >
            <v-list-item-subtitle class="headline">{{
              row.private_club
            }}</v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>

        <v-list-item two-line>
          <v-list-item-content>
            <v-list-item-title class="font-weight-bold"
              >Administrateur</v-list-item-title
            >
            <v-list-item-subtitle class="headline">{{
              row.is_admin
            }}</v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
      </div>
    </div>
    <WorkInProgress v-else></WorkInProgress>
  </div>
</template>

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
      clubsToShow: {}
    };
  },

  async created() {
    const clubs = await axios.get(
      "http://fama6831.odns.fr/dbcontrol/api/v1/Clubs/id" + this.id,
      {
        responseType: "json"
      }
    );
    this.clubsToShow = clubs.data;
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
