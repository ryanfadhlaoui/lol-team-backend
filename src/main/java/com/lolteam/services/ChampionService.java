package com.lolteam.services;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lolteam.dao.ChampionDao;
import com.lolteam.entities.general.ChampionEntity;
import com.lolteam.framework.core.db.EntityCache;
import com.lolteam.services.riotApi.RiotApiService;

import net.rithms.riot.api.endpoints.static_data.dto.Champion;

@Service("championService")
public class ChampionService implements EntityService<Integer, ChampionEntity>{
	
	@Autowired
	private ChampionDao championDao;
	
	@Autowired
	private RiotApiService riotApiService;
	
	public ChampionService() {
	}
		
	@Transactional
	public Optional<ChampionEntity> smartLoadChampion(int championId) {
		Supplier<ChampionEntity> championSupplier = () -> {
			Champion champion = riotApiService.getChampion(championId).orElse(null);
			if(champion != null) {
				ChampionEntity championEntity = new ChampionEntity();
				championEntity.setChampionId(championId);
				championEntity.setChampionName(champion.getName());
				championDao.save(championEntity);
				return championEntity;
			}
			return null;
		};
		
		ChampionEntity championEntity = championDao.getChampionEntityByChampionId(championId)
				.orElseGet(championSupplier);
		return Optional.ofNullable(championEntity);
	}

	@Override
	public EntityCache<Integer, ChampionEntity> getCache() {
		Function<Integer, ChampionEntity> championLoaderFunction = championId -> smartLoadChampion(championId)
				.orElse(null);
		return new EntityCache<>(championLoaderFunction,
				(championId, champion) -> champion.getChampionId() == championId.intValue());

	}

}
