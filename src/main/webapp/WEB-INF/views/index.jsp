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
    <script src="https://google-code-prettify.googlecode.com/svn/loader/run_prettify.js"></script>
</head>
<body>
    <div class="container">
        <h1>Welcome to SockJS test</h1>
        
        <p>
           <table class="table table-bordered table-hover">
             <thead>
                <tr>
                  <th>library</th>
                  <th>version</th>
                </tr>
             </thead>
             <tbody>
                <tr>
                  <td>sock-js</td>
                  <td>${sockjsVersion}</td>
                </tr>
                <tr>
                  <td>jquery-js</td>
                  <td>${jqueryVersion}</td>
                </tr>
                <tr>
                  <td>bootrstap</td>
                  <td>${bootstrapVersion}</td>
                </tr>
             </tbody>
           </table>
        </p>
        <p>
           <blockquote>
             <p>system will automatically make deposite for you upon WS connection, see response below</p>
           </blockquote>
           <pre class="prettyprint lang-json" id="pre-resp"></pre>
        </p>
    </div>
     <script type="text/javascript">
     $(document).ready(function () {
    	 var sock = new SockJS('${urlBase}/${wsPath}');
    	 sock.onopen = function() {
    	       console.log('ws-open');
    	       
    	       var req = {
    	          qualifier: 'com.turbospaces.api.DepositeRequest',
    	    	  correlationId: new Date().getTime().toString(),
    	    	  data: {
    	    		  balance : 23.34
    	    	  }
    	       };
    	       
    	       sock.send(JSON.stringify(req));
    	 };
    	 sock.onmessage = function(e) {
    	       console.log('message', e.data);
    	       $("#pre-resp").text( JSON.stringify(JSON.parse( e.data ), null, 4) );    	           	      
    	 };
    	 sock.onclose = function() {
    	       console.log('ws-close');
    	 };
     });
     </script>
</body>
</html>

