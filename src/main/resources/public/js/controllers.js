angular.module('app.controllers', []).controller('TodoListController', function($scope, $state, popupService, $window, Todo) {
  $scope.todos = Todo.query(); //fetch all todos. Issues a GET to /api/vi/todos

  $scope.deleteTodo = function(todo) { // Delete a Todo. Issues a DELETE to /api/v1/todos/:id
    if (popupService.showPopup('Really delete this?')) {
      todo.$delete(function() {
        $scope.todos = Todo.query(); 
        $state.go('todos');
      });
    }
  };
}).controller('TodoViewController', function($scope, $stateParams, Todo) {
  $scope.todo = Todo.get({ id: $stateParams.id }); //Get a single todo.Issues a GET to /api/v1/todos/:id
}).controller('TodoCreateController', function($scope, $state, $stateParams, Todo) {
  $scope.todo = new Todo();  //create new todo instance. Properties will be set via ng-model on UI

  $scope.addTodo = function() { //create a new todo. Issues a POST to /api/v1/todos
    $scope.todo.$save(function() {
      $state.go('todos'); // on success go back to the list i.e. todos state.
    });
  };
}).controller('TodoEditController', function($scope, $state, $stateParams, Todo) {
  $scope.updateTodo = function() { //Update the edited todo. Issues a PUT to /api/v1/todos/:id
    $scope.todo.$update(function() {
      $state.go('todos'); // on success go back to the list i.e. todos state.
    });
  };

  $scope.loadTodo = function() { //Issues a GET request to /api/v1/todos/:id to get a todo to update
    $scope.todo = Todo.get({ id: $stateParams.id });
  };

  $scope.loadTodo(); // Load a todo which can be edited on UI
});
