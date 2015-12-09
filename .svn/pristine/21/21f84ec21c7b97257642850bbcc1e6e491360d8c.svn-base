<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>
<script type="text/javascript">
//<![CDATA[
    $(document).ready(function() {
        try{
            var fullWin = window.open('${ctx_admin}/login',
                                      '<%=request.getServerName().replaceAll(".", "") + request.getServerPort()%>',
                                      'toolbar=no,menubar=no,resizable=no,scrollbars=auto,location=no,status=no');
            fullWin.moveTo(0, 0);
            fullWin.resizeTo(screen.availWidth, screen.availHeight);

            with (navigator) {
                if (fullWin == null) {
                    alert("您好，您现在使用的浏览器拦截了该页面，请将本网站加入到您的受信任站点。");
                    return;
                }
                if (appName == "Microsoft Internet Explorer") {
                    if ((parseInt(appVersion.split(" ")[3])) > 6) {
                        window.opener = null;
                        window.open("", "_self");
                        window.close();
                    } else {
                        window.opener = null;
                        window.top.close();
                    }
                } else {
                    window.opener = null;
                    window.close();
                }
            }
        } catch (err) {
            alert("您好，您的浏览器不支持本系统，请使用其它兼容浏览器。");
        }
    });
//]]>
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>