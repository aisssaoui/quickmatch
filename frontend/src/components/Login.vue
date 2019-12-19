<template>
    <v-container fluid v-if="!isSignedIn">
        <v-row justify="center" align="center">
            <v-col cols="1">
                <v-img src="../assets/logo.png" alt="Quick Match logo"></v-img>
            </v-col>
        </v-row>
        <v-layout row wrap align-center="true" justify-center="true">
            <v-card class="mx-10" raised width="25%">
                <v-form v-model="valid">
                <v-layout justify-center="true">
                    <v-card-title align="center">Connexion à Quick Match</v-card-title>
                </v-layout>
                    <v-col class="py-0" cols="4" md="12">
                        <v-text-field v-model="pseudo" :rules="pseudoRules" :counter="20" label="Pseudo" required outlined filled></v-text-field>
                    </v-col>
                    <v-col class="py-0" cols="6" md="12">
                        <v-text-field v-model="password" :rules="passwordRules" :counter="20" label="Mot de passe" type="password" required outlined filled></v-text-field>
                    </v-col>
                </v-form>
                <v-row class="pa-0" align="center" justify="center">
                    <v-col class="pt-0" cols="10">
                        <v-btn rounded outlined block>Connexion</v-btn>
                    </v-col>
                </v-row>
                <v-divider></v-divider>
                <v-row align="center" justify="center">
                    <v-col cols="10">
                        <button></button>
                        <v-btn v-google-signin-button="clientId" color="green lighten-1" rounded outlined block>Se connecter avec Google</v-btn>
                    </v-col>
                </v-row>
                <v-card-subtitle class="pa-0" align="center">OU</v-card-subtitle>
                <v-row class="pa-0" justify="center">
                    <v-col cols="10">
                        <v-btn color="blue darken-2" rounded outlined block>Se connecter avec Facebook</v-btn>
                    </v-col>
                </v-row>
            </v-card>
            <v-card class="mx-10" raised width="25%">

                <v-layout justify-center="true">
                    <v-card-title align="center">Pas encore inscrit(e) ?</v-card-title>
                </v-layout>
                <v-row class="pa-0" align="center" justify="center">
                    <v-col cols="10">
                        <v-btn to="/signin" rounded outlined block>Inscrivez vous !</v-btn>
                    </v-col>
                </v-row>
                <v-divider></v-divider>
                <v-layout justify-center="true">
                    <v-card-title align="center">Mot de passe oublié ?</v-card-title>
                </v-layout>
                <v-row class="pa-0" align="center" justify="center">
                    <v-col cols="10">
                        <v-btn rounded outlined block>Changer de mot de passe</v-btn>
                    </v-col>
                </v-row>
            </v-card>
        </v-layout>
    </v-container>
</template>

<script>
import GoogleSignInButton from '../main.js';
import store from "../store";
import axios from "axios";
import router from "../router";

export default {
  directives: {
      GoogleSignInButton
  },
  data: () => ({
      clientId: '864617003210-1dr6nsvputhjv59l5b3633sslri4vdjd.apps.googleusercontent.com',
      valid: false,
      pseudo: '',
      pseudoRules: [
          v => !!v || 'Pseudo requis',
          v => v.length >= 2 || 'Pseudo trop court',
          v => /^[a-zA-Z0-9 _-éèç]+$/.test(v) || 'Pseudo invalide (lettres, nombres, espace, "_" et "-" seulement)'
      ],
      password: '',
      passwordRules: [
          v => !!v || 'Mot de passe requis',
          v => v.length >= 8 || 'Mot de passe trop court'
      ]
  }),
  methods: {
      OnGoogleAuthSuccess (googleUser) {
          store.dispatch("setGoogleUser",googleUser);
          this.findUser(); // méthode initiale
      },
      OnGoogleAuthFail (error) {
          console.log(error);
      },
      findUser() {
          axios
              .get("http://fama6831.odns.fr/dbcontrol/api/v1/Players/" + store.getters.email)
              .then(response => {
                  store.dispatch("hasAccount");
                  router.push("/"); // redirection vers la page d'accueil
              })
              .catch(e => {
                  router.push("/createAccount");
          });
      },
      logout() {
          store.dispatch("logout");
          router.push("/disconnected");
      }
},
computed: {
    isSignedIn: function() {
        this.$store.dispatch("isSignedIn");
        return store.getters.isSignedIn;
    }
}
}
</script>
