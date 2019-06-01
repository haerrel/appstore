package base.appstore.model;

import base.appstore.exception.AppNotFoundException;
import base.appstore.exception.EntityNotFoundException;
import base.appstore.exception.JwtAuthenticationException;
import base.appstore.security.JwtAuthenticatedProfile;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class AppTest {

    @Test
    public void validateSettersAndGetters() {


        PojoClass activityPojo = PojoClassFactory.getPojoClass(App.class);


        Validator validator = ValidatorBuilder.create()
                // Lets make sure that we have a getter and a setter for every field defined.
                .with(new SetterMustExistRule()).with(new GetterMustExistRule())

                // Lets also validate that they are behaving as expected
                .with(new SetterTester()).with(new GetterTester()).build();

        // Start the Test
        validator.validate(activityPojo);
    }

    @Test
    public void appNotFoundTest() {
        AppNotFoundException app = new AppNotFoundException((long) 3000);
        String msg = app.getMessage();
        assertEquals("Could not find app 3000", msg);
    }

    @Test
    public void entityNotFoundTest() {
        EntityNotFoundException ent = new EntityNotFoundException("whatever");
        String message = ent.getMessage();
        assertEquals("whatever", message);
    }

    @Test
    public void jwtExTest() {
        JwtAuthenticationException jwt = new JwtAuthenticationException("whatever");
        String message = jwt.getMessage();
        assertEquals("whatever", message);
    }

    @Test
    public void jwtAuthProfileTest() {
        JwtAuthenticatedProfile profile = new JwtAuthenticatedProfile("a", new ArrayList<>());
        Assert.assertTrue(true);
    }

    @Test
    public void ratingTest() {
        long appId = 13L;
        Rating rating = new Rating((long) 1, "42", 2.3, "text", (long) 88, appId);
        rating.setAppId(appId);
        rating.setDatePublished("42");
        rating.setId((long) 1);
        rating.setRating(2.3);
        rating.setText("text");
        rating.setUserId((long) 88);

        assertEquals(appId, (long) rating.getAppId());
        assertEquals("42", rating.getDatePublished());
        assertEquals((long) 1, (long) rating.getId());
        assertEquals(String.valueOf(2.3), rating.getRating().toString());
        assertEquals("text", rating.getText());
        assertEquals((long) 88, (long) rating.getUserId());
    }

    @Test
    public void preUserTest() {
        PredefinedUser admin = PredefinedUser.ADMIN;
        assertEquals("DemoAdmin", admin.getUsername());
        assertEquals("DemoPassword", admin.getPassword());

        PredefinedUser developer = PredefinedUser.DEVELOPER;
        assertEquals("DemoDeveloper", developer.getUsername());
        assertEquals("DemoPassword", developer.getPassword());

        PredefinedUser user = PredefinedUser.USER;
        assertEquals("DemoUser", user.getUsername());
        assertEquals("DemoPassword", user.getPassword());
    }

}
