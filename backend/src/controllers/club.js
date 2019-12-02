import moment from 'moment';
//import uuidv4 from 'uuid/v4';
import db from '../db';

const Club = {
  /**
   * Create A Reflection
   * @param {object} req
   * @param {object} res
   * @returns {object} Club object
   */
  async create(req, res) {
    const text = `INSERT INTO Club (club_name, private_club)
	    VALUES($1, $2)
        returning *`;
    const values = [
      req.body.club_name,
      req.body.private_club
    ];

    try {
      const { rows } = await db.query(text, values);
      return res.status(201).send(rows[0]);
    } catch(error) {
      return res.status(400).send(error);
    }
  },
  /**
   * Get All clubs
   * @param {object} req
   * @param {object} res
   * @returns {object} clubs array
   */
  async getAll(req, res) {
    const findAllQuery = 'SELECT * FROM club';
    try {
      const { rows, rowCount } = await db.query(findAllQuery);
      return res.status(200).send({ rows, rowCount });
    } catch(error) {
      return res.status(400).send(error);
    }
  },
  /**
   * Get A Club
   * @param {object} req
   * @param {object} res
   * @returns {object} club object
   */
  async getOne(req, res) {
    const text = 'SELECT * FROM club WHERE id = $1';
    try {
      const { rows } = await db.query(text, [req.params.id]);
      if (!rows[0]) {
        return res.status(404).send({'message': 'club not found'});
      }
      return res.status(200).send(rows[0]);
    } catch(error) {
      return res.status(400).send(error)
    }
  },
  /**
   * Update A Club
   * @param {object} req
   * @param {object} res
   * @returns {object} updated reflection
   */
  async update(req, res) {
    const findOneQuery = 'SELECT * FROM club WHERE id=$1';
    const updateOneQuery =`UPDATE club
      SET club_name=$1,private_club=$2
      WHERE id=$3 returning *`;
    try {
      const { rows } = await db.query(findOneQuery, [req.params.id]);
      if(!rows[0]) {
        return res.status(404).send({'message': 'club not found'});
      }
      const values = [
        req.body.club_name || rows[0].club_name,
        req.body.private_club || rows[0].private_club,
        req.params.id
      ];
      const response = await db.query(updateOneQuery, values);
      return res.status(200).send(response.rows[0]);
    } catch(err) {
      return res.status(400).send(err);
    }
  },
  /**
   * Delete A Club
   * @param {object} req
   * @param {object} res
   * @returns {void} return statuc code 204
   */
  async delete(req, res) {
    const deleteQuery = 'DELETE FROM club WHERE id=$1 returning *';
    try {
      const { rows } = await db.query(deleteQuery, [req.params.id]);
      if(!rows[0]) {
        return res.status(404).send({'message': 'club not found'});
      }
      return res.status(204).send({ 'message': 'deleted' });
    } catch(error) {
      return res.status(400).send(error);
    }
  }
}

export default Club;
