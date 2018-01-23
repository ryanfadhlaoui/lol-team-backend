package com.lolteam.dao;

import java.util.Optional;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.springframework.stereotype.Repository;

import com.lolteam.entities.SummonerEntity;

@Repository
public class SummonerDao extends GenericDao<SummonerEntity>{

	@Override
	protected Class<SummonerEntity> getClassType() {
		return SummonerEntity.class;
	}
	
	public Optional<SummonerEntity> getSummonerEntityByAccountId(long accountId) {
		try {
			return Optional.of(em.createNamedQuery("summoner.getSummonerEntityByAccountId", SummonerEntity.class)
			                     .setParameter("accountId", accountId)
			                     .getSingleResult());
		} catch (NoResultException nre) {
		} catch (NonUniqueResultException nure) {
		}
		//TODO HANDLE EXCEPTION
		return Optional.empty();
	}

}
