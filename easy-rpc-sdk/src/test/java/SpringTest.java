import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.util.Set;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/14
 */
public class SpringTest {

    @Test
    public void test() throws ClassNotFoundException {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
//        scanner.addIncludeFilter(new AssignableTypeFilter(Interf.class));
        Set<BeanDefinition> beanDefinitions = scanner.findCandidateComponents("cn.colins.rpc.remote");
        for (BeanDefinition beanDefinition : beanDefinitions) {
            Class<?> clazz = Class.forName(beanDefinition.getBeanClassName());
            // 处理接口类
            System.out.println(clazz);
        }
    }
}
