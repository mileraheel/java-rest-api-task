package de.egym.recruiting.codingtask.jpa.domain;

public final class Enums {

	public enum ExerciseType {
		RUNNING(2),
		CYCLING(2),
		SWIMMING(3),
		ROWING(2),
		WALKING(1),
		CIRCUIT_TRAINING(4),
		STRENGTH_TRAINING(3),
		FITNESS_COURSE(2),
		SPORTS(3),
		OTHER(1);
		
		private int points;
		
		private ExerciseType(int points) {
			this.points = points;
		}
		
		public int points() {
			return points;
		}
	}
	
	
	public enum APIMessages {
		SAVE_HAS_ID("save exercise request is coming with ID", "id"),
		CONFLIT_TIME("Conflicting Exercise exsists for the given time", "startTime"),
		
		UPDATE_HAS_NO_ID("updating exercise with no Id found", "id"),
		NO_EXERCISE_FOUND_WITH_ID("No Exercise with the given Id found.", "id"),
		
		UPDATE_USER_ID_CHANGED("UserId cannot be changed while updating", "userId"),
		UPDATE_TYPE_CHANGED("Exercice Type cannoe be changed while updated", "type"),
		
		DELETE_EXERCISE_NOT_FOUND("No Exercise found by the given Id", "id"),
		DELETE_SUCCESS("Exercise Deleted Successfuly", null),
		
		INVALID_TYPE_PROVIDED("Invalid Exercise Type given", "type"),
		INVALID_DATE_FORMAT("Invalid Date format provided", "date")
		
		;
		
		String value;
		String field;
		
		private APIMessages(String value, String field) {
			this.value = value;
			this.field = field;
		}
		
		public String value() {
			return this.value;
		}
		
		public String field() {
			return this.field;
		}
	}
}
