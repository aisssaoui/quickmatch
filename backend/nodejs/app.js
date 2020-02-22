"use strict";

var _express = require("express");

var _express2 = _interopRequireDefault(_express);

var _dotenv = require("dotenv");

var _dotenv2 = _interopRequireDefault(_dotenv);

var _cors = require("cors");

var _cors2 = _interopRequireDefault(_cors);

require("babel-polyfill");

var _player = require("./src/controllers/player");
var _player2 = _interopRequireDefault(_player);

var _club = require("./src/controllers/club");
var _club2 = _interopRequireDefault(_club);

var _invitation = require("./src/controllers/invitation");
var _invitation2 = _interopRequireDefault(_invitation);

var _invitation_club = require("./src/controllers/invitation_club");
var _invitation_club2 = _interopRequireDefault(_invitation_club);

// var _invitation_for_meet = require("./src/controllers/invitation_for_meet");
// var _invitation_for_meet2 = _interopRequireDefault(_invitation_for_meet);

var _meet = require("./src/controllers/meet");
var _meet2 = _interopRequireDefault(_meet);

var _meet_sheet = require("./src/controllers/meet_sheet");
var _meet_sheet2 = _interopRequireDefault(_meet_sheet);

var _slot = require("./src/controllers/slot");
var _slot2 = _interopRequireDefault(_slot);

var _player_belong_club = require("./src/controllers/player_belong_club");
var _player_belong_club2 = _interopRequireDefault(_player_belong_club);

var _calendar_db = require("./src/controllers/calendar_db");
var _calendar_db2 = _interopRequireDefault(_calendar_db);

var _validAccount = require("./src/controllers/validAccount");
var _validAccount2 = _interopRequireDefault(_validAccount);

function _interopRequireDefault(obj) {
  return obj && obj.__esModule ? obj : { default: obj };
}

_dotenv2.default.config();
console.log(process.env.DATABASE_URL);
var app = (0, _express2.default)();

app.use(_express2.default.json());
app.use((0, _cors2.default)());

app.get("/dbcontrol", function(req, res) {
  return res
    .status(200)
    .send({ message: "YAY! Congratulations! Your first endpoint is working" });
});

app.get("/dbcontrol/test", function(req, res) {
  res.send("Hello");
});

// Table player
app.post("/dbcontrol/api/v1/Players", _player2.default.create);
app.get("/dbcontrol/api/v1/Players", _player2.default.getAll);
app.get("/dbcontrol/api/v1/Players/ma:mail_address", _player2.default.getByMail);
app.get("/dbcontrol/api/v1/Players/p:p", _player2.default.getByPseudo);
app.get("/dbcontrol/api/v1/Players/stat:id", _player2.default.getPlayerStat);
app.get("/dbcontrol/api/v1/Players/id:id", _player2.default.getByID);
app.put("/dbcontrol/api/v1/Players/id:id", _player2.default.update);
app.delete("/dbcontrol/api/v1/Players/:id", _player2.default.delete);
app.get("/dbcontrol/api/v1/PlayersRows", _player2.default.getAllRows);

//Table club
app.post("/dbcontrol/api/v1/Clubs", _club2.default.create);
app.get("/dbcontrol/api/v1/Clubs", _club2.default.getAll);
app.get("/dbcontrol/api/v1/Clubs/:id", _club2.default.getOne);
//app.put("/dbcontrol/api/v1/Clubs/:id", _club2.default.update);
app.delete("/dbcontrol/api/v1/Clubs/:id", _club2.default.delete);
app.get("/dbcontrol/api/v1/ClubsRows", _club2.default.getAllRows);

// Table invitation
app.post("/dbcontrol/api/v1/Invitations", _invitation2.default.create);
app.get("/dbcontrol/api/v1/Invitations", _invitation2.default.getAll);
app.get("/dbcontrol/api/v1/Invitations/:id", _invitation2.default.getOne);
app.put("/dbcontrol/api/v1/Invitations/:id", _invitation2.default.update);
app.delete("/dbcontrol/api/v1/Invitations/:id", _invitation2.default.delete);
app.get("/dbcontrol/api/v1/InvitationsRows", _invitation2.default.getAllRows);


// Table Invitation_Club
app.post("/dbcontrol/api/v1/InvitationClub/:pid&:cid", _invitation_club2.default.create);
app.get("/dbcontrol/api/v1/InvitationClub/:pid", _invitation_club2.default.getPlayerInvitations);
app.delete("/dbcontrol/api/v1/InvitationClub/:ci_id", _invitation_club2.default.delete);

