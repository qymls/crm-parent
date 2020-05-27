import cn.nine.crm.realm.MD5Utils;
import org.junit.Test;

public class MD5Test {
    @Test
    public void test() throws Exception {
        System.out.println(MD5Utils.getMD5Password("123456"));
    }
}
