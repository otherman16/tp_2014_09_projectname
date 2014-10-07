define([
	// Libs
	'backbone'
], function(Backbone) {
	var ScoreModel = Backbone.Model.extend({
		defaults: {
			login: "",
			score: 0
		}
	});
	return ScoreModel;
})