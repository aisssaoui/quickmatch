<template>
  <div v-if="isSignedIn">
  <WorkInProgress></WorkInProgress>
</div>
<NotConnected v-else></NotConnected>

</template>

<script>
import store from "../store";
import WorkInProgress from "./WorkInProgress";
import NotConnected from "./NotConnected";

export default {
  components: {
    WorkInProgress,
    NotConnected
  },
  computed: {
    isSignedIn: function() {
      this.$store.dispatch("isSignedIn");
      return store.getters.isSignedIn;
    }
  }
};
</script>

<style>
</style>
