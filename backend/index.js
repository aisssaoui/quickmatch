import express from 'express';
import dotenv from 'dotenv';
import 'babel-polyfill';
import Player from './src/controllers/player';


dotenv.config();
const app = express()

app.use(express.json())

app.get('/', (req, res) => {
  return res.status(200).send({'message': 'YAY! Congratulations! Your first endpoint is working'});
});

app.post('/api/v1/Players', Player.create);
app.get('/api/v1/Players', Player.getAll);
app.get('/api/v1/Players/:id', Player.getOne);
app.put('/api/v1/Players/:id', Player.update);
app.delete('/api/v1/Players/:id', Player.delete);

app.post('/api/v1/Clubs', Club.create);
app.get('/api/v1/Clubs', Club.getAll);
app.get('/api/v1/Clubs/:id', Club.getOne);
app.put('/api/v1/Clubs/:id', Club.update);
app.delete('/api/v1/Clubs/:id', Club.delete);

app.listen(3000)
console.log('app running on port ', 3000);
