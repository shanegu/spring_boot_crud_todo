angular.module('app.services', []).factory('Todo', function($resource) {
  return $resource('/api/v1/todos/:id', { id: '@id' }, {
    update: {
      method: 'PUT'
    }
  });
}).service('popupService',function($window){
    this.showPopup=function(message){
        return $window.confirm(message);
    }
});
