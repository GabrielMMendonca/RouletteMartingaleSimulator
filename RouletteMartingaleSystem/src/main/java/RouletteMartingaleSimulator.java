public class RouletteMartingaleSimulator {
    public static void main(String[] args) {
        int[] betAmounts = new int[]{2,10};
        String[] messageResults = new String[betAmounts.length];
        int betAmount = 0;
        int bet = 0;//red

        for (int j = 0; j < betAmounts.length; j++) {
            betAmount = betAmounts[j];
            int buyInMoney = 10;
            int playingMoney=0;
            int numberOfBets = 100;
            int testIterations = 10;
            boolean isSuccess = false;//martingale strategy is a success
            int totalMoney = 0;
            int successCounter = 0;
            int averageWinnings=0;

            do {
                playingMoney = buyInMoney;
                boolean wonPreviousBet = false;//won
                int outcome = 0;

                for (int i = 0; i < numberOfBets; i++) {
                    if (!wonPreviousBet && i != 0) {
                        betAmount *= 2;
                    } else {
                        betAmount = betAmounts[j];
                    }

                    if (playingMoney < betAmount) {
                        System.out.println("----------------------------------------------");
                        System.out.println("betAmount: " + betAmount);
                        System.out.println("Playing money: " + playingMoney);
                        System.out.println("Total money: " + totalMoney);
                        System.out.println("Martingale strategy failed !");
                        System.out.println("----------------------------------------------");
                        totalMoney=0;
                        successCounter=0;
                        isSuccess = false;
                        break;
                    }

                    playingMoney -= betAmount;

                    outcome = (int) Math.round(Math.random());
                    if (outcome == bet) {
                        wonPreviousBet = true;
                        playingMoney += betAmount * 2;
                    } else {
                        wonPreviousBet = false;
                    }

                    if (playingMoney == 0) {
                        System.out.println("Ran out of money !");
                        totalMoney=0;
                        isSuccess = false;
                        successCounter=0;
                        buyInMoney += 20;
                        break;
                    }

                    isSuccess = true;
                }

                if (isSuccess) {
                    successCounter++;
                }

                averageWinnings = (totalMoney + playingMoney)/testIterations;
                if (successCounter >= testIterations && averageWinnings < buyInMoney) {
                    playingMoney=0;
                    totalMoney=0;
                    successCounter = 0;
                    buyInMoney += 20;
                }

                totalMoney += playingMoney;
            } while (successCounter < testIterations);

            messageResults[j] = BuildMessage(numberOfBets,betAmount,buyInMoney,totalMoney,testIterations);
        }

        for (int m = 0; m < messageResults.length; m++) {
            System.out.println(messageResults[m].toString());
        }
    }

    private static String BuildMessage(int numberOfBets, int betAmount, int buyInMoney, int totalMoney, int testIterations)
    {
        StringBuilder result = new StringBuilder();
        result.append("************************************************\n");
        result.append("------------------------------------------------\n");
        result.append("Time spent: " + (numberOfBets * 5) / 60 + " hours (1 bet = 5 mins)\n");
        result.append("Bet amount: " + betAmount + " euros\n");
        result.append("BuyIn money: " + buyInMoney + " euros\n");
        result.append("Number of bets: " + numberOfBets + "\n");
        result.append("Average total Winnings: " + (totalMoney/testIterations) + " euros\n");
        result.append("------------------------------------------------\n");
        result.append("************************************************\n");
        return result.toString();
    }
}
