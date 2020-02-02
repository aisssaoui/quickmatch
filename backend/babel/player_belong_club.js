import moment from "moment";
//import uuidv4 from 'uuid/v4';
import db from "../db";

const player_belong_club = {
  /**
   * Get all the clubs where the player is an admin
   * @param {object} req
   * @param {object} res
   * @returns {object} player_belong_club & club object
   */
  async getPlayerAdminClubs(req, res) {
    const text =
      "SELECT * FROM player_belong_club P INNER JOIN club C on P.club = C.id WHERE player = $1 AND is_admin = TRUE";
    try {
      const { rows, rowCount } = await db.query(text, [req.params.id]);
      return res.status(200).send({ rows, rowCount });
    } catch (error) {
      return res.status(400).send(error);
    }
  },

  /**
   * Get all the player who belong to the $1 club
   * @param {object} req
   * @param {object} res
   * @returns {object} player_belong_club & player object
   */
  async getPlayerClubsByClubID(req, res) {
    const text =
      `SELECT P.id, P.surname, P.first_name, P.pseudo, PBC.is_admin FROM
       player_belong_club PBC INNER JOIN player P ON P.id = PBC.player
       WHERE PBC.club = $1`;
    try {
      const { rows, rowCount } = await db.query(text, [req.params.id]);
      return res.status(200).send({ rows, rowCount });
    } catch (error) {
      return res.status(400).send(error);
    }
  },

  /**
  * Get all the player who do not belong to the $1 club
  * @param {object} req
  * @param {object} res
  * @returns {object} player_belong_club & player object
  */
  async NgetPlayerClubsByClubID(req, res) {
    const text =
    `SELECT P.id, P.surname, P.first_name, P.pseudo FROM player P
     WHERE P.id NOT IN (SELECT PBC.player FROM player_belong_club PBC WHERE PBC.club = $1)`;
    try {
      const { rows, rowCount } = await db.query(text, [req.params.id]);
      return res.status(200).send({ rows, rowCount });
    } catch (error) {
      return res.status(400).send(error);
    }
  },

  /**
   * Get all the club where the $1 player is
   * @param {object} req
   * @param {object} res
   * @returns {object} player_belong_club & club object
   */
  async getPlayerClubsByPlayerID(req, res) {
    const text =
      "SELECT * FROM player_belong_club PBC INNER JOIN club C on PBC.club = C.id WHERE PBC.player = $1";
    try {
      const { rows, rowCount } = await db.query(text, [req.params.id]);
      return res.status(200).send({ rows, rowCount });
    } catch (error) {
      return res.status(400).send(error);
    }
  },

  /**
   * Get all the club where the $1 player is not and which are public club
   * @param {object} req
   * @param {object} res
   * @returns {object} player_belong_club & club object
   */
  async NgetPlayerClubsByPlayerID(req, res) {
    const text =
      `SELECT * FROM club C WHERE
       C.id NOT IN
         (SELECT PBC.club FROM player_belong_club PBC WHERE PBC.player = $1)
       AND C.private_club = FALSE`;
    try {
      const { rows, rowCount } = await db.query(text, [req.params.id]);
      return res.status(200).send({ rows, rowCount });
    } catch (error) {
      return res.status(400).send(error);
    }
  },

  /**
   * Add a player to a club
   * @param {object} req
   * @param {object} res
   * @returns {object} player_belong_club object
   */
  async addPlayerToClub(req, res) {
    const text = "INSERT INTO player_belong_club (player, club, is_admin) VALUES($1, $2, $3) RETURNING *";
    const values = [req.body.player, req.body.club, req.body.is_admin];
    try {
      const { rows } = await db.query(text, values);
      return res.status(201).send(rows[0]);
    } catch (error) {
      return res.status(400).send(error);
    }
  },

  /**
   * Delete a player from a club
   * @param {object} req
   * @param {object} res
   * @returns {object} player_belong_club object
   */
  async deletePlayerFromClub(req, res) {
    const deleteQuery =
      "DELETE FROM player_belong_club WHERE club = $1 AND player = $2 RETURNING *";
    const values = [
      req.params.cid,
      req.params.pid
    ];
    try {
      const { rows } = await db.query(deleteQuery, values);
      if (!rows[0]) {
        return res.status(404).send({ message: "belonging of the player to the club not found" });
      }
      return res.status(204).send({ message: "deleted" });
    } catch (error) {
      return res.status(400).send(error);
    }
  },

  /**
   * Get all the belonging of players to clubs
   * @param {object} req
   * @param {object} res
   * @returns {object} player_belong_club object
   */
  async getAll(req, res) {
    const findAllQuery = "SELECT * FROM player_belong_club";
    try {
      const { rows, rowCount } = await db.query(findAllQuery);
      return res.status(200).send({ rows, rowCount });
    } catch (error) {
      return res.status(400).send(error);
    }
  },

  /**
   * Get the number of admin of the $1 club
   * @param {object} req
   * @param {object} res
   * @returns {object} int
   */
  async getCountAdmin(req, res) {
    const text =
      "SELECT COUNT(*) AS nb FROM player_belong_club WHERE club = $1 AND is_admin = TRUE GROUP BY club";
    try {
      const { rows, rowCount } = await db.query(text, [req.params.cid]);
      return res.status(200).send({ rows, rowCount });
    } catch (error) {
      return res.status(400).send(error);
    }
  },

  /**
   * Promote a player to admin of the given club
   * @param {object} req
   * @param {object} res
   * @returns {object} updated player_belong_club object
   */
  async promoteToAdmin(req, res) {
    const text =
      "UPDATE player_belong_club SET is_admin = TRUE WHERE player = $1 AND club = $2 RETURNING *";
      const values = [
        req.params.pid,
        req.params.cid
      ];
    try {
      const { rows } = await db.query(text, values);
      return res.status(200).send(rows[0]);
    } catch (error) {
      return res.status(400).send(error);
    }
  }
};

export default player_belong_club;
