<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
    String contextPath = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + contextPath + "/";
    String username = (String) request.getSession().getAttribute("username");
%>
<html>
<head>
    <base href="<%=basePath %>">
    <meta content="text/html;charset=UTF-8"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>Chat With STOMP Message</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.4/sockjs.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
    <script src="<%=basePath%>layui/layui.js"></script>
    <script src="<%=basePath%>layui/lay/modules/layer.js"></script>
    <link href="<%=basePath%>layui/css/layui.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>layui/css/modules/layer/default/layer.css" rel="stylesheet" type="text/css" />
    <%--<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />--%>
    <style type="text/css">
        #connect-container {
            margin: 0 auto;
            width: 400px;
        }

        #connect-container div {
            padding: 5px;
            margin: 0 7px 10px 0;
        }

        .message input {
            padding: 5px;
            margin: 0 7px 10px 0;
        }

        .layui-btn {
            display: inline-block;
        }
    </style>
    <script type="text/javascript">
        var stompClient = null;

        $(function () {
            var target = $("#target");
            if (window.location.protocol === 'http:') {
                target.val('http://' + window.location.host + target.val());
            } else {
                target.val('https://' + window.location.host + target.val());
            }
        });

        function setConnected(connected) {
            var connect = $("#connect");
            var disconnect = $("#disconnect");
            var echo = $("#echo");

            if (connected) {
                connect.addClass("layui-btn-disabled");
                disconnect.removeClass("layui-btn-disabled");
                echo.removeClass("layui-btn-disabled");
            } else {
                connect.removeClass("layui-btn-disabled");
                disconnect.addClass("layui-btn-disabled");
                echo.addClass("layui-btn-disabled");
            }

            connect.attr("disabled", connected);
            disconnect.attr("disabled", !connected);
            echo.attr("disabled", !connected);
        }

        //连接
        function connect() {
            var target = $("#target").val();

            var ws = new SockJS(target);
            stompClient = Stomp.over(ws);

            stompClient.connect({}, function () {
                setConnected(true);
                log('Info: STOMP connection opened.');

                //连接成功后，主动拉取未读消息
                pullUnreadMessage("/topic/reply");

                //订阅服务端的/topic/reply地址
                stompClient.subscribe("/user/topic/reply", function (response) {
                    log(JSON.parse(response.body).content);
                })
            },function () {
                //断开处理
                setConnected(false);
                log('Info: STOMP connection closed.');
            });
        }

        //断开连接
        function disconnect() {
            if (stompClient != null) {
                stompClient.disconnect();
                stompClient = null;
            }
            setConnected(false);
            log('Info: STOMP connection closed.');
        }

        //向指定用户发送消息
        function sendMessage() {
            if (stompClient == null) {
                layer.msg('STOMP connection not established, please connect.', {
                    offset: 'auto'
                    ,icon: 2
                });
                return;
            }
            var receiver = $("#receiver").val();
            var content = $("#content").val();
            var sender = $("#sender").val();
            let chat = JSON.stringify({'receiver': receiver, 'content': content, 'sender': sender});
            log('Sent: ' + chat);
            stompClient.send("/app/person", {}, chat);
            // $.ajax({
            //     url: "/api/person",
            //     type: "POST",
            //     dataType: "json",
            //     contentType:'application/json',
            //     // 异步请求
            //     async: true,
            //     data: {
            //         "receiver": receiver,
            //         "sender": sender,
            //         "content": content
            //     },
            //     success: function (data) {
            //
            //     }
            // });
        }

        //从服务器拉取未读消息
        function pullUnreadMessage(destination) {
            $.ajax({
                url: "/api/message",
                type: "POST",
                dataType: "json",
                contentType:'application/json',
                async: true,
                data: {
                    "destination": destination
                },
                success: function (data) {
                    if (data.result != null) {
                        $.each(data.result, function (i, item) {
                            log(JSON.parse(item).content);
                        })
                    } else if (data.code !=null && data.code == "500") {
                        layer.msg(data.msg, {
                            offset: 'auto'
                            ,icon: 2
                        });
                    }
                }
            });
        }

        //日志输出
        function log(message) {
            console.debug(message);
        }
    </script>
</head>
<body>
<noscript><h2 style="color: #ff0000">Seems your browser doesn't support Javascript! Websockets rely on Javascript being
    enabled. Please enable
    Javascript and reload this page!</h2></noscript>
<div>
    <div id="connect-container" class="layui-elem-field">
        <legend>Chat With STOMP Message</legend>
        <div>
            <input id="target" type="text" class="layui-input" size="40" style="width: 350px" value="/api/chat-websocket"/>
        </div>
        <div>
            <button id="connect" class="layui-btn layui-btn-normal" onclick="connect();">Connect</button>
            <button id="disconnect" class="layui-btn layui-btn-normal layui-btn-disabled" disabled="disabled"
                    onclick="disconnect();">Disconnect
            </button>

        </div>
        <div class="message">
            <input id="sender" type="text" value="<%=username%>" hidden>
            <input id="receiver" type="text" class="layui-input" size="40" style="width: 350px" placeholder="接收者姓名" value=""/>
            <input id="content" type="text" class="layui-input" size="40" style="width: 350px" placeholder="消息内容" value=""/>
        </div>
        <div>
            <button id="echo" class="layui-btn layui-btn-normal layui-btn-disabled" disabled="disabled"
                    onclick="sendMessage();">Send Message
            </button>
        </div>
    </div>
</div>
</body>
</html>