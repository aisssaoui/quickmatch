<template>
    <v-container fluid>
        <v-layout row wrap align-center="true" justify-center="true">
            <v-card class="mx-10" raised width="25%">
                <v-form v-model="valid">
                    <v-row class="pa-0" align="center">
                        <v-card-title>
                                <v-img src="../assets/logo.png" alt="Quick Match logo"></v-img>Register
                        </v-card-title>
                    </v-row>
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
        </v-layout>
    </v-container>
</template>

<script>

</script>