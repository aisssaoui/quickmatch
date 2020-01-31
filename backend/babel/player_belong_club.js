import db from "../db";

const player_belong_club = {
  async getPlayerAdminClubs(req, res) {
    const text =
      "SELECT * FROM player_belong_club P INNER JOIN club C on P.club = C.id WHERE player = $1 AND is_admin = 'true' ";
    try {
      const { rows, rowCount } = await db.query(text, [req.params.id]);
      return res.status(200).send({ rows, rowCount });
    } catch (error) {
      return res.status(400).send(error);
    }
  },

  async getPlayerClubsByClubID(req, res) {
    const text =
      "SELECT * FROM player_belong_club INNER JOIN club WHERE club = $1";
    try {
      const { rows, rowCount } = await db.query(text, [req.params.id]);
      return res.status(200).send({ rows, rowCount });
    } catch (error) {
      return res.status(400).send(error);
    }
  },

  async getPlayerClubsByPlayerID(req, res) {
    const text =
      "SELECT * FROM player_belong_club INNER JOIN club WHERE player = $1";
    try {
      const { rows, rowCount } = await db.query(text, [req.params.id]);
      return res.status(200).send({ rows, rowCount });
    } catch (error) {
      return res.status(400).send(error);
    }
  },

  async NgetPlayerClubsByPlayerID(req, res) {
    const text =
      "(SELECT club_name FROM player_belong_club P INNER JOIN club C on P.club = C.id) EXCEPT (SELECT club_name FROM player_belong_club P INNER JOIN club C on P.club = C.id WHERE player=$1)";
    try {
      const { rows, rowCount } = await db.query(text, [req.params.id]);
      return res.status(200).send({ rows, rowCount });
    } catch (error) {
      return res.status(400).send(error);
    }
  },

  async addPlayerToClub(req, res) {
    const text = `INSERT INTO player_belong_club (player, club, is_admin)
	    VALUES($1, $2, $3)
        returning *`;
    const values = [req.body.player, req.body.club, req.body.is_admin];

    try {
      const { rows } = await db.query(text, values);
      return res.status(201).send(rows[0]);
    } catch (error) {
      return res.status(400).send(error);
    }
  },

  async deletePlayerFromClub(req, res) {
    const deleteQuery =
      "DELETE FROM player_belong_club WHERE player=$1 returning *";
    try {
      const { rows } = await db.query(deleteQuery, [req.params.id]);
      if (!rows[0]) {
        return res.status(404).send({ message: "club not found" });
      }
      return res.status(204).send({ message: "deleted" });
    } catch (error) {
      return res.status(400).send(error);
    }
  },

  async getAll(req, res) {
    const findAllQuery = "SELECT * FROM player_belong_club";
    try {
      const { rows, rowCount } = await db.query(findAllQuery);
      return res.status(200).send({ rows, rowCount });
    } catch (error) {
      return res.status(400).send(error);
    }
  },

  async getCount(req, res) {
    const text =
      "select club, count(*) as nb from player_belong_club where club=$1 group by club ;";
    try {
      const { rows } = await db.query(text, [req.params.id]);
      if (!rows[0]) {
        return res.status(404).send({ message: "club not found" });
      }
      return res.status(200).send(rows[0]);
    } catch (error) {
      return res.status(400).send(error);
    }
  }
};

export default player_belong_club;
