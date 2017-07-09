package de.egym.recruiting.codingtask;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.inject.Inject;

import de.egym.recruiting.codingtask.dto.SummaryDto;
import de.egym.recruiting.codingtask.jpa.domain.Enums;
import de.egym.recruiting.codingtask.jpa.domain.Enums.ExerciseType;
import de.egym.recruiting.codingtask.jpa.domain.Exercise;
import de.egym.recruiting.codingtask.rest.ExerciseService;

public class TestClientService {

	private final ExerciseService exerciseService;

	@Inject
	public TestClientService(final ExerciseService exerciseService) {
		this.exerciseService = exerciseService;
	}

	/**
	 * Get the exercise for a given exerciseId.
	 *
	 * @param exerciseId
	 *            id to search
	 * @return the exercise for the given exerciseId
	 * @throws RuntimeException
	 *             if there is an error please throw an appropriate exception here
	 */
	@Nonnull
	public Exercise getExercise(@Nullable final Long exerciseId) {
		return exerciseService.getExerciseById(exerciseId);
	}

	/**
	 * Get the exercises with the given description.
	 *
	 * @param description
	 *            description to search
	 * @return the exercises for the given description
	 * @throws RuntimeException
	 *             if there is an error please throw an appropriate exception here
	 */
	@Nonnull
	public List<Exercise> getExerciseByDescription(@Nullable final String description) {
		return exerciseService.getExerciseByDescription(description);
	}

	/**
	 * Persists a given exercise.
	 *
	 * @param exercise
	 *            to persist
	 * @return the persisted exercise with created id
	 * @throws RuntimeException
	 *             if there is an error please throw an appropriate exception here
	 */
	@Nonnull
	public Exercise createExercise(@Nullable final Exercise exercise) {
		Exercise exer = exerciseService.save(exercise);
		return exer;
	}

	/**
	 * Updates an existing exercise.
	 *
	 * @param exercise
	 *            to update
	 * @return the updated exercise
	 * @throws RuntimeException
	 *             if there is an error please throw an appropriate exception here
	 */
	@Nonnull
	public Exercise updateExercise(@Nullable final Exercise exercise) {
		return exerciseService.update(exercise);
	}

	/**
	 * Deletes an exercise with the given exerciseId.
	 *
	 * @param exerciseId
	 *            id to delete
	 * @throws RuntimeException
	 *             if there is an error please throw an appropriate exception here
	 */
	public void deleteExercise(@Nullable final Long exerciseId) {
		exerciseService.deleteExerciseById(exerciseId);
	}

	/**
	 * Returns a list of exercises for a specific user and some filter items (type + date).
	 *
	 * @param userId
	 *            who did the exercise
	 * @param exerciseType
	 *            filter: type of the exercise
	 * @param date
	 *            filter: date ("yyyy-MM-dd") of the exercise
	 * @return a list of exercises
	 * @throws RuntimeException
	 *             if there is an error please throw an appropriate exception here
	 */
	@Nonnull
	public List<Exercise> getExercises(@Nullable final Long userId, @Nullable final Enums.ExerciseType exerciseType,
			@Nullable final String date) {
		return exerciseService.getExerciseByUserId(userId, exerciseType, date);
	}

	/**
	 * Calculate the ranking for the given user ids. The Calculation based on the exercises the user has done. The first in the list is the
	 * user with the highest score.
	 *
	 * @param userIds
	 *            list of user ids
	 * @return the user list orders by there calculated exercise points
	 * @throws RuntimeException
	 *             if there is an error please throw an appropriate exception here
	 */
	@Nonnull
	public List<Long> getRanking(@Nullable final List<Long> userIds) {
		return exerciseService.getRanking(userIds);
	}
	
	/**
	 * It summarizes all the exercises by type grouped by user. results are sorted by user
	 * who has done the most duration in that exercise for last 28 days.
	 * @param type Exercise Type to get the summary for.
	 * @return List of Summary dto.
	 */
	@Nonnull
	public List<SummaryDto> getSummaryByType(@Nonnull ExerciseType type) {
		return exerciseService.getSummaryByType(type);
	}
}
