(function () {
    angular.module('bitBlog')
        .controller("SearchController", ['$scope', '$http', SearchController]);

    function SearchController($scope, $http) {

        $scope.items = [];

        if($scope.search === undefined){
            $scope.search = "";
        }
        var pendingTask;

        $scope.change = function(){
            if(pendingTask) {
                clearTimeout(pendingTask);
            }
            pendingTask = setTimeout(fetch, 500);
        };

        function fetch() {
            $http.get("http://localhost:9100/search/" + $scope.search)
                .success(function(response){
                    console.log(response.length)
                    $scope.items = response;
                });
        }

        $scope.$watch('search', function (newVal){
            if(angular.isDefined(newVal)){
                console.log(newVal);
                if(newVal.length === 0){
                    $scope.items = [];
                } else {
                    $scope.change();
                }
            }
        });
    }
}());
