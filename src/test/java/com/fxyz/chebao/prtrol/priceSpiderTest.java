package com.fxyz.chebao.prtrol;

import com.fxyz.chebao.service.PetrolPriceSpiderService;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;

import java.io.FileNotFoundException;

/**
 *
 * 2017/9/5  测试汽油价格抓取方法
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:config/spring/applicationContext-*.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class priceSpiderTest {
    @Autowired
    PetrolPriceSpiderService petrolPriceSpiderService;

    private static Logger logger = Logger.getLogger(priceSpiderTest.class);

    /**
     * 打印日志
     */
    @Before
    public void loadConfig(){
        try {
            Log4jConfigurer.initLogging("classpath:config/property/log4j.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetPrice(){
        petrolPriceSpiderService.getNewPetrolPrice();

    }




}
