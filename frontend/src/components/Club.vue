<template>
  <div>
    <div v-if="!isSignedIn">
      <!-- /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// -->

      <div v-if="!switch_menu">
        <div class="col-l">
          <div id="vos_clubs">
            <v-card dark color="#000">
              <v-list-item three-line>
                <v-list-item-content>
                  <v-list-item-title class="display-2">Vos clubs</v-list-item-title>
                </v-list-item-content>
              </v-list-item>


              <div v-for="row in clubsInToShow" :key="row.id">
                <hr>

                <v-list-item two-line>
                  <v-list-item-content>
                    <v-list-item-title class="font-weight-bold">Nom du Club</v-list-item-title>
                    <v-list-item-subtitle class="headline">{{ row.club_name }}</v-list-item-subtitle>
                  </v-list-item-content>
                </v-list-item>

                <v-list-item two-line>
                  <v-list-item-content>
                    <v-list-item-title class="font-weight-bold">Date de création</v-list-item-title>
                    <v-list-item-subtitle class="headline">{{ row.creation_date }}</v-list-item-subtitle>
                  </v-list-item-content>
                </v-list-item>

                <v-list-item two-line>
                  <v-list-item-content>
                    <v-list-item-title class="font-weight-bold">Club privé</v-list-item-title>
                    <v-list-item-subtitle v-if="row.private_club" class="headline">oui</v-list-item-subtitle>
                    <v-list-item-subtitle v-else class="headline">non</v-list-item-subtitle>
                  </v-list-item-content>
                </v-list-item>

                <v-list-item two-line>
                  <v-list-item-content>
                    <v-list-item-title class="font-weight-bold">Administrateur</v-list-item-title>
                    <v-list-item-subtitle v-if="row.is_admin" class="headline">oui</v-list-item-subtitle>
                    <v-list-item-subtitle v-else class="headline">non</v-list-item-subtitle>
                  </v-list-item-content>
                </v-list-item>

                <div v-if="row.is_admin">
                  <v-btn class="btn" rounded color="#666" v-on:click="switch_menu=true;name_club_switch=row.club_name">gérer le club</v-btn>
                  <br><br>
                </div>

                <v-btn class="btn" rounded color="#666" v-on:click="leave_club(row.id, row.club_name, row.is_admin)">Quitter le club</v-btn>
                <br><br>
              </div>
            </v-card>
          </div>
        </div>

        <div class="col-r">
          <div id="creer_club">
            <v-card dark color="#000">
              <v-form v-model="valid">
                <v-list-item three-line>
                  <v-list-item-content>
                    <v-list-item-title class="display-2">Créer un club</v-list-item-title>
                  </v-list-item-content>
                </v-list-item>
                <v-col class="py-0" cols="4" md="12">
                  <v-text-field
                  v-model="club_name"
                  :rules="club_nameRules"
                  :counter="50"
                  label="Nom du club"
                  required
                  outlined
                  filled
                  ></v-text-field>
                  <v-checkbox
                  v-model="private_club"
                  label="Club privé"
                  ></v-checkbox>
                </v-col>
              </v-form>
              <v-btn class="btn" rounded align="left" color="#666" v-on:click="create_club">Créer le club</v-btn>
              <br><br>
            </v-card>
          </div>

          <div id="rejoindre_club">
            <v-card dark color="#000">
              <v-list-item three-line>
                <v-list-item-content>
                  <v-list-item-title class="display-2">Rejoindre un club</v-list-item-title>
                </v-list-item-content>
              </v-list-item>


              <div v-for="row in clubsNotInToShow" :key="row.id">
                <hr>

                <v-list-item two-line>
                  <v-list-item-content>
                    <v-list-item-title class="font-weight-bold">Nom du Club</v-list-item-title>
                    <v-list-item-subtitle class="headline">{{ row.club_name }}</v-list-item-subtitle>
                  </v-list-item-content>
                </v-list-item>


                <v-list-item two-line>
                  <v-list-item-content>
                    <v-list-item-title class="font-weight-bold">Date de création</v-list-item-title>
                    <v-list-item-subtitle class="headline">{{ row.creation_date }}</v-list-item-subtitle>
                  </v-list-item-content>
                </v-list-item>

                <v-btn class="btn" rounded color="#666" v-on:click="join_club(row.id, row.club_name)">Demander à rejoindre</v-btn>
                <br><br>
              </div>
            </v-card>
          </div>
        </div>
      </div>

      <!-- /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// -->

      <div v-else>
        <div class="col-l">
          <div id="liste_des_joueurs">
            <v-card dark color="#000">
              <v-list-item three-line>
                <v-list-item-content>
                  <v-list-item-title class="display-2">Joueurs du club {{ name_club_switch }}</v-list-item-title>
                </v-list-item-content>
              </v-list-item>

              <v-btn class="btn" rounded color="#666" v-on:click="switch_menu=false">retourner au menu club</v-btn>
              <br><br>

              <div v-for="row in playersInClub" :key="row.id">
                <hr>

                <v-list-item two-line>
                  <v-list-item-content>
                    <v-list-item-title class="font-weight-bold">Nom du joueur</v-list-item-title>
                    <v-list-item-subtitle class="headline">{{ row.surname }}</v-list-item-subtitle>
                  </v-list-item-content>
                </v-list-item>

                <v-list-item two-line>
                  <v-list-item-content>
                    <v-list-item-title class="font-weight-bold">Prénom du joueur</v-list-item-title>
                    <v-list-item-subtitle class="headline">{{ row.first_name }}</v-list-item-subtitle>
                  </v-list-item-content>
                </v-list-item>

                <v-list-item two-line>
                  <v-list-item-content>
                    <v-list-item-title class="font-weight-bold">Pseudo du joueur</v-list-item-title>
                    <v-list-item-subtitle class="headline">{{ row.pseudo }}</v-list-item-subtitle>
                  </v-list-item-content>
                </v-list-item>

                <div v-if="! row.is_admin">
                  <v-btn class="btn" rounded color="#666" >nommer admin</v-btn>
                  <br><br>

                  <v-btn class="btn" rounded color="#666" >supprimer</v-btn>
                  <br><br>
                </div>
              </div>
            </v-card>
          </div>
        </div>

        <div class="col-r">
          <div id="ajout_joueurs">
            <v-card dark color="#000">
              <v-list-item three-line>
                <v-list-item-content>
                  <v-list-item-title class="display-2">Ajouter un joueur</v-list-item-title>
                </v-list-item-content>
              </v-list-item>

              <div v-for="row in playersNotInClub" :key="row.id">
                <hr>

                <v-list-item two-line>
                  <v-list-item-content>
                    <v-list-item-title class="font-weight-bold">Nom du joueur</v-list-item-title>
                    <v-list-item-subtitle class="headline">{{ row.surname }}</v-list-item-subtitle>
                  </v-list-item-content>
                </v-list-item>

                <v-list-item two-line>
                  <v-list-item-content>
                    <v-list-item-title class="font-weight-bold">Prénom du joueur</v-list-item-title>
                    <v-list-item-subtitle class="headline">{{ row.first_name }}</v-list-item-subtitle>
                  </v-list-item-content>
                </v-list-item>

                <v-list-item two-line>
                  <v-list-item-content>
                    <v-list-item-title class="font-weight-bold">Pseudo du joueur</v-list-item-title>
                    <v-list-item-subtitle class="headline">{{ row.pseudo }}</v-list-item-subtitle>
                  </v-list-item-content>
                </v-list-item>
              </div>
            </v-card>
          </div>
        </div>
      </div>

      <!-- /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// -->
    </div>
    <WorkInProgress v-else></WorkInProgress>
  </div>
