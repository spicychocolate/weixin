<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width,user-scalable=0">
<title>Insert title here</title>
</head>
<body>
	
	
<script src="http://cdn.bootcss.com/jquery/3.1.1/jquery.min.js"></script>
<script>
/* $.post('http://127.0.0.1:8888/role/get_list',function(data){
		console.log(data);
	})
 */
	$.ajax({
            url: "http://127.0.0.1:8888/role/get_list",
            type: "get",
            dataType: "jsonp",  // not "json" we'll parse
            jsonp: "callback",
            success: function(result) {
              console.log(result);
            }
        });
         
    var  wsServer = 'ws://127.0.0.1/hello'; 
    var  websocket = new WebSocket(wsServer); 
    websocket.onopen = function (evt) { onOpen(evt) }; 
    websocket.onclose = function (evt) { onClose(evt) }; 
    websocket.onmessage = function (evt) { onMessage(evt) }; 
    websocket.onerror = function (evt) { onError(evt) }; 
    function onOpen(evt) { 
       console.log("Connected to WebSocket server."); 
    } 
    function onClose(evt) { 
       console.log("Disconnected"); 
    } 
    function onMessage(evt) { 
       console.log('Retrieved data from server: ' + evt.data); 
    } 
    function onError(evt) { 
       console.log('Error occured: ' + evt.data); 
    }
        
</script>
</body>
</html>