var BirthdateSearch = Backbone.Collection.extend({
	url : '/search/birthdate',
	parse : function(response) {
		if (response) {
			return response.person;
		} else
			return null;
	}
});

var MeasureSearch = Backbone.Collection.extend({
	url : '/search/profile',
	parse : function(response) {
		if (response) {
			return response.person;
		} else
			return null;
	}
});

var NameSearch = Backbone.Collection.extend({
	url : '/search/name',
	parse : function(response) {
		if (response) {
			return response.person;
		} else
			return null;
	}
});

People = Backbone.Collection.extend({
	model : Person,
	url : '/person',
	parse : function(response) {
		if (response) {
			return response.person;
		} else
			return null;
	}
});

HealthProfileHistory = Backbone.Collection.extend({
	model : HealthProfile,
	parse : function(response) {
		if (response)
			return response.healthProfile;
		else
			return null;
	}
});