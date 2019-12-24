<template>
  <div>
    <br />
    <br />
    <v-card class="mx-auto" max-width="800" tile>
      <v-list-item three-line>
        <v-list-item-content>
          <v-list-item-title class="display-2">Modifie ton profile !</v-list-item-title>
          <v-list-item-subtitle>
            <v-text-field label="Bio" v-model="playerToShow.bio"></v-text-field>
          </v-list-item-subtitle>
        </v-list-item-content>

        <v-list-item-avatar size="80">
          <img id="avatar" />
        </v-list-item-avatar>
      </v-list-item>
    </v-card>
    <br />
    <v-card class="mx-auto" max-width="800" tile>
      <v-list-item two-line>
        <v-list-item-content>
          <v-list-item-title class="font-weight-bold">Pseudo</v-list-item-title>
          <v-list-item-subtitle class="headline">
            <v-text-field v-model="playerToShow.pseudo"></v-text-field>
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>

      <v-list-item two-line>
        <v-list-item-content>
          <v-list-item-title class="font-weight-bold">Nom</v-list-item-title>
          <v-list-item-subtitle class="headline">
            <v-text-field v-model="playerToShow.surname"></v-text-field>
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>

      <v-list-item two-line>
        <v-list-item-content>
          <v-list-item-title class="font-weight-bold">Prénom</v-list-item-title>
          <v-list-item-subtitle class="headline">
            <v-text-field v-model="playerToShow.first_name"></v-text-field>
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>

      <v-list-item two-line>
        <v-list-item-content>
          <v-list-item-title class="font-weight-bold">Adresse mail</v-list-item-title>
          <v-list-item-subtitle class="headline">
            <v-text-field v-model="playerToShow.mail_address"></v-text-field>
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>

      <v-list-item two-line>
        <v-list-item-content>
          <v-list-item-title class="font-weight-bold">Numéro de téléphone</v-list-item-title>
          <v-list-item-subtitle class="headline">
            <v-text-field v-model="playerToShow.phone_number"></v-text-field>
          </v-list-item-subtitle>
        </v-list-item-content>

        <v-card-actions>
          <v-btn
            text
            color="deep-purple accent-4"
            v-on:click="updatePlayer"
          >Enregistrer les modifications</v-btn>
        </v-card-actions>
      </v-list-item>
    </v-card>
    <br />
    <br />
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

  methods: {
    updatePlayer: async function() {
      await axios.put(
        "http://fama6831.odns.fr/dbcontrol/api/v1/players/id" + this.id,
        {
          bio: this.playerToShow.bio,
          surname: this.playerToShow.surname,
          first_name: this.playerToShow.first_name,
          mail_address: this.playerToShow.mail_address,
          phone_number: this.playerToShow.phone_number,
          pseudo: this.playerToShow.pseudo
        }
      );
      this.$router.push({ path: `/profile/${this.id}` });
    }
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
  }
};
</script>

<style>
</style>