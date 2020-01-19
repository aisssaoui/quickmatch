
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

// var _invitation_for_meet = require("./src/controllers/invitation_for_meet");
// var _invitation_for_meet2 = _interopRequireDefault(_invitation_for_meet);

var _meet = require("./src/controllers/meet");
var _meet2 = _interopRequireDefault(_meet);

var _meet_sheet = require("./src/controllers/meet_sheet");
var _meet_sheet2 = _interopRequireDefault(_meet_sheet);

var _slot = require("./src/controllers/slot");
var _slot2 = _interopRequireDefault(_slot);

// var _player_belong_club = require("./src/controllers/player_belong_club");
// var _player_belong_club2 = _interopRequireDefault(_player_belong_club);

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

app.get("/test", function(req, res) {
  res.send("Hello");
});

// Table player
app.post("/dbcontrol/api/v1/Players", _player2.default.create);
app.get("/dbcontrol/api/v1/Players", _player2.default.getAll);
app.get(
  "/dbcontrol/api/v1/Players/ma:mail_address",
  _player2.default.getByMail
);
app.get(
  "/dbcontrol/api/v1/Players/ma_stat:mail_address",
  _player2.default.getByMailStat
);
app.get("/dbcontrol/api/v1/Players/id:id", _player2.default.getByID);
app.put("/dbcontrol/api/v1/Players/id:id", _player2.default.update);
app.delete("/dbcontrol/api/v1/Players/:mail_address", _player2.default.delete);

// Table club
// app.post("/dbcontrol/api/v1/Clubs", _club2.default.create);
// app.get("/dbcontrol/api/v1/Clubs", _club2.default.getAll);
// app.get("/dbcontrol/api/v1/Clubs/:id", _club2.default.getOne);
// //app.put("/dbcontrol/api/v1/Clubs/:id", _club2.default.update);
// app.delete("/dbcontrol/api/v1/Clubs/:id", _club2.default.delete);

// Table invitation
app.post("/dbcontrol/api/v1/Invitations", _invitation2.default.create);
app.get("/dbcontrol/api/v1/Invitations", _invitation2.default.getAll);
app.get("/dbcontrol/api/v1/Invitations/:id", _invitation2.default.getOne);
//app.put("/dbcontrol/api/v1/Invitations/:id", _invitation2.default.update);
app.delete("/dbcontrol/api/v1/Invitations/:id", _invitation2.default.delete);

// Table invitation_for_meet
// app.post("/dbcontrol/api/v1/InvitationForMeet", _invitation_for_meet2.default.create);
// app.get("/dbcontrol/api/v1/InvitationForMeet", _invitation_for_meet2.default.getAll);
// app.get("/dbcontrol/api/v1/InvitationForMeet/:id", _invitation_for_meet2.default.getOne);
// app.put("/dbcontrol/api/v1/InvitationForMeet/:id", _invitation_for_meet2.default.update);
// app.delete("/dbcontrol/api/v1/InvitationForMeet/:id", _invitation_for_meet2.default.delete);

// Table slot
app.post("/dbcontrol/api/v1/Slots", _slot2.default.create);
app.get("/dbcontrol/api/v1/Slots", _slot2.default.getAll);
app.get("/dbcontrol/api/v1/Slots/:id", _slot2.default.getOne);
//app.put("/dbcontrol/api/v1/Slots/:id", _slot2.default.update);
app.delete("/dbcontrol/api/v1/Slots/:id", _slot2.default.delete);
// Table player_belong_club
// app.post("/dbcontrol/api/v1/PlayersBelongClub", _player_belong_club2.default.create);
// app.get("/dbcontrol/api/v1/PlayersBelongClub", _player_belong_club2.default.getAll);
// app.get("/dbcontrol/api/v1/PlayersBelongClub/:player", _player_belong_club2.default.getOne);
// app.put("/dbcontrol/api/v1/PlayersBelongClub/:player", _player_belong_club2.default.update);
// app.delete("/dbcontrol/api/v1/PlayersBelongClub/:player", _player_belong_club2.default.delete);

// Table meet_sheet
app.post("/dbcontrol/api/v1/MeetsSheet", _meet_sheet2.default.create);
app.get("/dbcontrol/api/v1/MeetsSheet", _meet_sheet2.default.getAll);
app.get(
  "/dbcontrol/api/v1/MeetsSheet/:player_mail_address",
  _meet_sheet2.default.getOne
);
//app.put("/dbcontrol/api/v1/MeetsSheet/:player_mail_adress", _meet_sheet2.default.update);
app.delete(
  "/dbcontrol/api/v1/MeetsSheet/:player_mail_address",
  _meet_sheet2.default.delete
);

// Table meet
app.post("/dbcontrol/api/v1/Meets", _meet2.default.create);
app.get("/dbcontrol/api/v1/Meets", _meet2.default.getAll);
app.get("/dbcontrol/api/v1/Meets/:id", _meet2.default.getOne);
// app.put("/dbcontrol/api/v1/Meets/:id", _meet2.default.update);
app.delete("/dbcontrol/api/v1/Meets/:id", _meet2.default.delete);

app.listen(3000);
console.log("app running on port ", 3000);
