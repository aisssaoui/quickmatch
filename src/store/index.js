import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        givenName: "",
        name: "", // nom complet
        familyName: "",
        email: "",
        isSignedIn: false,
        connectionDate: Object,
        expirationDate: Object,
        expirationTimer: 0,
        googleUser: Object,
        profile: Object,
        flag: 0 // to detect if googleUser is filled or not

    },
    mutations: {
        setGoogleUser(state, googleUser) {
            state.flag = 1;
            state.googleUser = googleUser;
            state.profile = googleUser.getBasicProfile();
            state.givenName = state.profile.getGivenName();
            state.name = state.profile.getName();
            state.familyName = state.profile.getFamilyName();
            state.email = state.profile.getEmail();
            state.isSignedIn = state.googleUser.isSignedIn();
            state.expirationTimer = state.googleUser.getAuthResponse().expires_in;
            /*** Setting expiration timer ***/
            state.connectionDate = new Date();
            state.expirationDate = new Date();
            state.expirationDate.setSeconds(state.connectionDate.getSeconds() + state.expirationTimer);
        },
        isSignedIn(state) {
            if (state.flag) {
                var now = new Date();
                state.isSignedIn = ( now < state.expirationDate);
            }
        },
        logout(state) {
            state.flag = 0;
            state.isSignedIn = false;
            state.googleUser.disconnect();
        }
    },
    actions: {
        setGoogleUser(commit, googleUser) {
            this.commit("setGoogleUser", googleUser);
        },
        isSignedIn(commit) {
            this.commit("isSignedIn");
        },
        logout(commit) {
            this.commit("logout");
        }
    },
    getters: {
        givenName: function(state) {
            return state.givenName;
        },
        name: function(state) {
            return state.name;
        },
        familyName: function(state) {
            return state.familyName;
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
        }
    }
});

