package de.egym.recruiting.codingtask.rest;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import com.google.inject.Inject;

import de.egym.recruiting.codingtask.AbstractIntegrationTest;
import de.egym.recruiting.codingtask.TestClientService;
import de.egym.recruiting.codingtask.jpa.domain.Enums;
import de.egym.recruiting.codingtask.jpa.domain.Exercise;

public class RankingBasicTest extends AbstractIntegrationTest {

	@Inject
	private TestClientService testClientService;

	@Test
	public void testRangingList() {
		final long userId1 = 20L;
		final long userId2 = 21L;
			  long userId3 = 22l;
			  
		Date now = new Date();
		Date now1 = addDaysToCurrentDate(-1);
		Date now2 = addDaysToCurrentDate(-2);

		final Exercise exercise1ToInsert = new Exercise();
		exercise1ToInsert.setDescription("Coding Task");
		exercise1ToInsert.setDuration(14400);
		exercise1ToInsert.setDistance(0);
		exercise1ToInsert.setCalories(500);
		exercise1ToInsert.setStartTime(Calendar.getInstance().getTime());
		exercise1ToInsert.setType(Enums.ExerciseType.OTHER);
		exercise1ToInsert.setUserId(userId1);

		final Exercise persistedExercise1 = testClientService.createExercise(exercise1ToInsert);
		assertNotNull(persistedExercise1);
		assertNotNull(persistedExercise1.getId());

		final Exercise exercise2ToInsert = new Exercise();
		exercise2ToInsert.setDescription("Onsite Interview");
		exercise2ToInsert.setDuration(7200);
		exercise2ToInsert.setDistance(1500);
		exercise2ToInsert.setCalories(700);
		exercise2ToInsert.setStartTime(Calendar.getInstance().getTime());
		exercise2ToInsert.setType(Enums.ExerciseType.OTHER);
		exercise2ToInsert.setUserId(userId2);

		final Exercise persistedExercise2 = testClientService.createExercise(exercise2ToInsert);
		assertNotNull(persistedExercise2);
		assertNotNull(persistedExercise2.getId());
		
		final Exercise exercise3ToInsert = new Exercise();
		exercise3ToInsert.setDescription("Onsite Interview");
		exercise3ToInsert.setDuration(7200);
		exercise3ToInsert.setDistance(1500);
		exercise3ToInsert.setCalories(700);
		exercise3ToInsert.setStartTime(now);
		exercise3ToInsert.setType(Enums.ExerciseType.OTHER);
		exercise3ToInsert.setUserId(userId3);

		final Exercise persistedExercise3 = testClientService.createExercise(exercise3ToInsert);
		assertNotNull(persistedExercise3);
		assertNotNull(persistedExercise3.getId());
		
		final Exercise exercise4ToInsert = new Exercise();
		exercise4ToInsert.setDescription("Onsite Interview");
		exercise4ToInsert.setDuration(7200);
		exercise4ToInsert.setDistance(1500);
		exercise4ToInsert.setCalories(700);
		exercise4ToInsert.setStartTime(now1);
		exercise4ToInsert.setType(Enums.ExerciseType.OTHER);
		exercise4ToInsert.setUserId(userId3);

		final Exercise persistedExercise4 = testClientService.createExercise(exercise4ToInsert);
		assertNotNull(persistedExercise4);
		assertNotNull(persistedExercise4.getId());
		
		final Exercise exercise5ToInsert = new Exercise();
		exercise5ToInsert.setDescription("Onsite Interview");
		exercise5ToInsert.setDuration(7200);
		exercise5ToInsert.setDistance(1500);
		exercise5ToInsert.setCalories(700);
		exercise5ToInsert.setStartTime(now2);
		exercise5ToInsert.setType(Enums.ExerciseType.OTHER);
		exercise5ToInsert.setUserId(userId3);

		final Exercise persistedExercise5 = testClientService.createExercise(exercise5ToInsert);
		assertNotNull(persistedExercise5);
		assertNotNull(persistedExercise5.getId());

		final List<Long> ranking = testClientService.getRanking(Arrays.asList(userId1, userId2, userId3));
		assertNotNull(ranking);
		assertThat(ranking.size(), is(3));
		assertThat(ranking.get(0), is(userId3));
		assertThat(ranking.get(1), is(userId2));
	}
	
	private Date addDaysToCurrentDate(int days) {
	    Calendar c = Calendar.getInstance();
	    c.setTime(new Date()); // Now use today date.
	    c.add(Calendar.DATE, days);
	    return DateUtils.truncate(c.getTime(), Calendar.DATE);
	}

}
