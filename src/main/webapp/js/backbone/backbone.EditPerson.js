var EditPerson = Backbone.View.extend({
	el : '.page',
	render : function(options) {
		var that = this;
		if (options.person_id) {
			var person = new Person({
				id : options.person_id
			});
			person.fetch({
				success : function(person) {
					var template = _.template($('#new-person-template').html(),{person : person });
					that.$el.html(template);
				}
			});
		} else {
			var template = _.template($('#new-person-template').html(), { person : null });
			this.$el.html(template);
		}
	},
	events : {
		'submit .new-person-form' : 'savePerson',
		'click .deletePerson' : 'deletePerson'
	},
	savePerson : function(ev) {
		personDetails = $(ev.currentTarget).serializeObject();
		var person = new Person();
		personDetails.person_id = personDetails.id;
		person.save(personDetails, {
			success : function(user) {
				router.navigate('showPerson/'+ user.id, {trigger : true});
			}
		})
		return false;
	},

	deletePerson : function(ev) {
		this.person.destroy({
			success : function() {
				router.navigate('', { trigger : true });
			}
		});
		return false;
	}
});