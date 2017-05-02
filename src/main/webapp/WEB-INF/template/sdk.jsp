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

	<input type="button" id="sao" value="chooseImg" />
	<input type="button" id="record" value="record" />
	<input type="button" id="stop" value="stop" />
	<input type="button" id="scan" value="scan" />
	<input id="appId" type="hidden" value="${sign.appId }" />
	<input id="url" type="hidden" value="${sign.url}" />
	<input id="tk" type="hidden" value="${sign.jsapi_ticket }" />
	<input id="nonceStr" type="hidden" value="${sign.nonceStr }" />
	<input id="timestamp" type="hidden" value="${sign.timestamp }" />
	<input id="signature" type="hidden" value="${sign.signature }" />

	<script src="http://cdn.bootcss.com/jquery/3.1.1/jquery.min.js"></script>
	<script src="http://res.wx.qq.com/open/js/jweixin-1.1.0.js"></script>
	<script type="text/javascript">
		$(function() {
			var appId = $("#appId").val();
			var nonceStr = $("#nonceStr").val();
			var timestamp = $("#timestamp").val();
			var signature = $("#signature").val();
			wx.config({
				debug : true,
				appId : appId,
				timestamp : timestamp,
				nonceStr : nonceStr,
				signature : signature,
				jsApiList : [ 'checkJsApi', 
							'chooseImage',
							'onMenuShareAppMessage',
							'onMenuShareTimeline',
							'stopRecord',
							'scanQRCode']
			});

			
		
		wx.ready(function() {
		
		 wx.checkJsApi({
            jsApiList: [
                'onMenuShareTimeline',
                'onMenuShareAppMessage'
            ]
        });
		
			//在这里写微信扫一扫的接口
			$("#sao").bind("click", function() {
				wx.chooseImage({
					count : 9, // 默认9
					sizeType : [ 'original', 'compressed' ], // 可以指定是原图还是压缩图，默认二者都有
					sourceType : [ 'album', 'camera' ], // 可以指定来源是相册还是相机，默认二者都有
					success : function(res) {
						var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
					}
				});
			
			});

					wx.onMenuShareAppMessage({
						title : '分享给盆友测试',
						desc : '在长大的过程中，我才慢慢发现，我身边的所有事，别人跟我说的所有事，那些所谓本来如此，注定如此的事，它们其实没有非得如此，事情是可以改变的。更重要的是，有些事既然错了，那就该做出改变。',
						link : 'http://www.baidu.com',
						imgUrl : 'http://www.baidu.com',
						trigger : function(res) {
							alert('用户点击发送给朋友');
						},
						success : function(res) {
							alert('已分享');
						},
						cancel : function(res) {
							alert('已取消');
						},
						fail : function(res) {
							alert(JSON.stringify(res));
						}
				});
				
			
				wx.onMenuShareTimeline({
				title: '分享盆友圈测试',
				link: 'http://wx.vland.cc/mobile.php?act=module&rid=406&fromuser=oktsYuHivHXuzdsMeCbWyF7b14UU&name=hllihe&do=sharelihe&weid=7',
				imgUrl: 'http://wx.vland.cc/resource/attachment/images/7/2015/01/fl2Lk2p5o3iOJP3jdp9iPXI9i93iPm.jpg',
				trigger: function (res) {
				alert('用户点击分享到朋友圈');
				},
				success: function (res) {
				alert('已分享');
				},
				cancel: function (res) {
				alert('已取消');
				},
				fail: function (res) {
				alert('wx.onMenuShareTimeline:fail: '+JSON.stringify(res));
				}
				});
			
			$("#record").bind("click", function() {
				wx.startRecord();
			});
			
			$("#stop").bind("click", function() {
				wx.stopRecord({
				    success: function (res) {
				        var localId = res.localId;
				        alert("success");
				    }
				});
			});
			$("#scan").bind("click", function() {
			wx.scanQRCode({
			    needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
			    scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
			    success: function (res) {
			    var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
			    alert("result");
				}
				});
			});
		});

		wx.error(function(res) {
			alert(res.errMsg);
		});
});
</script>
</body>
</html>