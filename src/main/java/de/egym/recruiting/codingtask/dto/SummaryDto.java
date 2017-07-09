package de.egym.recruiting.codingtask.dto;

import de.egym.recruiting.codingtask.jpa.domain.Enums.ExerciseType;

public class SummaryDto {
	
	private ExerciseType type;
	private Long userId;
	private Integer count;
	private Integer duration;
	private Integer distance;
	private Integer calories;
	
	
	
	public SummaryDto(Long userId, Integer count, Integer duration, Integer distance, Integer calories) {
		super();
		this.userId = userId;
		this.count = count;
		this.duration = duration;
		this.distance = distance;
		this.calories = calories;
	}
	
	public SummaryDto(ExerciseType type, Object userId, Object count, Object duration, Object distance, Object calories) {
		super();
		this.setType(type);
		this.userId = Long.parseLong(userId.toString());
		this.count = Integer.parseInt(count.toString());
		this.duration = Integer.parseInt(duration.toString());
		this.distance = Integer.parseInt(distance.toString());
		this.calories = Integer.parseInt(calories.toString());
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public Integer getDistance() {
		return distance;
	}
	public void setDistance(Integer distance) {
		this.distance = distance;
	}
	public Integer getCalories() {
		return calories;
	}
	public void setCalories(Integer calories) {
		this.calories = calories;
	}

	public ExerciseType getType() {
		return type;
	}

	public void setType(ExerciseType type) {
		this.type = type;
	}
	
	
}
