const profileRoutes = require('./user_routes');
module.exports = function (app) {
    profileRoutes(app);
}