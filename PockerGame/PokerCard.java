package org.home.pocker;


public class PokerCard {
  private final String pokerNum;
  private final String suit;

  public PokerCard(String pokerNum, String suit){
      this.pokerNum = pokerNum;
      this.suit = suit;
  }

  public String getSuit(){return suit;}
  public int value(){
      return switch (pokerNum){
          case "J" ->  11;
          case "Q" ->  12;
          case "K" ->  13;
          case "A" ->  1;
          default -> Integer.parseInt(pokerNum);
      };
    }
  @Override
    public  String toString(){
      return pokerNum + suit;
  }
}
