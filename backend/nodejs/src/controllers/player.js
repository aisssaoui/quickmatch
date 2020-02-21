"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _moment = require("moment");

var _moment2 = _interopRequireDefault(_moment);

var _db = require("../db");

var _db2 = _interopRequireDefault(_db);

function _interopRequireDefault(obj) {
  return obj && obj.__esModule ? obj : { default: obj };
}

function _asyncToGenerator(fn) {
  return function() {
    var gen = fn.apply(this, arguments);
    return new Promise(function(resolve, reject) {
      function step(key, arg) {
        try {
          var info = gen[key](arg);
          var value = info.value;
        } catch (error) {
          reject(error);
          return;
        }
        if (info.done) {
          resolve(value);
        } else {
          return Promise.resolve(value).then(
            function(value) {
              step("next", value);
            },
            function(err) {
              step("throw", err);
            }
          );
        }
      }
      return step("next");
    });
  };
}
//import uuidv4 from 'uuid/v4';

var Player = {
  /**
   * Create A Player
   * @param {object} req
   * @param {object} res
   * @returns {object} Player object
   */
  create: (function() {
    var _ref = _asyncToGenerator(
      /*#__PURE__*/ regeneratorRuntime.mark(function _callee(req, res) {
        var text, values, _ref2, rows;

        return regeneratorRuntime.wrap(
          function _callee$(_context) {
            while (1) {
              switch ((_context.prev = _context.next)) {
                case 0:
                  text =
                    "INSERT INTO Player (surname, first_name, mail_address, phone_number, pseudo, mdp, avatar)\n\t    VALUES($1, $2, $3, $4, $5, $6, $7)\n      RETURNING *";
                  values = [
                    req.body.surname,
                    req.body.first_name,
                    req.body.mail_address,
                    req.body.phone_number,
                    req.body.pseudo,
                    req.body.mdp,
                    req.body.avatar
                  ];
                  _context.prev = 2;
                  _context.next = 5;
                  return _db2.default.query(text, values);

                case 5:
                  _ref2 = _context.sent;
                  rows = _ref2.rows;
                  return _context.abrupt(
                    "return",
                    res.status(201).send(rows[0])
                  );

                case 10:
                  _context.prev = 10;
                  _context.t0 = _context["catch"](2);
                  return _context.abrupt(
                    "return",
                    res.status(200).send(_context.t0)
                  );

                case 13:
                case "end":
                  return _context.stop();
              }
            }
          },
          _callee,
          this,
          [[2, 10]]
        );
      })
    );

    function create(_x, _x2) {
      return _ref.apply(this, arguments);
    }

    return create;
  })(),

  /**
   * Get All players
   * @param {object} req
   * @param {object} res
   * @returns {object} players array
   */
  getAll: (function() {
    var _ref3 = _asyncToGenerator(
      /*#__PURE__*/ regeneratorRuntime.mark(function _callee2(req, res) {
        var findAllQuery, _ref4, rows, rowCount;

        return regeneratorRuntime.wrap(
          function _callee2$(_context2) {
            while (1) {
              switch ((_context2.prev = _context2.next)) {
                case 0:
                  findAllQuery = "SELECT * FROM player";
                  _context2.prev = 1;
                  _context2.next = 4;
                  return _db2.default.query(findAllQuery);

                case 4:
                  _ref4 = _context2.sent;
                  rows = _ref4.rows;
                  rowCount = _ref4.rowCount;
                  return _context2.abrupt(
                    "return",
                    res.status(200).send({ rows: rows, rowCount: rowCount })
                  );

                case 10:
                  _context2.prev = 10;
                  _context2.t0 = _context2["catch"](1);
                  return _context2.abrupt(
                    "return",
                    res.status(200).send(_context2.t0)
                  );

                case 13:
                case "end":
                  return _context2.stop();
              }
            }
          },
          _callee2,
          this,
          [[1, 10]]
        );
      })
    );

    function getAll(_x3, _x4) {
      return _ref3.apply(this, arguments);
    }

    return getAll;
  })(),

  /**
   * Get All players rows
   * @param {object} req
   * @param {object} res
   * @returns {object} players array
   */
  getAllRows: (function() {
    var _ref5 = _asyncToGenerator(
      /*#__PURE__*/ regeneratorRuntime.mark(function _callee3(req, res) {
        var findAllQuery, a;
        return regeneratorRuntime.wrap(
          function _callee3$(_context3) {
            while (1) {
              switch ((_context3.prev = _context3.next)) {
                case 0:
                  findAllQuery = "SELECT * FROM player";
                  _context3.prev = 1;
                  _context3.next = 4;
                  return _db2.default.query(findAllQuery);

                case 4:
                  a = _context3.sent;
                  return _context3.abrupt(
                    "return",
                    res.status(200).send(a.rows)
                  );

                case 8:
                  _context3.prev = 8;
                  _context3.t0 = _context3["catch"](1);
                  return _context3.abrupt(
                    "return",
                    res.status(200).send(_context3.t0)
                  );

                case 11:
                case "end":
                  return _context3.stop();
              }
            }
          },
          _callee3,
          this,
          [[1, 8]]
        );
      })
    );

    function getAllRows(_x5, _x6) {
      return _ref5.apply(this, arguments);
    }

    return getAllRows;
  })(),
  /**
   * Get A Player by his id
   * @param {object} req
   * @param {object} res
   * @returns {object} player object
   */
  getByID: (function() {
    var _ref7 = _asyncToGenerator(
      /*#__PURE__*/ regeneratorRuntime.mark(function _callee4(req, res) {
        var text, _ref8, rows;

        return regeneratorRuntime.wrap(
          function _callee4$(_context4) {
            while (1) {
              switch ((_context4.prev = _context4.next)) {
                case 0:
                  text = "SELECT * FROM player WHERE id = $1";
                  _context4.prev = 1;
                  _context4.next = 4;
                  return _db2.default.query(text, [req.params.id]);

                case 4:
                  _ref8 = _context4.sent;
                  rows = _ref8.rows;

                  if (rows[0]) {
                    _context4.next = 8;
                    break;
                  }

                  return _context4.abrupt(
                    "return",
                    res.status(200).send({ message: "player not found" })
                  );

                case 8:
                  return _context4.abrupt(
                    "return",
                    res.status(200).send(rows[0])
                  );

                case 11:
                  _context4.prev = 11;
                  _context4.t0 = _context4["catch"](1);
                  return _context4.abrupt(
                    "return",
                    res.status(200).send(_context4.t0)
                  );

                case 14:
                case "end":
                  return _context4.stop();
              }
            }
          },
          _callee4,
          this,
          [[1, 11]]
        );
      })
    );

    function getByID(_x7, _x8) {
      return _ref7.apply(this, arguments);
    }

    return getByID;
  })(),
    /**
   * Get A Player by his pseudo
   * @param {object} req
   * @param {object} res
   * @returns {object} player object
   */
  getByPseudo: (function() {
    var _ref7 = _asyncToGenerator(
      /*#__PURE__*/ regeneratorRuntime.mark(function _callee4(req, res) {
        var text, _ref8, rows;

        return regeneratorRuntime.wrap(
          function _callee4$(_context4) {
            while (1) {
              switch ((_context4.prev = _context4.next)) {
                case 0:
                  text = "SELECT * FROM player WHERE pseudo = $1";
                  _context4.prev = 1;
                  _context4.next = 4;
                  return _db2.default.query(text, [req.params.p]);

                case 4:
                  _ref8 = _context4.sent;
                  rows = _ref8.rows;

                  if (rows[0]) {
                    _context4.next = 8;
                    break;
                  }

                  return _context4.abrupt(
                    "return",
                    res.status(200).send({ message: "player not found" })
                  );

                case 8:
                  return _context4.abrupt(
                    "return",
                    res.status(200).send(rows[0])
                  );

                case 11:
                  _context4.prev = 11;
                  _context4.t0 = _context4["catch"](1);
                  return _context4.abrupt(
                    "return",
                    res.status(200).send(_context4.t0)
                  );

                case 14:
                case "end":
                  return _context4.stop();
              }
            }
          },
          _callee4,
          this,
          [[1, 11]]
        );
      })
    );

    function getByPseudo(_x7, _x8) {
      return _ref7.apply(this, arguments);
    }

    return getByPseudo;
  })(),

  /**
   * Get A Player by his mail address
   * @param {object} req
   * @param {object} res
   * @returns {object} player object
   */
  getByMail: (function() {
    var _ref9 = _asyncToGenerator(
      /*#__PURE__*/ regeneratorRuntime.mark(function _callee5(req, res) {
        var text, _ref10, rows;

        return regeneratorRuntime.wrap(
          function _callee5$(_context5) {
            while (1) {
              switch ((_context5.prev = _context5.next)) {
                case 0:
                  text = "SELECT * FROM player WHERE mail_address = $1";
                  _context5.prev = 1;
                  _context5.next = 4;
                  return _db2.default.query(text, [req.params.mail_address]);

                case 4:
                  _ref10 = _context5.sent;
                  rows = _ref10.rows;

                  if (rows[0]) {
                    _context5.next = 8;
                    break;
                  }

                  return _context5.abrupt(
                    "return",
                    res.status(202).send({ message: "player not found" })
                  );

                case 8:
                  return _context5.abrupt(
                    "return",
                    res.status(200).send(rows[0])
                  );

                case 11:
                  _context5.prev = 11;
                  _context5.t0 = _context5["catch"](1);
                  return _context5.abrupt(
                    "return",
                    res.status(202).send(_context5.t0)
                  );

                case 14:
                case "end":
                  return _context5.stop();
              }
            }
          },
          _callee5,
          this,
          [[1, 11]]
        );
      })
    );

    function getByMail(_x9, _x10) {
      return _ref9.apply(this, arguments);
    }

    return getByMail;
  })(),

  /**
   * Get A Player stats per match
   * @param {object} req
   * @param {object} res
   * @returns {object} player_stat object
   */
  getPlayerStat: (function() {
    var _ref11 = _asyncToGenerator(
      /*#__PURE__*/ regeneratorRuntime.mark(function _callee6(req, res) {
        var text, _ref12, rows, rowCount;

        return regeneratorRuntime.wrap(
          function _callee6$(_context6) {
            while (1) {
              switch ((_context6.prev = _context6.next)) {
                case 0:
                  text =
                    "SELECT MS.won, MS.scored_goals, MS.conceded_goals, M.precise_date, M.location FROM Meet_sheet MS JOIN Meet M ON MS.meet = M.id WHERE MS.player = $1";
                  _context6.prev = 1;
                  _context6.next = 4;
                  return _db2.default.query(text, [req.params.id]);

                case 4:
                  _ref12 = _context6.sent;
                  rows = _ref12.rows;
                  rowCount = _ref12.rowCount;
                  return _context6.abrupt(
                    "return",
                    res.status(200).send({ rows: rows, rowCount: rowCount })
                  );

                case 10:
                  _context6.prev = 10;
                  _context6.t0 = _context6["catch"](1);
                  return _context6.abrupt(
                    "return",
                    res.status(400).send(_context6.t0)
                  );

                case 13:
                case "end":
                  return _context6.stop();
              }
            }
          },
          _callee6,
          this,
          [[1, 10]]
        );
      })
    );

    function getPlayerStat(_x11, _x12) {
      return _ref11.apply(this, arguments);
    }

    return getPlayerStat;
  })(),

  /**
   * Update A Player
   * @param {object} req
   * @param {object} res
   * @returns {object} updated Player
   */
  update: (function() {
    var _ref13 = _asyncToGenerator(
      /*#__PURE__*/ regeneratorRuntime.mark(function _callee7(req, res) {
        var findOneQuery, updateOneQuery, _ref14, rows, values, response;

        return regeneratorRuntime.wrap(
          function _callee7$(_context7) {
            while (1) {
              switch ((_context7.prev = _context7.next)) {
                case 0:
                  findOneQuery = "SELECT * FROM player WHERE id = $1";
                  updateOneQuery =
                    "UPDATE player\n      SET pseudo = $1, surname = $2, phone_number = $3, bio=$4, avatar=$5, mdp=$6, is_valid=$7\n      WHERE id = $8 RETURNING *";
                  _context7.prev = 2;
                  _context7.next = 5;
                  return _db2.default.query(findOneQuery, [req.params.id]);

                case 5:
                  _ref14 = _context7.sent;
                  rows = _ref14.rows;

                  if (rows[0]) {
                    _context7.next = 9;
                    break;
                  }

                  return _context7.abrupt(
                    "return",
                    res.status(200).send({ message: "player not found" })
                  );

                case 9:
                  values = [
                    req.body.pseudo || rows[0].pseudo,
                    req.body.mail_address || rows[0].mail_address,
                    req.body.phone_number || rows[0].phone_number,
                    req.body.bio || rows[0].bio,
                    req.body.avatar || rows[0].avatar,
                    req.body.mdp || rows[0].mdp,
                    req.body.is_valid === null ? rows[0].is_valid : req.body.is_valid,
                    req.params.id
                  ];
                  _context7.next = 12;
                  return _db2.default.query(updateOneQuery, values);

                case 12:
                  response = _context7.sent;
                  return _context7.abrupt(
                    "return",
                    res.status(200).send(response.rows[0])
                  );

                case 16:
                  _context7.prev = 16;
                  _context7.t0 = _context7["catch"](2);
                  return _context7.abrupt(
                    "return",
                    res.status(200).send(_context7.t0)
                  );

                case 19:
                case "end":
                  return _context7.stop();
              }
            }
          },
          _callee7,
          this,
          [[2, 16]]
        );
      })
    );

    function update(_x13, _x14) {
      return _ref13.apply(this, arguments);
    }

    return update;
  })(),

  /**
   * Delete A Player
   * @param {object} req
   * @param {object} res
   * @returns {void} return statuc code 204
   */
  delete: (function() {
    var _ref15 = _asyncToGenerator(
      /*#__PURE__*/ regeneratorRuntime.mark(function _callee8(req, res) {
        var deleteQuery, _ref16, rows;

        return regeneratorRuntime.wrap(
          function _callee8$(_context8) {
            while (1) {
              switch ((_context8.prev = _context8.next)) {
                case 0:
                  deleteQuery = "DELETE FROM player WHERE id=$1 RETURNING *";
                  _context8.prev = 1;
                  _context8.next = 4;
                  return _db2.default.query(deleteQuery, [req.params.id]);

                case 4:
                  _ref16 = _context8.sent;
                  rows = _ref16.rows;

                  if (rows[0]) {
                    _context8.next = 8;
                    break;
                  }

                  return _context8.abrupt(
                    "return",
                    res.status(404).send({ message: "player not found" })
                  );

                case 8:
                  return _context8.abrupt(
                    "return",
                    res.status(204).send({ message: "deleted" })
                  );

                case 11:
                  _context8.prev = 11;
                  _context8.t0 = _context8["catch"](1);
                  return _context8.abrupt(
                    "return",
                    res.status(400).send(_context8.t0)
                  );

                case 14:
                case "end":
                  return _context8.stop();
              }
            }
          },
          _callee8,
          this,
          [[1, 11]]
        );
      })
    );

    function _delete(_x15, _x16) {
      return _ref15.apply(this, arguments);
    }

    return _delete;
  })()
};

exports.default = Player;
