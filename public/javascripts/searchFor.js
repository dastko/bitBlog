(function () {

    angular.module('bitBlog').filter('searchFor', function () {
        return function (arr, searchString) {
            if (!searchString) {
                return arr;
            }
            var result = [];
            angular.forEach(arr, function (item) {
                console.log("item: " + item);
                if (item.indexOf(searchString) !== -1) {
                    result.push(item);
                }
            });
            return result;
        };
    });
}());
