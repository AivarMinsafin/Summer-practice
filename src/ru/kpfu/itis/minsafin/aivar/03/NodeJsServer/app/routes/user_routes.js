bodyParser = require('body-parser').json();
const { Client } = require('pg');
const connectionData = {
    host: 'localhost',
    user: 'postgres',
    password: 'postgres',
    database: 'nodejspractice',
};


module.exports = function (app) {
    app.get('/profile', (request, response) => {
        let result = [
            {
                "name": "Айвар",
                "age": 19,
                "description": "Тут должно быть описание, но я пока не придумал."
            }
        ];
        response.setHeader("Content-type", "application/json");
        response.send(JSON.stringify(result));
    });

    app.post('/profile', bodyParser, (request, response) => {
        let body = request.body;
        console.log(body["name"]);
        let responseBody = {
            id: Math.random(),
            "name": body["name"]
        }
        response.setHeader("Content-type", "application/json");
        response.send(JSON.stringify(responseBody));
    })

    app.post('/user', bodyParser, (request, response) => {
        let body = request.body;
        console.log(body.user.name);
        console.log(body.user.age);
        let id = 'undefined';
        client = new Client(connectionData);
        client.connect();
        client.query("INSERT INTO users(name, age) VALUES ($1, $2) RETURNING id;", [body.user.name, body.user.age], (err, res) =>{
            if (err){
                console.log(err.stack);
            } else {
                id = res.rows[0]["id"];
                console.log(id);
                console.log("INFO: user are added")
            }
            response.render("request_sent.html", {name: body.user.name, age: body.user.age, userId: id});
        });
    });

    app.get('/users', (request, response) => {
        client = new Client(connectionData);
        client.connect();
        client.query("SELECT * FROM users", [], (err, res) =>{
            if (err){
                console.log(err.stack);
            } else {
                console.log("INFO: Users are loaded");
            }
            response.setHeader("Content-type", "application/json");
            response.json(res.rows);
        });
    });
}