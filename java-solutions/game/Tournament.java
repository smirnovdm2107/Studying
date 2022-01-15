package game;

import java.awt.*;

public class Tournament {
    private final Player[] players;
    private Board board;
    Tournament(Board board,  Player... players) {
        if (players.length < 1) {
            throw new AssertionError("There is a lack of players!");
        }
        this.players = players;
        this.board = board;
    }


    public void play(boolean log) {
        int[] tournamentTable = new int[players.length];
        String[][] extendedTournamentTable = new String[players.length][players.length];
        for (int i = 0; i < players.length; i++) {
            for (int j = 0; j < players.length; j++) {
                if (i != j) {
                    int result = new TwoPlayerGame(board, players[i], players[j]).play(false);
                    switch (result) {
                        case 1:
                            tournamentTable[i] += 2;
                            extendedTournamentTable[i][j] = Integer.toString(i + 1);
                            break;
                        case 2:
                            tournamentTable[j] += 2;
                            extendedTournamentTable[i][j] = Integer.toString(j + 1);
                            break;
                        case 0:
                            tournamentTable[i] += 1;
                            tournamentTable[j] += 1;
                            extendedTournamentTable[i][j] = Integer.toString(0);
                            break;
                        default:
                            throw new AssertionError("Impossible game result!");
                    }

                } else {
                    extendedTournamentTable[i][j] = "-";
                }
                board.clear();
            }

        }

        if (log) {
            StringBuilder sb = new StringBuilder(" ");
            int numMaxLength = Integer.toString(players.length).length();
            numStringIncreaser(sb, numMaxLength, 0);
            for (int i = 0; i < players.length; i++) {
                numStringIncreaser(sb, numMaxLength, Integer.toString(i + 1).length());
                sb.append(i + 1).append(" ");
            }

            sb.append(System.lineSeparator());
            for (int i = 0; i < players.length; i++) {
                numStringIncreaser(sb, numMaxLength, Integer.toString(i + 1).length());
                sb.append(i + 1);
                sb.append(" ");
                for (int j = 0; j < players.length; j++) {
                    numStringIncreaser(sb, numMaxLength, 1);
                    sb.append(extendedTournamentTable[i][j]).append(" ");
                }
                sb.append(System.lineSeparator());
            }
            System.out.println(sb);
        }
        for (int i = 0; i < tournamentTable.length; i++) {
            System.out.println("Player " + (i + 1) + ": " + tournamentTable[i]);
        }

    }

    private void numStringIncreaser(StringBuilder sb, int numMaxLength, int numLength) {
        sb.append(" ".repeat(Math.max(0, numMaxLength - numLength)));

    }

}
