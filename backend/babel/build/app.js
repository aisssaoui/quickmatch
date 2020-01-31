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

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

_dotenv2.default.config();
console.log(process.env.DATABASE_URL);
var app = (0, _express2.default)();

app.use(_express2.default.json());
app.use((0, _cors2.default)());

app.get("/", function (req, res) {
  return res.status(200).send({ message: "YAY! Congratulations! Your first endpoint is working" });
});

app.post("/api/v1/Players", _player2.default.create);
app.get("/api/v1/Players", _player2.default.getAll);
app.get("/api/v1/Players/:id", _player2.default.getOne);
app.put("/api/v1/Players/:id", _player2.default.update);
app.delete("/api/v1/Players/:id", _player2.default.delete);

app.post("/api/v1/Clubs", _club2.default.create);
app.get("/api/v1/Clubs", _club2.default.getAll);
app.get("/api/v1/Clubs/:id", _club2.default.getOne);
app.put("/api/v1/Clubs/:id", _club2.default.update);
app.delete("/api/v1/Clubs/:id", _club2.default.delete);

app.listen(3000);
console.log("app running on port ", 3000);