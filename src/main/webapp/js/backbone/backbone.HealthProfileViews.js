var EditHealthProfile = Backbone.View.extend({
	el : '.page',
	render : function(options) {
		var that = this;
		if (options.healthprofile_id) {
			that.hp = new HealthProfile({
				person_id : options.person_id,
				healthprofile_id : options.healthprofile_id
			});
			that.hp.fetch({
				success : function(healthProfile) {
					var template = _.template($('#edit-healthprofile-template')
							.html(), {
						healthProfile : healthProfile,
						person_id : options.person_id
					});
					that.$el.html(template);
				}
			});
		} else {
			var template = _.template($('#edit-healthprofile-template').html(),
					{
						healthProfile : null,
						person_id : options.person_id
					});
			that.$el.html(template);
		}
	},
	events : {
		'submit .edit-healthprofile-form' : 'saveHealthProfile',
		'click .deleteHealthProfile' : 'deleteHealthProfile'
	},

	saveHealthProfile : function(ev) {
		healthProfileDetails = $(ev.currentTarget).serializeObject();
		var person_id = healthProfileDetails.person_id;
		var hp = new HealthProfile();
		hp.save(healthProfileDetails, {
			success : function(healthProfile) {
				router.navigate('showPerson/' + person_id, {
					trigger : true
				});
			}
		})
		return false;
	},

	deleteHealthProfile : function(options) {
		var hp = null;
		var person_id = null;
		
		if(options.healthprofile_id){  //delete from healthprofile history
			hp = new HealthProfile({
				person_id : options.person_id,
				healthprofile_id : options.healthprofile_id
			});
			person_id = options.person_id;
		} else {	//delete from edit healthprofile from
			person_id = this.hp.get('person_id');
			hp = this.hp;
		}
		
		hp.destroy({
			success : function() {
				router.navigate('#/showPerson/' + person_id, {
					trigger : true
				});
			}
		});
		return false;
	}
});