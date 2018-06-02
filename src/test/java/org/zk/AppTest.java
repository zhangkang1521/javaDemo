package org.zk;


import net.sf.cglib.core.DefaultNamingPolicy;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void test1() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserService.class);
        enhancer.setNamingPolicy(new MyNamePolicy());
        enhancer.setCallbackFilter(new CallbackFilterImpl());
        enhancer.setCallbacks(new Callback[] {
                NoOp.INSTANCE,
                new MyMethodInterceptor(),
                new MyMethodInterceptor2()
        });

        UserService userService = (UserService)enhancer.create();
        String result = userService.sayHello();
        System.out.println(result);
    }

    class CallbackFilterImpl implements CallbackFilter {

        public int accept(Method method) {
            return 2; // 选择callbacks[index]
        }
    }

    class MyNamePolicy extends DefaultNamingPolicy {
        public String getTag() {
            return "ByZk";
        }
    }
}