</template>

<style>
  .btn{
    float: right;
    margin-right: 2%
  }
  .col-l{
    width: 45%;
    margin-left: 3%;
    float: left;
  }
  .col-r{
    width: 45%;
    margin-right: 3%;
    float: right;
  }
  #vos_clubs{
    margin-top: 2%;
    margin-bottom: 2%;
    height: 800px;
    overflow: scroll;
  }
  #creer_club{
    margin-top: 2%;
    height: 285px;
  }
  #rejoindre_club{
    margin-top: 2%;
    margin-bottom: 2%;
    height: 500px;
    overflow: scroll;
  }
  #liste_des_joueurs{
    margin-top: 2%;
    margin-bottom: 2%;
    height: 800px;
    overflow: scroll;
  }
  #ajout_joueurs{
    margin-top: 2%;
    margin-bottom: 2%;
    height: 800px;
    overflow: scroll;
  }
</style>

<script>
import store from "../store";
import axios from "axios";
import router from "../router";
import WorkInProgress from "./WorkInProgress";
export default {
  components: {
    WorkInProgress
  },
  data() {
    return {
      clubsInToShow: [
        {"club_name": "justice league", "creation_date": "1970-01-01 00:00:01", "private_club": true, "is_admin": false},
        {"club_name": "tortue ninja", "creation_date": "1970-01-01 00:00:02", "private_club": true, "is_admin": true},
        {"club_name": "lyoko", "creation_date": "1970-01-01 00:00:03", "private_club": false, "is_admin": false},
        {"club_name": "doroté", "creation_date": "1970-01-01 00:00:04", "private_club": true, "is_admin": true}
      ],
      clubsNotInToShow: [
        {"club_name": "avengers", "creation_date": "1971-01-01 00:00:01"},
        {"club_name": "scrabble", "creation_date": "1970-01-01 20:00:01"},
        {"club_name": "scrabble", "creation_date": "1970-01-01 20:00:01"},
        {"club_name": "scrabble", "creation_date": "1970-01-01 20:00:01"},
        {"club_name": "scrabble", "creation_date": "1970-01-01 20:00:01"},
        {"club_name": "scrabble", "creation_date": "1970-01-01 20:00:01"}
      ],
      playersInClub: [
        {"surname": "wayne", "first_name": "bruce", "pseudo": "batman", "is_admin": false},
        {"surname": "michael", "first_name": "bay", "pseudo": "BOUUUUUUM", "is_admin": true},
        {"surname": "kent", "first_name": "clark", "pseudo": "superman", "is_admin": false},
        {"surname": "pi", "first_name": "ka", "pseudo": "chu", "is_admin": true},
      ],
      playersNotInClub: [
        {"surname": "ragnar", "first_name": "lodbrok", "pseudo": "odin"},
        {"surname": "bond", "first_name": "james", "pseudo": "007"},
        {"surname": "god of", "first_name": "war", "pseudo": "kratos"},
        {"surname": "a", "first_name": "b", "pseudo": "ab"},
      ],
      valid: false,
      club_name: null,
      private_club: false,
      club_nameRules: [
        v => !!v || "Nom de club requis",
        v => v.length >= 2 || "Nom de club trop court",
        v =>
          /^[a-zA-Z \-éèçîïœžâêôàûùâãäåæçëìíîïðñòóôõúûüýö]+$/.test(v) ||
          "Nom de club invalide"
      ],
      switch_menu: false,
      id_club_switch: -1,
      name_club_switch: "",
    };
  },
  async created() {
    const clubs_in = await axios.get(
      "https://dbcontrol.quickmatch.fr/dbcontrol/api/v1/PlayerClubs/pid" +
        this.id,
      {
        responseType: "json"
      }
    );
    this.clubsInToShow = clubs_in.data.rows;
    const clubs_not_in = await axios.get(
      "https://dbcontrol.quickmatch.fr/dbcontrol/api/v1/PlayerClubs/npid" +
        this.id,
      {
        responseType: "json"
      }
    );
    this.clubsNotInToShow = clubs_not_in.data.rows;
  },
  computed: {
    isSignedIn() {
      store.dispatch("isSignedIn");
      return store.getters.isSignedIn;
    },
    id() {
      store.dispatch("id");
      return store.getters.id;
    }
  },
  methods: {
    async create_club() {
      let apiRep = null;
      apiRep = await axios.post(
        "https://dbcontrol.quickmatch.fr/dbcontrol/api/v1/Clubs",
        {
          club_name: this.club_name,
          private_club: this.private_club
        }
      );
      let cid = apiRep.data.id;

      let apiRep1 = null;
      apiRep1 = await axios.post(
        "https://dbcontrol.quickmatch.fr/dbcontrol/api/v1/PlayerClubs",
        {
          club: cid,
          player: this.id,
          is_admin: true
        }
      );
      alert("Club " + this.club_name + " créé !");
      this.$router.go();
    },
    async join_club(cid, club_name) {
      console.log(cid);
      let apiRep1 = null;
      apiRep1 = await axios.post(
        "https://dbcontrol.quickmatch.fr/dbcontrol/api/v1/PlayerClubs",
        {
          club: id,
          player: this.id,
          is_admin: false
        }
      );
      alert("vous avez rejoins le club " + club_name);
      this.$router.go();
    },
    async leave_club(cid, club_name, is_admin) {
      let apiRep = await axios.get(
        "https://dbcontrol.quickmatch.fr/dbcontrol/api/v1/PlayerClubsCountAdmin/" +
          cid,
        {
          responseType: "json"
        }
      );
      let nb = apiRep.data.nb;
      let destroy_club = true;

      if (nb == 1 && is_admin) {
        if (! confirm("vous êtes le dernier admin de ce club, le quitter le détruira, souhaitez vous continuer ?")){
          destroy_club = false;
        }
      }

      if (destroy_club){
        await axios.delete(
          "https://dbcontrol.quickmatch.fr/dbcontrol/api/v1/Clubs/" + cid
        );

        await axios
          .delete(
            "https://dbcontrol.quickmatch.fr/dbcontrol/api/v1/PlayerClubs/" +
              cid +
              "&" +
              this.id
          )
          .then(response => {
            alert("Vous avez quitté le club " + club_name);
          })
          .catch(e => {
            alert("Echec, veuillez réessayer, si le problème persiste, réessayer plus tard");
          });
      }

      this.$router.go();
    },
  }
};
</script>
