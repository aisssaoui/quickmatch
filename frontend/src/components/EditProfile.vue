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
            <v-text-field v-model="playerToShow.pseudo" :rules="pseudoRules" :counter="20"></v-text-field>
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>

      <v-list-item two-line>
        <v-list-item-content>
          <v-list-item-title class="font-weight-bold">Nom</v-list-item-title>
          <v-list-item-subtitle class="headline">
            <v-text-field v-model="playerToShow.surname" :rules="surnameRules" :counter="20"></v-text-field>
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>

      <v-list-item two-line>
        <v-list-item-content>
          <v-list-item-title class="font-weight-bold">Prénom</v-list-item-title>
          <v-list-item-subtitle class="headline">
            <v-text-field v-model="playerToShow.first_name" :rules="first_nameRules" :counter="20"></v-text-field>
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>

      <v-list-item two-line>
        <v-list-item-content>
          <v-list-item-title class="font-weight-bold">Adresse mail</v-list-item-title>
          <v-list-item-subtitle class="headline">
            <v-text-field v-model="playerToShow.mail_address" :rules="emailRules" :counter="50"></v-text-field>
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>

      <v-list-item two-line>
        <v-list-item-content>
          <v-list-item-title class="font-weight-bold">Numéro de téléphone</v-list-item-title>
          <v-list-item-subtitle class="headline">
            <v-text-field v-model="playerToShow.phone_number" :rules="telRules" :counter="14"></v-text-field>
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
  data: () => ({
    id: this.$route.params.id,
    playerToShow: {},
    surnameRules: [
      v => !!v || 'Nom requis',
      v => v.length >= 2 || 'Nom trop court',
      v => /^[a-zA-Z \-éèçîïœžâêôàûùâãäåæçëìíîïðñòóôõúûüýö]+$/.test(v) || 'Nom invalide'
    ],
    first_nameRules: [
      v => !!v || 'Prénom requis',
      v => v.length >= 2 || 'Prénom trop court',
      v => /^[a-zA-Z \-éèçîïœžâêôàûùâãäåæçëìíîïðñòóôõúûüýö]+$/.test(v) || 'Prénom invalide'
    ],
    pseudoRules: [
      v => !!v || 'Pseudo requis',
      v => v.length >= 2 || 'Pseudo trop court',
      v => /^[a-zA-Z0-9 _\-éèçîïœžâêôàûùâãäåæçëìíîïðñòóôõúûüýö]+$/.test(v) || 'Pseudo invalide (lettres, nombres, espace, "_" et "-" seulement)'
    ],
    emailRules: [
      v => !!v || 'E-mail requis',
      v => /^[a-zA-Z0-9._-]+@[a-z0-9._-]{2,}\.[a-z]{2,4}$/.test(v) || 'E-mail invalide'
    ],
    telRules: [
      v => !!v || 'Numéro de téléphone requis',
      v => /^(0[1-8])(?:[ -]?([0-9]{2})){4}$/.test(v) || 'Numéro de téléphone invalide (vous pouvez utilisé "-" ou des espaces comme séparateur (06-66-66-66-66)'
    ],
  }),

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
