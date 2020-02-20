"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _moment = require("moment");

var _moment2 = _interopRequireDefault(_moment);

var _db = require("../db");

var _db2 = _interopRequireDefault(_db);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _asyncToGenerator(fn) { return function () { var gen = fn.apply(this, arguments); return new Promise(function (resolve, reject) { function step(key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { return Promise.resolve(value).then(function (value) { step("next", value); }, function (err) { step("throw", err); }); } } return step("next"); }); }; }
//import uuidv4 from 'uuid/v4';


var invitation_club = {
  /**
   * Create an invitation into a club
   * @param {object} req
   * @param {object} res
   * @returns {object} Invitation_Club object
   */
  create: function () {
    var _ref = _asyncToGenerator( /*#__PURE__*/regeneratorRuntime.mark(function _callee(req, res) {
      var text, values, _ref2, rows;

      return regeneratorRuntime.wrap(function _callee$(_context) {
        while (1) {
          switch (_context.prev = _context.next) {
            case 0:
              text = "INSERT INTO Invitation_Club (player, club) VALUES($1, $2) RETURNING *";
              values = [req.params.pid, req.params.cid];
              _context.prev = 2;
              _context.next = 5;
              return _db2.default.query(text, values);

            case 5:
              _ref2 = _context.sent;
              rows = _ref2.rows;
              return _context.abrupt("return", res.status(201).send(rows[0]));

            case 10:
              _context.prev = 10;
              _context.t0 = _context["catch"](2);
              return _context.abrupt("return", res.status(200).send(_context.t0));

            case 13:
            case "end":
              return _context.stop();
          }
        }
      }, _callee, this, [[2, 10]]);
    }));

    function create(_x, _x2) {
      return _ref.apply(this, arguments);
    }

    return create;
  }(),


  /**
   * Get the $1 player inviations into a club
   * @param {object} req
   * @param {object} res
   * @returns {object} inviations array
   */
  getPlayerInvitations: function () {
    var _ref3 = _asyncToGenerator( /*#__PURE__*/regeneratorRuntime.mark(function _callee2(req, res) {
      var text, values, _ref4, rows, rowCount;

      return regeneratorRuntime.wrap(function _callee2$(_context2) {
        while (1) {
          switch (_context2.prev = _context2.next) {
            case 0:
              text = "SELECT I.id, I.player, I.club, C.club_name FROM\n                    Invitation_Club I\n                    JOIN\n                    Club C\n                    ON\n                    I.club = C.id\n                  WHERE I.player = $1";
              values = [req.params.pid];
              _context2.prev = 2;
              _context2.next = 5;
              return _db2.default.query(text);

            case 5:
              _ref4 = _context2.sent;
              rows = _ref4.rows;
              rowCount = _ref4.rowCount;
              return _context2.abrupt("return", res.status(200).send({ rows: rows, rowCount: rowCount }));

            case 11:
              _context2.prev = 11;
              _context2.t0 = _context2["catch"](2);
              return _context2.abrupt("return", res.status(200).send(_context2.t0));

            case 14:
            case "end":
              return _context2.stop();
          }
        }
      }, _callee2, this, [[2, 11]]);
    }));

    function getPlayerInvitations(_x3, _x4) {
      return _ref3.apply(this, arguments);
    }

    return getPlayerInvitations;
  }(),


  /**
   * Delete a player inviation into a club
   * @param {object} req
   * @param {object} res
   * @returns {void} return status code 204
   */
  delete: function () {
    var _ref5 = _asyncToGenerator( /*#__PURE__*/regeneratorRuntime.mark(function _callee3(req, res) {
      var deleteQuery, values, _ref6, rows;

      return regeneratorRuntime.wrap(function _callee3$(_context3) {
        while (1) {
          switch (_context3.prev = _context3.next) {
            case 0:
              deleteQuery = "DELETE FROM Invitation_Club WHERE id = $1 RETURNING *";
              values = [req.params.ci_id];
              _context3.prev = 2;
              _context3.next = 5;
              return _db2.default.query(deleteQuery, values);

            case 5:
              _ref6 = _context3.sent;
              rows = _ref6.rows;

              if (rows[0]) {
                _context3.next = 9;
                break;
              }

              return _context3.abrupt("return", res.status(404).send({ message: "player not found" }));

            case 9:
              return _context3.abrupt("return", res.status(204).send({ message: "deleted" }));

            case 12:
              _context3.prev = 12;
              _context3.t0 = _context3["catch"](2);
              return _context3.abrupt("return", res.status(400).send(_context3.t0));

            case 15:
            case "end":
              return _context3.stop();
          }
        }
      }, _callee3, this, [[2, 12]]);
    }));

    function _delete(_x5, _x6) {
      return _ref5.apply(this, arguments);
    }

    return _delete;
  }()
};

exports.default = invitation_club;