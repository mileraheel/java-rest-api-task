package de.egym.recruiting.codingtask.jpa.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

import de.egym.recruiting.codingtask.dto.SummaryDto;
import de.egym.recruiting.codingtask.jpa.domain.Enums.ExerciseType;
import de.egym.recruiting.codingtask.jpa.domain.Exercise;
import de.egym.recruiting.codingtask.util.ExerciseUtilities;

@Transactional
public class ExerciseDaoImpl extends AbstractBaseDao<Exercise>implements ExerciseDao {

	private ExerciseUtilities utilities;
	
	@Inject
	ExerciseDaoImpl(final Provider<EntityManager> entityManagerProvider, ExerciseUtilities utilities) {
		super(entityManagerProvider, Exercise.class);
		this.utilities = utilities;
	}
	
	public Long findExistingExerciseByTimeAndUser(Date startTime, Date endTime, Long userId) {
		try {
			return (Long) getEntityManager()
					.createQuery("SELECT e.id FROM Exercise e WHERE "
							+ "( (e.startTime <= :startTime AND e.endTime >= :startTime) OR "
							+ "(e.startTime <= :endTime AND e.endTime >= :endTime) ) AND "
							+ "e.userId = :userId")
					.setParameter("startTime", startTime)
					.setParameter("endTime", endTime)
					.setParameter("userId", userId)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Nonnull
	@Override
	public List<Exercise> findByDescription(@Nullable String description) {
		if (description == null) {
			return Collections.emptyList();
		}
		description = description.toLowerCase();
		try {
			return getEntityManager()
					.createQuery("SELECT e FROM Exercise e WHERE LOWER(e.description) = :description")
					.setParameter("description", description)
					.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
	
	@Nonnull
	@Override
	public List<Exercise> findByUserId(Long userId, @Nullable ExerciseType type, @Nullable Date date) {
		try {
			String queryString = "SELECT e FROM Exercise e WHERE e.userId = :userId ";
			if (type != null) {
				queryString += " and e.type = :type";
			}
			if (date!=null) {
				queryString += " and e.startTime >= :start and e.startTime <=:end";
			}
			
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("userId", userId);
			
			if (type != null) {
				query.setParameter("type", type);
			}
			if (date!=null) {
				Date[] array = utilities.getStartEndTimeForDate(date);
				query.setParameter("start", array[0]);
				query.setParameter("end", array[1]);
			}
			return query.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
	
	@Nonnull
	@Override
	public List<Exercise>getExercisByUserAndUptoDate(Long userId, Date uptoDate) {
		try {
			//and e.endTime <= :date
			//Date date = new Date();
			return getEntityManager()
					.createQuery("SELECT e FROM Exercise e WHERE e.userId = :userId "
							+ "and e.startTime >= :uptoDate  order by e.startTime desc")
					//.setParameter("date", date)
					.setParameter("userId", userId)
					.setParameter("uptoDate", uptoDate)
					.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
	
	@Nonnull
	@Override
	public List<SummaryDto> getExercisesByType(ExerciseType type, Date uptoDate) {
		List<SummaryDto> summaryList = new ArrayList<>();
		List<Object[]> list = getEntityManager()
				.createQuery("SELECT e.userId, count(e.id), sum(e.duration), sum(e.distance), sum(calories) "
						+ "FROM Exercise e WHERE e.type = :type "
						+ "and e.startTime >= :uptoDate "
						+ "group by e.userId order by sum(e.duration) desc")
				.setParameter("type", type)
				.setParameter("uptoDate", uptoDate)
				
				.getResultList();
		for (Object[] array : list) {
			SummaryDto summary = new SummaryDto(type, array[0], array[1], array[2], array[3], array[4]);
			summaryList.add(summary);
		}
		return summaryList;
	}
}
