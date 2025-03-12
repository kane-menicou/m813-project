import static org.junit.Assert.assertThrows;

import org.junit.Assert;
import org.junit.Test;

import com.J0175043.estimation.PersonState;
import com.J0175043.estimation.Time;
import com.J0175043.estimation.developer.Developer;
import com.J0175043.estimation.individualEstimate.IndividualEstimate;

final public class IndividualEstimateTest {
    @Test
    public void itWillStoreTimeWhenCreated() throws Exception {
        Time time = new Time(5);
        Developer developer = new Developer("John Doe", "john.doe", PersonState.Active);

        IndividualEstimate estimate = new IndividualEstimate(time, developer);

        Assert.assertSame(time, estimate.getTime());
        Assert.assertFalse(estimate.isUnknown());
    }

    @Test
    public void itWillStoreTheEstimateIfUnknown() throws Exception {
        Developer developer = new Developer("John Doe", "john.doe", PersonState.Active);

        IndividualEstimate estimate = new IndividualEstimate(developer);

        Assert.assertEquals(0, estimate.getTime().getHours());
        Assert.assertTrue(estimate.isUnknown());
    }

    @Test
    public void itWillThrowOnCreateIfTheEstimatorIsReadOnly() {
        Developer developer = new Developer("John Doe", "john.doe", PersonState.ReadOnly);

        assertThrows("Developer", Exception.class, () -> {
            new IndividualEstimate(developer);
        });
    }

    @Test
    public void itWillThrowOnCreateIfTheEstimatorIsDisabled() {
        Developer developer = new Developer("John Doe", "john.doe", PersonState.Disabled);

        assertThrows("Developer", Exception.class, () -> {
            new IndividualEstimate(developer);
        });
    }
}
