/**
 * Created by dastko on 9/7/15.
 */
(function () {

    angular.module('bitBlog')
        .factory('dataService', ['$http', '$q', '$cacheFactory', dataService]);

    function dataService($http, $q) {

        return {
            getAllPosts: getAllPosts
        };

        function getAllPosts() {
            return $http({
                method: 'GET',
                url: 'posts'
            })
                .then(sendResponseData)
                .catch(sendGetPostsError)
        }

        function sendResponseData(response) {
            console.log(response.data);
            console.log(response);
            return response.data;
        }

        function sendGetPostsError(response) {
            return $q.reject('Error: ' + response.status);
        }
    }
}());
