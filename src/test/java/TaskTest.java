import org.junit.Assert;
import org.junit.Test;

import com.J0175043.estimation.task.Task;

final public class TaskTest {
    @Test
    public void itWillStoreTheTaskCode() {
        String taskCode = "JFS123";

        Task task = new Task(taskCode);

        Assert.assertEquals(taskCode, task.getTaskCode());
    }
}
