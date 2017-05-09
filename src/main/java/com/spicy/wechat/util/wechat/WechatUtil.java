package com.spicy.wechat.util.wechat;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.spicy.wechat.entity.wechat.AccessToken;
import com.spicy.wechat.entity.wechat.Button;
import com.spicy.wechat.entity.wechat.ClickButton;
import com.spicy.wechat.entity.wechat.Menu;
import com.spicy.wechat.entity.wechat.SendAllMsg;
import com.spicy.wechat.entity.wechat.Template;
import com.spicy.wechat.entity.wechat.TemplateData;
import com.spicy.wechat.entity.wechat.UserInfo;
import com.spicy.wechat.entity.wechat.ViewButton;
import com.spicy.wechat.entity.wechat.WeixinOauth2Token;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class WechatUtil {
	
    //微信公众号配置信息
	private static final String APPID = "wxfbcd993f38d706db";
	private static final String APPSECRET = "04ae1a26f60964d961e0bc42252e5fa2";
	
	//动态网址
	public final static String PROXYADDRESS = "http://spicy.tunnel.qydev.com/";

	//获取ACCESS_TOKEN
	private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	//上传文件
	private static final String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	//下载文件
	private static final String DOWNLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
	//创建菜单
	private static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	//查询菜单
	private static final String QUERY_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	//删除菜单
	private static final String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	//获取用户信息
	private static final String USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

	private static final String MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/trymatch?access_token=ACCESS_TOKEN";
	//获取分组
	private static final String GROUP_URL = "https://api.weixin.qq.com/cgi-bin/groups/get?access_token=ACCESS_TOKEN";
	//创建分组
	private static final String CREATE_GROUP = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token=$access_token";
	//网页授权TOKEN
	private static final String OAUTH2TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	//获取jssdk票据
	private static final String TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	//客服消息
	public static final String CUSTOM_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
	// 群发消息（POST）
    public final static String SEND_MSG_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN";
	
	/**
	 * 发起HTTP请求
	 * @param requestUrl
	 * @param requestMethod
	 * @param outputStr
	 * @return
	 */
   public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
       StringBuffer buffer = new StringBuffer();
       try {
           URL url = new URL(requestUrl);
           HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
           httpUrlConn.setDoOutput(true);
           httpUrlConn.setDoInput(true);
           httpUrlConn.setUseCaches(false);
           httpUrlConn.setRequestMethod(requestMethod);
           if ("GET".equalsIgnoreCase(requestMethod))
               httpUrlConn.connect();
           if (null != outputStr) {
               OutputStream outputStream = httpUrlConn.getOutputStream();
               outputStream.write(outputStr.getBytes("UTF-8"));
               outputStream.close();
           }
           InputStream inputStream = httpUrlConn.getInputStream();
           InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
           BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

           String str = null;
           while ((str = bufferedReader.readLine()) != null) {
               buffer.append(str);
           }
           bufferedReader.close();
           inputStreamReader.close();
           inputStream.close();
           httpUrlConn.disconnect();
       } catch (ConnectException ce) {
           ce.printStackTrace();
       } catch (Exception e) {
           e.printStackTrace();
       }
       return JSONObject.fromObject(buffer.toString());
   }
   
   /**
    * 调用微信JS接口的临时票据jsapi_ticket
    * 
    * @param access_token 接口访问凭证
    * @return
	 * @throws IOException 
	 * @throws ParseException 
    */
   public static String getJsApiTicket(String access_token) throws ParseException, IOException {
       String url = TICKET.replace("ACCESS_TOKEN", access_token);
       // 发起GET请求获取凭证
       JSONObject jsonObject = httpRequest(url,"GET",null);
       String ticket = null;
       if (null != jsonObject) {
           try {
               ticket = jsonObject.getString("ticket");
           } catch (JSONException e) {
               // 获取token失败
           	e.printStackTrace();
           }
       }
       return ticket;
   }
   
   
   /**
	 * jssdk 签名验证
	 * @param jsapi_ticket
	 * @param url
	 * @return
	 */
   public static Map<String, String> sign(String jsapi_ticket, String url) {
       Map<String, String> ret = new HashMap<String, String>();
       String nonce_str = create_nonce_str();
       String timestamp = create_timestamp();
       String string1;
       String signature = "";

       //注意这里参数名必须全部小写，且必须有序
       string1 = "jsapi_ticket=" + jsapi_ticket +
                 "&noncestr=" + nonce_str +
                 "&timestamp=" + timestamp +
                 "&url=" + url;
       System.out.println(string1);

       try
       {
           MessageDigest crypt = MessageDigest.getInstance("SHA-1");
           crypt.reset();
           crypt.update(string1.getBytes("UTF-8"));
           signature = byteToHex(crypt.digest());
       }
       catch (NoSuchAlgorithmException e)
       {
           e.printStackTrace();
       }
       catch (UnsupportedEncodingException e)
       {
           e.printStackTrace();
       }

       ret.put("url", url);
       ret.put("appId",APPID);
       ret.put("jsapi_ticket", jsapi_ticket);
       ret.put("nonceStr", nonce_str);
       ret.put("timestamp", timestamp);
       ret.put("signature", signature);

       return ret;
   }

   private static String byteToHex(final byte[] hash) {
       Formatter formatter = new Formatter();
       for (byte b : hash)
       {
           formatter.format("%02x", b);
       }
       String result = formatter.toString();
       formatter.close();
       return result;
   }

   private static String create_nonce_str() {
       return UUID.randomUUID().toString();
   }

   private static String create_timestamp() {
       return Long.toString(System.currentTimeMillis() / 1000);
   }
   
   
   /**
	 * 获取网页授权token
	 * @param code
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static WeixinOauth2Token getOauth2AccessToken(String code)
			throws ParseException, IOException {
		WeixinOauth2Token wat = null;
		// 拼接请求地址
		String url = OAUTH2TOKEN.replace("APPID", APPID).replace("SECRET", APPSECRET).replace("CODE", code);
		// 获取网页授权凭证
		JSONObject jsonObject = httpRequest(url,"GET",null);
		if (null != jsonObject) {
			try {
				wat = new WeixinOauth2Token();
				//String refresh_url = REFRESH_TOKEN.replace("APPID", APPID).replace("REFRESH_TOKEN", json.getString("refresh_token"));
				//JSONObject jsonObject = doGetStr(refresh_url);
				wat.setAccessToken(jsonObject.getString("access_token"));
				wat.setExpiresIn(jsonObject.getInt("expires_in"));
				wat.setRefreshToken(jsonObject.getString("refresh_token"));
				wat.setOpenId(jsonObject.getString("openid"));
				wat.setScope(jsonObject.getString("scope"));
			} catch (Exception e) {

			}
		}
		return wat;
	}
   
	/**
	 * 通过网页授权获取用户信息
	 * 
	 * @param accessToken
	 *            网页授权接口调用凭证
	 * @param openId
	 *            用户标识
	 * @return SNSUserInfo
	 * @throws IOException
	 * @throws ParseException
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public static UserInfo getUserInfo(String access_token, String openId)
			throws ParseException, IOException {
		UserInfo UserInfo = null;
		// 拼接请求地址
		String url = USER_INFO.replace("ACCESS_TOKEN", access_token).replace(
				"OPENID", openId);
		// 通过网页授权获取用户信息
		JSONObject jsonObject = httpRequest(url,"GET",null);

		if (null != jsonObject) {
			try {
				UserInfo = new UserInfo();
				// 用户的标识
				UserInfo.setOpenId(jsonObject.getString("openid"));
				// 昵称
				UserInfo.setNickname(jsonObject.getString("nickname"));
				// 性别（1是男性，2是女性，0是未知）
				UserInfo.setSex(jsonObject.getInt("sex"));
				// 用户所在国家
				UserInfo.setCountry(jsonObject.getString("country"));
				// 用户所在省份
				UserInfo.setProvince(jsonObject.getString("province"));
				// 用户所在城市
				UserInfo.setCity(jsonObject.getString("city"));
				// 用户头像
				UserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
			} catch (Exception e) {
				UserInfo = null;
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				System.out.println(errorCode+","+errorMsg);
			}
		}
		return UserInfo;
	}
	
	/**
	 * URL编码（utf-8）
	 * 
	 * @param source
	 * @return
	 */
	public static String urlEncodeUTF8(String source) {
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 获取分组
	 * @param token
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static JSONObject getGroup(String token) throws ParseException,
			IOException {
		String url = GROUP_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = httpRequest(url,"GET",null);
		return jsonObject;
	}

	/**
	 * 创建分组
	 * @param token
	 * @param str
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static JSONObject createGroup(String token, String str)
			throws ParseException, IOException {
		String url = CREATE_GROUP.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = httpRequest(url,"POST",str);
		return jsonObject;
	}
	
	/**
	 * 素材上传
	 * 
	 * @param filePath
	 * @param accessToken
	 * @param type
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws KeyManagementException
	 */
	public static String upload(String filePath, String accessToken, String type)
			throws IOException, NoSuchAlgorithmException,
			NoSuchProviderException, KeyManagementException {
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			throw new IOException("文件不存在");
		}

		String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace(
				"TYPE", type);

		URL urlObj = new URL(url);
		// 连接
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

		con.setRequestMethod("POST");
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false);

		// 设置请求头信息
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");

		// 设置边界
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary="
				+ BOUNDARY);

		StringBuilder sb = new StringBuilder();
		sb.append("--");
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"file\";filename=\""
				+ file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");

		byte[] head = sb.toString().getBytes("utf-8");

		// 获得输出流
		OutputStream out = new DataOutputStream(con.getOutputStream());
		// 输出表头
		out.write(head);

		// 文件正文部分
		// 把文件已流文件的方式 推入到url中
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();

		// 结尾部分
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线

		out.write(foot);

		out.flush();
		out.close();

		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		String result = null;
		try {
			// 定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}

		JSONObject jsonObj = JSONObject.fromObject(result);
		System.out.println("jsonObj:"+jsonObj);
		String typeName = "media_id";
		/*if (!"image".equals(type)) {
			typeName = type + "_media_id";
		}*/
		String mediaId = jsonObj.getString(typeName);
		return mediaId;
	}
	
	/**
	 * 下载素材
	 * @param accessToken
	 * @param mediaId
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static String downLoad( String accessToken, String mediaId) throws ParseException, IOException{
		String url = DOWNLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("MEDIA_ID",mediaId);
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		HttpResponse httpResponse = client.execute(httpGet);
		Header[] header = httpResponse.getHeaders("Content-disposition");
		String str = header[0].toString();
		String fileName = str.substring(str.indexOf("=")+1);
		
		HttpEntity entity = httpResponse.getEntity();
		
		InputStream in = entity.getContent(); 
		String ext = fileName.substring(fileName.indexOf("."));
		String filePath = "D:\\downloadImg\\download";
        File file = new File( filePath + ext);
        if(!file.exists()){
        	file.mkdirs();
        } 
        FileOutputStream out = new FileOutputStream(file);
        IOUtils.copy(in, out);
        
        
		System.out.println(fileName);
		return fileName;
	}
	
	/**
	 * 获取accessToken
	 * 
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static AccessToken getAccessToken() throws ParseException,
			IOException {
		AccessToken token = new AccessToken();
		String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace(
				"APPSECRET", APPSECRET);
		JSONObject jsonObject = httpRequest(url,"GET",null);
		if (jsonObject != null) {
			token.setToken(jsonObject.getString("access_token"));
			token.setExpiresIn(jsonObject.getInt("expires_in"));
		}
		return token;
	}

	/**
	 * 设置模板信息
	 * @param access_token
	 * @param openId
	 * @throws ParseException
	 * @throws IOException
	 */
	public static void sendTemplateMessage(String access_token,String openId) throws ParseException, IOException {
		
		String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="
				+ access_token;
		Template temp = new Template();
		temp.setUrl("http://www.baidu.com");
		temp.setTouser(openId);
		temp.setTopcolor("#000000");
		temp.setTemplate_id("d_enT8Vii8_b9dI7tRIonSF05JHWB1dhou-IQKdDGhc");
		Map<String, TemplateData> m = new HashMap<String, TemplateData>();
		TemplateData first1 = new TemplateData();
		first1.setColor("#d93030");
		first1.setValue("这里填写您要发送的模板信息");
		m.put("first", first1);
		TemplateData first = new TemplateData();
		first.setColor("#734cd9");
		first.setValue("另一行内人");
		m.put("first1", first);
		TemplateData wuliu = new TemplateData();
		wuliu.setColor("#d3339c");
		wuliu.setValue("N行");
		m.put("wuliu", wuliu);
		TemplateData orderNo = new TemplateData();
		orderNo.setColor("#33d357");
		orderNo.setValue("**666666");
		m.put("orderNo", orderNo);
		TemplateData receiveAddr = new TemplateData();
		receiveAddr.setColor("#000000");
		receiveAddr.setValue("*测试模板");
		m.put("receiveAddr", receiveAddr);
		TemplateData remark = new TemplateData();
		remark.setColor("#c9d333");
		remark.setValue("***备注说明***");
		m.put("Remark", remark);
		temp.setData(m);
		String jsonString = JSONObject.fromObject(temp).toString();
		JSONObject jsonObject = httpRequest(url,"POST",jsonString);
		int result = 0;
		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				System.out.println("获取模板失败！");
			}else{
				System.out.println("获取模板成功！");
			}
		}
		//System.out.println(result);
	}

	/**
	 * 组装菜单
	 * 
	 * @return
	 */
	public static Menu initMenu() {
		Menu menu = new Menu();
		ClickButton button11 = new ClickButton();
		button11.setName("click菜单");
		button11.setType("click");
		button11.setKey("11");

		ViewButton button21 = new ViewButton();
		button21.setName("网页授权");
		button21.setType("view");
		button21.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxfbcd993f38d706db&redirect_uri=http://spicy.tunnel.qydev.com/wechat/oauth&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");

		ClickButton button31 = new ClickButton();
		button31.setName("扫码推");
		button31.setType("scancode_push");
		button31.setKey("31");

		ClickButton button32 = new ClickButton();
		button32.setName("地理位置");
		button32.setType("location_select");
		button32.setKey("32");

		ViewButton button33 = new ViewButton();
		button33.setName("jssdk");
		button33.setType("view");
		button33.setUrl("http://spicy.tunnel.qydev.com/wechat/sdk");

		ClickButton button34 = new ClickButton();
		button34.setName("模板消息");
		button34.setType("click");
		button34.setKey("34");
		
		ClickButton button35 = new ClickButton();
		button35.setName("扫码");
		button35.setType("scancode_waitmsg");
		button35.setKey("35");
		
		Button button = new Button();
		button.setName("菜单");
		button.setSub_button(new Button[] { button31, button32 ,button33, button34,button35});

		menu.setButton(new Button[] { button11, button21, button });
		return menu;
	}
	
	/**
	 * 创建菜单
	 * @param token
	 * @param menu
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static int createMenu(String token, String menu)
			throws ParseException, IOException {
		int result = 0;
		String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = httpRequest(url, "POST", menu);
		if (jsonObject != null) {
			result = jsonObject.getInt("errcode");
		}
		return result;
	}

	/**
	 * 获取菜单
	 * @param token
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static JSONObject getMenu(String token) throws ParseException,
			IOException {
		String url = MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = httpRequest(url, "GET", null);
		return jsonObject;
	}

	/**
	 * 查询菜单
	 * @param token
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static JSONObject queryMenu(String token) throws ParseException,
			IOException {
		String url = QUERY_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = httpRequest(url, "GET", null);
		return jsonObject;
	}

	/**
	 * 删除菜单
	 * @param token
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static int deleteMenu(String token) throws ParseException,
			IOException {
		String url = DELETE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = httpRequest(url, "GET", null);
		int result = 0;
		if (jsonObject != null) {
			result = jsonObject.getInt("errcode");
		}
		return result;
	}
	
	/**
	 * 判断是否关注公众号
	 * @param token
	 * @param openid
	 * @return
	 */
   public static Integer judgeIsFollow(String token,String openid){
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+token+"&openid="+openid+"&lang=zh_CN";
        JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
        return jsonObject.getInt("subscribe");
    }
   
     // 群发消息
      public static JSONObject sendMsg(String accessToken, SendAllMsg sendAllMsg) {
       // 拼接请求地址
       String requestUrl = SEND_MSG_URL.replace("ACCESS_TOKEN", accessToken);
      return httpRequest(requestUrl, "POST", JSONObject.fromObject(sendAllMsg).toString());
   }
}
