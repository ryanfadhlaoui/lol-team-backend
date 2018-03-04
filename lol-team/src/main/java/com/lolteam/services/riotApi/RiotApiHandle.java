package com.lolteam.services.riotApi;

import java.util.Optional;

import com.lolteam.utils.exceptions.RiotApiHandleException;

import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.request.ratelimit.RateLimitException;

class RiotApiHandle {

	/**
	 * Handles native riot api.
	 * 
	 * @return the result if everything goes well,
	 * if a RateLimiteException is thrown, the method will try again went possible.
	 * Otherwise returns null*/
	public <R> Optional<R> execute(RiotSupplier<R> supplier) {
		try {
			return Optional.of(supplier.get());
		} catch (RiotApiException e) {
			return handleRiotApiException(e, supplier);
		}
	}
	
	private <R> Optional<R> handleRiotApiException(RiotApiException e, RiotSupplier<R> supplier) {
		if(e instanceof RateLimitException) {
			RateLimitException rateExeption = (RateLimitException) e;
			System.out.println("Need to reconnect after "+rateExeption.getRetryAfter()+  " s");
			try {
				Thread.sleep(rateExeption.getRetryAfter() * 1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			return execute(supplier);
		}
		if(e.getErrorCode() == RiotApiException.DATA_NOT_FOUND) {
			return Optional.empty();
		}
		if(e.getErrorCode() == RiotApiException.SERVER_ERROR) {
			return Optional.empty();
		}
		e.printStackTrace();
		//todo log error
		System.out.println("Error "+ e.getMessage());
		throw new RiotApiHandleException(e.getMessage());
	}
	
	

}
