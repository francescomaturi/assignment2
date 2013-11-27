var BirthdateSearch = Backbone.Collection.extend({
	url : '/person/birthdate',
	parse : function(response) {
		if (response) {
			return response.person;
		} else
			return null;
	}
});

var MeasureSearch = Backbone.Collection.extend({
	url : '/person/profile',
	parse : function(response) {
		if (response) {
			return response.person;
		} else
			return null;
	}
});

var NameSearch = Backbone.Collection.extend({
	url : '/person/search',
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
		return response.person;
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