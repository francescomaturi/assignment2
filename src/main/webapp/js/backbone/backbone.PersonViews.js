var PersonList = Backbone.View.extend({
	el : '.page',
	render : function() {
		var that = this;
		var persons = new People();
		persons.fetch({
			success : function(people) {
				var filtersTemplate = _.template($('#filters-template').html(),
						{});
				that.$el.html(filtersTemplate);

				var template = _.template($('#people-list-template').html(), {
					people : people.models
				});
				that.$el.find("div#peopleList").html(template);
			}
		});
	},
	events : {
		'submit form.searchForm' : 'search'
	},
	search : function(ev) {
		var that = this;

		var searchValues = $(ev.currentTarget).serializeObject();

		if (searchValues.condition == "measure") {

			var measureSearch = new MeasureSearch();

			measure = new String(searchValues.measure);
			max = new String(searchValues.max);
			min = new String(searchValues.min);

			measureSearch.fetch({
				data : $.param({
					measure : measure,
					min : min,
					max : max
				}),
				success : function(filteredPeople) {
					var template = _.template(
							$('#people-list-template').html(), {
								people : filteredPeople.models
							});
					that.$el.find("div#peopleList").html(template);
				}
			});
		} else if (searchValues.condition == "birthdate") {

			var birthdateSearch = new BirthdateSearch();

			from = new String(searchValues.from);
			to = new String(searchValues.to);

			birthdateSearch.fetch({
				data : $.param({
					from : from,
					to : to
				}),
				success : function(filteredPeople) {
					var template = _.template(
							$('#people-list-template').html(), {
								people : filteredPeople.models
							});
					that.$el.find("div#peopleList").html(template);
				}
			});
		} else {
			this.render(); // all people selected!
		}

		return false;
	},
	searchName : function(options){
		var that = this;
		var name = options.name;
		
		var nameSearch = new NameSearch();
		nameSearch.fetch({
			data : $.param({
				q : name
			}),
			success : function(filteredPeople) {
				var template = _.template(
						$('#people-list-template').html(), {
							people : filteredPeople.models
						});
				that.$el.find("div#peopleList").html(template);
			}
		});
		
	}
});

var EditPerson = Backbone.View.extend({
	el : '.page',
	render : function(options) {
		var that = this;
		if (options.person_id) {  // edit form
			that.person = new Person({ id : options.person_id });
			that.person.fetch({
				success : function(person) {
					var template = _.template($('#new-person-template').html(),{person : person });
					that.$el.html(template);
				}
			});
		} else {  // new form
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

	deletePerson : function(options) {
		var person = null;
		
		if(options.person_id)
			person = new Person({id : options.person_id}); //from delete button of list
		else
			person = this.person; //from delete button of edit profile
		
		person.destroy({
			success : function() {
				router.navigate('', { trigger : true });
			}
		});
		return false;
	},
	showPerson : function(options) {
		var that = this;
		var person = new Person({
			id : options.person_id
		});
		person.fetch({
			success : function(person) {
				person.healthProfileHistory.fetch({
					success : function(healthProfileHistory) {
						var template = _.template($('#show-person-template')
								.html(), {
							person : person,
							healthProfileHistory : healthProfileHistory.models
						});
						that.$el.html(template);
					}
				});
			}
		});
	}
});
