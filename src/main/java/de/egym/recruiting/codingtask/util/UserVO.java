package de.egym.recruiting.codingtask.util;

public class UserVO implements Comparable<UserVO>{
	
	public Long userId;
	public int points;
	
	@Override
	public int compareTo(UserVO o) {
		return o.points - this.points;
	}
	
	public UserVO(Long userId, int points) {
		this.userId = userId;
		this.points = points;
	}
	
}
