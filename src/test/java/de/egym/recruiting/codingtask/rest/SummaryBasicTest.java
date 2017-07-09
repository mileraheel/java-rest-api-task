package de.egym.recruiting.codingtask.rest;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import com.google.inject.Inject;

import de.egym.recruiting.codingtask.AbstractIntegrationTest;
import de.egym.recruiting.codingtask.TestClientService;
import de.egym.recruiting.codingtask.dto.SummaryDto;
import de.egym.recruiting.codingtask.jpa.domain.Enums;
import de.egym.recruiting.codingtask.jpa.domain.Enums.ExerciseType;
import de.egym.recruiting.codingtask.jpa.domain.Exercise;

public class SummaryBasicTest extends AbstractIntegrationTest {

	@Inject
	private TestClientService testClientService;
	
	private Date addDaysToCurrentDate(int days) {
	    Calendar c = Calendar.getInstance();
	    c.setTime(new Date()); // Now use today date.
	    c.add(Calendar.DATE, days);
	    return DateUtils.truncate(c.getTime(), Calendar.DATE);
	}
	
	@Test
	public void testSummaryList() {
		
		long user1 = 10;
		long user2 = 20;
		long user3 = 30;
		
		Date now = new Date();
		Date now1 = addDaysToCurrentDate(-1);
		Date now2 = addDaysToCurrentDate(-2);
		
		final Exercise u1e1 = new Exercise();
		u1e1.setDescription("Optional Task");
		u1e1.setDuration(1000);
		u1e1.setDistance(1000);
		u1e1.setCalories(500);
		u1e1.setStartTime(now);
		u1e1.setType(Enums.ExerciseType.RUNNING);
		u1e1.setUserId(user1);
		
		final Exercise pu1e1 = testClientService.createExercise(u1e1);
		assertNotNull(pu1e1);
		assertNotNull(pu1e1.getId());
		
		final Exercise u1e2 = new Exercise();
		u1e2.setDescription("Optional Task");
		u1e2.setDuration(1000);
		u1e2.setDistance(1000);
		u1e2.setCalories(500);
		u1e2.setStartTime(now1);
		u1e2.setType(Enums.ExerciseType.RUNNING);
		u1e2.setUserId(user1);
		
		final Exercise pu1e2 = testClientService.createExercise(u1e2);
		assertNotNull(pu1e2);
		assertNotNull(pu1e2.getId());
		
		final Exercise u1e3 = new Exercise();
		u1e3.setDescription("Optional Task");
		u1e3.setDuration(1000);
		u1e3.setDistance(1000);
		u1e3.setCalories(500);
		u1e3.setStartTime(now2);
		u1e3.setType(Enums.ExerciseType.RUNNING);
		u1e3.setUserId(user1);
		
		final Exercise pu1e3 = testClientService.createExercise(u1e3);
		assertNotNull(pu1e3);
		assertNotNull(pu1e3.getId());
		
		final Exercise u2e1 = new Exercise();
		u2e1.setDescription("Optional Task");
		u2e1.setDuration(1000);
		u2e1.setDistance(1000);
		u2e1.setCalories(500);
		u2e1.setStartTime(now);
		u2e1.setType(Enums.ExerciseType.RUNNING);
		u2e1.setUserId(user2);
		
		final Exercise pu2e1 = testClientService.createExercise(u2e1);
		assertNotNull(pu2e1);
		assertNotNull(pu2e1.getId());
		
		final Exercise u2e2 = new Exercise();
		u2e2.setDescription("Optional Task");
		u2e2.setDuration(1000);
		u2e2.setDistance(1000);
		u2e2.setCalories(500);
		u2e2.setStartTime(now1);
		u2e2.setType(Enums.ExerciseType.RUNNING);
		u2e2.setUserId(user2);
		
		final Exercise pu2e2 = testClientService.createExercise(u2e2);
		assertNotNull(pu2e2);
		assertNotNull(pu2e2.getId());
		
		final Exercise u3e1 = new Exercise();
		u3e1.setDescription("Optional Task");
		u3e1.setDuration(1000);
		u3e1.setDistance(1000);
		u3e1.setCalories(500);
		u3e1.setStartTime(now);
		u3e1.setType(Enums.ExerciseType.RUNNING);
		u3e1.setUserId(user3);
		
		final Exercise pu3e1 = testClientService.createExercise(u3e1);
		assertNotNull(pu3e1);
		assertNotNull(pu3e1.getId());
		
		final List<SummaryDto> summary = testClientService.getSummaryByType(ExerciseType.RUNNING);
		assertNotNull(summary);
		assertThat(summary.size(), is(3));
		assertThat(summary.get(0).getUserId(), is(user1));
		assertThat(summary.get(1).getUserId(), is(user2));
		assertThat(summary.get(2).getUserId(), is(user3));
	}
}
