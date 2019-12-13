<template>
  <v-footer color="black" padless>
    <v-row justify="center">

      <v-btn v-for="link in links" :key="link" color="white" text rounded class="my-2">
        {{ link }}
      </v-btn>

      <v-col class="black py-4 text-center white--text" cols="12">   
        {{ new Date().getFullYear() }} — <strong>Quick Match</strong>
      </v-col>

    </v-row>
  </v-footer>
</template>

<script>
  export default {
    data: () => ({
      links: [
        'Home',
        'About Us',
        'Team',
        'Services',
        'Contact Us',
      ],
    }),
  }
</script>