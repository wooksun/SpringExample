package com.tjoeun.vo;

public class CardVO {
	
	private String consumerId;
	private String amount;
	
	//	getter & setter
	public String getConsumerId() {
		return consumerId;
	}
	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	//	toString()
	@Override
	public String toString() {
		return "CardVO [consumerId=" + consumerId + ", amount=" + amount + "]";
	}
	
	
	
}