// Table invitation_for_meet
// app.post("/dbcontrol/api/v1/InvitationForMeet", _invitation_for_meet2.default.create);
// app.get("/dbcontrol/api/v1/InvitationForMeet", _invitation_for_meet2.default.getAll);
// app.get("/dbcontrol/api/v1/InvitationForMeet/:id", _invitation_for_meet2.default.getOne);
// app.put("/dbcontrol/api/v1/InvitationForMeet/:id", _invitation_for_meet2.default.update);
// app.delete("/dbcontrol/api/v1/InvitationForMeet/:id", _invitation_for_meet2.default.delete);
//app.get("/dbcontrol/api/v1/Playersrows", _player2.default.getAllRows);

// Table slot
app.post("/dbcontrol/api/v1/Slots", _slot2.default.create);
app.get("/dbcontrol/api/v1/Slots", _slot2.default.getAll);
app.get("/dbcontrol/api/v1/Slots/:id", _slot2.default.getOne);
//app.put("/dbcontrol/api/v1/Slots/:id", _slot2.default.update);
app.delete("/dbcontrol/api/v1/Slots/:id", _slot2.default.delete);
app.get("/dbcontrol/api/v1/SlotsRows", _slot2.default.getAllRows);

// Table meet_sheet
app.post("/dbcontrol/api/v1/MeetsSheet", _meet_sheet2.default.create);
app.get("/dbcontrol/api/v1/MeetsSheet", _meet_sheet2.default.getAll);
app.get("/dbcontrol/api/v1/MeetsSheet/:player_mail_address",_meet_sheet2.default.getOne);
//app.put("/dbcontrol/api/v1/MeetsSheet/:player_mail_adress", _meet_sheet2.default.update);

/* TODO : meetsheet getone avec l'@ mail ne marche pas */

app.delete("/dbcontrol/api/v1/MeetsSheet/:player_mail_address", _meet_sheet2.default.delete);
app.get("/dbcontrol/api/v1/MeetsSheetRows", _meet_sheet2.default.getAllRows);

// Table meet
app.post("/dbcontrol/api/v1/Meets", _meet2.default.create);
app.get("/dbcontrol/api/v1/Meets", _meet2.default.getAll);
app.get("/dbcontrol/api/v1/Meets/:id", _meet2.default.getOne);
// app.put("/dbcontrol/api/v1/Meets/:id", _meet2.default.update);
app.delete("/dbcontrol/api/v1/Meets/:id", _meet2.default.delete);
app.get("/dbcontrol/api/v1/MeetsRows", _meet2.default.getAllRows);

// Table player_belong_club
app.get("/dbcontrol/api/v1/PlayerClubs/paid:id", _player_belong_club2.default.getPlayerAdminClubs);
app.get("/dbcontrol/api/v1/PlayerClubs/pid:id",_player_belong_club2.default.getPlayerClubsByPlayerID);
app.get("/dbcontrol/api/v1/PlayerClubs/npid:id", _player_belong_club2.default.NgetPlayerClubsByPlayerID);
app.get("/dbcontrol/api/v1/PlayerClubs/cid:id", _player_belong_club2.default.getPlayerClubsByClubID);
app.get("/dbcontrol/api/v1/PlayerClubs/ncid:id", _player_belong_club2.default.NgetPlayerClubsByClubID);
app.post("/dbcontrol/api/v1/PlayerClubs", _player_belong_club2.default.addPlayerToClub);
app.delete("/dbcontrol/api/v1/PlayerClubs/:cid&:pid", _player_belong_club2.default.deletePlayerFromClub);
app.get("/dbcontrol/api/v1/PlayerClubs", _player_belong_club2.default.getAll);
app.get("/dbcontrol/api/v1/playerClubsCountAdmin/:cid", _player_belong_club2.default.getCountAdmin);
app.put("/dbcontrol/api/v1/PlayerClubsPromoteToAdmin/:pid&:cid", _player_belong_club2.default.promoteToAdmin);

app.get("/dbcontrol/api/v1/CalendarBPlayer/:id", _calendar_db2.default.getByPlayer);
app.get("/dbcontrol/api/v1/CalendarBMeet/:id", _calendar_db2.default.getByMeet);
app.get("/dbcontrol/api/v1/CalendarMAccept/:id", _calendar_db2.default.getAccepted);
app.get("/dbcontrol/api/v1/CalendarMDecline/:id", _calendar_db2.default.getDeclined);
app.get("/dbcontrol/api/v1/CalendarMInv/:pid/:mid", _calendar_db2.default.getInv);


// Envoi de mail 
app.post("/dbcontrol/api/v1/SendMail", _validAccount2.default.sendMail)

app.listen(3000);
console.log("app running on port ", 3000);
