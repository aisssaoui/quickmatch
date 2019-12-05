import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        firstName: "",
        fullName: "",
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
            state.flag = 1
            state.googleUser = googleUser
            state.profile = googleUser.getBasicProfile()
            state.firstName = state.profile.getGivenName()
            state.fullName = state.profile.getName()
            state.familyName = state.profile.getFamilyName()
            state.email = state.profile.getEmail()
            state.isSignedIn = state.googleUser.isSignedIn()
            state.expirationTimer = state.googleUser.getAuthResponse().expires_in
            /*** Setting expiration timer ***/
            state.connectionDate = new Date()
            state.expirationDate = new Date()
            state.expirationDate.setSeconds(state.connectionDate.getSeconds() + state.expirationTimer)
        },
        isSignedIn(state) {
            if (state.flag) {
                var now = new Date()
                state.isSignedIn = ( now < state.expirationDate)
            }
        },
        logout(state) {
            state.flag = 0
            state.isSignedIn = false
            state.googleUser.disconnect()
        }
    },

    actions: {
        setGoogleUser(commit, googleUser) {
            this.commit("setGoogleUser", googleUser);
        },
        // eslint-disable-next-line no-unused-vars
        isSignedIn(commit) {
            this.commit("isSignedIn");
        },
        // eslint-disable-next-line no-unused-vars
        logout(commit) {
            this.commit("logout");
        }
    },
    getters: {
        firstName: function (state) {
            return state.firstName;
        },
        fullName: function (state) {
            return state.fullName;
        },
        familyName: function (state) {
            return state.familyName;
        },
        email: function (state) {
            return state.email;
        },
        isSignedIn: function (state) {
            return state.isSignedIn;
        },
        connectionDate: function (state) {
            return state.connectionDate;
        },
        expirationDate: function (state) {
            return state.expirationDate;
        }
    }
});
