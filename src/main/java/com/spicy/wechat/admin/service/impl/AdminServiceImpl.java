package com.spicy.wechat.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.spicy.wechat.admin.mapper.AdminMapper;
import com.spicy.wechat.admin.service.AdminService;
import com.spicy.wechat.entity.Admin;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminMapper adminMapper;

	@SuppressWarnings("unchecked")
	@Override
	public List<Admin> getList() throws Exception {
		return adminMapper.getList();
	}

}
