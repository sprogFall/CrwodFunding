package test;

import org.fall.util.CrowdUtil;
import org.junit.Test;

public class TestString {

    //测试MD5加密
    @Test
    public void testMd5(){
        String source = "fall";
        String md5 = CrowdUtil.md5(source);
        System.out.println(md5);

    }

}
