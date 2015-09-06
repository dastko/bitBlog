/**
 * Created by dastko on 9/6/15.
 */
(function() {

    angular.module("bitBlog", [

    ]).controller('CommentCtrl', function ($scope, $http) {

        $scope.postForm = {};

        $scope.comment = function () {

            $http.post('api/comment', $scope.postForm)
                .success(function (data) {
                    console.log(data);
                    $scope.postForm = {};
                }).error(function (data) {
                    console.log(data);
                });
        }
        $scope.getComments = function () {
            $http.get('api/comment')
                .success(function (data) {
                    $scope.comments = data;
                });
        };
        $scope.getComments();
    });

}());
