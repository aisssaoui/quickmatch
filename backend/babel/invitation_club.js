import moment from "moment";
//import uuidv4 from 'uuid/v4';
import db from "../db";

const invitation_club = {
  /**
   * Create an invitation into a club
   * @param {object} req
   * @param {object} res
   * @returns {object} Invitation_Club object
   */
  async create(req, res) {
    const text = "INSERT INTO Invitation_Club (player, club) VALUES($1, $2) RETURNING *";
    const values = [req.params.pid, req.params.cid];

    try {
      const { rows } = await db.query(text, values);
      return res.status(201).send(rows[0]);
    } catch (error) {
      return res.status(200).send(error);
    }
  },

  /**
   * Get the $1 player inviations into a club
   * @param {object} req
   * @param {object} res
   * @returns {object} inviations array
   */
  async getPlayerInvitations(req, res) {
    const text = `SELECT I.id, I.player, I.club, C.club_name FROM
                    Invitation_Club I
                    JOIN
                    Club C
                    ON
                    I.club = C.id
                  WHERE I.player = $1`;
    const values = [req.params.pid];

    try {
      const { rows, rowCount } = await db.query(text);
      return res.status(200).send({ rows, rowCount });
    } catch (error) {
      return res.status(200).send(error);
    }
  },

  /**
   * Delete a player inviation into a club
   * @param {object} req
   * @param {object} res
   * @returns {void} return status code 204
   */
  async delete(req, res) {
    const deleteQuery = "DELETE FROM Invitation_Club WHERE id = $1 RETURNING *";
    const values = [req.params.ci_id];

    try {
      const { rows } = await db.query(deleteQuery, values);
      if (!rows[0]) {
        return res.status(404).send({ message: "player not found" });
      }
      return res.status(204).send({ message: "deleted" });
    } catch (error) {
      return res.status(400).send(error);
    }
  }
};

export default invitation_club;
