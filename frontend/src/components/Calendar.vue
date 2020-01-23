<template>
  <div v-if="isSignedIn">
    <v-row class="fill-height">
      <v-col>
        <v-sheet height="64">
          <v-toolbar flat color="white">
            <v-btn outlined class="mr-4" @click="setToday">Aujourd'hui</v-btn>
            <v-btn outlined class="mr-4" to="CreateMatch">Créer match</v-btn>
            <v-spacer></v-spacer>
            <v-btn fab text small @click="prev">
              <v-icon small>mdi-chevron-left</v-icon>
            </v-btn>
            <v-toolbar-title>{{ title }}</v-toolbar-title>
            <v-btn fab text small @click="next">
              <v-icon small>mdi-chevron-right</v-icon>
            </v-btn>
            <v-spacer></v-spacer>
            <v-menu bottom right>
              <template v-slot:activator="{ on }">
                <v-btn outlined v-on="on">
                  <span>{{ typeToLabel[type] }}</span>
                  <v-icon right>mdi-menu-down</v-icon>
                </v-btn>
              </template>
              <v-list>
                <v-list-item @click="type = 'day'">
                  <v-list-item-title>Jour</v-list-item-title>
                </v-list-item>
                <v-list-item @click="type = 'week'">
                  <v-list-item-title>Semaine</v-list-item-title>
                </v-list-item>
                <v-list-item @click="type = 'month'">
                  <v-list-item-title>Mois</v-list-item-title>
                </v-list-item>
                <v-list-item @click="type = '4day'">
                  <v-list-item-title>4 jours</v-list-item-title>
                </v-list-item>
              </v-list>
            </v-menu>
          </v-toolbar>
        </v-sheet>
        <v-sheet height="600">
          <v-calendar
            ref="calendar"
            v-model="focus"
            color="primary"
            :events="events"
            :event-color="getEventColor"
            :event-margin-bottom="3"
            :type="type"
            @click:event="showEvent"
            @click:more="viewDay"
            @click:date="viewDay"
            @change="updateRange"
          ></v-calendar>
          <v-menu
            v-model="selectedOpen"
            :close-on-content-click="false"
            :activator="selectedElement"
            full-width
            offset-x
          >
            <v-card color="grey lighten-4" min-width="350px" flat>
              <v-toolbar :color="selectedEvent.color" dark>
                <v-btn icon>
                  <v-icon>mdi-pencil</v-icon>
                </v-btn>
                <v-toolbar-title v-html="selectedEvent.name"></v-toolbar-title>
                <v-spacer></v-spacer>
                <v-btn icon>
                  <v-icon>mdi-heart</v-icon>
                </v-btn>
                <v-btn icon>
                  <v-icon>mdi-dots-vertical</v-icon>
                </v-btn>
              </v-toolbar>
              <v-card-text>
                <span v-html="selectedEvent.details"></span>
              </v-card-text>
              <v-card-actions>
                <v-btn text color="secondary" @click="selectedOpen = false">Cancel</v-btn>
              </v-card-actions>
            </v-card>
          </v-menu>
        </v-sheet>
      </v-col>
    </v-row>
  </div>
  <WorkInProgress v-else></WorkInProgress>
</template>

<script>
import axios from "axios";
import moment from "moment";
import WorkInProgress from "./WorkInProgress";
import store from "../store";

