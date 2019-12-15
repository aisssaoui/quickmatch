<template>
    <v-container fluid>
        <v-layout row wrap align-center="true" justify-center="true">
            <v-card class="mx-10" raised width="25%">
                <v-form v-model="valid" >
                    <v-row class="pa-0" justify="center" align="center">
                      <v-col align="center" cols="5">
                          <v-img src="../assets/logo.png" alt="Quick Match logo"></v-img>Register
                      </v-col>
                    </v-row>
                    <v-col class="py-0" cols="6" md="12">
                        <v-text-field v-model="surname" :rules="surnameRules" :counter="20" label="Surname" required outlined filled></v-text-field>
                    </v-col>
                    <v-col class="py-0" cols="6" md="12">
                        <v-text-field v-model="first_name" :rules="first_nameRules" :counter="20" label="First name" required outlined filled></v-text-field>
                    </v-col>
                    <v-col class="py-0" cols="6" md="12">
                        <v-text-field v-model="pseudo" :rules="pseudoRules" :counter="20" label="Pseudo" required outlined filled></v-text-field>
                    </v-col>
                    <v-col class="py-0" cols="6" md="12">
                        <v-text-field v-model="mdp" :rules="mdpRules" :counter="20" label="Password" type="password" required outlined filled></v-text-field>
                    </v-col>
                    <v-col class="py-0" cols="6" md="12">
                        <v-text-field v-model="email" :rules="emailRules" :counter="50" label="E-mail" required outlined filled></v-text-field>
                    </v-col>
                    <v-col class="py-0" cols="6" md="12">
                        <v-text-field v-model="tel" :rules="telRules" :counter="14" label="phone number" required outlined filled></v-text-field>
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
  export default {
    data: () => ({
      valid: false,
      surname: '',
      first_name: '',
      pseudo: '',
      mdp: '',
      email: '',
      tel: '',
      surnameRules: [
        v => !!v || 'Surname is required',
        v => /^[a-zA-Z -]{3,20}$/.test(v) || 'Surname must be valid',
      ],
      first_nameRules: [
        v => !!v || 'First name is required',
        v => /^[a-zA-Z -]{3,20}$/.test(v) || 'First name must be valid',
      ],
      pseudoRules: [
        v => !!v || 'Pseudo is required',
        v => /^[a-zA-Z0-9 _-]{3,20}$/.test(v) || 'Pseudo must be valid (letters, numbers, space, "_" and "-" only)',
      ],
      mdpRules: [
        v => !!v || 'Password is required',
      ],
      emailRules: [
        v => !!v || 'E-mail is required',
        v => /^[a-zA-Z0-9._-]+@[a-z0-9._-]{2,}\.[a-z]{2,4}$/.test(v) || 'E-mail must be valid',
      ],
      telRules: [
        v => !!v || 'Phone number is required',
        v => /^(0[1-8])(?:[ -]?([0-9]{2})){4}$/.test(v) || 'Phone number must be valid (can use space or "-" as separator like in 06-66-66-66-66)',
      ],
    }),
  }
</script>
