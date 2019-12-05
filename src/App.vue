<template>
  <v-app>
    <v-app-bar hide-on-scroll app color="light-green darken-1" dark>

      <div class="d-flex align-center">
        <v-btn to="/" x-large text>Home Page</v-btn>
        <v-divider vertical></v-divider>

        <v-btn to="/invitation" text>
          <v-icon>mdi-email</v-icon>
        </v-btn>
        <v-divider vertical></v-divider>
      </div>

      <v-spacer></v-spacer>

      <v-divider vertical></v-divider>
      <div>
        <v-btn v-if="!isLoggedIn" v-on:click="login" text>Login</v-btn>
        <v-btn v-if="isLoggedIn" v-on:click="logout" to="/" text>Logout</v-btn>
      </div>

    </v-app-bar>

    <v-content>
      <router-view></router-view>
    </v-content>
  </v-app>
</template>

<script>
  import store from "./store";

  export default {
    components: {

    },
    methods: {
      logout: function() {
        store.dispatch('logout')
        this.$router.push('/')
      },
      login: function() {
        store.dispatch('login')
        this.$router.push('/login')
      }
    },
    computed: {
      isLoggedIn() {
        return this.$store.getters.isLoggedIn;
      }
    }
  };

</script>

<style>
  .button-low-padding {
    padding: 0;
  }
</style>
