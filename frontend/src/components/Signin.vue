<template>
    <v-container fluid>
        <v-layout row wrap align-center="true" justify-center="true">
            <v-card class="mx-10" raised width="25%">
                <v-form v-model="valid" >
                    <v-row class="pa-0" justify="center" align="center">
                      <v-col align="center" cols="5">
                          <v-img src="../assets/logo.png" alt="Quick Match logo"></v-img>Inscription
                      </v-col>
                    </v-row>
                    <v-col class="py-0" cols="6" md="12">
                        <v-text-field v-model="surname" :rules="surnameRules" :counter="20" label="Nom" required outlined filled></v-text-field>
                    </v-col>
                    <v-col class="py-0" cols="6" md="12">
                        <v-text-field v-model="first_name" :rules="first_nameRules" :counter="20" label="Prénom" required outlined filled></v-text-field>
                    </v-col>
                    <v-col class="py-0" cols="6" md="12">
                        <v-text-field v-model="pseudo" :rules="pseudoRules" :counter="20" label="Pseudo" required outlined filled></v-text-field>
                    </v-col>
                    <v-col class="py-0" cols="6" md="12">
                        <v-text-field v-model="password" :rules="passwordRules" :counter="20" label="Mot de passe" type="password" required outlined filled></v-text-field>
                    </v-col>
                    <v-col class="py-0" cols="6" md="12">
                        <v-text-field v-model="email" :rules="emailRules" :counter="50" label="E-mail" required outlined filled></v-text-field>
                    </v-col>
                    <v-col class="py-0" cols="6" md="12">
                        <v-text-field v-model="tel" :rules="telRules" :counter="14" label="Numéro de téléphone" required outlined filled></v-text-field>
                    </v-col>


                </v-form>
                <v-row class="pa-0" align="center" justify="center">
                    <v-col class="pt-0" cols="10">
                        <v-btn rounded outlined block v-on:click="login">Se connecter</v-btn>
                    </v-col>
                </v-row>
                <v-divider></v-divider>
                <v-row align="center" justify="center">
                    <v-col cols="10">
                        <v-btn
                          v-google-signin-button="clientId"
                          color="green lighten-1"
                          rounded
                          outlined
                          block
                          >Se connecter avec Google</v-btn
                        >
                    </v-col>
                </v-row>
            </v-card>
        </v-layout>
    </v-container>
</template>

<script>
  import GoogleSignInButton from "../main.js";
  import store from "../store";
  import axios from "axios";
  import router from "../router";

  export default {
      directives: {
        GoogleSignInButton
      },
    data: () => ({
      clientId:
        "864617003210-1dr6nsvputhjv59l5b3633sslri4vdjd.apps.googleusercontent.com",
      valid: false,
      surname: '',
      first_name: '',
      pseudo: '',
      password: '',
      email: '',
      tel: '',
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
      passwordRules: [
        v => !!v || 'Mot de passe requis',
        v => v.length >= 8 || 'Mot de passe trop court'
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
        OnGoogleAuthSuccess(googleUser) {
          store.dispatch("setGoogleUser", googleUser);
          this.findUser(); // méthode initiale
        },
        OnGoogleAuthFail(error) {
          store.dispatch("hasAccount");
          console.log(error);
        },
        findUser() {
          axios
            .get(
              "http://fama6831.odns.fr/dbcontrol/api/v1/Players/ma" +
                store.getters.email
            )
            .then(response => {
              store.dispatch("hasAccount");
              store.dispatch("setID");
              router.push("/"); // redirection vers la page d'accueil
            })
            .catch(e => {
              router.push("/createAccount");
            });
        },
        logout() {
          store.dispatch("logout");
          router.push("/disconnected");
        },
        login() {
          axios
            .get(
              "http://fama6831.odns.fr/dbcontrol/api/v1/Players/ma" +
                store.getters.email
            )
            .then(response => {
              store.dispatch("login");
              router.push("/");
            })
            .catch(e => {
              router.push("/createAccount");
            });
        }/*
      login() {
        axios
          .post("http://fama6831.odns.fr/dbcontrol/api/v1/Players")
          .then(response => {
            store.dispatch("login");
            router.push("/");
          })
          .catch(e => {
            router.push("/createAccount");
          });
      }*/
    }
  }
</script>
