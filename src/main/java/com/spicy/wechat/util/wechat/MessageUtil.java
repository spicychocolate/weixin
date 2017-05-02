package com.spicy.wechat.util.wechat;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.spicy.wechat.entity.wechat.Image;
import com.spicy.wechat.entity.wechat.ImageMessage;
import com.spicy.wechat.entity.wechat.Music;
import com.spicy.wechat.entity.wechat.MusicMessage;
import com.spicy.wechat.entity.wechat.News;
import com.spicy.wechat.entity.wechat.NewsMessage;
import com.spicy.wechat.entity.wechat.TextMessage;
import com.thoughtworks.xstream.XStream;
/**
 * 消息封装类
 * @author Stephen
 *
 */
public class MessageUtil {	
	
	public static final String MESSAGE_TEXT = "text";
	public static final String MESSAGE_NEWS = "news";
	public static final String MESSAGE_IMAGE = "image";
	public static final String MESSAGE_VOICE = "voice";
	public static final String MESSAGE_MUSIC = "music";
	public static final String MESSAGE_VIDEO = "video";
	public static final String MESSAGE_LINK = "link";
	public static final String MESSAGE_LOCATION = "location";
	public static final String MESSAGE_EVNET = "event";
	public static final String MESSAGE_SUBSCRIBE = "subscribe";
	public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
	public static final String MESSAGE_CLICK = "CLICK";
	public static final String MESSAGE_VIEW = "VIEW";
	public static final String MESSAGE_SCANCODE= "scancode_push";
	public static final String MESSAGE_SCANCODE_WAITMSG = "scancode_waitmsg";
	
	/**
	 * 消息工具类,解析XML
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> parseXml(HttpServletRequest request) throws Exception {
        // 将解析结果存储在HashMap中
        Map<String, Object> map = new HashMap<String, Object>();
        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();

        // 遍历所有子节点
        for (Element e : elementList) {
            if (e.elements().size() > 0) {
                map.put(e.getName(), e.elements());
            } else {
                map.put(e.getName(), e.getText());
            }
        }

        // 释放资源
        inputStream.close();
        inputStream = null;

        return map;
    }
	
	
	/**
	 * 发送客服消息
	 * @param accessToken
	 * @param toUser
	 * @param content
	 * @throws ParseException
	 * @throws IOException
	 */
	public static void sendCustomMessage(String accessToken,String toUser,String content) throws Exception, IOException{
        String strJson = "{\"touser\" :\""+toUser+"\",";
        strJson += "\"msgtype\":\"text\",";
        strJson += "\"text\":{";
        strJson += "\"content\":\""+content+"\"";
        strJson += "}}";
	String url = WechatUtil.CUSTOM_MESSAGE_URL.replace("ACCESS_TOKEN", accessToken);
	JSONObject jsonObject = WechatUtil.httpRequest(url, "POST", strJson);
	if(null != jsonObject){
		int errorCode = jsonObject.getInt("errcode");
		if(0 == errorCode){
			System.out.println("客服消息發送成功！");
		}else{
			System.out.println("客服消息發送失敗！");
		}
	}
}
	
	/**
	 * 将文本消息对象转为xml
	 * @param textMessage
	 * @return
	 */
	public static String textMessageToXml(TextMessage textMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}
	
	/**
	 * 组装文本消息
	 * @param toUserName
	 * @param fromUserName
	 * @param content
	 * @return
	 */
	public static String initText(String toUserName,String fromUserName,String content){
		TextMessage text = new TextMessage();
		text.setFromUserName(toUserName);
		text.setToUserName(fromUserName);
		text.setMsgType(MessageUtil.MESSAGE_TEXT);
		text.setCreateTime(new Date().getTime());
		text.setContent(content);
		return textMessageToXml(text);
	}
	
	/**
	 * 主菜单
	 * @return
	 */
	public static String menuText(){
		StringBuffer sb = new StringBuffer();
		sb.append("欢迎您的关注，请按照菜单提示进行操作：PPAP\n\n");
		sb.append("1、I have a pen\n");
		sb.append("2、I have an apple\n");
		sb.append("3、ah\n");
		sb.append("4、apple-pen\n");
		sb.append("回复？调出此菜单。");
		return sb.toString();
	}
	
