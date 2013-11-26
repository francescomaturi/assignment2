var PersonList = Backbone.View.extend({
  el : '.page',
  render : function() {
    that = this;
    var persons = new People();
    persons.fetch({
      success : function(people) {
        var template = _.template($('#person-list-template')
            .html(), {
          people : people.models
        });
        that.$el.html(template);
      }
    });
  }
});