const profileRoutes = require('./profile_routes');
module.exports = function (app) {
    profileRoutes(app);
}