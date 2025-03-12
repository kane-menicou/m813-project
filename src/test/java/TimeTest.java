import org.junit.Assert;
import org.junit.Test;

import com.J0175043.estimation.Time;

final public class TimeTest {
    @Test()
    public void itWillCalculate1DeveloperDayFor5h() {
        Time time = new Time(5);

        Assert.assertEquals(1, time.getDeveloperDays(), 0);
        Assert.assertEquals(5, time.getHours(), 0);
    }

    @Test()
    public void itWillCalculate2DeveloperDaysFor10h() {
        Time time = new Time(10);

        Assert.assertEquals(2, time.getDeveloperDays(), 0);
        Assert.assertEquals(10, time.getHours(), 0);
    }

    @Test()
    public void itWillCalculate2p4DeveloperDaysFor12h() {
        Time time = new Time(12);

        Assert.assertEquals(2.4, time.getDeveloperDays(), 0);
        Assert.assertEquals(12, time.getHours(), 0);
    }
}
