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

var PlayerClubs = {
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
                  text = "SELECT * FROM player_belong_club WHERE club = $1";
                  _context2.prev = 1;
                  _context2.next = 4;
                  return _db2.default.query(text, [req.params.cid]);

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

    function getPlayerClubsByClubID(_x3, _x4) {
      return _ref3.apply(this, arguments);
    }

    return getPlayerClubsByClubID;
  })()
};

exports.default = PlayerClubs;
