(function () {
    angular.module('bitBlog', ['angularUtils.directives.dirPagination'])
        .controller("MyController", ['$scope', 'dataService', MyController]);

    function MyController($scope, dataService) {
        $scope.currentPage = 1;
        $scope.pageSize = 10;
        $scope.posts = [];

        var vm = this;

        dataService.getAllPosts()
            .then(getOnlineSuccess)
            .catch(errorCallback);

        function getOnlineSuccess(posts) {
            console.log("Data Friends" + posts.length);
            vm.allPost = posts;
            $scope.allPosts = posts;
        }

        function errorCallback(msg) {
            console.log(msg);
        }

        $scope.posts.push($scope.allPosts);


        $scope.pageChangeHandler = function (num) {
            console.log('Blog page changed to ' + num);
        };
    }
}());
