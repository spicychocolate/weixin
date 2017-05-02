package com.spicy.wechat.common;

import java.io.Serializable;

public final class PagePath implements Serializable {
    private static final long serialVersionUID = 8957070956245738627L;
    /** 管理员登录界面 */
    public static final String ADMIN_LOGIN = "admin/login";
    /** 首页 **/
    public static final String ADMIN_HOME_PAGE = "admin/homePage";
    /** 主页 */
    public static final String ADMIN_WELCOME = "admin/welcome";
    /** 添加管理员用户 */
    public static final String ADMIN_ADD = "admin/add";
    /** 管理员用户列表 */
    public static final String ADMIN_LIST = "admin/list";
    /** 编辑用户 */
    public static final String USER_EDIT = "admin/edit";

    
    /**admin*/
    public static final String ADMIN_INDEX = "admin/layout/index";
    public static final String ADMIN_HOME = "admin/home";
    
    /**setting*/
    public static final String SETTING_LIST = "homesetting/homepage/list";
    public static final String SETTING_ADDIMG = "homesetting/homepage/addImg";
    public static final String SETTING_EDITIMG = "homesetting/homepage/editImg";
    public static final String SETTING_EDITGOODS = "homesetting/homepage/editGoods";
    /**goodstype*/
    public static final String GOODSTYPE_LIST = "homesetting/goodstype/list";
    public static final String GOODSTYPE_ADD = "homesetting/goodstype/add";
    public static final String GOODSTYPE_EDIT = "homesetting/goodstype/edit";
    
    /**test*/
    public static final String EDITOR_INDEX = "test/editor";
    
    /**商品*/
    public static final String GOODS_LIST = "goods/list";
    public static final String GOODS_EDIT = "goods/edit";
    
    /**会员*/
    public static final String MEMBER_LIST = "member/list";
    
    /**订单*/
    public static final String ORDER_LIST = "order/list";
    
    /**报表*/
    public static final String SALE_REPORT = "sale/report";
    
    /**仓库*/
    public static final String INVENTORY_LIST = "inventory/list";
    public static final String OUTBOUND_LIST = "outbound/list";
    public static final String RETURNORDER_LIST = "returnorder/list";
    
}
