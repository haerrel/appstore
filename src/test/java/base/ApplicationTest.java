package base;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {
 
  @Test
  public void contextLoads() throws Exception {
    //removing code smell
    assertTrue(true);
  }
  
}
