package com.atguigu.gmall.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.UserAddress;
import com.atguigu.gmall.service.OrderService;
import com.atguigu.gmall.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 1、将服务提供者注册到注册中心（暴露服务）
 * 		1）、导入dubbo依赖（2.6.2）\操作zookeeper的客户端(curator)
 * 		2）、配置服务提供者
 * 
 * 2、让服务消费者去注册中心订阅服务提供者的服务地址
 * @author lfy
 *
 */
@Service
public class OrderServiceImpl implements OrderService {

	//@Autowired //这是用在直接调用的
	//@Reference(check = false)这个是启动时不检查
//	@Reference(timeout = 2000)//timeout默认是1000ms
	//重试次数,这是一个整数,不包含第一次调用,新增方法不能进行重试,因为万一成功了一次那么就要三次新增成功.
	//如果第一个服务重试不成功,那么有多的服务,就会继续调用其他服务.
//	<!-- timeout="0" 默认是1000ms-->
//	<!-- retries="":重试次数，不包含第一次调用，0代表不重试-->
//	<!-- 幂等（设置重试次数）【查询、删除、修改】、非幂等（不能设置重试次数）【新增】 -->
//	@Reference(registry = "3")
//	@Reference(version = "1.0.0")//这可以帮我们指定版本
//	@Reference(url = "127.0.0.1:20880")//通过直连的方式,不通过注册中心也可以访问其他微服务
	@Reference//帮我们远程引入服务,Dubbo的注解
	UserService userService;

	@Override
//	@HystrixCommand(fallbackMethod = "hello")
	public List<UserAddress> initOrder(String userId) {
		// TODO Auto-generated method stub
		System.out.println("用户id："+userId);
		//1、查询用户的收货地址
		List<UserAddress> addressList = userService.getUserAddressList(userId);
//		for (UserAddress userAddress : addressList) {
//			System.out.println(userAddress.getUserAddress());
//		}
		return addressList;
	}

	public List<UserAddress> hello(String userId) {

		return Arrays.asList(new UserAddress(10,"测试地址","1","测试","测试","Y"));
	}
	

}
