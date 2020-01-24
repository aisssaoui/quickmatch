<template>
  <div>
    <div v-if="isSignedIn">
      <!-- ///////////////////////////////////////////////////////////////// -->

      <div id="col-l">
        <div id="vos_clubs">
          <v-card dark color="#000">
            <v-list-item three-line>
              <v-list-item-content>
                <v-list-item-title class="display-2">Vos clubs</v-list-item-title>
              </v-list-item-content>
            </v-list-item>

            <div v-for="row in clubsInToShow" :key="row.id">
              <hr />

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

              <v-btn
                class="btn"
                rounded
                color="#666"
                v-on:click="leave_club(row.club, row.club_name)"
              >Quitter le club</v-btn>
              <br />
              <br />
            </div>
          </v-card>
        </div>
      </div>

      <div id="col-r">
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
                <v-checkbox v-model="private_club" label="Club privé"></v-checkbox>
              </v-col>
            </v-form>
            <v-btn
              class="btn"
              rounded
              align="left"
              color="#666"
              v-on:click="create_club"
            >Créer le club</v-btn>
            <br />
            <br />
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
              <hr />

              <v-list-item two-line>
                <v-list-item-content>
                  <v-list-item-title class="font-weight-bold">Nom du Club</v-list-item-title>
                  <v-list-item-subtitle class="headline">{{ row.club_name }}</v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>

              <v-list-item two-line>
                <v-list-item-content>
                  <v-list-item-title class="font-weight-bold">Club privé</v-list-item-title>
                  <v-list-item-subtitle v-if="row.private_club" class="headline">oui</v-list-item-subtitle>
                  <v-list-item-subtitle v-else class="headline">non</v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>

              <v-btn
                class="btn"
                rounded
                color="#666"
                v-on:click="join_club(row.id, row.club_name)"
              >Demander à rejoindre</v-btn>
              <br />
              <br />
            </div>
          </v-card>
        </div>
      </div>

      <!-- ///////////////////////////////////////////////////////////////// -->
    </div>
    <WorkInProgress v-else></WorkInProgress>
  </div>
</template>

<style>
.btn {
  float: right;
  margin-right: 2%;
}
#col-l {
  width: 45%;
  margin-left: 3%;
  float: left;
}
#col-r {
  width: 45%;
  margin-right: 3%;
  float: right;
}
#vos_clubs {
  margin-top: 2%;
  margin-bottom: 2%;
}
#creer_club {
  margin-top: 2%;
}
#rejoindre_club {
  margin-top: 2%;
  margin-bottom: 2%;
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
      valid: false,
      club_name: null,
      private_club: false,
      club_nameRules: [
        v => !!v || "Nom de club requis",
        v => v.length >= 2 || "Nom de club trop court",
        v =>
          /^[a-zA-Z \-éèçîïœžâêôàûùâãäåæçëìíîïðñòóôõúûüýö]+$/.test(v) ||
          "Nom de club invalide"
      ]
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
    async join_club(id, name) {
      console.log(id);
      let apiRep1 = null;
      apiRep1 = await axios.post(
        "https://dbcontrol.quickmatch.fr/dbcontrol/api/v1/PlayerClubs",
        {
          club: id,
          player: this.id,
          is_admin: true
        }
      );
      alert("vous avez rejoins le club " + name);
      this.$router.go();
    },

    async leave_club(cid, club_name) {
      let apiRep = await axios.get(
        "https://dbcontrol.quickmatch.fr/dbcontrol/api/v1/PlayerClubsCount/" +
          cid,
        {
          responseType: "json"
        }
      );
      let nb = apiRep.data.nb;

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
          alert(
            "Echec, veuillez réessayer, si le problème persiste, réessayer plus tard"
          );
        });

      if (nb == 1) {
        await axios.delete(
          "https://dbcontrol.quickmatch.fr/dbcontrol/api/v1/Clubs/" + cid
        );
      }

      this.$router.go();
    }
  }
};
</script>
