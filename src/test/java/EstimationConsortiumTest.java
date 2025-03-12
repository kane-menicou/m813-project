import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.J0175043.estimation.PersonState;
import com.J0175043.estimation.Time;
import com.J0175043.estimation.developer.Developer;
import com.J0175043.estimation.estimationConsortium.EstimationConsortium;
import com.J0175043.estimation.estimationConsortium.EstimationConsortiumState;
import com.J0175043.estimation.individualEstimate.IndividualEstimate;
import com.J0175043.estimation.task.Task;

final public class EstimationConsortiumTest {
    @Test
    public void itWillStoreCorrectDetailsOnCreate() {
        Task task = new Task("ZFG-1344");

        EstimationConsortium consortium = new EstimationConsortium(task, 120);

        Assert.assertSame(task, consortium.getTask());
        Assert.assertSame(120, consortium.getMaxWaitSeconds());
    }

    @Test
    public void itWillBeStateNewWhenCreated() {
        Task task = new Task("ZFG-1344");

        EstimationConsortium consortium = new EstimationConsortium(task, 120);

        Assert.assertSame(EstimationConsortiumState.New, consortium.getState());
    }

    @Test
    public void itWillStoreTheMembersOfTheEstimationConsortium() {
        Developer developer1 = new Developer("John Doe", "john.doe", PersonState.Active);
        Developer developer2 = new Developer("Joanna Soe", "joanna.soe", PersonState.Active);
        List<Developer> developers = new ArrayList<Developer>();
        developers.add(developer1);
        developers.add(developer2);

        EstimationConsortium consortium = new EstimationConsortium(new Task("ZFG-1344"), 120);

        consortium.addMember(developer1);
        consortium.addMember(developer2);

        Assert.assertEquals(developers, consortium.getMembers());
    }

    @Test
    public void itWillStoreTheIndividualEstimateOfADeveloper() throws Exception {
        Developer developer = new Developer("John Doe", "john.doe", PersonState.Active);
        Time time = new Time(15);

        EstimationConsortium consortium = new EstimationConsortium(new Task("ZFG-1344"), 120);
        consortium.addMember(developer);

        consortium.addIndividualEstimate(time, false, developer);

        List<IndividualEstimate> estimates = consortium.getIndividualEstimates();

        Assert.assertSame(1, estimates.size());
        Assert.assertSame(time, estimates.get(0).getTime());
        Assert.assertSame(developer, estimates.get(0).getEstimator());
        Assert.assertFalse(estimates.get(0).isUnknown());
    }

    @Test
    public void itWillStoreAnUnknownIndividualEstimateOfADeveloper() throws Exception {
        Developer developer = new Developer("John Doe", "john.doe", PersonState.Active);

        EstimationConsortium consortium = new EstimationConsortium(new Task("ZFG-1344"), 120);
        consortium.addMember(developer);

        consortium.addIndividualEstimate(new Time(0), true, developer);

        List<IndividualEstimate> estimates = consortium.getIndividualEstimates();

        Assert.assertSame(1, estimates.size());
        Assert.assertSame(developer, estimates.get(0).getEstimator());
        Assert.assertTrue(estimates.get(0).isUnknown());
    }

    @Test
    public void itWillOnlyStoreZeroHourTimeForAnUnknownEstimate() throws Exception {
        Developer developer = new Developer("John Doe", "john.doe", PersonState.Active);

        EstimationConsortium consortium = new EstimationConsortium(new Task("ZFG-1344"), 120);
        consortium.addMember(developer);

        consortium.addIndividualEstimate(new Time(15), true, developer);

        List<IndividualEstimate> estimates = consortium.getIndividualEstimates();

        Assert.assertSame(1, estimates.size());
        Assert.assertSame(0, estimates.get(0).getTime().getHours());
    }

    @Test
    public void itWillNotStoreAnIndividualEstimateForADeveloperWhichIsNotAMember() {
        Developer developer = new Developer("John Doe", "john.doe", PersonState.Active);

        EstimationConsortium consortium = new EstimationConsortium(new Task("ZFG-1344"), 120);

        Assert.assertThrows(Exception.class, () -> {
            consortium.addIndividualEstimate(new Time(15), true, developer);
        });

        Assert.assertSame(0, consortium.getIndividualEstimates().size());
    }

    @Test
    public void itWillEnsureTheEstimationConsortiumIsInProgress() throws Exception {
        Developer developer = new Developer("John Doe", "john.doe", PersonState.Active);

        EstimationConsortium consortium = new EstimationConsortium(new Task("ZFG-1344"), 120);
        consortium.addMember(developer);

        consortium.addIndividualEstimate(new Time(15), false, developer);

        Assert.assertSame(EstimationConsortiumState.InProgress, consortium.getState());
    }

