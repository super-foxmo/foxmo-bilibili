package com.foxmo.bilibili.controller;

import com.foxmo.bilibili.controller.support.UserSupport;
import com.foxmo.bilibili.domain.FollowingGroup;
import com.foxmo.bilibili.domain.JsonResponse;
import com.foxmo.bilibili.domain.User;
import com.foxmo.bilibili.domain.UserFollowing;
import com.foxmo.bilibili.domain.exception.ConditionException;
import com.foxmo.bilibili.service.impl.UserFollowingServiceImpl;
import com.foxmo.bilibili.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.bind.util.JAXBSource;
import java.util.List;

@Controller
public class UserFollowingController {
    @Autowired
    UserFollowingServiceImpl userFollowingService;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    UserSupport userSupport;

    /**
     * 新增关注用户
     */
    @ResponseBody
    @PostMapping("/user-followings")
    public JsonResponse<String> addUserFollowing(@RequestBody UserFollowing userFollowing){
        Long userId = userSupport.getCurrentUserId();
        User user = userService.queryUserById(userId);
        if(user == null){
            throw new ConditionException("该用户不存在！");
        }
        userFollowing.setUserId(userId);
        userFollowingService.addUserFollowing(userFollowing);

        return JsonResponse.success();
    }

    /**
     * 查询用户关注分组列表
     */
    @ResponseBody
    @GetMapping("/user-followings")
    public JsonResponse<List<FollowingGroup>> getUserFollowingsByUserId(){
        Long userId = userSupport.getCurrentUserId();

        User user = userService.queryUserById(userId);
        if(user == null){
            throw new ConditionException("该用户不存在！");
        }
        List<FollowingGroup> followingGroupList = userFollowingService.getUserFollowingsByUserId(userId);

        return new JsonResponse<>(followingGroupList);
    }

    /**
     * 获取用户粉丝列表
     */
    @ResponseBody
    @GetMapping("/user-fens")
    public JsonResponse<List<UserFollowing>> getUsaerFensByUserId(){
        Long userId = userSupport.getCurrentUserId();
        User user = userService.queryUserById(userId);
        if(user == null){
            throw new ConditionException("该用户不存在！");
        }
        List<UserFollowing> userFensList = userFollowingService.getUsaerFensByUserId(userId);

        return new JsonResponse<>(userFensList);
    }

    /**
     * 新增用户关注分组
     */
    @ResponseBody
    @PostMapping("/user-following-groups")
    public JsonResponse<Long> addUserFollowingGroup(FollowingGroup followingGroup){
        Long userId = userSupport.getCurrentUserId();
        User user = userService.queryUserById(userId);
        if(user == null){
            throw new ConditionException("该用户不存在！");
        }
        followingGroup.setUserId(userId);
        Long groupId = userFollowingService.addFollowingGroup(followingGroup);

        return new JsonResponse<>(groupId);
    }

    /**
     * 获取用户所有自定义关注分组
     * @return
     */
    @ResponseBody
    @GetMapping("/user-following-groups")
    public JsonResponse<List<FollowingGroup>> getFollowingGroupsByUserId(){
        Long userId = userSupport.getCurrentUserId();
        User user = userService.queryUserById(userId);
        if(user == null){
            throw new ConditionException("该用户不存在！");
        }

        List<FollowingGroup> groupList = userFollowingService.getFollowingGroupsByUserId(userId);

        return new JsonResponse<>(groupList);
    }
}
