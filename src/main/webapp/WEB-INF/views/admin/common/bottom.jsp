<script type="text/javascript">
//<![CDATA[
    $(function () {
        $("input[readOnly],textarea[readOnly]").keydown(function (e) {
            var ev = e || window.event;//获取event对象
            if(ev.keyCode == 8) {
                return false;
            }
        });
    });
//]]>
</script>
</body>
</html>