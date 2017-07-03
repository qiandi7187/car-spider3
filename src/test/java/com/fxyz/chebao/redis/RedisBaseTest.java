package com.fxyz.chebao.redis;

/**
 * Created by qd on 2017/6/20.
 */

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:config/spring/applicationContext-*.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RedisBaseTest {
    @Autowired
    RedisTemplate redisTemplate;
    /**
     * Redis是一个开源，高级的键值存储和一个适用的解决方案，用于构建高性能，可扩展的Web应用程序。
     * Redis有三个主要特点，使它优越于其它键值数据存储系统。
     * Redis将其数据库完全保存在内存中，仅使用磁盘进行持久化。
     * 与其它键值数据存储相比，Redis有一组相对丰富的数据类型。
     * Redis可以将数据复制到任意数量的从机中。
     */

    /**
     * Redis支持5种数据类型。 1.字符串  2.散列/哈希  3.列表  4.集合  5.可排序集合
     */

    /**
     * 1.字符串  Value
     * Redis中的字符串是一个字节序列。Redis中的字符串是二进制安全的，这意味着它们的长度不由任何特殊的终止字符决定。因此，可以在一个字符串中存储高达512兆字节的任何内容。
     */
    @Test
    public void a1_setValue(){
        System.out.println("1.字符串  Value");
        ValueOperations<String, Object> value = redisTemplate.opsForValue();
        value.set("qdValue", "百川东到海，何时复西归！");
       // value.set("test", "100",60*10, TimeUnit.SECONDS);
        System.out.println("存入成功！！！   初始过期时间："+redisTemplate.getExpire("qdValue"));
        //设定过期时间 2min
        redisTemplate.expire("qdValue",60*2,TimeUnit.SECONDS);
        System.out.println("过期时间："+redisTemplate.getExpire("qdValue"));
    }
    @Test
    public void a2_getValue(){
        ValueOperations<String, Object> value = redisTemplate.opsForValue();
        System.out.println(value.get("qdValue")!=null?"成功取到数据："+value.get("qdValue"):"数据不存在");
    }
    @Test
    public void a3_deleteValue(){
        System.out.println( redisTemplate.hasKey("qdValue")? "qdValue 存在！！！":"qdValue 不存在！！！");
        redisTemplate.delete("qdValue");
        System.out.println( "删除操作,即使数据不存在，也不会报错，五种类型的删除操作基本雷同，在此不重复测试！！！");
        System.out.println( redisTemplate.hasKey("qdValue")? "qdValue 存在！！！":"qdValue 不存在！！！");
    }

    /**
     * 2.散列/哈希 Hash
     * Redis散列/哈希(Hashes)是键值对的集合。Redis散列/哈希是字符串字段和字符串值之间的映射。因此，它们用于表示对象
     */
    @Test
    public void b1_setHash(){
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("2.散列/哈希 Hash");
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        //value必须为String 不然会报运行时会产生java.lang.ClassCastException
        Map<String,String> map = new HashMap<String,String>();
        //Map<String,Object> map = new HashMap<String,Object>();
        map.put("name", "qd");
        map.put("age","18");
        hash.putAll("qdMap", map);
        //追加键值对
        hash.put("qdMap","desc", "nb");
        //设定过期时间 2min
        redisTemplate.expire("qdMap",60*2,TimeUnit.SECONDS);
        System.out.println("存入成功！！！   过期时间："+redisTemplate.getExpire("qdMap"));
    }
    @Test
    public void b2_getHash(){
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        System.out.println(hash.entries("qdMap").size()>0?"成功取到数据："+hash.entries("qdMap")+"  age:"+hash.get("qdMap","age"):"数据不存在");

    }


    /**
     * 3.列表 List
     * redis列表只是字符串列表，按插入顺序排序。您可以向Redis列表的头部或尾部添加元素。
     */
    @Test
    public void c1_setList(){
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("3.列表 List");
        ListOperations<String, Object> list = redisTemplate.opsForList();
        //value必须为String 不然会报运行时会产生java.lang.ClassCastException

        list.rightPush("qdList", " 莫愁前路无知己，天下谁人不识君。");
        list.rightPush("qdList", " 六翮飘飖私自怜，一离京洛十馀年。");
        list.leftPush("qdList", " 千里黄云白日曛，北风吹雁雪纷纷。");
        list.rightPush("qdList", " 丈夫贫践应未足，今日相逢无酒钱。");
        //设定过期时间 2min
        redisTemplate.expire("qdList",60*2,TimeUnit.SECONDS);
        System.out.println("存入成功！！！   过期时间："+redisTemplate.getExpire("qdList"));
    }
    @Test
    public void c2_getList(){
        ListOperations<String, String> list = redisTemplate.opsForList();
        if(!redisTemplate.hasKey("qdList")){
            System.out.println("数据不存在");
            return ;
        }
        System.out.println(" 列表  长度："+list.size("qdList"));
        for(String value  : list.range("qdList", 0, 10)){
            System.out.println(value);

        }

    }


    /**
     * 4.无序集合 Set
     * Redis集合是字符串的无序集合。在Redis中，您可以添加，删除和测试成员存在的时间O(1)复杂性。
     */
    @Test
    public void d1setSet(){
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("4.无序集合 Set");
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add("qdSet", " 茶");
        set.add("qdSet", " 香叶，嫩芽。");
        set.add("qdSet", " 慕诗客，爱僧家。");
        set.add("qdSet", " 碾雕白玉，罗织红纱。");
        set.add("qdSet", " 铫煎黄蕊色，碗转麹尘花。");
        set.add("qdSet", " 夜后邀陪明月，晨前命对朝霞。");
        set.add("qdSet", " 洗尽古今人不倦，将知醉后岂堪夸。");
        //设定过期时间 2min
        redisTemplate.expire("qdSet",60*2,TimeUnit.SECONDS);
        System.out.println("存入成功！！！   过期时间："+redisTemplate.getExpire("qdSet"));
    }
    @Test
    public void d2_getSet(){
        SetOperations<String, String> set = redisTemplate.opsForSet();
        if(!redisTemplate.hasKey("qdSet")){
            System.out.println("数据不存在");
            return ;
        }
        System.out.println(" 无序集合  长度："+set.size("qdSet"));
        for(String value : set.members("qdSet")){
            System.out.println(value);

        }

    }

    /**
     * 5.可排序集合 ZSet
     * Redis可排序集合类似于Redis集合，是不重复的字符集合。 不同之处在于，排序集合的每个成员都与分数相关联，
     * 这个分数用于按最小分数到最大分数来排序的排序集合。虽然成员是唯一的，但分数值可以重复。
     */

    @Test
    public void e1_setZSet(){
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("5.可排序集合 ZSet");
        ZSetOperations<String, Object> zSet = redisTemplate.opsForZSet();
        zSet.add("qdZSet", " 茶",0);
        zSet.add("qdZSet", " 香叶，嫩芽。",1);
        zSet.add("qdZSet", " 慕诗客，爱僧家。",2);
        zSet.add("qdZSet", " 碾雕白玉，罗织红纱。",3);
        zSet.add("qdZSet", " 铫煎黄蕊色，碗转麹尘花。",4);
        zSet.add("qdZSet", " 夜后邀陪明月，晨前命对朝霞。",5);
        zSet.add("qdZSet", " 洗尽古今人不倦，将知醉后岂堪夸。",6);
        //设定过期时间 2min
        redisTemplate.expire("qdZSet",60*2,TimeUnit.SECONDS);
        System.out.println("存入成功！！！   过期时间："+redisTemplate.getExpire("qdZSet"));
    }
    @Test
    public void e2_getZSet(){
        ZSetOperations<String, String> zSet = redisTemplate.opsForZSet();
        if(!redisTemplate.hasKey("qdZSet")){
            System.out.println("数据不存在");
            return ;
        }
        System.out.println(" 有序集合 长度："+zSet.size("qdZSet"));
        for(String value : zSet.rangeByScore("qdZSet", 0, 100)){
            System.out.println(value);

        }

    }







}
