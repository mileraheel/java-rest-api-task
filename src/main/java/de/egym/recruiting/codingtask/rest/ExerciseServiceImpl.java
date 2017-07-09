package de.egym.recruiting.codingtask.rest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.egym.recruiting.codingtask.dto.ErrorDto;
import de.egym.recruiting.codingtask.dto.SummaryDto;
import de.egym.recruiting.codingtask.jpa.dao.ExerciseDao;
import de.egym.recruiting.codingtask.jpa.domain.Enums.APIMessages;
import de.egym.recruiting.codingtask.jpa.domain.Enums.ExerciseType;
import de.egym.recruiting.codingtask.jpa.domain.Exercise;
import de.egym.recruiting.codingtask.util.ExerciseUtilities;
import de.egym.recruiting.codingtask.util.UserVO;

@Singleton
public class ExerciseServiceImpl implements ExerciseService {

	private static final Logger log = LoggerFactory.getLogger(ExerciseServiceImpl.class);
	private static final String dateFormat = "yyyy-MM-dd";
	private final ExerciseDao exerciseDao;
	private ExerciseUtilities utilites;

	@Inject
	ExerciseServiceImpl(final ExerciseDao exerciseDao, ExerciseUtilities utilities) {
		this.exerciseDao = exerciseDao;
		this.utilites = utilities;
	}
	
	@Nonnull
	@Override
	public Exercise update(@Nonnull Exercise exercise) {
		log.debug("updating exercise.");
		Set<ErrorDto> errors = new HashSet<>();
		if (exercise.getId() == null) {
			errors.add(new ErrorDto(Response.Status.BAD_REQUEST.getStatusCode(), 
					APIMessages.UPDATE_HAS_NO_ID.value(), 
					APIMessages.UPDATE_HAS_NO_ID.field()));
			log.debug(APIMessages.UPDATE_HAS_NO_ID.value());
		}
		
		Set<ErrorDto> validationErrors = utilites.validateExercise(exercise);
		if (validationErrors !=null) {
			errors.addAll(validationErrors);
		}
		
		Exercise updateExer = exerciseDao.findById(exercise.getId());
		if (updateExer == null) {
			errors.add(new ErrorDto(Response.Status.NOT_FOUND.getStatusCode(), 
					APIMessages.NO_EXERCISE_FOUND_WITH_ID.value(), 
					APIMessages.NO_EXERCISE_FOUND_WITH_ID.field()));
			log.debug(APIMessages.NO_EXERCISE_FOUND_WITH_ID.value());
		}
		
		if (!updateExer.getUserId().equals(exercise.getUserId())) {
			errors.add(new ErrorDto(Response.Status.BAD_REQUEST.getStatusCode(), 
					APIMessages.UPDATE_USER_ID_CHANGED.value(), 
					APIMessages.UPDATE_USER_ID_CHANGED.field()));
			log.debug(APIMessages.UPDATE_USER_ID_CHANGED.value());
		}
		
		if (!updateExer.getType().equals(exercise.getType())) {
			errors.add(new ErrorDto(Response.Status.BAD_REQUEST.getStatusCode(), 
					APIMessages.UPDATE_TYPE_CHANGED.value(), 
					APIMessages.UPDATE_TYPE_CHANGED.field()));
			log.debug(APIMessages.UPDATE_TYPE_CHANGED.value());
		}
		
		if (errors.size() > 0) {
        	throw new MalformedRestException(errors);
        } else {
        	exercise = exerciseDao.update(exercise);
        }
		return exercise;
	}
	
	@Nonnull
	@Override
	public Exercise save(@Nonnull Exercise exercise) {
		log.debug("save new exercise.");
		Set<ErrorDto> errors = new HashSet<>();
		if (exercise.getId() != null) {
			errors.add(new ErrorDto(Response.Status.BAD_REQUEST.getStatusCode(), 
					APIMessages.SAVE_HAS_ID.value(), 
					APIMessages.SAVE_HAS_ID.field()));
			log.debug(APIMessages.SAVE_HAS_ID.value());
		}
		
		Set<ErrorDto> validationErrors = utilites.validateExercise(exercise);
		if (validationErrors !=null) {
			errors.addAll(validationErrors);
		}
        
        Long id = exerciseDao.findExistingExerciseByTimeAndUser(exercise.getStartTime(), 
        		exercise.getEndTime(), exercise.getUserId());
        if (id !=null) {
        	errors.add(new ErrorDto(Response.Status.CONFLICT.getStatusCode(), 
        			APIMessages.CONFLIT_TIME.value(), 
        			APIMessages.CONFLIT_TIME.field()));
        	log.debug(APIMessages.CONFLIT_TIME.value());
        }
        
        if (errors.size() > 0) {
        	throw new MalformedRestException(errors);
        } else {
        	exercise = exerciseDao.create(exercise);
        }
		return exercise;
	}

