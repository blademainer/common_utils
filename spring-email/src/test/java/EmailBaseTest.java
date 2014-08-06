import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.unitils.UnitilsJUnit4;

//@SpringApplicationContext({ "classpath:/applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:applicationContext-mail-test.xml",
    })
public class EmailBaseTest extends UnitilsJUnit4 {
}
