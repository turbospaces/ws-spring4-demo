<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <c:set var="req" value="${pageContext.request}" />
    <c:set var="urlBase" value="${req.scheme}://${req.serverName}:${req.serverPort}${req.contextPath}" />
    <title>WS SockJS</title>

    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="content-type" content="text/html;charset=UTF-8"/>

    <link href="${urlBase}/webjars/bootstrap/${bootstrapVersion}/css/bootstrap.min.css" rel="stylesheet" media="screen" />
    <link href="${urlBase}/webjars/bootstrap/${bootstrapVersion}/css/bootstrap-theme.min.css" rel="stylesheet" media="screen" />

    <script src="${urlBase}/webjars/jquery/${jqueryVersion}/jquery.min.js"></script>
    <script src="${urlBase}/webjars/bootstrap/${bootstrapVersion}/js/bootstrap.min.js"></script>
    <script src="${urlBase}/webjars/sockjs-client/${sockjsVersion}/sockjs.min.js"></script>
</head>
<body>
    <div class="container">
        <h1>Welcome to SockJS test</h1>
    </div>
     <script type="text/javascript">
     $(document).ready(function () {
    	 var sock = new SockJS('${urlBase}/${wsPath}');
    	 sock.onopen = function() {
    	       console.log('ws-open');
    	       
    	       var req = {
    	          qualifier: 'com.turbospaces.api.DepositeRequest',
    	    	  correlationId: '${uuid}',
    	    	  data: {}
    	       };
    	       
    	       sock.send(JSON.stringify(req));
    	 };
    	 sock.onmessage = function(e) {
    	       console.log('message', e.data);
    	 };
    	 sock.onclose = function() {
    	       console.log('ws-close');
    	 };
     });
     </script>
</body>
</html>

