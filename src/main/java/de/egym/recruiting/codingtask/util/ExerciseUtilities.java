package de.egym.recruiting.codingtask.util;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egym.recruiting.codingtask.dto.ErrorDto;
import de.egym.recruiting.codingtask.jpa.domain.Exercise;

/**
 * Utility class created to help with converting dates
 * and doing validation for Exercise.
 * @author Raheel
 *
 */
public class ExerciseUtilities {
	
	private static final Logger log = LoggerFactory.getLogger(ExerciseUtilities.class);
	
	/**
	 * Utility method to do validate exercise method
	 * before saving or update.
	 * @param exercise object to be persisted.
	 * @return list of errors if exist.
	 */
	public Set<ErrorDto> validateExercise(Exercise exercise) {
		Set<ErrorDto> errors = null;
		log.debug("validating exercise.");
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator(); 
        Set<ConstraintViolation<Exercise>> constraintViolations = validator.validate(exercise); 
        if (constraintViolations.size() > 0) {
        	errors = new HashSet<>();
            for (ConstraintViolation<Exercise> cv : constraintViolations) {
            	log.debug("issue in field :" + cv.getPropertyPath().toString() + 
            			", error :" + cv.getMessageTemplate());
            	errors.add(new ErrorDto(Response.Status.BAD_REQUEST.getStatusCode(), 
            			cv.getMessageTemplate(), cv.getPropertyPath().toString()));
            } 
        } else {
        	log.debug("validation ok.");
        }
        return errors;
	}
	
	/**
	 * gets date by adding days to it.
	 * @param days number of days to add in the current date.
	 * @return date with added days.
	 */
	public Date addDaysToCurrentDate(int days) {
	    Calendar c = Calendar.getInstance();
	    c.setTime(new Date()); // Now use today date.
	    c.add(Calendar.DATE, days);
	    return getStartEndTimeForDate(c.getTime())[0];
	}
	
	/**
	 * gets first and last minute of any given day.
	 * @param date day for which first and last minute to be generated.
	 * @return date array.
	 */
	public Date[]getStartEndTimeForDate(Date date) {
		Date[] array = new Date[2];
		array[0] = DateUtils.truncate(date, Calendar.DATE);
		array[1] = DateUtils.addMilliseconds(DateUtils.ceiling(date, Calendar.DATE), -1);
		return array;
	}
}
