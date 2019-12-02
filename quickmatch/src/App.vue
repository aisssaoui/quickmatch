<template>
  <div id="app">

    <Nav msg="Nav" />
      <router-view></router-view>
    <Footer msg="Footer" />

    <router-link to="/player">easter egg</router-link>
  </div>
</template>

<script>
import Nav from "./components/Nav.vue";
import Footer from "./components/Footer.vue";

export default {
  name: "app",
  components: {
    Nav, Footer
  }
};
</script>

<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
#title {
  padding-top: 200px; 
}
#logo {
  text-align: left;
}
</style>
