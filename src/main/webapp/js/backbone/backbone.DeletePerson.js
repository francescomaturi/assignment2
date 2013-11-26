var DeletePerson = Backbone.View.extend({
	el : '.page',
	render : function(options) {
		var person = new Person({
			id : options.person_id
		});
		person.destroy({
			success : function() {
				router.navigate('', {
					trigger : true
				});
			}
		});
		return false;
	}
});