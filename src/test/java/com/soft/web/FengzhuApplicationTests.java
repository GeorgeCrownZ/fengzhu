package com.soft.web;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class FengzhuApplicationTests {
    public static void main(String[] args) {
        int random = (int) (Math.random() * 1000000000 + 1);
        System.out.println(random);
    }
}
