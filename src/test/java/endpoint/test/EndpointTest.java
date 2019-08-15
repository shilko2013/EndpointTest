package endpoint.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        IllegalCompanyIdTest.class,
        IllegalUserNameTest.class,
        NonexistentCompanyIdTest.class,
        NonExistentUserNameTest.class,
        NormalBehaviorTest.class,
        UserNamePermissionTest.class
})
public class EndpointTest {

}
