var helloApp = angular.module('helloApp', []);


helloApp.controller('helloController', ['$http', '$scope', function($http, $scope) {
	$scope.greeting  = ' ?';
	$scope.personnes = null;
	$scope.personne  = null;
     	$scope.hello = function () {
     		$scope.greeting = $scope.name + ' !';
     	};
    	$scope.reset = function () {
      		$scope.name = null;
      		$scope.id = null;
      		$scope.personne  = null;
      		$scope.personnes = null;
         	$scope.greeting = ' ?';
       };
       
       $scope.getPersonne = function () {
    	   personnes = [
    	  	         	   {"id":1,"nom":"Durand","prenom":"Pascal","email":"pascal.durand@free.fr"},
    	  	         	   {"id":2,"nom":"Martin","prenom":"Jean","email":"jean.martind@gmail.com"},
    	  	         	   {"id":3,"nom":"Dupont","prenom":"Jérome","email":"jerome.dupont@yahoo.com"},
    	  	         	   {"id":4,"nom":"Ternois","prenom":"Sophie","email":"sophie.ternois@orange.fr"}];
    	   
    	   //console.log($scope.persons);
    	   //console.log($scope.id);
    	   for(var i=0; i < personnes.length; i++) {
    		   if(personnes[i].id == $scope.id)
    			   $scope.greeting=personnes[i].nom + ' ' + personnes[i].prenom;
    	   }
    	   //console.log($scope.person);
    	   
    	};
    	$scope.getPersonneFromServer = function() {
    		$http.get('http://localhost:8080/biblio/adherent/'+ $scope.id +'.spring').then(function(value) {
    			personne=value.data;
    			$scope.greeting=personne.nom + ' ' + personne.prenom;
    			console.log(value.data);
    		}, function(reason) {
    			//en cas de réponse en erreur reason contient l'erreur ....
    			console.log(reason);
    			
    		}, function(value) {
    			//optionnel ... ne sert à rien ici ... !
    		});
    	};
    	$scope.getPersonnesFromServer = function() {
    		$http.get('http://localhost:8080/biblio/adherent.spring').then(function(value) {
    			$scope.personnes=value.data;
    			console.log(value.data);
    		}, function(reason) {
    			//en cas de réponse en erreur reason contient l'erreur ....
    			console.log(reason);
    			
    		}, function(value) {
    			//optionnel ... ne sert à rien ici ... !
    		});
    	};
    	
    	$scope.edit = function(id) {
    		$http.get('http://localhost:8080/biblio/adherent/'+ id +'.spring').then(function(value) {
    			$scope.personne=value.data;
    			$scope.greeting=$scope.personne.nom + ' ' + $scope.personne.prenom;
    			$scope.id=$scope.personne.id;
    			console.log(value.data);
    		}, function(reason) {
    			//en cas de réponse en erreur reason contient l'erreur ....
    			console.log(reason);
    			
    		}, function(value) {
    			//optionnel ... ne sert à rien ici ... !
    		});
    	};
    	
    	$scope.update = function() {
    		$http.put('http://localhost:8080/biblio/adherent/'+ $scope.personne.id +'.spring', $scope.personne).then(function(value) {
    			$scope.getPersonnesFromServer();
    		}, function(reason) {
    			
    		}, function(value) {
    			
    		})
    	};
    	
       
  }]);
