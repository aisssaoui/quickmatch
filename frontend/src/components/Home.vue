<template>
  <div class="hello">
    <!-- image slider -->
    <div >
      <b-carousel
        id="carousel-1"
        v-model="slide"
        indicators
        @sliding-start="onSlideStart"
        @sliding-end="onSlideEnd"
      >
        <b-carousel-slide>
          <template v-slot:img>
            <img src="../assets/foot1.jpg" />
            <div id="caption" class="carousel-caption">
              <h1 class="display-2">QuickMatch</h1>
              <h3 id="head-c">Plus jamais du temps à perdre</h3>
              <button type="button" class="btn btn-outline-dark btn-lg btn-light">Se connecter</button>
              <button type="button" class="btn btn-primary btn-lg">S'inscrire</button>
            </div>
          </template>
        </b-carousel-slide>
        <b-carousel-slide>
          <template v-slot:img>
            <img src="../assets/basketball.jpg" />
          </template>
        </b-carousel-slide>
        <b-carousel-slide>
          <template v-slot:img>
            <img src="../assets/volleyball.jpg" />
          </template>
        </b-carousel-slide>
        </b-carousel>
    </div>

    <!-- Jumbotron -->
    <div class="container-fluid">
      <div id="jumb" class="row jumbotron">
        <div class="col-xs-12 col-sm-12 col-md-9 col-lg-9 col-xl-10">
          <p class="lead">Quick Match organise votre vie sportive.</p>
        </div>
        <div class="col-xs-12 col-sm-12 col-md-3 col-lg-3 col-xl-2">
          <a href="#">
            <button id="reg" type="button" class="btn btn-outline-secondary btn-lg">rejoignez nous</button>
          </a>
        </div>
      </div>
    </div>

    <!-- Welcome Section -->
    <div class="container-fluid padding">
      <div class="row welcome text-center">
        <div class="col-12">
          <h1 class="display-4">Une meilleure expérience</h1>
        </div>
        <hr />
        <div class="col-12">
          <p class="lead">Organisez, Jouez et Mémorisez vos meilleurs moments avec QuickMatch!</p>
        </div>
      </div>
    </div>

    <!-- Three Column Section -->
    <div class="container-fluid padding balls">
      <div class="row text-center padding" id="types">
        <div class="col-sm-12 col-md-6">
          <a href="#">
            <img src="../assets/pro.png" style="width:80px;height:70px;" />
            <h3 id="foot">
              <span>PRO FOOT</span>
            </h3>
          </a>
          <p>Match de football professionel.</p>
        </div>
        <div class="col-sm-12 col-md-6">
          <a href="#">
            <img src="../assets/street.png" style="width:70px;height:70px;" />
            <h3 id="foot">
              <span>STREET FOOT</span>
            </h3>
          </a>
          <p>Match de professionel de salle.</p>
        </div>
        <!-- <div class="col-sm-6 col-md-4">
          <img src="../assets/pro.png" style="width:80px;height:70px;" />
          <h3 id="foot">PRO FOOT</h3>
          <p>Match de football professionel.</p>
        </div>-->
      </div>
      <hr class="my-4" />
    </div>

    <!-- connect -->
    <div id="connect" class="container-fluid padding">
      <div class="row text-center padding">
        <div class="col-12">
          <h2>Connect</h2>
        </div>
        <div class="col-12 social padding">
          <a href="#">
            <img src="../assets/fb.png" style="width:50px;height:50px;" />
          </a>
          <a href="#">
            <img src="../assets/twitter.png" style="width:50px;height:50px;" />
          </a>
          <a href="#">
            <img src="../assets/instagram.png" style="width:50px;height:50px;" />
          </a>
          <a href="#">
            <img src="../assets/google.png" style="width:50px;height:50px;" />
          </a>
          <a href="#">
            <img src="../assets/youtube.png" style="width:50px;height:50px;" />
          </a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      slide: 0,
      sliding: null
    };
  }
  // methods: {
  //   onSlideStart(slide) {
  //     this.sliding = true;
  //   },
  //   onSlideEnd(slide) {
  //     this.sliding = false;
  //   }
  // }
};
</script>


<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
/* h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
} */

#head-c {
  margin-top: 0px;
  margin-bottom: 10px;
}
#reg {
  margin-right: 10px;
}
#foot {
  margin-top: 15px;
}
#jumb {
  padding: 20px 10px 20px 10px;
}
#connect {
  padding-bottom: 40px;
}
#types {
  margin-left: 70px;
  margin-right: 70px;
}

.balls {
  padding-top: 50px;
}
.display-2 {
  margin-bottom: 0px;
}

.lead {
  padding: 12px 0px 0px 0px;
}

.social {
  padding-top: 30px;
}

.social a {
  margin-right: 40px;
}
.carousel-inner img {
  width: 100%;
  height: 100%;
}

.carousel-caption {
  position: absolute;
  top: 50%;
  transform: translateY(-30%);
}
.carousel-caption h1 {
  font-size: 500%;
  text-shadow: 1px 1px 7px #1e013d;
}
.carousel-caption h2 {
  font-size: 200%;
  font-weight: 500;
  text-shadow: 1px 1px 1px 7px #1e013d;
}

.container-fluid a {
  color: #000;
}
.container-fluid a:hover {
  color: #3c007a;
  text-decoration: none;
}
.btn-primary {
  background-color: #3c007a;
  border: 1px solid black;
}
.btn-primary:hover {
  background-color: #180031;
  border: 1px solid grey;
}

.btn-secondary {
  border: 1px solid #3c007a;
}
</style>
