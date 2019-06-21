/**
 * @author LeeReindeer
 **/

function conformDelete() {
    return confirm("确认删除吗？");
}

function isLikeBlog(blogId) {
    $.get('/api/blog/liked/' + blogId, function (data, status) {
        return data;
    });
}

function getLikes(blogId) {
    $.get('/api/blog/likes/' + blogId, function (data) {
        return data;
    })
}

function likeBlog(blogId) {
    var elementId = 'likeButton' + blogId;
    $.ajax({
        url: '/api/blog/like/' + blogId,
        type: 'PUT',
        success: function (data, status) {
            if (status === "success") {
                var element = $("#" + elementId).find("span");
                element.text(data);
            } else {
                alert("网络连接失败")
            }
        }
    });
}

function dislikeBlog(blogId) {
    var elementId = 'dislikeButton' + blogId;
    $.ajax({
        url: '/api/blog/dislike/' + blogId,
        type: 'PUT',
        success: function (data, status) {
            if (status === "success") {
                var element = $("#" + elementId).find("span");
                element.text(data);
            } else {
                alert("网络连接失败")
            }
        }
    });
}
