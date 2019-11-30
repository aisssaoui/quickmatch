<template>
  <div class="hello">
    <!-- image slider -->
    <div id="slide" class="carousel slide" data-ride="carousel">
      <ul class="carousel-indicators">
        <li data-target="#slide" data-slide-to="0" class="active"></li>
        <li data-target="#slide" data-slide-to="1"></li>
        <li data-target="#slide" data-slide-to="2"></li>
      </ul>
      <div class="carousel-inner">
        <div class="carousel-item active">
          <img src="../assets/foot1.jpg" />
          <div id="caption" class="carousel-caption">
            <h1 class="display-2">QuickMatch</h1>
            <h3 id="head-c">Plus jamais du temps à perdre</h3>
            <button type="button" class="btn btn-outline-light btn-lg">Se connecter</button>``
            <button type="button" class="btn btn-primary btn-lg">S'inscrire</button>
          </div>
        </div>
        <div class="carousel-item">
          <img src="../assets/foot2.jpg" />
        </div>
        <div class="carousel-item">
          <img src="../assets/foot3.jpg" />
        </div>
      </div>
    </div>

    <!-- Jumbotron -->
    <div class="container-fluid">
      <div class="row jumbotron">
        <div class="col-xs-12 col-sm-12 col-md-9 col-lg-9 col-xl-10">
          <p class="lead">Avec Quick Match, une meilleure organisation de vos matchs.</p>
        </div>
          <div class="col-xs-12 col-sm-12 col-md-3 col-lg-3 col-xl-2">
            <a href="#"><button type="button" class="btn btn-outline-secondary btn-lg">Regoinez nous</button></a>
          </div>
        </div>
      </div>
  </div>
</template>

<script>
export default {
  name: "HelloWorld",
  props: {
    msg: String
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
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
}
#caption {
  margin-bottom: 300px;
}
#head-c {
  margin-top: 0px;
  margin-bottom: 10px;
}
.display-2 {
  margin-bottom: 0px;
}
</style>