	@Nonnull
	@Override
	public Exercise getExerciseById(@Nonnull final Long exerciseId) {
		log.debug("Get exercise by id.");

		final Exercise exercise = exerciseDao.findById(exerciseId);
		if (exercise == null) {
			throw new NotFoundException("Exercise with id = " + exerciseId + " could not be found.");
		}

		return exercise;
	}

	@Nonnull
	@Override
	public List<Exercise> getExerciseByDescription(@Nonnull final String description) {
		log.debug("Get exercise by description.");
		return exerciseDao.findByDescription(description);
	}
	
	@Nonnull
	@Override
	public String deleteExerciseById(Long exerciseId) {
		try {
		   exerciseDao.deleteById(exerciseId);
		} catch (IllegalArgumentException e) {
			throw new MalformedRestException(new ErrorDto(
					Response.Status.NOT_FOUND.getStatusCode(), 
					APIMessages.DELETE_EXERCISE_NOT_FOUND.value(), 
					APIMessages.DELETE_EXERCISE_NOT_FOUND.field()));
		}
		return APIMessages.DELETE_SUCCESS.value();
	}

	@Nonnull
	@Override
	public List<Exercise> getExerciseByUserId(Long userId, ExerciseType type, String date) {
		Date _date = null;
		if (date !=null) {
			try {
				if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
					_date = new SimpleDateFormat(dateFormat).parse(date);  
				} else {
					throw new MalformedRestException(new ErrorDto(
							Response.Status.BAD_REQUEST.getStatusCode(), 
							APIMessages.INVALID_DATE_FORMAT.value(), 
							APIMessages.INVALID_DATE_FORMAT.field()));
				}
			} catch (Exception e) {
				throw new MalformedRestException(new ErrorDto(
						Response.Status.BAD_REQUEST.getStatusCode(), 
						APIMessages.INVALID_DATE_FORMAT.value(), 
						APIMessages.INVALID_DATE_FORMAT.field()));
			}
		}
		return exerciseDao.findByUserId(userId, type, _date);
	}
	
	@Nonnull
	@Override
	public List<Long> getRanking(@Nonnull final List<Long> userIds) {
		int noOfDays = -28; //i.e four weeks
		Date uptoDate = utilites.addDaysToCurrentDate(noOfDays);
		List<UserVO> users = new ArrayList<>(userIds.size());
		for (Long userId : userIds) {
			List<Exercise> exercises = exerciseDao.getExercisByUserAndUptoDate(userId, uptoDate);
			int points = 0;
			Set<ExerciseType> types = new HashSet<>();
			for(Exercise exercise : exercises) {
				int exercisePoints = (exercise.getDuration()/60) + exercise.getCalories();
				if (types.contains(exercise.getType())) {
					Double res = (90d/100d) * ((double) exercisePoints);
					exercisePoints = res.intValue();
				} else {
					types.add(exercise.getType());
				}
				if (exercisePoints < 0) {
					exercisePoints = 0;
				}
				points += (exercise.getType().points() * exercisePoints);
			}
			users.add(new UserVO(userId, points));
		}
		Collections.sort(users);
		List<Long> sortedUserIds = new ArrayList<>(userIds.size());
		
		for (UserVO userVO : users) {
			sortedUserIds.add(userVO.userId);
		}
	 return sortedUserIds;
	}
	
	@Nonnull
	@Override
	public List<SummaryDto> getSummaryByType(@Nonnull ExerciseType type) {
		int noOfDays = -28; //i.e four weeks
		Date uptoDate = utilites.addDaysToCurrentDate(noOfDays);
		return exerciseDao.getExercisesByType(type, uptoDate);
	}
}
