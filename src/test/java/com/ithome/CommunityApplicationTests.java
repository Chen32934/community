package com.ithome;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class CommunityApplicationTests {

    @Test
    void contextLoads() {
    }


    @Test
    void testr() {

        String a="2.3";
        BigDecimal D=new BigDecimal(a);
        System.out.println(D);
    }

}
