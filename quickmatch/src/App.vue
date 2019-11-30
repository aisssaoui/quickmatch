<template>
  <div id="app">
    <Nav msg="Nav" />
    <Home msg="QuickMatch" />
    <Footer msg="Footer" />
  </div>
</template>

<script>
import Home from "./components/Home.vue";
import Nav from "./components/Nav.vue";
import Footer from "./components/Footer.vue";

export default {
  name: "app",
  components: {
    Home, Nav, Footer
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
