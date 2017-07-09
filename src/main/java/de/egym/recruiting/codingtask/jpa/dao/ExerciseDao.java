package de.egym.recruiting.codingtask.jpa.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import de.egym.recruiting.codingtask.dto.SummaryDto;
import de.egym.recruiting.codingtask.jpa.domain.Exercise;
import de.egym.recruiting.codingtask.jpa.domain.Enums.ExerciseType;

public interface ExerciseDao extends BaseDao<Exercise> {

	/**
	 * Returns a list of exercises with the given description
	 *
	 * @param description
	 *            of the exercise
	 * @return filters list of exercise
	 */
	@Nonnull
	List<Exercise> findByDescription(@Nullable String description);
	
	/**
	 * return if present existing exercise with the same time and user.
	 * @param startTime start time of the exercise
	 * @param endTime end time of the exercise, calculated by duration.
	 * @param userId user id of the user.
	 * @return id of the existing exercise.
	 */
	@Nonnull
	Long findExistingExerciseByTimeAndUser(@Nonnull Date startTime, @Nonnull Date endTime, 
			@Nonnull Long userId);
	
	/**
	 * finding by userId, also gives optinal params to search by type and sameday.
	 * @param userId not null user id
	 * @param type optional type param to filter
	 * @param date optional date param to filter.
	 * @return list of exercises by user and type and date.
	 */
	@Nonnull
	List<Exercise> findByUserId(@Nonnull Long userId, @Nullable ExerciseType type, @Nullable Date date);
	
	/**
	 * getting all exercises by user by upto date.
	 * @param userId required userId param
	 * @param uptoDate date for which to search
	 * @return getting exercises by user and upto the date.
	 */
	@Nonnull
	List<Exercise> getExercisByUserAndUptoDate(@Nonnull Long userId, @Nonnull Date uptoDate);
	
	/**
	 * getting excersises summary by type and upto date.
	 * @param type not null param for filter.
	 * @param uptoDate date till when to search.
	 * @return list of summary DTOs.
	 */
	@Nonnull
	List<SummaryDto> getExercisesByType(@Nonnull ExerciseType type, @Nonnull Date uptoDate);
}
