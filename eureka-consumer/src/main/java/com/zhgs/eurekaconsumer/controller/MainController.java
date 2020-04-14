package com.zhgs.eurekaconsumer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.InstanceInfo.InstanceStatus;
import com.netflix.discovery.EurekaClient;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * 需求：
 * 	1、获取服务端所有服务
 * 	2、获取指定app服务
 * 	3、手动调用远端服务
 * 	4、通过Ribbon负载均衡获取服务并访问
 *
 */

@RestController
public class MainController {

	@Autowired
	private DiscoveryClient discoveryClient;

	@Autowired
	private LoadBalancerClient loadBalancerClient;

	// 获取服务端所有服务
	@GetMapping("/getHi")
	public String getHi() {
		List<String> services = discoveryClient.getServices();
		services.stream().forEach(System.out::println);
		return ToStringBuilder.reflectionToString(services);
	}
	
	// 获取服务端所有服务
	@GetMapping("/client")
	public String client() {
		return "";
	}

	//2、获取指定app服务
	@GetMapping("/client2")
	public Object client2() {
		List<ServiceInstance> provider = discoveryClient.getInstances("provider");
		ServiceInstance instance = provider.get(0);

		String forObject = new RestTemplate().getForObject(instance.getUri() + "/getHi", String.class);

		return forObject;
	}
	
	
	@GetMapping("/client3")
	public Object client3() {
		ServiceInstance serviceInstance = loadBalancerClient.choose("provider");
		String forObject = new RestTemplate().getForObject(serviceInstance.getUri() + "/getHi", String.class);

		return forObject;
	}
	
	@GetMapping("/client4")
	public Object client4() {
		return "";
	}
}