	public static String firstMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("I have a pen,I have an apple,ah,apple-pen");
		return sb.toString();
	}
	
	public static void turnToPage(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException{
		
		req.getRequestDispatcher("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxfbcd993f38d706db&redirect_uri=http://spicy.tunnel.qydev.com/weixin/oauthServlet&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect").forward(req, res);
		
	}
	
	public static String secondMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("I have a pen,I have a pineapple,ah,Pineapples pen");
		return sb.toString();
	}
	
	public static String threeMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("Apple pen,Pineapple pen,ah,Pen pineapple Apple Penpineapple pen,Pen pineapple Apple Penpineapple pen");
		
		return sb.toString();
	}
	
	/**
	 * 图文消息转为xml
	 * @param newsMessage
	 * @return
	 */
	public static String newsMessageToXml(NewsMessage newsMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new News().getClass());
		return xstream.toXML(newsMessage);
	}
	
	/**
	 * 图片消息转为xml
	 * @param imageMessage
	 * @return
	 */
	public static String imageMessageToXml(ImageMessage imageMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);
	}
	
	/**
	 * 音乐消息转为xml
	 * @param musicMessage
	 * @return
	 */
	public static String musicMessageToXml(MusicMessage musicMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}
	/**
	 * 图文消息的组装
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initNewsMessage(String toUserName,String fromUserName){
		String message = null;
		List<News> newsList = new ArrayList<News>();
		NewsMessage newsMessage = new NewsMessage();
		
		News news = new News();
		news.setTitle("PPAP");
		news.setDescription("I have a pen,I have a pineapple,ah,Pineapples pen");
		news.setPicUrl("http://kyrh4lbaxf.proxy.qqbrowser.cc/weixin/image/imooc.jpg");
		news.setUrl("www.baidu.com");
		
		newsList.add(news);
		
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MESSAGE_NEWS);
		newsMessage.setArticles(newsList);
		newsMessage.setArticleCount(newsList.size());
		
		message = newsMessageToXml(newsMessage);
		return message;
	}
	
	/**
	 * 组装图片消息
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initImageMessage(String toUserName,String fromUserName){
		String message = null;
		Image image = new Image();
		image.setMediaId("JTH8vBl0zDRlrrn2bBnMleySuHjVbMhyAo0U2x7kQyd1ciydhhsVPONbnRrKGp8m");
		ImageMessage imageMessage = new ImageMessage();
		imageMessage.setFromUserName(toUserName);
		imageMessage.setToUserName(fromUserName);
		imageMessage.setMsgType(MESSAGE_IMAGE);
		imageMessage.setCreateTime(new Date().getTime());
		imageMessage.setImage(image);
		message = imageMessageToXml(imageMessage);
		return message;
	}
	
	/**
	 * 组装音乐消息
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initMusicMessage(String toUserName,String fromUserName){
		String message = null;
		Music music = new Music();
		music.setThumbMediaId("WsHCQr1ftJQwmGUGhCP8gZ13a77XVg5Ah_uHPHVEAQuRE5FEjn-DsZJzFZqZFeFk");
		music.setTitle("see you again");
		music.setDescription("速7片尾曲");
		music.setMusicUrl("http://kyrh4lbaxf.proxy.qqbrowser.cc/weixin/resource/See You Again.mp3");
		music.setHQMusicUrl("http://kyrh4lbaxf.proxy.qqbrowser.cc/weixin/resource/See You Again.mp3");
		
		MusicMessage musicMessage = new MusicMessage();
		musicMessage.setFromUserName(toUserName);
		musicMessage.setToUserName(fromUserName);
		musicMessage.setMsgType(MESSAGE_MUSIC);
		musicMessage.setCreateTime(new Date().getTime());
		musicMessage.setMusic(music);
		message = musicMessageToXml(musicMessage);
		return message;
	}
}
