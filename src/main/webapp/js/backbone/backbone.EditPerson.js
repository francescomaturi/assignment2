var EditPerson = Backbone.View.extend({
	el : '.page',
	render : function(options) {
		var that = this;
		if (options.person_id) {
			that.person = new Person({ id : options.person_id });
			that.person.fetch({
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
		person.save(personDetails, {
			success : function(user) {
				var person_id = personDetails.id ? personDetails.id : user.get('person_id');
				router.navigate('#/showPerson/'+ person_id, {trigger : true});
			}
		});
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