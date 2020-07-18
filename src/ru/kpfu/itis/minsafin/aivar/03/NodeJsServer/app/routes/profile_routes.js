module.exports = function (app) {
    app.get('/profile', (request, response) => {
        var result = [
            {
                "name": "Айвар",
                "age": 19,
                "description": "Тут должно быть описание, но я пока не придумал."
            }
        ];
        response.send(JSON.stringify(result));
    })
}