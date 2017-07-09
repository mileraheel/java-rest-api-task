package de.egym.recruiting.codingtask.rest;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.egym.recruiting.codingtask.jpa.domain.Enums.ExerciseType;
import de.egym.recruiting.codingtask.dto.SummaryDto;
import de.egym.recruiting.codingtask.jpa.domain.Exercise;
import io.swagger.annotations.Api;

@Path("/api/v1/exercise")
@Api(value = "Exercise Service")
public interface ExerciseService {

	/**
	 * Get the exercise for a given exerciseId.
	 *
	 * @param exerciseId
	 *            id to search
	 * @return the exercise for the given exerciseId
	 */
	@GET
	@Path("/{exerciseId}")
	@Nonnull
	@Produces(MediaType.APPLICATION_JSON)
	Exercise getExerciseById(@Nonnull @PathParam("exerciseId") Long exerciseId);

	/**
	 * Get the exercises with the given description.
	 *
	 * @param description
	 *            description to search
	 * @return the exercises for the given description
	 */
	@GET
	@Path("/")
	@Nonnull
	@Produces(MediaType.APPLICATION_JSON)
	List<Exercise> getExerciseByDescription(@Nullable @QueryParam("description") String description);
	
	/**
	 * Gets all the exercises by User id, also offers
	 * optional type and date in format 'YYYY-mm-dd'
	 * @param userId required userId to search
	 * @param type optional parameter to filter by type
	 * @param date optional param, format 'YYYY-mm-dd' 
	 * 			to filter data by that provided day.
	 * @return list of Exercises matching the criteria
	 */
	@GET
	@Path("/user/{userId}")
	@Nonnull
	@Produces(MediaType.APPLICATION_JSON)
	List<Exercise> getExerciseByUserId(@Nonnull @PathParam("userId") Long userId, 
			@Nullable @QueryParam("type") ExerciseType type,
			@Nullable @QueryParam("date") String date);
	
	
	/**
	 * This method persists the exercise, should'nt have id 
	 * while saving, also similar exercise must not exist in DB
	 * @param exercise a complete filled object other than the ID
	 * @return persisted exercise object with auto generated ID
	 */
	@POST
	@Path("/")
	@Nonnull
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Exercise save(@Nonnull Exercise exercise);
	
	/**
	 * This method updates the existing exercise, id 
	 * should not be null, and id should be existing 
	 * in the db. type and starttime should not change
	 * @param exercise pre existing exercise to be updated.
	 * @return updated exercise from the DB.
	 */
	@PUT
	@Path("/")
	@Nonnull
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Exercise update(@Nonnull Exercise exercise);
	
	/**
	 * Exercise ID to be deleted.
	 * @param exerciseId id to be deleted.
	 * @return success message.
	 */
	@DELETE
	@Path("/{exerciseId}")
	@Nonnull
	String deleteExerciseById(@Nonnull @PathParam("exerciseId") Long exerciseId);
	
	/**
	 * this method provides ranking for last 
	 * 28 days for all the provided users.
	 * @param users list of users to get rank among.
	 * @return rankwise sorted users list.
	 */
	@GET
	@Path("/ranking")
	@Nonnull
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	List<Long> getRanking(@Nonnull @QueryParam("users") List<Long> users);
	
	/**
	 * Summary by type for all the users.
	 * @param type type to get the summary for.
	 * @return List<SummaryDto> summary dto.
	 */
	@GET
	@Path("/summary/{type}")
	@Nonnull
	@Produces(MediaType.APPLICATION_JSON)
	List<SummaryDto> getSummaryByType(@Nonnull @PathParam("type")ExerciseType type);
}