export default {
  components: {
    WorkInProgress
  },
  data: () => ({
    today: Date(),
    focus: Date(),
    type: "month",
    typeToLabel: {
      month: "Mois",
      week: "Semaine",
      day: "Jour",
      "4day": "4 jours"
    },
    daysOfWeek: [
      "Monday",
      "Tuesday",
      "Wednesday",
      "Thursday",
      "Friday",
      "Saturday",
      "Sunday"
    ],
    slotsTable: {},
    invitationsTable: {},
    playersTable: {},
    start: null,
    end: null,
    selectedEvent: {},
    selectedElement: null,
    selectedOpen: false,
    events: [
      {
        name: "RDV PFA",
        details: "Go to INRIA",
        start: "2019-12-20 11:00:00",
        end: "2019-12-20 12:00:00",
        color: "blue"
      },
      {
        name: "New Year",
        details: "Eat chocolate until you pass out",
        start: "2020-01-01",
        end: "2020-01-01",
        color: "green"
      }
    ]
  }),
  computed: {
    isSignedIn() {
      store.dispatch("isSignedIn");
      return store.getters.isSignedIn;
    },
    id() {
      store.dispatch("id");
      return store.getters.id;
    },
    title() {
      const { start, end } = this;
      if (!start || !end) {
        return "";
      }

      const startMonth = this.monthFormatter(start);
      const endMonth = this.monthFormatter(end);
      const suffixMonth = startMonth === endMonth ? "" : endMonth;

      const startYear = start.year;
      const endYear = end.year;
      const suffixYear = startYear === endYear ? "" : endYear;

      const startDay = start.day + this.nth(start.day);
      const endDay = end.day + this.nth(end.day);

      switch (this.type) {
        case "month":
          return `${startMonth} ${startYear}`;
        case "week":
        case "4day":
          return `${startMonth} ${startDay} ${startYear} - ${suffixMonth} ${endDay} ${suffixYear}`;
        case "day":
          return `${startMonth} ${startDay} ${startYear}`;
      }
      return "";
    },
    monthFormatter() {
      return this.$refs.calendar.getFormatter({
        timeZone: "UTC",
        month: "long"
      });
    }
  },
  mounted() {
    this.$refs.calendar.checkChange();
  },
  methods: {
    setSlotsTable: function(response) {
      this.slotsTable = response.data;
    },
    setInvitationsTable: function(response) {
      this.invitationsTable = response.data;
    },
    setPlayersTable: function(response) {
      this.playersTable = response.data;
    },
    showSlots: function() {
      let playerList = [];
      let playerNameList = [];

      for (let i = 0; i < this.slotsTable.rowCount; i++) {
        const dayINeed =
          this.daysOfWeek.indexOf(this.slotsTable.rows[i].repeat_day) + 1;
        const today = moment().isoWeekday();
        let gameDate = "";

        if (today <= dayINeed) {
          gameDate = moment().isoWeekday(dayINeed);
        } else {
          gameDate = moment()
            .add(1, "weeks")
            .isoWeekday(dayINeed);
        }

        playerList = [];

        for (let j = 0; j < this.invitationsTable.rowCount; j++) {
          if (
            this.invitationsTable.rows[j].slot == this.slotsTable.rows[i].id
          ) {
            playerList.push(this.invitationsTable.rows[j].player);
          }
        }

        for (let j = 0; j < playerList.length; j++) {
          for (let k = 0; k < this.playersTable.rowCount; k++) {
            if (this.playersTable.rows[k].id == playerList[j]) {
              playerNameList.push([
                this.playersTable.rows[k].pseudo,
                this.playersTable.rows[k].id
              ]);
            }
          }
        }

        for (let k = 0; k < playerNameList.length; k++) {
          if (playerNameList[k][1] == this.id) {
            this.events.push({
              name: "Match n° " + this.slotsTable.rows[i].id,
              details: "Joueurs : " + playerNameList + ", ",
              start:
                gameDate.format("YYYY-MM-DD") +
                " " +
                this.slotsTable.rows[i].start_hour,
              end:
                gameDate.format("YYYY-MM-DD") +
                " " +
                this.slotsTable.rows[i].end_hour,
              color: "purple"
            });
          }
        }
      }
    },
    viewDay({ date }) {
      this.focus = date;
      this.type = "day";
    },
    getEventColor(event) {
      return event.color;
    },
    setToday() {
      this.focus = this.today;
    },
    prev() {
      this.$refs.calendar.prev();
    },
    next() {
      this.$refs.calendar.next();
    },
    showEvent({ nativeEvent, event }) {
      const open = () => {
        this.selectedEvent = event;
        this.selectedElement = nativeEvent.target;
        setTimeout(() => (this.selectedOpen = true), 10);
      };

      if (this.selectedOpen) {
        this.selectedOpen = false;
        setTimeout(open, 10);
      } else {
        open();
      }

      nativeEvent.stopPropagation();
    },
    updateRange({ start, end }) {
      this.start = start;
      this.end = end;
    },
    nth(d) {
      return d > 3 && d < 21
        ? "th"
        : ["th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"][d % 10];
    }
  },
  async created() {
    const slot_Table = await axios.get(
      "https://dbcontrol.quickmatch.fr/dbcontrol/api/v1/Slots",
      {
        responseType: "json"
      }
    );
    const invitation_Table = await axios.get(
      "https://dbcontrol.quickmatch.fr/dbcontrol/api/v1/Invitations",
      {
        responseType: "json"
      }
    );
    const player_Table = await axios.get(
      "https://dbcontrol.quickmatch.fr/dbcontrol/api/v1/Players",
      {
        responseType: "json"
      }
    );
    this.setSlotsTable(slot_Table);
    this.setInvitationsTable(invitation_Table);
    this.setPlayersTable(player_Table);
    this.showSlots();
  }
};
</script>