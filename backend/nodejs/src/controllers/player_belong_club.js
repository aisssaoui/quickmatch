"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});

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

var player_belong_club = {
  getPlayerAdminClubs: (function() {
    var _ref = _asyncToGenerator(
      /*#__PURE__*/ regeneratorRuntime.mark(function _callee(req, res) {
        var text, _ref2, rows, rowCount;

        return regeneratorRuntime.wrap(
          function _callee$(_context) {
            while (1) {
              switch ((_context.prev = _context.next)) {
                case 0:
                  text =
                    "SELECT * FROM player_belong_club P INNER JOIN club C on P.club = C.id WHERE player = $1 AND is_admin = 'true' ";
                  _context.prev = 1;
                  _context.next = 4;
                  return _db2.default.query(text, [req.params.id]);

                case 4:
                  _ref2 = _context.sent;
                  rows = _ref2.rows;
                  rowCount = _ref2.rowCount;
                  return _context.abrupt(
                    "return",
                    res.status(200).send({ rows: rows, rowCount: rowCount })
                  );

                case 10:
                  _context.prev = 10;
                  _context.t0 = _context["catch"](1);
                  return _context.abrupt(
                    "return",
                    res.status(400).send(_context.t0)
                  );

                case 13:
                case "end":
                  return _context.stop();
              }
            }
          },
          _callee,
          this,
          [[1, 10]]
        );
      })
    );

    function getPlayerAdminClubs(_x, _x2) {
      return _ref.apply(this, arguments);
    }

    return getPlayerAdminClubs;
  })(),
  getPlayerClubsByClubID: (function() {
    var _ref3 = _asyncToGenerator(
      /*#__PURE__*/ regeneratorRuntime.mark(function _callee2(req, res) {
        var text, _ref4, rows, rowCount;

        return regeneratorRuntime.wrap(
          function _callee2$(_context2) {
            while (1) {
              switch ((_context2.prev = _context2.next)) {
                case 0:
                  text =
                    "SELECT * FROM player_belong_club P INNER JOIN club C on P.club = C.id WHERE club=$1";
                  _context2.prev = 1;
                  _context2.next = 4;
                  return _db2.default.query(text, [req.params.id]);

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
                    res.status(400).send(_context2.t0)
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

    function getPlayerClubsByClubID(_x3, _x4) {
      return _ref3.apply(this, arguments);
    }

    return getPlayerClubsByClubID;
  })(),
  getPlayerClubsByPlayerID: (function() {
    var _ref5 = _asyncToGenerator(
      /*#__PURE__*/ regeneratorRuntime.mark(function _callee3(req, res) {
        var text, _ref6, rows, rowCount;

        return regeneratorRuntime.wrap(
          function _callee3$(_context3) {
            while (1) {
              switch ((_context3.prev = _context3.next)) {
                case 0:
                  text =
                    "SELECT * FROM player_belong_club P INNER JOIN club C on P.club = C.id WHERE player=$1";
                  _context3.prev = 1;
                  _context3.next = 4;
                  return _db2.default.query(text, [req.params.id]);

                case 4:
                  _ref6 = _context3.sent;
                  rows = _ref6.rows;
                  rowCount = _ref6.rowCount;
                  return _context3.abrupt(
                    "return",
                    res.status(200).send({ rows: rows, rowCount: rowCount })
                  );

                case 10:
                  _context3.prev = 10;
                  _context3.t0 = _context3["catch"](1);
                  return _context3.abrupt(
                    "return",
                    res.status(400).send(_context3.t0)
                  );

                case 13:
                case "end":
                  return _context3.stop();
              }
            }
          },
          _callee3,
          this,
          [[1, 10]]
        );
      })
    );

    function getPlayerClubsByPlayerID(_x5, _x6) {
      return _ref5.apply(this, arguments);
    }

    return getPlayerClubsByPlayerID;
  })(),
  NgetPlayerClubsByPlayerID: (function() {
    var _ref7 = _asyncToGenerator(
      /*#__PURE__*/ regeneratorRuntime.mark(function _callee4(req, res) {
        var text, _ref8, rows, rowCount;

        return regeneratorRuntime.wrap(
          function _callee4$(_context4) {
            while (1) {
              switch ((_context4.prev = _context4.next)) {
                case 0:
                  text =
                    "SELECT C.id, C.club_name, C.creation_date FROM player_belong_club P INNER JOIN club C on P.club = C.id WHERE player!=$1 AND private_club='false'";
                  _context4.prev = 1;
                  _context4.next = 4;
                  return _db2.default.query(text, [req.params.id]);

                case 4:
                  _ref8 = _context4.sent;
                  rows = _ref8.rows;
                  rowCount = _ref8.rowCount;
                  return _context4.abrupt(
                    "return",
                    res.status(200).send({ rows: rows, rowCount: rowCount })
                  );

                case 10:
                  _context4.prev = 10;
                  _context4.t0 = _context4["catch"](1);
                  return _context4.abrupt(
                    "return",
                    res.status(400).send(_context4.t0)
                  );

                case 13:
                case "end":
                  return _context4.stop();
              }
            }
          },
          _callee4,
          this,
          [[1, 10]]
        );
      })
    );

    function NgetPlayerClubsByPlayerID(_x7, _x8) {
      return _ref7.apply(this, arguments);
    }

    return NgetPlayerClubsByPlayerID;
  })(),
  addPlayerToClub: (function() {
    var _ref9 = _asyncToGenerator(
      /*#__PURE__*/ regeneratorRuntime.mark(function _callee5(req, res) {
        var text, values, _ref10, rows;

        return regeneratorRuntime.wrap(
          function _callee5$(_context5) {
            while (1) {
              switch ((_context5.prev = _context5.next)) {
                case 0:
                  text =
                    "INSERT INTO player_belong_club (player, club, is_admin)\n\t    VALUES($1, $2, $3)\n        returning *";
                  values = [req.body.player, req.body.club, req.body.is_admin];
                  _context5.prev = 2;
                  _context5.next = 5;
                  return _db2.default.query(text, values);

                case 5:
                  _ref10 = _context5.sent;
                  rows = _ref10.rows;
                  return _context5.abrupt(
                    "return",
                    res.status(201).send(rows[0])
                  );

                case 10:
                  _context5.prev = 10;
                  _context5.t0 = _context5["catch"](2);
                  return _context5.abrupt(
                    "return",
                    res.status(400).send(_context5.t0)
                  );

                case 13:
                case "end":
                  return _context5.stop();
              }
            }
          },
          _callee5,
          this,
          [[2, 10]]
        );
      })
    );

    function addPlayerToClub(_x9, _x10) {
      return _ref9.apply(this, arguments);
    }

    return addPlayerToClub;
  })(),
  deletePlayerFromClub: (function() {
    var _ref11 = _asyncToGenerator(
      /*#__PURE__*/ regeneratorRuntime.mark(function _callee6(req, res) {
        var deleteQuery, _ref12, rows;

        return regeneratorRuntime.wrap(
          function _callee6$(_context6) {
            while (1) {
              switch ((_context6.prev = _context6.next)) {
                case 0:
                  deleteQuery =
                    "DELETE FROM player_belong_club WHERE club=$1 AND player= $2 returning *";
                  _context6.prev = 1;
                  _context6.next = 4;
                  return _db2.default.query(deleteQuery, [
                    req.params.cid,
                    req.params.pid
                  ]);

                case 4:
                  _ref12 = _context6.sent;
                  rows = _ref12.rows;

                  if (rows[0]) {
                    _context6.next = 8;
                    break;
                  }

                  return _context6.abrupt(
                    "return",
                    res.status(404).send({ message: "club not found" })
                  );

                case 8:
                  return _context6.abrupt(
                    "return",
                    res.status(204).send({ message: "deleted" })
                  );

                case 11:
                  _context6.prev = 11;
                  _context6.t0 = _context6["catch"](1);
                  return _context6.abrupt(
                    "return",
                    res.status(400).send(_context6.t0)
                  );

                case 14:
                case "end":
                  return _context6.stop();
              }
            }
          },
          _callee6,
          this,
          [[1, 11]]
        );
      })
    );

    function deletePlayerFromClub(_x11, _x12) {
      return _ref11.apply(this, arguments);
    }

    return deletePlayerFromClub;
  })(),
  getAll: (function() {
    var _ref13 = _asyncToGenerator(
      /*#__PURE__*/ regeneratorRuntime.mark(function _callee7(req, res) {
        var findAllQuery, _ref14, rows, rowCount;

        return regeneratorRuntime.wrap(
          function _callee7$(_context7) {
            while (1) {
              switch ((_context7.prev = _context7.next)) {
                case 0:
                  findAllQuery = "SELECT * FROM player_belong_club";
                  _context7.prev = 1;
                  _context7.next = 4;
                  return _db2.default.query(findAllQuery);

                case 4:
                  _ref14 = _context7.sent;
                  rows = _ref14.rows;
                  rowCount = _ref14.rowCount;
                  return _context7.abrupt(
                    "return",
                    res.status(200).send({ rows: rows, rowCount: rowCount })
                  );

                case 10:
                  _context7.prev = 10;
                  _context7.t0 = _context7["catch"](1);
                  return _context7.abrupt(
                    "return",
                    res.status(400).send(_context7.t0)
                  );

                case 13:
                case "end":
                  return _context7.stop();
              }
            }
          },
          _callee7,
          this,
          [[1, 10]]
        );
      })
    );

    function getAll(_x13, _x14) {
      return _ref13.apply(this, arguments);
    }

    return getAll;
  })(),
  getCountAdmin: (function() {
    var _ref15 = _asyncToGenerator(
      /*#__PURE__*/ regeneratorRuntime.mark(function _callee8(req, res) {
        var text, _ref16, rows;

        return regeneratorRuntime.wrap(
          function _callee8$(_context8) {
            while (1) {
              switch ((_context8.prev = _context8.next)) {
                case 0:
                  text =
                    "SELECT COUNT(*) as nb FROM player_belong_club WHERE club=$1 AND is_admin='true'";
                  _context8.prev = 1;
                  _context8.next = 4;
                  return _db2.default.query(text, [req.params.cid]);

                case 4:
                  _ref16 = _context8.sent;
                  rows = _ref16.rows;

                  if (rows[0]) {
                    _context8.next = 8;
                    break;
                  }

                  return _context8.abrupt(
                    "return",
                    res.status(404).send({ message: "club not found" })
                  );

                case 8:
                  return _context8.abrupt(
                    "return",
                    res.status(200).send(rows[0])
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

    function getCount(_x15, _x16) {
      return _ref15.apply(this, arguments);
    }

    return getCount;
  })()
};

exports.default = player_belong_club;
