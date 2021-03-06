bodyParser = require('body-parser').json();
const { Client } = require('pg');
const connectionData = {
    host: 'localhost',
    user: 'postgres',
    password: 'postgres',
    database: 'nodejspractice',
};


module.exports = function (app) {
    //About me
    app.get('/profile', (request, response) => {
        let result = [
            {
                "name": "Айвар",
                "age": 19,
                "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum vulputate fringilla rhoncus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Phasellus malesuada in elit sit amet hendrerit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus tristique, metus at auctor pretium, nisl sapien tempor metus, eu aliquam leo urna et eros. In arcu felis, fermentum quis enim maximus, commodo semper lacus. Maecenas eu lectus dictum justo vulputate faucibus nec sed mi. "
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

    //Route for render User's information
    app.post('/user_render', bodyParser, (request, response) => {
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


    //Getting users list
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

    //Just API route
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
            let result = {
                "name": body.user.name,
                "age": body.user.age,
                "id": parseInt(id)
            }
            response.setHeader("Content-type", "application/json");
            response.json(result);
        });
    });
}