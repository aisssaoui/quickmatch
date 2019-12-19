import Vue from "vue";
import Router from "vue-router";
/* components */
import Login from "../components/Login";
import Profile from "../components/Profile";
import Home from "../components/Home";
import Invitation from "../components/Invitation";
import Signin from "../components/Signin";
import Calendar from "../components/Calendar";
import Stat from "../components/Stat";
import Match from "../components/Match";
import Team from "../components/Team";
import AboutUs from "../components/AboutUs";
import CreateAccount from "../components/CreateAccount";
import ContactUs from "../components/ContactUs";

Vue.use(Router);

export default new Router({
  routes: [
    { name: "home", path: "/", component: Home },
    { name: "login", path: "/login", component: Login },
    { name: "createAccount", path: "/createAccount", component: CreateAccount },
    { name: "signin", path: "/signin", component: Signin },
    { name: "profile", path: "/profile/:id", component: Profile },
    { name: "invitation", path: "/invitation", component: Invitation },
    { name: "calendar", path: "/calendar", component: Calendar },
    { name: "stat", path: "/stat", component: Stat },
    { name: "match", path: "/match", component: Match },
    { name: "team", path: "/team", component: Team },
    { name: "aboutus", path: "/aboutus", component: AboutUs },
    { name: "contactus", path: "/contactus", component: ContactUs }
  ],
  mode: "history"
});
