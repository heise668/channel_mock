package com.qpay.channel.test.mock.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.qpay.channel.test.mock.services.User;

@RestController
public class UserController {

    @PostMapping(value = "/user", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
//	@PostMapping(value = "/user", produces = { "application/xml;charset=UTF-8" })
    //注意；consumes注解用于标识，该方法接受xml参数，并反序列化为对应的对象；
     //  produces注解用于标识，该方法的返回值为xml参数，将返回的对象序列化为xml，
    public User create(@RequestBody User user) {
        user.setName("didispace.com : " + user.getName());
        user.setAge(user.getAge() + 100);
        return user;
    }

}
