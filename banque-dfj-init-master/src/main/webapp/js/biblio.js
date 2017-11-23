var banqueApp = angular.module('banqueApp', []);

banqueApp.controller ('compteController', [ '$http', '$scope', function($http, $scope) {
	$scope.comptes = [];
	$scope.compte = null;
	$scope.loadAll = function() {
		$http.get('http://localhost:8080/banque/rest/comptes/all').then(function(value) {
			$scope.comptes=value.data;
			console.log(value.data);
		}, function(reason) {
			//en cas de réponse en erreur reason contient l'erreur ....
			console.log(reason);
			
		}, function(value) {
			//optionnel ... ne sert à rien ici ... !
		});
	};
	$scope.load = function(id) {
		$http.get('http://localhost:8080/banque/rest/comptes/'+id).then(function(value) {
			$scope.compte=value.data;
			console.log(value.data);
		}, function(reason) {
			//en cas de réponse en erreur reason contient l'erreur ....
			console.log(reason);
			
		}, function(value) {
			//optionnel ... ne sert à rien ici ... !
		});
	};
	$scope.reset = function() {
		$scope.comptes = [];
	};
	$scope.back = function() {
		$scope.compte = null;
	};
} ]);
