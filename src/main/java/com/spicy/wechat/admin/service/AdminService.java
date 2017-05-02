package com.spicy.wechat.admin.service;

import java.util.List;

import com.spicy.wechat.entity.Admin;


public interface AdminService {

	List<Admin> getList() throws Exception;
}
