<template>
  <div>
    <div v-if="isSignedIn">
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


              <div v-if="clubsInToShow.length != 0">
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
                      <v-list-item-subtitle class="headline">{{ new Date(row.creation_date).toLocaleDateString('fr-FR') }}</v-list-item-subtitle>
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
                    <v-btn class="btn" rounded color="#666" v-on:click="manage_club(row.club_name, row.id)">gérer le club</v-btn>
                    <br><br>
                  </div>

                  <v-btn class="btn" rounded color="#666" v-on:click="leave_club(row.id, row.club_name, row.is_admin)">Quitter le club</v-btn>
                  <br><br>
                </div>
              </div>


              <div v-else>
                <hr>
                <v-list-item two-line>
                  <v-list-item-content>
                    <v-list-item-title class="font-weight-bold">Vous n'êtes dans aucun club</v-list-item-title>
                  </v-list-item-content>
                </v-list-item>
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
                  <v-text-field v-model="club_name" :rules="club_nameRules" :counter="50" label="Nom du club" required outlined filled></v-text-field>
                  <v-checkbox v-model="private_club" label="Club privé"></v-checkbox>
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


              <div v-if="clubsNotInToShow.length != 0">
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
                      <v-list-item-subtitle class="headline">{{ new Date(row.creation_date).toLocaleDateString('fr-FR') }}</v-list-item-subtitle>
                    </v-list-item-content>
                  </v-list-item>

                  <v-btn class="btn" rounded color="#666" v-on:click="join_club(row.id, row.club_name)">Demander à rejoindre</v-btn>
                  <br><br>
                </div>
              </div>


              <div v-else>
                <hr>
                <v-list-item two-line>
                  <v-list-item-content>
                    <v-list-item-title class="font-weight-bold">Aucun club disponible</v-list-item-title>
                  </v-list-item-content>
                </v-list-item>
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

              <v-btn class="btn" rounded color="#666" v-on:click="return_to_main_menu()">retourner au menu club</v-btn>
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
                  <v-btn class="btn" rounded color="#666" v-on:click="promote_to_admin(row.id, id_club_switch, row.pseudo)">nommer admin</v-btn>
                  <br><br>

                  <v-btn class="btn" rounded color="#666" v-on:click="delete_from_club(row.id, id_club_switch, row.pseudo)">supprimer</v-btn>
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


              <div v-if="playersNotInClub.length != 0">
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

                  <v-btn class="btn" rounded color="#666" v-on:click="add_to_club(row.id, id_club_switch, row.pseudo)">ajouter</v-btn>
                  <br><br>
                </div>
              </div>


              <div v-else>
                <hr>
                <v-list-item two-line>
                  <v-list-item-content>
                    <v-list-item-title class="font-weight-bold">Aucun joueur disponible</v-list-item-title>
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
      clubsInToShow: {},
      clubsNotInToShow: {},
      playersInClub: {},
      playersNotInClub: {},
      valid: false,
      club_name: null,
      private_club: false,
      club_nameRules: [
        v => !!v || "Nom de club requis",
        v => v.length >= 2 || "Nom de club trop court",
        v =>
          /^[a-zA-Z1-9_ \-éèçîïœžâêôàûùâãäåæçëìíîïðñòóôõúûüýö]+$/.test(v) ||
          "Nom de club invalide"
      ],
      switch_menu: false,
      id_club_switch: -1,
      name_club_switch: "",
    };
  },
  async created() {
    var clubs_in = await axios
      .get(
        "https://dbcontrol.quickmatch.fr/dbcontrol/api/v1/PlayerClubs/pid" +
          this.id,
        {
          responseType: "json"
        }
      )
      .catch(e => {
        if (this.isSignedIn()){
          alert("Une erreur s'est produite, nous allons rafraichir la page, si le problème persiste, quittez la page");
          this.$router.go();
        }
      });
    this.clubsInToShow = clubs_in.data.rows;
    var clubs_not_in = await axios
      .get(
        "https://dbcontrol.quickmatch.fr/dbcontrol/api/v1/PlayerClubs/npid" +
          this.id,
        {
          responseType: "json"
        }
      )
      .catch(e => {
        if (this.isSignedIn()){
          alert("Une erreur s'est produite, nous allons rafraichir la page, si le problème persiste, quittez la page");
          this.$router.go();
        }
      });
    this.clubsNotInToShow = clubs_not_in.data.rows;
  },
  computed: {
    isSignedIn() {
      store.dispatch("isSignedIn");
      return store.getters.isSignedIn;
    },
    id() {
      store.dispatch("setID");
      return store.getters.id;
    }
  },
  methods: {
    async create_club() {
      let apiRep = null;
      apiRep = await axios
        .post(
          "https://dbcontrol.quickmatch.fr/dbcontrol/api/v1/Clubs",
          {
            club_name: this.club_name,
            private_club: this.private_club
          }
        )
        .catch(e => {
          if (this.name_already_exist(this.club_name)){
            alert("un club porte déjà le même nom, choisissez-en un autre");
            this.$router.go();
          }
          else{
            alert("Echec, veuillez réessayer, si le problème persiste, réessayer plus tard");
          }
        });
      let cid = apiRep.data.id;
      let apiRep1 = null;
      apiRep1 = await axios
        .post(
          "https://dbcontrol.quickmatch.fr/dbcontrol/api/v1/PlayerClubs",
          {
            club: cid,
            player: this.id,
            is_admin: true
          }
        )
        .then(response => {
          alert("Club " + this.club_name + " créé !");
          this.$router.go();
        })
        .catch(e => {
          alert("Echec, veuillez réessayer, si le problème persiste, réessayer plus tard");
        });
    },
    async join_club(cid, club_name) {
      let apiRep1 = null;
      apiRep1 = await axios.post(
        "https://dbcontrol.quickmatch.fr/dbcontrol/api/v1/PlayerClubs",
        {
          club: cid,
          player: this.id,
          is_admin: false
        }
      )
      .then(response => {
        alert("Vous avez rejoins le club " + club_name);
      })
      .catch(e => {
        alert("Echec, veuillez réessayer, si le problème persiste, réessayer plus tard");
      });
      this.$router.go();
    },
    async leave_club(cid, club_name, is_admin) {
      let count_admin = await axios
        .get("https://dbcontrol.quickmatch.fr/dbcontrol/api/v1/PlayerClubsCountAdmin/" + cid,
          {
            responseType: "json"
          }
        )
        .catch(e => {
          alert("Echec, veuillez réessayer, si le problème persiste, réessayer plus tard");
          this.$router.go();
        });
      let nb = count_admin.data.rows[0]["nb"];
      let destroy_club = true;

      if (nb == 1 && is_admin) {
        if (! confirm("vous êtes le dernier admin de ce club, le quitter le détruira, souhaitez vous continuer ?")){
          destroy_club = false;
        }
      }

      if (destroy_club){
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
            this.$router.go();
          });
        if (nb == 1 && is_admin){
          await axios
            .delete("https://dbcontrol.quickmatch.fr/dbcontrol/api/v1/Clubs/" + cid)
            .then(response => {
              alert("Le club " + club_name + " a été détruit");
            });
        }
      }
      this.$router.go();
    },
    async promote_to_admin(pid, cid, pseudo){
      await axios
        .put("https://dbcontrol.quickmatch.fr/dbcontrol/api/v1/PlayerClubsPromoteToAdmin/" + pid + "&" + cid)
        .then(response => {
          alert("Vous avez promu admin " + pseudo);
          this.manage_club(this.name_club_switch, this.id_club_switch);
        })
        .catch(e => {
          alert("Echec, veuillez réessayer, si le problème persiste, réessayer plus tard");
        });
    },
    async delete_from_club(pid, cid, pseudo){
      await axios
        .delete("https://dbcontrol.quickmatch.fr/dbcontrol/api/v1/PlayerClubs/" + cid + "&" + pid)
        .then(response => {
          alert("Vous avez renvoyer " + pseudo + " du club " + this.name_club_switch);
          this.manage_club(this.name_club_switch, this.id_club_switch);
        })
        .catch(e => {
          alert("Echec, veuillez réessayer, si le problème persiste, réessayer plus tard");
          this.$router.go();
        });
    },
    async add_to_club(pid, cid, pseudo){
      await axios
        .post("https://dbcontrol.quickmatch.fr/dbcontrol/api/v1/PlayerClubs/",
          {
            club: cid,
            player: pid,
            is_admin: false
          })
        .then(response => {
          alert("Vous avez ajouter " + pseudo + " au club " + this.name_club_switch);
          this.manage_club(this.name_club_switch, this.id_club_switch);
        })
        .catch(e => {
          alert("Echec, veuillez réessayer, si le problème persiste, réessayer plus tard");
          this.$router.go();
        });
    },
    async manage_club(club_name, cid){
      this.name_club_switch = club_name;
      this.id_club_switch = cid;
      let players_in = await axios
        .get("https://dbcontrol.quickmatch.fr/dbcontrol/api/v1/PlayerClubs/cid" + cid, {responseType: "json"})
        .catch(e => {
          alert("Une erreur s'est produite, nous allons rafraichir la page, si le problème persiste, quittez la page");
          this.$router.go();
        });
      this.playersInClub = players_in.data.rows;
      let players_not_in = await axios
        .get("https://dbcontrol.quickmatch.fr/dbcontrol/api/v1/PlayerClubs/ncid" + cid, {responseType: "json"})
        .catch(e => {
          alert("Une erreur s'est produite, nous allons rafraichir la page, si le problème persiste, quittez la page");
          this.$router.go();
        });
      this.playersNotInClub = players_not_in.data.rows;
      this.switch_menu = true;
    },
    async return_to_main_menu(){
      let clubs_in = await axios
        .get("https://dbcontrol.quickmatch.fr/dbcontrol/api/v1/PlayerClubs/pid" + this.id, {responseType: "json"})
        .catch(e => {
          alert("Une erreur s'est produite, nous allons rafraichir la page, si le problème persiste, quittez la page");
          this.$router.go();
        });
      this.clubsInToShow = clubs_in.data.rows;
      let clubs_not_in = await axios
        .get("https://dbcontrol.quickmatch.fr/dbcontrol/api/v1/PlayerClubs/npid" + this.id, {responseType: "json"})
        .catch(e => {
          alert("Une erreur s'est produite, nous allons rafraichir la page, si le problème persiste, quittez la page");
          this.$router.go();
        });
      this.clubsNotInToShow = clubs_not_in.data.rows;
      this.switch_menu = false;
    },
    name_already_exist(club_name){
      for (var row_in in this.clubsInToShow){
        if (this.clubsInToShow[row_in]["club_name"] == club_name){
          return true;
        }
      }
      for (var row_not_in in this.clubsNotInToShow){
        if (this.clubsNotInToShow[row_not_in]["club_name"] == club_name){
          return true;
        }
      }
      return false;
    },
  }
};
</script>
