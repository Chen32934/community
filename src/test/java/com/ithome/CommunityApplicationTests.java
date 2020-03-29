package com.ithome;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;


class CommunityApplicationTests {

    @Test
    void contextLoads() {
    }


    @Test
    void testr() {

        String a="2.3,5,8，9";
        String tag = StringUtils.replace(a, "，", ",").replace(",", "|");

        System.out.println(tag);
    }

}
