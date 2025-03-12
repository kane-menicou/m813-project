import org.junit.Assert;
import org.junit.Test;

import com.J0175043.estimation.PersonState;
import com.J0175043.estimation.developer.Developer;

final public class DeveloperTest {
    @Test
    public void itWillStoreInformationAboutTheDeveloper() {
        Developer developer = new Developer("John Doe", "john.doe", PersonState.Active);

        Assert.assertEquals("John Doe", developer.getName());
        Assert.assertEquals("john.doe", developer.getUsername());
        Assert.assertEquals(PersonState.Active, developer.getState());
    }
}
