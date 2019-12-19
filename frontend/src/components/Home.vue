<template>
  <div>
    <v-carousel cycle show-arrows hide-delimiter-background show-arrows-on-hover>
      <v-carousel-item
        v-for="(item,i) in items"
        :key="i"
        :src="item.src"
        reverse-transition="fade-transition"
        transition="fade-transition"
      >
        <v-container fill-height>
          <v-layout row wrap align-center>
            <v-flex v-if="i==0" class="text-center">
              <p class="display-4 white--text" style="text-shadow: 1px 1px 7px #16002d;">QuickMatch</p>
              <v-btn
                to="/login"
                large
                color="#e3e3e3"
                style="border: 1px solid #20003c;"
                class="black--text"
              >Se connecter</v-btn>
              <v-btn to="/signin" large color="#20003c" style="border: 1px solid grey;">S'inscrire</v-btn>
            </v-flex>
            <v-flex v-if="i==1" class="text-center">
              <p class="display-4 white--text" style="text-shadow: 1px 1px 7px #16002d;">Statistiques</p>
              <p class="display-1 white--text" style="text-shadow: 1px 1px 7px #16002d;">Les statistiques de tous vos matchs</p>
              <v-btn
                to="/stat"
                large
                color="#20003c"
                style="border: 1px solid grey;"
                class="white--text"
              >Consulter</v-btn>
            </v-flex>

            <v-flex v-if="i==2" class="text-center">
              <p class="display-4 white--text" style="text-shadow: 1px 1px 7px #040083;">Matchs</p>
              <p class="display-1 white--text" style="text-shadow: 1px 1px 7px #040083;">L'historique et les résultats de tous les matchs</p>
              <v-btn
                to="/match"
                large
                color="#20003c"
                style="border: 1px solid grey;"
                class="white--text"
              >Consulter</v-btn>
            </v-flex>

          </v-layout>
        </v-container>
      </v-carousel-item>
    </v-carousel>
    <v-flex>
      <v-jumbotron id="jumb" class="row grey lighten-3" style="padding: 10px 5px 10px 5px;">
        <div class="col-md-12 col-sm-12 col-md-9 col-lg-9 col-xl-10">
          <p
            class="title font-weight-regular"
            style="padding: 12px 0px 0px 100px;"
          >Quick Match organise votre vie sportive.</p>
        </div>
        <div class="col-xs-12 col-sm-12 col-md-3 col-lg-3 col-xl-2">
          <v-btn class="ma-2" outlined color="#20003c">Decouvrez</v-btn>
        </div>
      </v-jumbotron>
    </v-flex>

    <v-container id="propos">
      <div class="row text-center padding">
        <div class="col-12">
          <p class="headline font-weight-medium">A PROPOS DE NOUS</p>
            <div>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent non congue neque. Vestibulum viverra erat at efficitur auctor. Curabitur pulvinar luctus ante in faucibus. Nam egestas vulputate quam ut eleifend. Proin orci nunc, venenatis sit amet ultrices vel, maximus sed nisl. Proin nec auctor purus, eget scelerisque mi. Duis vulputate pellentesque felis, nec porta arcu iaculis non.</div>
        </div>
      </div>
    </v-container>
    
    <hr style="width:50%; margin: auto;" color="#20003c" size="1px"/>

    <v-container id="team">
      <div class="row text-center padding">
        <div class="col-12">
          <p class="headline font-weight-medium">EQUIPE</p>
          <div>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent non congue neque. Vestibulum viverra erat at efficitur auctor. Curabitur pulvinar luctus ante in faucibus. Nam egestas vulputate quam ut eleifend. Proin orci nunc, venenatis sit amet ultrices vel, maximus sed nisl. Proin nec auctor purus, eget scelerisque mi. Duis vulputate pellentesque felis, nec porta arcu iaculis non.</div>
        </div>
      </div>
    </v-container>
    <hr style="width:50%; margin: auto;" color="#20003c" size="1px"/>
    <v-container id="connect">
      <div class="row text-center">
        <div class="col-12">
          <p class="headline font-weight-medium">NOUS CONTACTER</p>
        </div>
        <v-container fluid fill-height class="col-12 social">
              <a v-for="item in socials" :key="item.src" :to="item.route">
                <v-img class="img" v-bind:src="item.src" width="50px" height="50px"></v-img>
              </a>
        </v-container>
      </div>
    </v-container>
  </div>
</template>

<script>
export default {
  data: () => ({
    items: [
      { src: require("../assets/foot.jpg") },
      { src: require("../assets/volley.jpg") },
      { src: require("../assets/basket.jpg") }
    ],
    socials: [
      { src: require("../assets/fb.png"), route: "/" },
      { src: require("../assets/twitter.png"), route: "/" },
      { src: require("../assets/instagram.png"), route: "/" },
      { src: require("../assets/google.png"), route: "/" },
      { src: require("../assets/youtube.png"), route: "/" }
    ]
  })
};
</script>

<style>
.social {
    position: relative;
    left: 30%; 
    right: 30%;

}
.social a {
  margin-right: 40px;
  float: left;
}
</style>