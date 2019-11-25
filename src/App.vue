<template>
    <div id="app" style="background-image: url('./assets/background.jpg')">
        <b-navbar toggleable="lg" type="dark" variant="warning">
            <b-navbar-brand to="/">Home Page</b-navbar-brand>
                <b-navbar-nav>
                    <b-nav-item to="/invitation"><img src="./assets/invit-icon.png" width="30px" alt="Invitation list icon"></b-nav-item>
                </b-navbar-nav>
            <b-navbar-nav class="ml-auto">
                <b-nav-item-dropdown text="Lang" right>
                    <b-dropdown-item href="#">EN</b-dropdown-item>
                    <b-dropdown-item href="#">FR</b-dropdown-item>
                </b-nav-item-dropdown>
                <b-nav-item-dropdown text="User" right>
                    <b-dropdown-item to="/profile/id">Profile</b-dropdown-item>
                    <b-dropdown-item to="/">Log out</b-dropdown-item>
                </b-nav-item-dropdown>
            </b-navbar-nav>
        </b-navbar>
    </div>
</template>

<script>


</script>

<style>

</style>