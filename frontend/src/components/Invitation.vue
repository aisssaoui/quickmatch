<template>
  <div>
    <div v-if="isSignedIn">
      <div v-if="InvitationsToShow.rowCount != 0">
        <div v-for="row in InvitationsToShow.rows" :key="row.meet">
          <div v-if="row.status === null">
            <hr />

            <v-list-item two-line>
              <v-list-item-content>
                <v-list-item-title class="font-weight-bold">Invitation pour un match</v-list-item-title>
              </v-list-item-content>
            </v-list-item>

            <v-list-item two-line>
              <v-list-item-content>
                <v-list-item-title class="font-weight-bold">Localisation</v-list-item-title>
                <v-list-item-subtitle class="headline">{{row.location}}</v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>

            <v-list-item two-line>
              <v-list-item-content>
                <v-list-item-title class="font-weight-bold">Date</v-list-item-title>
                <v-list-item-subtitle
                  class="headline"
                >Le {{DayInFrench(row.repeat_day)}} de {{row.start_hour}} à {{row.end_hour}}</v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>

            <v-btn class="btn" rounded color="red" v-on:click="declineInv(row.meet)">Refuser</v-btn>
            <v-btn class="btn" rounded color="green" v-on:click="acceptInv(row.meet)">Accepter</v-btn>
            <br />
            <br />
          </div>
        </div>
      </div>


      <div v-for="row in InvitationsClubToShow" :key="row.id">
          <hr />

          <v-list-item two-line>
            <v-list-item-content>
              <v-list-item-title class="font-weight-bold">Invitation pour un match</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item two-line>
            <v-list-item-content>
              <v-list-item-title class="font-weight-bold">Localisation</v-list-item-title>
              <v-list-item-subtitle class="headline">{{row.location}}</v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>

          <v-list-item two-line>
            <v-list-item-content>
              <v-list-item-title class="font-weight-bold">Date</v-list-item-title>
              <v-list-item-subtitle
                class="headline"
              >Le {{DayInFrench(row.repeat_day)}} de {{row.start_hour}} à {{row.end_hour}}</v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>

          <v-btn class="btn" rounded color="red" v-on:click="declineInv(row.meet)">Refuser</v-btn>
          <v-btn class="btn" rounded color="green" v-on:click="acceptInv(row.meet)">Accepter</v-btn>
          <br />
          <br />
      </div>


      <br />
      <br />
      <p style="text-align: center">Vous n'avez pas d'autres invitations</p>
    </div>
    <WorkInProgress v-else></WorkInProgress>
  </div>
</template>

<script>
import WorkInProgress from "./WorkInProgress";
import axios from "axios";
import store from "../store";

export default {
  components: {
    WorkInProgress
  },
  data() {
    return {
      InvitationsToShow: {},
      InvitationsClubToShow: {}
    };
  },
  async created() {
    const player = await axios.get(
      "https://dbcontrol.quickmatch.fr/dbcontrol/api/v1/CalendarBPlayer/" +
        this.id,
      {
        responseType: "json"
      }
    );
    this.InvitationsToShow = player.data;

    const invitationsClub = await axios
      .get("https://dbcontrol.quickmatch.fr/dbcontrol/api/v1/InvitationClub/" + this.id);
    this.InvitationsClubToShow = invitationsClub.data.rows;
  },
  methods: {
    DayInFrench: function(day) {
      if (day == "Monday") return "Lundi";
      if (day == "Tuesday") return "Mardi";
      if (day == "Wednesday") return "Mercredi";
      if (day == "Friday") return "Vendredi";
      if (day == "Saturday") return "Samedi";
      if (day == "Sunday") return "dimanche";
      if (day == "Thursday") return "jeudi";
    },
    acceptInv: async function(mid) {
      let invID;
      let apiRep = await axios.get(
        "https://dbcontrol.quickmatch.fr/dbcontrol/api/v1/CalendarMInv/" +
          this.id +
          "/" +
          mid,
        { responseType: "json" }
      );
      invID = apiRep.data.rows[0].id;
      await axios.put(
        "https://dbcontrol.quickmatch.fr/dbcontrol/api/v1/Invitations/" + invID,
        {
          status: true
        }
      );
      alert("Match accepté !");
      this.$router.go();
    },
    declineInv: async function(mid) {
      let invID;
      let apiRep = await axios.get(
        "https://dbcontrol.quickmatch.fr/dbcontrol/api/v1/CalendarMInv/" +
          this.id +
          "/" +
          mid,
        { responseType: "json" }
      );
      invID = apiRep.data.rows[0].id;
      await axios.put(
        "https://dbcontrol.quickmatch.fr/dbcontrol/api/v1/Invitations/" + invID,
        {
          status: false
        }
      );
      alert("Match refusé !");
      this.$router.go();
    },
    acceptClubInv: async function(ci_id, cid, pid, club_name) {
      await axios
        .post("https://dbcontrol.quickmatch.fr/dbcontrol/api/v1/PlayerClubs",
          {
            club: cid,
            player: pid,
            is_admin: false
          })
        .then(response => {
          alert("Invitation du club '" + club_name + "' accepté !");
          this.$router.go();
        })
        .catch(e => {
          alert("Echec, veuillez réessayer, si le problème persiste, réessayer plus tard");
          this.$router.go();
        });
    },
    declineClubInv: async function(ci_id, club_name) {
      await axios
        .delete("https://dbcontrol.quickmatch.fr/dbcontrol/api/v1/InvitationClub/" + ci_id)
        .then(response => {
          alert("Invitation du club '" + club_name + "' refusé !");
          this.$router.go();
        })
        .catch(e => {
          alert("Echec, veuillez réessayer, si le problème persiste, réessayer plus tard");
          this.$router.go();
        });
    }
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
  }
};
</script>

<style>
</style>
