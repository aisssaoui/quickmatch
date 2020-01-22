import Vue from "vue";
import Vuex from "vuex";
import axios from "axios";

Vue.use(Vuex);

/* Cookies administration */

function setCookie(name, value, expire, path, domain, security) {
  document.cookie =
    name +
    " = " +
    escape(value) +
    "  " +
    (expire == undefined ? "" : "; expires = " + expire.toUTCString()) +
    (path == undefined ? "" : "; path = " + path) +
    (domain == undefined ? "" : "; domain = " + domain) +
    (security == true ? "; secure" : "");
}

function getCookie(name) {
  if (document.cookie.length == 0) return "zero";

  var regSepCookie = new RegExp("(; )", "g");
  var cookies = document.cookie.split(regSepCookie);

  for (var i = 0; i < cookies.length; i++) {
    var regInfo = new RegExp("=", "g");
    var infos = cookies[i].split(regInfo);
    if (infos[0] == name) {
      return unescape(infos[1]);
    }
  }
  return null;
}

/* ********************* */

export default new Vuex.Store({
  state: {
    id: 0,
    givenName: "",
    name: "", // nom complet
    surname: "",
    email: "none", // can't be empty
    nickname: "",
    imageUrl: "",
    isSignedIn: false,
    connectionDate: Object,
    expirationDate: Object,
    expirationTimer: 0,
    googleUser: Object,
    profile: Object,
    hasAccount: false,
    cookieCheck: false // to avoid multiple checkings (infinite loop)
  },
  mutations: {
    setGoogleUser(state, googleUser) {
      state.cookieCheck = true;
      state.googleUser = googleUser;
      state.profile = googleUser.getBasicProfile();
      state.givenName = state.profile.getGivenName();
      state.name = state.profile.getName();
      state.surname = state.profile.getFamilyName();
      state.email = state.profile.getEmail();
      state.imageUrl = state.profile.getImageUrl();
      state.isSignedIn = state.googleUser.isSignedIn();
      state.expirationTimer = state.googleUser.getAuthResponse().expires_in;
      /*** Setting expiration timer ***/
      state.connectionDate = new Date();
      state.expirationDate = new Date();
      state.expirationDate.setSeconds(
        state.connectionDate.getSeconds() + state.expirationTimer
      );
    },
    isSignedIn(state) {
      if (state.cookieCheck == false) {
        state.cookieCheck = true;
        var cookieExpiration = getCookie("quickmatchExpiration");
        var cookieId = getCookie("quickmatchId");
        if (cookieId != null && cookieExpiration != null) {
          state.expirationDate = new Date(cookieExpiration);
          state.id = cookieId;
          state.hasAccount = true;
        }
      }
      if (state.hasAccount == true) {
        var now = new Date();
        state.isSignedIn = now < state.expirationDate;
      } else {
        state.isSignedIn = false;
      }
    },
    logout(state) {
      state.hasAccount = false;
      state.isSignedIn = false;
      var end = new Date();
      end.setTime(end.getTime() - 1);
      /* cookie deletion */
      setCookie(
        "quickmatchExpiration",
        state.expirationDate,
        end,
        "/",
        "quickmatch.fr",
        false
      );
      setCookie("quickmatchId", state.id, end, "/", "quickmatch.fr", false);
    },
    hasAccount(state) {
      state.hasAccount = true;
      setCookie(
        "quickmatchExpiration",
        state.expirationDate,
        state.expirationDate,
        "/",
        "quickmatch.fr",
        false
      );
    },
    async setID(state) {
      const player = await axios.get(
        "http://fama6831.odns.fr/dbcontrol/api/v1/players/ma" + state.email,
        { ResponseType: "json" }
      );
      state.id = player.data.id;
      setCookie(
        "quickmatchId",
        state.id,
        state.expirationDate,
        "/",
        "quickmatch.fr",
        false
      );
    }
  },
  actions: {
    setGoogleUser(commit, googleUser) {
      this.commit("setGoogleUser", googleUser);
    },
    isSignedIn(commit) {
      this.commit("isSignedIn");
    },
    hasAccount(commit) {
      this.commit("hasAccount");
    },
    logout(commit) {
      this.commit("logout");
    },
    setID(state) {
      this.commit("setID");
    }
  },
  getters: {
    givenName: function(state) {
      return state.givenName;
    },
    name: function(state) {
      return state.name;
    },
    surname: function(state) {
      return state.surname;
    },
    email: function(state) {
      return state.email;
    },
    isSignedIn: function(state) {
      return state.isSignedIn;
    },
    connectionDate: function(state) {
      return state.connectionDate;
    },
    expirationDate: function(state) {
      return state.expirationDate;
    },
    id: function(state) {
      return state.id;
    },
    imageUrl: function(state) {
      return state.imageUrl;
    }
  }
});
