import org.junit.Assert;
import org.junit.Test;

import com.J0175043.estimation.Time;
import com.J0175043.estimation.agreedEstimate.AgreedEstimate;

final public class AgreedEstimateTest {
    @Test
    public void itWillStoreTimeWhenCreated() throws Exception {
        Time time = new Time(5);

        AgreedEstimate estimate = new AgreedEstimate(time);

        Assert.assertSame(time, estimate.getTime());
    }
}
