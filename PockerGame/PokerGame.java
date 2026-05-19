package org.home.pocker;

import java.util.*;

public class PokerGame {
   List<PokerCard> deck = new ArrayList<>();
   List<PokerCard> community = new ArrayList<>();

   int pot = 0;
   int currentBet = 0;

   public PokerGame() {
      String[] suits = {"❤", "🔶", "♣", "♠"};
      String[] pokerNums = {"A", "2", "3", "4", "5", "6", "7",
              "8", "9", "10", "11", "12", "J", "Q", "K"};
      for (String suit : suits) {
         for (String num : pokerNums) {
            deck.add(new PokerCard(num, suit));
         }
      }

      Collections.shuffle(deck);
   }

   public PokerCard deal() {return deck.remove(0);}

   public List<PokerCard> community() {return community;}

   public void flop() {
      community.add(deal());
      community.add(deal());
      community.add(deal());
   }
   public void turn(){community.add(deal());}
   public void river() {community.add(deal());}

   //  Betting
   public int getPot(){return pot;}
   public  int getCurrentBet() {return currentBet;}

   public  void  placeBet(int amount){
      currentBet = amount;
      pot += amount;
   }
   public void call(int amount) { pot += amount;}
   public void  fold(){}
   public void resetBetting(){currentBet = 0;}


   //   hand evaluations
   public int score(List<PokerCard> hand) {
      int score = 0;
      Map<Integer, Integer> count = new HashMap<>();
      boolean flush = true;
      String suit = hand.get(0).getSuit();

      for (PokerCard c : hand) {
         count.put(c.value(), count.getOrDefault(c.value(), 0) + 1);
         if (!c.getSuit().equals(suit)) {
            flush = false;
         }
      }

      int pairs = 0;
      int three = 0;
      int four = 0;
      for (int v : count.values()) {
         if (v == 2) pairs++;
         if (v == 3) three++;
         if (v == 4) four++;
      }

      int max = hand.stream().mapToInt(PokerCard::value).max().orElse(0);

      //      hand ranking

      if (four == 1) score = 700 + max;
      else if (three == 1 && pairs >= 1) score = 600 + max;
      else if (flush) score = 500 + max;
      else if (isStraight(hand)) score = 400 + max;
      else if (three == 1) score = 300 + max;
      else if (pairs >= 2) score = 200 + max;
      else if (pairs == 1) score = 100 + max;
      else score = max;

      return score;
   }

   boolean isStraight(List<PokerCard> hand) {
      List<Integer> values = hand.stream().map(PokerCard::value).sorted().toList();
      for (int i = 1; i < values.size(); i++) {
         if (values.get(i) != values.get(i - 1) + 1) return false;
      }
      return true;

   }

public String winner(List<PokerCard> player , List<PokerCard> dealer){
      int p = score(player);
      int d = score(dealer);

      if(p > d) return "Player Wins +$" + pot;
      if (d > p) return "Dealer Wins";
      return "Draw!";
}
}
