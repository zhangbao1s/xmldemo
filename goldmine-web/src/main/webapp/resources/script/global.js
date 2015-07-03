$(function () {
    $('#theme').find('ul a').click(function () {
        var theme = $(this).data('key');
        $.cookie('cookie_theme', theme, {expires: 365, path: '/'});
        location.reload();
        return false;
    });
});

var Ajax = {
    get: function (options) {
        $.ajax({
            url: options.url,
            type: 'GET',
            contentType : 'application/json',
            success: options.success,
            error: options.error
        });
    },
    post: function (options) {
        $.ajax({
            url: options.url,
            type: 'POST',
            contentType : 'application/json',
            data: JSON.stringify(options.data),
            beforeSend: options.beforeSend,
            success: options.success,
            error: options.error
        });
    },
    postForm: function (options) {
        $.ajax({
            url: options.url,
            type: 'POST',
            contentType : 'application/x-www-form-urlencoded',
            data: options.data,
            beforeSend: options.beforeSend,
            success: options.success,
            error: options.error
        });
    },
    put: function (options) {
        $.ajax({
            url: options.url,
            type: 'PUT',
            contentType : 'application/json',
            data: JSON.stringify(options.data),
            beforeSend: options.beforeSend,
            success: options.success,
            error: options.error
        });
    },
    delete: function (options) {
        $.ajax({
            url: options.url,
            type: 'DELETE',
            contentType : 'application/json',
            beforeSend: options.beforeSend,
            success: options.success,
            error: options.error
        });
    }
};