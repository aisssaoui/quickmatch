<template>
    <!-- Navigation barre -->
    <nav class="navbar navbar-expand-md navbar-light bg-light sticky-top">
      <div class="container-fluid">
        <a class="navbar-brand" href="#">
          <router-link to="/">
          <img
            alt="quickmatch-logo"
            src="../assets/logo-quickmatch.png"
            style="width:80px;height:80px;"
          />
          </router-link>
        </a>
        <button
          class="navbat-toggler"
          type="button"
          data-toggle="collapse"
          data-target="#navbarResponsive"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
          <ul class="navbar-nav ml-auto">
            <li class="nav-item active">
              <a class="nav-link" href="#">Home</a>
            </li>
            <li class="nav-item active">
              <a class="nav-link" href="#">Agenda</a>
            </li>
            <li class="nav-item active">
              <a class="nav-link" href="#">Statistiques</a>
            </li>
            <li class="nav-item active">
              <a class="nav-link" href="#">Matchs</a>
            </li>
            <li class="nav-item active">
              <a class="nav-link" href="#">Se Connecter</a>
            </li>
          </ul>
        </div>
      </div>
    </nav>
</template>

<script>
</script>

<style>

#logo {
  text-align: left;
}
.navbar{
  padding: .8rem;
  }
  
.navbar-nav li {
  padding-right: 20px;
}

.nav-link {
  font-size: 1.1em !important;
}


</style>