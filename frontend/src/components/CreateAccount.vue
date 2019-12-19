<template>
    <v-container fluid>
        <v-row justify="center" align="center">
            <v-col cols="1">
                <v-img src="../assets/logo.png" alt="Quick Match logo"></v-img>
            </v-col>
        </v-row>
        <v-layout row wrap align-center="true" justify-center="true">
            <v-card class="mx-10" raised width="25%">
                <v-form  v-on:submit.prevent="createAccount">
                <v-layout justify-center="true">
                    <v-card-title align="center">Création de compte</v-card-title>
                </v-layout>
                    <v-col class="py-0" cols="4" md="12">
                        <v-text-field v-model="pseudo" :rules="pseudoRules" :counter="20" label="Pseudonyme" required outlined filled></v-text-field>
                    </v-col>
                    <v-col class="py-0" cols="6" md="12">
                        <v-text-field v-model="surname" label="Nom" :rules="surnameRules" type="surname" required outlined filled></v-text-field>
                    </v-col>
                    <v-col class="py-0" cols="6" md="12">
                        <v-text-field v-model="firstName" label="Prénom" :rules="firstNameRules" type="firstName" required outlined filled></v-text-field>
                    </v-col>
                </v-form>
                <v-row class="pa-0" align="center" justify="center">
                    <v-col class="pt-0" cols="10">
                        <v-btn v-on:click="createAccount()" rounded outlined block>Créer !</v-btn>
                    </v-col>
                </v-row>
            </v-card>
        </v-layout>
    </v-container>
</template>

<script>
import store from "../store";
import axios from "axios";
import router from "../router";

export default {
    data(){
     return{
         surname: "",
         firstName: "",
         pseudo: "",
         mailAddress: "",
         phone_number: "none",
         mdp: "stocked by Google",
         response: "",
         pseudoRules: [
             v => !!v || 'Pseudo requis',
             v => v.length >= 2 || 'Pseudo trop court',
             v => /^[a-zA-Z0-9 _-éèç]+$/.test(v) || 'Pseudo invalide (lettres, nombres, espace, "_" et "-" seulement)'
         ],
         surnameRules: [
             v => !!v || 'Nom requis',
             v => /^[a-zA-Z -éèçîïœžâêôàûùâãäåæçëìíîïðñòóôõúûüýö]+$/.test(v) || 'Nom invalide (lettres (avec ou sans accent), espace, et "-" seulement)'
         ],
         firstNameRules: [
             v => !!v || 'Prénom requis',
             v => /^[a-zA-Z- éèçîïœžâêôàûùâãäåæçëìíîïðñòóôõúûüýö]+$/.test(v) || 'Prénom invalide (lettres (avec ou sans accent), espace, et "-" seulement)'
         ]
    }
},
methods: {
    createAccount: function() {
        axios
              .post("http://fama6831.odns.fr/dbcontrol/api/v1/Players/", {pseudo: this.pseudo,
              surname: this.surname,
              first_name: this.firstName,
              mdp: this.mdp,
              phone_number: this.phone_number,
              mail_address: this.mailAddress
              })
              .then(response => {
                  this.response = response.data;
                  store.dispatch("hasAccount");
                  router.push("/");
              })
              .catch(e => {
                  // création impossible
                  console.error(e);
              });
    }
},
created: function() {
    this.surname = store.getters.surname;
    this.firstName = store.getters.givenName;
    this.mailAddress = store.getters.email;
}
}
</script>
<style>

</style>
