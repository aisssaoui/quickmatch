<template>
    <v-container fluid>
        <v-row justify="center" align="center">
            <v-col cols="1">
                <v-img src="../assets/logo.png" alt="Quick Match logo"></v-img>
            </v-col>
        </v-row>
        <v-layout row wrap align-center="true" justify-center="true">
            <v-card class="mx-10" raised width="25%">
                <v-form v-model="valid">
                <v-layout justify-center="true">
                    <v-card-title align="center">Log in to Quick Match</v-card-title>
                </v-layout>
                    <v-col class="py-0" cols="4" md="12">
                        <v-text-field v-model="username" :rules="usernameRules" :counter="15" label="Username" required outlined filled></v-text-field>
                    </v-col>
                    <v-col class="py-0" cols="6" md="12">
                        <v-text-field v-model="password" :rules="passwordRules" label="Password" type="password" required outlined filled></v-text-field>
                    </v-col>
                </v-form>
                <v-row class="pa-0" align="center" justify="center">
                    <v-col class="pt-0" cols="10">
                        <v-btn rounded outlined block>Login</v-btn>
                    </v-col>
                </v-row>
                <v-divider></v-divider>
                <v-row align="center" justify="center">
                    <v-col cols="10">
                        <button class="g-signin2" data-onsuccess="OnGoogleAuthSuccess" data-onfail="OnGoogleAuthFail"></button>
                        <v-btn color="green lighten-1" rounded outlined block>Login with Google</v-btn>
                    </v-col>
                </v-row>
                <v-card-subtitle class="pa-0" align="center">OR</v-card-subtitle>
                <v-row class="pa-0" justify="center">
                    <v-col cols="10">
                        <v-btn color="blue darken-2" rounded outlined block>Login with Facebook</v-btn>
                    </v-col>
                </v-row>
            </v-card>
            <v-card class="mx-10" raised width="25%">
                
                <v-layout justify-center="true">
                    <v-card-title align="center">Not yet registered ?</v-card-title>
                </v-layout>
                <v-row class="pa-0" align="center" justify="center">
                    <v-col cols="10">
                        <v-btn to="/signin" rounded outlined block>Register Now !</v-btn>
                    </v-col>
                </v-row>
                <v-divider></v-divider>
                <v-layout justify-center="true">
                    <v-card-title align="center">Forgot Password ?</v-card-title>
                </v-layout>
                <v-row class="pa-0" align="center" justify="center">
                    <v-col cols="10">
                        <v-btn rounded outlined block>Reset Password</v-btn>
                    </v-col>
                </v-row>
            </v-card>
        </v-layout>
    </v-container>
</template>

<script>

export default {
    data: () => ({
        valid: false,
        username: '',
        usernameRules: [
            v => !!v || 'Name is required',
            v => v.length <= 15 || 'Username must be less than 10 characters',
        ],
        password: '',
        passwordRules: [
            v => !!v || 'Password is required',
            v => v.length >= 8 || 'Password must be more than 8 characters'
        ]
    }),
    methods: {
        OnGoogleAuthSuccess (googleUser) {
            this.$store.dispatch("setGoogleUser",googleUser);
        },
        OnGoogleAuthFail (error) {
            // eslint-disable-next-line no-console
            console.log(error);
        },
        logout() {
            this.$store.dispatch("logout");
            this.$router.push("/disconnected");
        }
    },
    computed: {
        isSignedIn() {
            this.$store.dispatch("isSignedIn");
            return this.$store.getters.isSignedIn;
        },
        givenName() {
            return this.$store.getters.givenName;
        },
        name() {
            return this.$store.getters.name;
        },
        familyName() {
            return this.$store.getters.familyName;
        },
        email() {
            return this.$store.getters.email;
        },
        connectionDate() {
            return this.$store.getters.connectionDate;
        },
        expirationDate() {
            return this.$store.getters.expirationDate;
        }

    }
}
</script>