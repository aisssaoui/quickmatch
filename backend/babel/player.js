import moment from "moment";
//import uuidv4 from 'uuid/v4';
import db from "../db";

const Player = {
  /**
   * Create A Player
   * @param {object} req
   * @param {object} res
   * @returns {object} Player object
   */
  async create(req, res) {
    const text = `INSERT INTO Player (surname, first_name, mail_address, phone_number)
	    VALUES($1, $2, $3, $4)
        returning *`;
    const values = [
      req.body.surname,
      req.body.first_name,
      req.body.mail_adress,
      req.body.phone_number
    ];

    try {
      const { rows } = await db.query(text, values);
      return res.status(201).send(rows[0]);
    } catch (error) {
      return res.status(400).send(error);
    }
  },
  /**
   * Get All players
   * @param {object} req
   * @param {object} res
   * @returns {object} players array
   */
  async getAll(req, res) {
    const findAllQuery = "SELECT * FROM player";
    try {
      const { rows, rowCount } = await db.query(findAllQuery);
      return res.status(200).send({ rows, rowCount });
    } catch (error) {
      return res.status(400).send(error);
    }
  },
  /**
   * Get A Player
   * @param {object} req
   * @param {object} res
   * @returns {object} player object
   */
  async getOne(req, res) {
    const text = "SELECT * FROM player WHERE id = $1";
    try {
      const { rows } = await db.query(text, [req.params.id]);
      if (!rows[0]) {
        return res.status(404).send({ message: "player not found" });
      }
      return res.status(200).send(rows[0]);
    } catch (error) {
      return res.status(400).send(error);
    }
  },
  /**
   * Update A Player
   * @param {object} req
   * @param {object} res
   * @returns {object} updated Player
   */
  async update(req, res) {
    const findOneQuery = "SELECT * FROM player WHERE id=$1";
    const updateOneQuery = `UPDATE player
      SET surname=$1,first_name=$2,mail_adress=$3,phone_number=$4
      WHERE id=$5 returning *`;
    try {
      const { rows } = await db.query(findOneQuery, [req.params.id]);
      if (!rows[0]) {
        return res.status(404).send({ message: "player not found" });
      }
      const values = [
        req.body.surname || rows[0].surname,
        req.body.first_name || rows[0].first_name,
        req.body.mail_adress || rows[0].mail_adress,
        req.body.phone_number || rows[0].phone_number,
        req.params.id
      ];
      const response = await db.query(updateOneQuery, values);
      return res.status(200).send(response.rows[0]);
    } catch (err) {
      return res.status(400).send(err);
    }
  },
  /**
   * Delete A Player
   * @param {object} req
   * @param {object} res
   * @returns {void} return statuc code 204
   */
  async delete(req, res) {
    const deleteQuery = "DELETE FROM player WHERE id=$1 returning *";
    try {
      const { rows } = await db.query(deleteQuery, [req.params.id]);
      if (!rows[0]) {
        return res.status(404).send({ message: "player not found" });
      }
      return res.status(204).send({ message: "deleted" });
    } catch (error) {
      return res.status(400).send(error);
    }
  }
};

export default Player;
