package com.hy.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hy.bean.Order;
import com.hy.bean.User;
import com.hy.mapper.UserMapper;
import com.hy.util.ParseData;
import com.hy.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Random;

@Service
public class Userserves extends ServiceImpl<UserMapper, User> {

    @Autowired
    private UserMapper userMapper;

    /**
     * 登陆页面查询名字(id是权限类型，uid是编号)
     *
     * @param user1
     * @return
     */
    public String selecet(User user1) {
        User user = userMapper.select(user1);
        if (user != null) {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();
            HttpSession session = request.getSession();
            session.setAttribute("userType", user.getType());
            session.setAttribute("userId", user.getId());
            session.setAttribute("userName", user.getUsername());
            return Util.sueess;
        } else {
            return Util.defact;
        }
    }

    ;

    /**
     * 所有业务员信息
     *
     * @return
     */
    public ParseData selectlist(Integer page, Integer limit) {
        Page<User> page1 = new Page<User>(page, limit);
        IPage<User> iPage = userMapper.selectlist(page1);
        return new ParseData(0, "", Integer.parseInt(Long.toString(iPage.getTotal())), iPage.getRecords());
    }

    /**
     * 权限管理获取session
     *
     * @param request
     * @return
     */
    public String getsession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String id = (String) session.getAttribute("userType");
        return id;
    }

    /**
     * 通过id查询当前业务员
     *
     * @param id
     * @return
     */
    public User getbyid(String id) {
        return userMapper.selectbyid(Integer.parseInt(id));
    }

    /**
     * 通過id修改业务员
     *
     * @param user
     * @return
     */
    public String update(User user) {
        User userList = userMapper.listbyid(user.getId());
        if (userList.getUsername().equals(user.getUsername())) {
            userMapper.update(user);
            return Util.sueess;
        } else{
            User user1 = userMapper.list(user.getUsername());
            if(user1==null){
                userMapper.update(user);
                return Util.sueess;
            }
        }
        return  Util.defact;
    }

    /**
     * 查询权限类型
     *
     * @return
     */
    public List<User> selecttype() {
        return userMapper.selcttype();
    }

    /**
     * 插入业务员信息
     *
     * @param user
     * @return
     */
    public String tt(User user) {
        User userList = userMapper.list(user.getUsername());
        if (userList != null) {
            if (userList.getUsername().equals(user.getUsername())) {
                return Util.defact;
            }
        }
        userMapper.ttt(user);
        return Util.sueess;

    }

    /**
     * 删除业务员信息
     *
     * @param id
     * @return
     */
    public String detele(Integer id) {
        try {
            userMapper.deleteById(id);
        } catch (Exception e) {
            return Util.defact;
        }
        return Util.sueess;
    }

    /**
     * 获取session中的UseId
     *
     * @return
     */
    public String getSessionUserId() {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        Integer id = (Integer) session.getAttribute("userId");
        return String.valueOf(id);
    }

    protected void checkCode() {
    }

}
