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
        $('#logout').click(function () {
            if (confirm('确定退出系统吗？')) {
                location.href = '${CTX}/';
            }
            return false;
        });
    });
</script>
