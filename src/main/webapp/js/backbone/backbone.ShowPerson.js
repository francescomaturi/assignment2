var ShowPerson = Backbone.View.extend({
	el : '.page',
	render : function(options) {
		var that = this;
		var person = new Person({
			id : options.person_id
		});
//		person.healthProfile.fetch({
//			success : function(person) {
//				var template = _.template($('#show-person-template').html(), {
//					person : person
//				});
//				that.$el.html(template);
//			}
//		});
		person.fetch();
	}
});