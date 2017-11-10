package rob.sportparser.event.card;

import rob.sportparser.Player;
import rob.sportparser.event.Event;

public class Card extends Event {
	
	private CardType cardType;
	
	/**
	 * 
	 * @param player
	 * @param time
	 * @param injuryTime
	 */
	public Card(Player player, int time, int injuryTime, CardType cardType) {
		this.setPlayer(player);
		this.setTime(time);
		this.setInjuryTime(injuryTime);
		this.setCardType(cardType);
	}

	/**
	 * @return the cardType
	 */
	public CardType getCardType() {
		return cardType;
	}

	/**
	 * @param cardType the cardType to set
	 */
	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	@Override
	public String toString() {
		String timeString = this.getTime()+"";
		if(this.getInjuryTime()!=0){
			timeString += this.getInjuryTime();
		}
		return this.getCardType() + " Card at " + timeString + " from " + this.getPlayer();
	}

}
