package com.xiongyingqi.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiongyingqi.jackson.annotation.IgnoreProperties;
import com.xiongyingqi.jackson.annotation.IgnoreProperty;
import com.xiongyingqi.jackson.helper.ThreadJacksonMixInHolder;
import com.xiongyingqi.jackson.impl.JavassistFilterPropertyHandler;
import com.xiongyingqi.jackson.pojo.Group;
import com.xiongyingqi.jackson.pojo.User;
import com.xiongyingqi.util.Assert;
import com.xiongyingqi.util.EntityHelper;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/6/4 0004.
 */
public class JsonFilterPropertyTest {

    @IgnoreProperties(@IgnoreProperty(pojo = User.class, name = "id"))
    public Collection<User> listUsers() {
        Group group1 = new Group();
        group1.setId(1);
        group1.setName("分组1");

        User user1 = new User();
        user1.setId(1);
        user1.setGroup(group1);
        user1.setName("用户1");
        User user2 = new User();
        user2.setId(1);
        user2.setGroup(group1);
        user2.setName("用户1");
        User user3 = new User();
        user3.setId(1);
        user3.setName("用户1");
        user3.setGroup(group1);


        Group group2 = new Group();
        group2.setId(2);
        group2.setName("分组2");

        User user4 = new User();
        user4.setId(4);
        user4.setGroup(group2);
        user4.setName("用户4");
        User user5 = new User();
        user5.setId(5);
        user5.setGroup(group2);
        user5.setName("用户5");
        User user6 = new User();
        user6.setId(6);
        user6.setName("用户6");
        user6.setGroup(group2);

        Collection<User> users = new ArrayList<User>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        users.add(user6);
        return users;
    }

    @Test
    public void jsonTest() throws NoSuchMethodException, JsonProcessingException {
        FilterPropertyHandler filterPropertyHandler = new JavassistFilterPropertyHandler(false);
        Object object = listUsers();

        object = filterPropertyHandler.filterProperties(JsonFilterPropertyTest.class.getMethod("listUsers"), object);


        ObjectMapper mapper = ThreadJacksonMixInHolder.builderMapper();
        String json = mapper.writeValueAsString(object);
        EntityHelper.print(json);
        Assert.hasText(json);
    }
}
