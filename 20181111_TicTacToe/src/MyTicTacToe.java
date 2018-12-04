import javax.swing.plaf.ColorUIResource;
import java.util.Scanner;

public class MyTicTacToe {

    //definim proprietati Tic Tac Toe
    static char SYMBOL_X = 'X';
    static char SYMBOL_0 = '0';
    //definire si instantiere vector ce contine simbolurile folosite in joc
    static char[] SYMBOLS = new char[]{SYMBOL_X, SYMBOL_0};

    //definire doi jucatori
    Player player1;
    Player player2;

    //definire matrice cu rol de board/tabla de joc
    char [][] board;
    //modificare tip de date pentru tabela de joc
    //String[][] board;
    //definire dimensiune board
    int size;

    //creare constructor(cu 3 parametri), pentru a initializa elementele jocului
    MyTicTacToe(int size, String player1, String player2){
        //initializare dimensiune matrice
        this.size = size;
        //instantiere matrice patratica board, de dimensiune size * size
        this.board = new char[size][size];
        //instantiere obiect player1, apeland constructor din clasa Player, simbolul este X
        this.player1 = new Player(player1, SYMBOL_X);
        //instantiere obiect player2, apeland constructor din clasa Player, simbolul este 0
        this.player2 = new Player(player2, SYMBOL_0);
        //apelare metoda de initializare tabla de joc cu primele numere, dar transformate in caracter
        initBoard();
    }
    //creare metoda de initializare board/tabla de joc
    public void initBoard(){
        //parcurgere matrice patratica de dimensiune size*size
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                //atribuire elemente in board, de tip caracter , insa cu semnificatii numerice
                board[i][j] = (char)(48 + size * i + 1 +j);
            }
        }
    }
    //creare metoda de afisare tabla de joc
    public void showBoard(){
        //parcurgere linie-coloana matrice patratica de dimensiune size*size
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                //afisare elemente linie cu linie
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
    //creare metoda publica de citire a unei mutari, pentru un jucator
    public int readMove(Player player){
        //definire obiect de tip Scanner si instantiere pentru a citi o valoare primitiva de la consola
        Scanner scanner = new Scanner(System.in);
        System.out.println("Jucatorul " + player.name + " muta:");
        //citire mutare
        int move = scanner.nextInt();
        // determinare coordonate de mutare pentru player-ul curent
        int i = (move - 1) / size;
        int j = (move - 1) % size;

        //testare existenta simbol in board/tabla de joc, locatia de coordonate (i,j), cu bucla while
        while(board[i][j] == SYMBOL_X || board[i][j] == SYMBOL_0){
            //afisare mesaj de atentionare si repetare pasi de citire mutare
            System.out.println("Mutare nevalida, incearca din nou:");
            move = scanner.nextInt();
            // determinare coordonate de mutare pentru player-ul curent
            i = (move - 1) / size;
            j = (move - 1) % size;

        }
        //returneaza valoarea/mutarea pentru un jucator
        return move;
    }
    //creare metoda publica pentru a realiza mutarea unui jucator
    public void makeMove(Player player, int move){
        //definire si calculare indice linie
        int i = (move - 1) / size;
        //definire si calculare indice coloana
        int j = (move - 1) % size;
        //punere simbol specific jucatorului curent, pe tabela de joc la coordonate i, j - a facut o mutare
        board[i][j] = player.symbol;
    }

    public boolean isWinLine(Player player, int line){
        boolean win = true;
        int i = 0;
        while(i < size && win){
            if (board[line][i] != player.symbol){
                win = false;
            }
            i++;
        }
        return win;
    }

    public boolean isWinCol(){
        return false;
    }

    public boolean isWinDiag1(Player player){

        boolean win = true;
        int i = 0;
        while(i < size){
            if(board[i][i] != player.symbol){
                win = false;
            }
            i++;
        }
        return win;
    }

    public boolean isWinDiag2(){
        return false;
    }

    public boolean isWin(Player player, int move){
        // determine coordinates
        int i = (move - 1) / size;
        int j = (move - 1) % size;

        // test win
        boolean win = false;
        win = isWinLine(player, i);
        if(!win){
            win = isWinCol();
        }
        if(!win && i == j){
            win = isWinDiag1(player);
        }
        if(!win && i == (size - 1 - j)){
            win = isWinDiag2();
        }

        return win;
    }

    public void playGame(){

        int nrMoves = 0;
        Player currentPlayer = player1;
        boolean win = false;
        while(nrMoves < (size * size) && !win){

            int move = readMove(currentPlayer);
            makeMove(currentPlayer, move);
            nrMoves++;

            showBoard();
            // not testing for sirst 4 moves on 3 x 3
            if(nrMoves >= 2 * size - 1){
                win = isWin(currentPlayer, move);
            }
            if(!win){

                // switch players
                if(currentPlayer == player1){
                    currentPlayer = player2;
                }else{
                    currentPlayer = player1;
                }
            }

        }
        if(win == false){
            System.out.println("Remiza!");
        } else {
            System.out.println("Castigator: " + currentPlayer.name + " !!!");
        }

    }

}
