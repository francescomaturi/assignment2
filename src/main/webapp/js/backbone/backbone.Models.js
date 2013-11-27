HealthProfile = Backbone.Model.extend({
	idAttribute : "healthprofile_id",
	urlRoot : function() {
		return '/person/' + this.get('person_id') + '/healthprofile';
	}
});

Person = Backbone.Model.extend({
	urlRoot : '/person',
	initialize : function(options) {
		this.healthProfileHistory = new HealthProfileHistory;
		this.healthProfileHistory.url = function() {
		    return '/person/' + options.id + '/healthprofile';
		};
		this.healthProfileHistory.bind("reset", this.updateCounts);
	}
});