    @Test 
    public void itWillNotMarkTheEstimationConsortiumAsInProgressIfANonMemberAddsAnEstimate() {
        Developer developer = new Developer("John Doe", "john.doe", PersonState.Active);

        EstimationConsortium consortium = new EstimationConsortium(new Task("ZFG-1344"), 120);

        Assert.assertThrows(Exception.class, () -> {
            consortium.addIndividualEstimate(new Time(15), true, developer);
        });

        Assert.assertSame(EstimationConsortiumState.New, consortium.getState());
    }

    @Test 
    public void itWillNotAllowTheDeveloperToAddMultipleEstimates() throws Exception {
        Developer developer = new Developer("John Doe", "john.doe", PersonState.Active);

        EstimationConsortium consortium = new EstimationConsortium(new Task("ZFG-1344"), 120);
        consortium.addMember(developer);
        consortium.addIndividualEstimate(new Time(14), false, developer);

        Assert.assertThrows(Exception.class, () -> {
            consortium.addIndividualEstimate(new Time(15), true, developer);
        });

        Assert.assertSame(1, consortium.getIndividualEstimates().size());
    }

    @Test 
    public void itWillNotAllowTheDeveloperToAddAnEstimateWhenTheEstimationConsortiumHasDecidedOnAnEstimate() throws Exception {
        Developer developer = new Developer("John Doe", "john.doe", PersonState.Active);

        EstimationConsortium consortium = new EstimationConsortium(new Task("ZFG-1344"), 120);
        consortium.addMember(developer);
        consortium.setState(EstimationConsortiumState.EstimationAgreed);

        Assert.assertThrows(Exception.class, () -> {
            consortium.addIndividualEstimate(new Time(15), true, developer);
        });

        Assert.assertSame(0, consortium.getIndividualEstimates().size());
    }

    @Test 
    public void itWillNotAllowTheDeveloperToAddAnEstimateWhenTheEstimationConsortiumHasBeenUnableToDecideOnAnEstimate() throws Exception {
        Developer developer = new Developer("John Doe", "john.doe", PersonState.Active);

        EstimationConsortium consortium = new EstimationConsortium(new Task("ZFG-1344"), 120);
        consortium.addMember(developer);
        consortium.setState(EstimationConsortiumState.EstimationAgreed);

        Assert.assertThrows(Exception.class, () -> {
            consortium.addIndividualEstimate(new Time(15), true, developer);
        });

        Assert.assertSame(0, consortium.getIndividualEstimates().size());
    }

    @Test
    public void itStoreTheAgreedEstimateIfAllDevelopersHaveProvidedEstimates() throws Exception {
        Developer developer = new Developer("John Doe", "john.doe", PersonState.Active);

        EstimationConsortium consortium = new EstimationConsortium(new Task("ZFG-1344"), 120);
        consortium.addMember(developer);

        consortium.addIndividualEstimate(new Time(0), true, developer);

        consortium.addAgreedEstimate(new Time(120));
    
        Assert.assertEquals(120, consortium.getAgreedEstimate().get().getTime().getHours());
    }


    @Test
    public void itStoreTheAgreedEstimateIfTheMaxWaitHasBeenReached() throws Exception {
        Developer developer = new Developer("John Doe", "john.doe", PersonState.Active);

        EstimationConsortium consortium = new EstimationConsortium(new Task("ZFG-1344"), 120);
        consortium.addMember(developer);
        consortium.setCreatedAt(new Date(System.currentTimeMillis() - 120_001));

        consortium.addAgreedEstimate(new Time(15));
    
        Assert.assertEquals(15, consortium.getAgreedEstimate().get().getTime().getHours());
    }

    @Test
    public void itNotStoreTheAgreedEstimateIfAllDevelopersHaveNoProvidedEstimates() throws Exception {
        Developer developer = new Developer("John Doe", "john.doe", PersonState.Active);

        EstimationConsortium consortium = new EstimationConsortium(new Task("ZFG-1344"), 120);
        consortium.addMember(developer);
        consortium.setCreatedAt(new Date(System.currentTimeMillis() + 121_000));

        Assert.assertThrows(Exception.class, () -> {
            consortium.addAgreedEstimate(new Time(120));
        });
    
        Assert.assertFalse(consortium.getAgreedEstimate().isPresent());
    }

    @Test
    public void itNotStoreTheAgreedEstimateIfTheMaxTimeHasNotBeenReached() throws Exception {
        Developer developer = new Developer("John Doe", "john.doe", PersonState.Active);

        EstimationConsortium consortium = new EstimationConsortium(new Task("ZFG-1344"), 120);
        consortium.addMember(developer);

        Assert.assertThrows(Exception.class, () -> {
            consortium.addAgreedEstimate(new Time(120));
        });
    
        Assert.assertFalse(consortium.getAgreedEstimate().isPresent());
    }
}
