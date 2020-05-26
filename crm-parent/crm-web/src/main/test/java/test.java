import cn.nine.crm.domain.Menu;
import cn.nine.crm.service.IMenuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-service.xml"})
public class test {

    @Autowired
    private IMenuService menuService;

    @Test
    public void test() throws Exception {
        List<Menu> all = menuService.findAll();
        all.forEach(menu -> System.out.println(menu));
    }
}
