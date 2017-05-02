package com.spicy.wechat.admin.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.spicy.wechat.admin.service.AdminService;

@Controller
public class AdminController {

	@Value("${site.imgs.path}")
	private String rootPath;
	
	@Autowired 
	private AdminService adminService;
	
	@RequestMapping(value="/wechat")
	public String getList(ModelMap map){
		try {
			map.put("list",adminService.getList());
			map.put("path", rootPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "index";
	}
	
	@RequestMapping(value="/test")
	@ResponseBody
	public String getList(HttpServletRequest request,HttpServletResponse response){
		String callback = request.getParameter("callback");
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "joe");
		map.put("age", "12");
		List<String> list = new LinkedList<String>();
		list.add("ddd22");
		list.add("ddd");
//		try {
//			System.out.println(map.toString());
//			PrintWriter out = response.getWriter();
//			out.write(callback+ "("+JSON.toJSONString(list)+")");
//			out.flush();  
//			out.close();
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return callback+ "("+JSON.toJSONString(list)+")";
		
	}
}
