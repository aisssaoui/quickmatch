<template>
    <v-container fluid>
        <v-row justify="center" align="center">
            <v-col cols="1">
                <v-img src="../assets/logo.png" alt="Quick Match logo"></v-img>
            </v-col>
        </v-row>
        <v-layout row wrap align-center="true" justify-center="true">
            <v-card raised width="25%">
                <v-form v-model="valid">
                <v-layout justify-center="true">
                    <v-card-title align="center" j>Log in to Quick Match</v-card-title>
                </v-layout>
                    <v-col cols="4" md="12">
                        <v-text-field v-model="username" :rules="usernameRules" :counter="10" label="Username" required outlined filled></v-text-field>
                    </v-col>
                    <v-col cols="6" md="12">
                        <v-text-field v-model="password" :rules="passwordRules" label="Password" type="password" required outlined filled></v-text-field>
                    </v-col>
                </v-form>
                <v-row class="pa-0" align="center" justify="center">
                    <v-col cols="10">
                        <v-btn rounded outlined block>Login</v-btn>
                    </v-col>
                </v-row>
                <v-divider></v-divider>
                <v-row class="pa-0" align="center" justify="center">
                    <v-col cols="10">
                        <v-btn rounded outlined block>Login with Google</v-btn>
                    </v-col>
                </v-row>
                <v-card-subtitle class="pa-0" align="center">OR</v-card-subtitle>
                <v-row class="pa-0" justify="center">
                    <v-col cols="10">
                        <v-btn rounded outlined block>Login with Facebook</v-btn>
                    </v-col>
                </v-row>
                <v-divider></v-divider>
                <v-row align="center" justify="center">
                    <v-col cols="6">
                        <v-btn outlined block rounded>Forgot password ?</v-btn>
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
                v => v.length <= 10 || 'Username must be less than 10 characters',
            ],
            password: '',
            passwordRules: [
                v => !!v || 'Password is required',
                v => v.length >= 8 || 'Password must be more than 8 characters'
            ]
        })
    }
</script>