package de.egym.recruiting.codingtask.jpa.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.time.DateUtils;

@Entity
public class Exercise extends AbstractEntity implements Comparable<Exercise> {

	private static final long serialVersionUID = 1L;

	private Long userId;
	
	@NotNull(message = "Description cannot be null.")
	@Pattern(regexp = "^[A-Za-z0-9? ]+$", message="Description is invalid, only alphanumeric and spaces allowed")
	private String description;
	
	@Enumerated(EnumType.STRING)
	private Enums.ExerciseType type;

	/**
	 * format: yyyy-MM-dd'T'HH:mm:ss
	 */
	@NotNull(message="start time cannot be null.")
	private Date startTime;

	/**
	 * in seconds
	 */
	@NotNull(message="duration cannot be null.")
	private Integer duration;

	/**
	 * in meters
	 */
	@NotNull(message="distance cannot be null.")
	private Integer distance;
	
	/**
	 * this is created to save endTime for exercise also,
	 * makes it easier to calculate and returns faster results
	 * when quering from database.
	 */
	private Date endTime;

	/**
	 * in kcal
	 */
	@NotNull(message="calories cannot be null.")
	private Integer calories;
	
	public Integer getCalories() {
		return calories;
	}

	public void setCalories(Integer calories) {
		this.calories = calories;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Enums.ExerciseType getType() {
		return type;
	}

	public void setType(Enums.ExerciseType type) {
		this.type = type;
	}

	public Date getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Date startTime) {
		if (duration !=null && endTime == null) {
			endTime = DateUtils.addSeconds(startTime, duration);
		}
		this.startTime = startTime;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		if (startTime!=null && endTime == null) {
			endTime = DateUtils.addSeconds(startTime, duration);
		}
		this.duration = duration;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Override
	public int compareTo(Exercise o) {
		return o.startTime.compareTo(this.startTime);
	}

}
