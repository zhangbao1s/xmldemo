<%@ page pageEncoding="UTF-8" %>

<script src="http://cdn.bootcss.com/jquery/2.1.4/jquery.min.js"></script>
<script src="http://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<script src="http://cdn.bootcss.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<script src="http://cdn.bootcss.com/handlebars.js/3.0.3/handlebars.min.js"></script>
<script src="http://cdn.bootcss.com/bootstrap-table/1.8.1/bootstrap-table.min.js"></script>
<script src="http://cdn.bootcss.com/bootstrap-table/1.8.1/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="${CTX}/resources/script/global.js"></script>

<script>
    $(function () {
        $.ajaxSetup({
            statusCode: {
                401: function () {
                    $.removeCookie(Cookie.TOKEN);
                    location.href = '${CTX}/';
                }
            }
        });

        $(document).ajaxSend(function (event, jqXHR, ajaxOptions) {
            jqXHR.setRequestHeader(RequestHeader.USERNAME, $.cookie(Cookie.USERNAME));
            jqXHR.setRequestHeader(RequestHeader.TOKEN, $.cookie(Cookie.TOKEN));
        });

//        $(document).ajaxComplete(function (event, jqXHR, ajaxOptions) {
//            console.log('更新 cookie 中的 token');
//            console.log(jqXHR.getAllResponseHeaders());
//            var token = jqXHR.getResponseHeader('X-Token');
//            console.log('token: ' + token);
//            jqXHR.setRequestHeader(RequestHeader.TOKEN, token);
//        });

        $('#theme').find('ul a').click(function () {
            var theme = $(this).data('key');
            $.cookie(Cookie.THEME, theme, {expires: 365});
            location.reload();
            return false;
        });

        $('#logout').click(function () {
            if (confirm('确定退出系统吗？')) {
                $.removeCookie(Cookie.TOKEN);
                location.href = '${CTX}/';
            }
            return false;
        });
    });
</script>
