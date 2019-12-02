<template>
  <b-container>
    <b-row>
      <b-col cols="4">
        <b-form v-on:submit.prevent="addPlayer">
            <b-form-group id="id" label="Numéro joueur" label-for="id">
              <b-form-input id="id" v-model="form.id"></b-form-input>
            </b-form-group>
           <b-form-group id="surname" label="Nom:" label-for="surname">
              <b-form-input id="surname" v-model="form.surname"></b-form-input>
            </b-form-group>
          <b-form-group id="first_name" label="Prénom :" label-for="first_name">
            <b-form-input id="first_name" v-model="form.first_name"></b-form-input>
          </b-form-group>
          <b-form-group id="mail_adress" label="Adresse email :" label-for="mail_adress">
            <b-form-input id="mail_adress" v-model="form.mail_adress"></b-form-input>
          </b-form-group>
         <b-form-group id="phone_number" label="Num Téléphone :" label-for="phone_number">
            <b-form-input id="phone_number" v-model="form.phone_number"></b-form-input>
          </b-form-group>
         <b-form-group id="scored_goals" label="Buts marqués :" label-for="scored_goals">
            <b-form-input id="scored_goals" v-model="form.scored_goals"></b-form-input>
          </b-form-group>
         <b-form-group id="conceded_goals" label="Buts encaissés :" label-for="conceded_goals">
            <b-form-input id="conceded_goals" v-model="form.conceded_goals"></b-form-input>
          </b-form-group>
         <b-form-group id="victories" label="Nombre de victoires :" label-for="victories">
            <b-form-input id="victories" v-model="form.victories"></b-form-input>
          </b-form-group>
          <b-button type="submit" variant="primary">Ajouter</b-button>
        </b-form>
      </b-col>
      <b-col cols="8">
        <h3>Liste des joueurs :</h3>
        <b-list-group class="mt-2">
            <br><br>
            <h1 v-on:click="loadPlayers">Loading ... </h1>
        </b-list-group>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>

import axios from "axios";

export default {
  components: {

  },
  data: function() {
    return {
      form: {
      id: null,
      surname: '',
      first_name: '',
      mail_adress: '',
      phone_number: '',
      scored_goals: null,
      conceded_goals: null,
      matches_played: null,
      victories: null
      },
    };
  },
  methods: {
    addPlayer: function() {
      axios
        .post("http://localhost:3000/api/v1/Players", {
        id: this.form.id,
        surname: this.form.surname,
        first_name: this.form.first_name,
        mail_adress: this.form.mail_adress,
        phone_number: this.form.phone_number,
        scored_goals: this.form.scored_goals,
        conceded_goals: this.form.conceded_goals,
        matches_played: this.form.matches_played,
        victories: this.form.victories  
        });

    },
    loadPlayers: function() {
      axios
        .get("http://localhost:3000/api/v1/Players");
    },
    deletePlayer: function(id) {
      axios
        .delete(`http://localhost:3000/api/v1/Players/${id}`);
    }
  },
  created: function() {
    this.loadPlayers();
  }
};
</script>

<style>

</style>