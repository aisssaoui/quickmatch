<template>
  <b-container>
    <b-row>
      <b-col cols="4">
        <b-form v-on:submit.prevent="addClub">
           <b-form-group id="club_name" label="Nom du club :" label-for="club_name">
              <b-form-input id="club_name" v-model="form.club_name"></b-form-input>
            </b-form-group>
           <b-form-group id="private_club" label="Club privée :" label-for="private_club">
             <b-form-select v-model="private_club" class="mb-3">
               <option value="true">Oui</option>
               <option value="false">Non</option>
             </b-form-select>
            </b-form-group>
          <b-button type="submit" variant="primary">Ajouter</b-button>
        </b-form>
      </b-col>
      <b-col cols="8">
        <h3>Liste des clubs :</h3>
        <b-list-group class="mt-2">
            <br><br>
            <h1 v-on:click="loadClubs">Loading ... </h1>
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
      club_name: '',
      private_club: ''
      },
    };
  },
  methods: {
    addClub: function() {
      axios
        .post("http://localhost:3000/api/v1/Clubs", {
        club_name: this.form.club_name,
        private_club: this.form.private_club,
        });

    },
    loadClubs: function() {
      axios
        .get("http://localhost:3000/api/v1/Clubs");
    },
    deleteClub: function(id) {
      axios
        .delete(`http://localhost:3000/api/v1/Clubs/${id}`);
    }
  },
  created: function() {
    this.loadClubs();
  }
};
</script>

<style>

</style>
