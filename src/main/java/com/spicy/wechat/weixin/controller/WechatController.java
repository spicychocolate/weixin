package com.spicy.wechat.weixin.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.ParseException;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spicy.wechat.entity.wechat.UserInfo;
import com.spicy.wechat.entity.wechat.WeixinOauth2Token;
import com.spicy.wechat.util.wechat.CheckUtil;
import com.spicy.wechat.util.wechat.MessageUtil;
import com.spicy.wechat.util.wechat.WechatUtil;

import net.sf.json.JSONObject;



@Controller
@RequestMapping(value="/wechat")
public class WechatController {
	
	/**
	 * 校验
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException{
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		System.out.println("111112");
		System.out.println(nonce);
		System.out.println(echostr);
		PrintWriter out = response.getWriter();
		if(CheckUtil.checkSignature(signature, timestamp, nonce)){
			out.print(echostr);
			System.out.println("验证成功");
		}
		out.close();
		out = null;
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.POST)
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        PrintWriter out = response.getWriter();
        try {
        	Map<String, Object> map = MessageUtil.parseXml(request);
			String fromUserName =  (String) map.get("FromUserName");
			String toUserName = (String) map.get("ToUserName");
			String msgType = (String) map.get("MsgType");
			String content =  (String) map.get("Content");
			
			String message = null;
			if(MessageUtil.MESSAGE_TEXT.equals(msgType)){
				if("1".equals(content)){
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.firstMenu());
				}else if("2".equals(content)){
					message = MessageUtil.initNewsMessage(toUserName, fromUserName);
				}else if("3".equals(content)){
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.threeMenu());
				}else if("?".equals(content) || "？".equals(content)){
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				}else if(content.startsWith("翻译")){
					String word = content.replaceAll("^翻译", "").trim();
					if("".equals(word)){
						message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.threeMenu());
					}else{
						//message = MessageUtil.initText(toUserName, fromUserName, WeixinUtil.translate(word));
					}
				}
			}else if(MessageUtil.MESSAGE_EVNET.equals(msgType)){
				String eventType = (String) map.get("Event");
				if(MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)){
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				}else if(MessageUtil.MESSAGE_CLICK.equals(eventType)){
					if("11".equals(map.get("EventKey"))){
						message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
					}else{
						String token = WechatUtil.getAccessToken().getToken();
						WechatUtil.sendTemplateMessage(token,fromUserName);
					}
				}else if(MessageUtil.MESSAGE_VIEW.equals(eventType)){
					String url = (String) map.get("EventKey");
					message = MessageUtil.initText(toUserName, fromUserName, url);
				}else if(MessageUtil.MESSAGE_SCANCODE.equals(eventType)){
					message = MessageUtil.initNewsMessage(toUserName, fromUserName);
				}else if("scancode_waitmsg".equals(eventType)){
					String accessToken = WechatUtil.getAccessToken().getToken();
					String content1 = "hello world!";
					try {
						MessageUtil.sendCustomMessage(accessToken, fromUserName, content1);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}else if(MessageUtil.MESSAGE_LOCATION.equals(msgType)){
				String label = (String) map.get("Label");
				message = MessageUtil.initText(toUserName, fromUserName, label);
			}
			
			System.out.println(message);
			if(message.length() > 1){
				out.print(message);
			}
		} catch (DocumentException e) {
 			e.printStackTrace();
		}finally{
			out.close();
			out=null;
		}
	}
	
	@RequestMapping(value="/sdk",method = RequestMethod.GET)
	public String sdk(HttpServletRequest request,ModelMap map) throws ParseException, IOException{
		String access_token = WechatUtil.getAccessToken().getToken();
		String jsapi_ticket = WechatUtil.getJsApiTicket(access_token);

        // 注意 URL 一定要动态获取，不能 hardcode
        String url = request.getRequestURL().toString();
        Map<String, String> ret = WechatUtil.sign(jsapi_ticket, url);
        map.put("sign",ret);
        for (Map.Entry entry : ret.entrySet()) {
        	System.out.println(entry.getKey()+","+entry.getValue());
        }
		return "sdk";
	}
    
	@RequestMapping(value="/oauth",method=RequestMethod.GET)
	public String oauth(HttpServletRequest request,ModelMap map) throws ParseException, IOException{
		// 用户同意授权后，能获取到code
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        
        // 用户同意授权
        if (!"authdeny".equals(code)) {
            // 获取网页授权access_token
            WeixinOauth2Token weixinOauth2Token = WechatUtil.getOauth2AccessToken(code);
            // 网页授权接口访问凭证
            //String accessToken = weixinOauth2Token.getAccessToken();
            String accessToken = WechatUtil.getAccessToken().getToken();
            System.out.println(accessToken);
            
            // 用户标识
            String openId = weixinOauth2Token.getOpenId();
            // 获取用户信息
            UserInfo UserInfo = WechatUtil.getUserInfo(accessToken, openId);

            // 设置要传递的参数
            map.put("user", UserInfo);
            map.put("state", state);
            return "oauth";
        }
        return null;
        
	}
	
	@RequestMapping(value="/createMenu",method=RequestMethod.GET)
	public void createMenu() throws ParseException, IOException{
		String menu = JSONObject.fromObject(WechatUtil.initMenu()).toString();
		String token = WechatUtil.getAccessToken().getToken();
		int result = WechatUtil.createMenu(token, menu);
		if(result == 0) {
			System.out.println("创建菜单成功");
		}else{
			System.out.println("创建失败");
		}
	}
        
	
}
