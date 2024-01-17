class WythoffOrdi {
    /**
     * Jeu de Wythoff, deux joueurs s'affrontent sur un plateau de jeu de taille n*n. 
     * Chaque joueur possède un pion, le but du jeu est de déplacer son pion sur la case en bas à gauche du plateau.
     * Le joueur A commence, il choisit une direction (bas, gauche ou diagonale) et un nombre de cases à parcourir.
     * Le joueur B joue ensuite, et ainsi de suite jusqu'à ce qu'un des deux joueurs atteigne la case en bas à gauche.
     */
    public static void main(String[] args) {
        //Tests unitaires
        testChangePlayer();
        testInitBoardPawn();
        testInitBoard();



        // Initialisation des variables
        String player = SimpleInput.getString("Saississez votre nom de joueur : \n");
        String computer = "Ordinateur";
        int boardSize = 0;
        int numberOfCases = 0;
        boolean moved = false;
        

        //Définition du joueur courant et du joueur qui commence
        String currentPlayer = "x";
        char begginer;

        do{
            begginer = SimpleInput.getChar(computer + " commence ? (o/n) \n");
            if (begginer == 'o'){
                currentPlayer = player;
            } else if (begginer == 'n') {
                currentPlayer = computer;
            }
        } while(begginer != 'n' && begginer != 'o');
        
        

        // Vérification que la taille du plateau est comprise entre 1 et 50
        do {
            boardSize = SimpleInput.getInt("Saississez la taille du plateau de jeu : \n");
        } while (boardSize <= 0 || boardSize > 50);

        // Initialisation du plateau de jeu
        int[][] board = initBoard(boardSize);
        board = putWinningPositions(board);
        

        // Boucle de jeu
        while (board[board.length-1][0] != 1) {
            currentPlayer = changePlayer(currentPlayer, player, computer);
            displayBoard(board);
            System.out.println("\n\nC'est à " + currentPlayer + " de jouer ! \n");
            // Déplacement du pion au tour de l'ordi
            if (currentPlayer == computer){
                moved = false;
                for (int i = board.length - 1; i >= 0; i--) {
                    for (int j = board[i].length - 1; j >= 0 && !moved; j--) {
                        if (board[i][j] == 1) {
                            // Déplacement du pion vers le bas
                            for (int k = i + 1; k < board.length && !moved; k++) {
                                if (board[k][j] == 2) {
                                    board[i][j] = 0;
                                    board[k][j] = 1;
                                    moved = true;
                                }
                            }
                        
                            // Déplacement du pion vers la gauche
                            for (int l = j - 1; l >= 0 && !moved; l--) {
                                if (board[i][l] == 2) {
                                    board[i][j] = 0;
                                    board[i][l] = 1;
                                    moved = true;
                                }
                            }
                        
                            // Déplacement du pion en diagonale (bas gauche)
                            int k = i + 1;
                            for (int l = j - 1; l >= 0 && k < board.length && !moved; l--, k++) {
                                if (board[k][l] == 2) {
                                    board[i][j] = 0;
                                    board[k][l] = 1;
                                    moved = true;
                                }
                            }
                        }
                    }

                }
                // Si le pion n'a pas pu être déplacé, on le déplace vers la gauche de une case
                if(!moved){
                    for (int i = board.length - 1; i >= 0 && !moved; i--) {
                        for (int j = board[i].length - 1; j >= 0 && !moved; j--) {
                            if (board[i][j] == 1) {
                                board[i][j-1] = 1;
                                board[i][j] = 0;
                                moved = true;
                            }
                        }
                    }
                }
            

            } else {
                char direction;
                do {
                    direction = SimpleInput.getChar("Dans quelle direction souhaitez vous déplacer le pion ? (b(bas), g(gauche), d(diagonale))\n");
                } while (direction != 'b' && direction != 'g' && direction != 'd');
                
            
                // Déplacement du pion
                if (direction == 'b') {
                    moved = false;
                    for (int i = 0; i < board.length; i++) {
                        for (int j = 0; j < board[i].length && !moved; j++) {
                            if (board[i][j] == 1) {
                                while (!moved) {
                                    numberOfCases = SimpleInput.getInt("Combien de cases souhaitez vous parcourir ? \n");
                                    // Vérification que le nombre de cases à parcourir ne dépasse pas la taille du plateau
                                    int newRow = i + numberOfCases;
                                    if (newRow >= 0 && newRow < board.length) {
                                        board[i][j] = 0;
                                        board[newRow][j] = 1;
                                        moved = true;
                                    } else {
                                        System.out.println("Déplacement impossible : le nombre que vous avez rentré dépasse la limite du plateau.");
                                    }  
                                }
                            }
                        }
                    }
                } else if (direction == 'g') {
                    moved = false;
                    for (int i = 0; i < board.length; i++) {
                        for (int j = 0; j < board[i].length && !moved; j++) {
                            if (board[i][j] == 1) {
                                while (!moved) {
                                    numberOfCases = SimpleInput.getInt("Combien de cases souhaitez vous parcourir ? \n");
                                    // Vérification que le nombre de cases à parcourir ne dépasse pas la taille du plateau
                                    int newCol = j - numberOfCases;
                                    if (newCol >= 0 && newCol < board[i].length) {
                                        board[i][j] = 0;
                                        board[i][newCol] = 1;
                                        moved = true;
                                    } else {
                                        System.out.println("Déplacement impossible : le nombre que vous avez rentré dépasse la limite du plateau.");
                                    }
                                }
                            }
                        }
                    }
                } else if (direction == 'd') {
                    moved = false;
                    for (int i = 0; i < board.length; i++) {
                        for (int j = 0; j < board[i].length && !moved; j++) {
                            if (board[i][j] == 1) {
                                while (!moved) {
                                    numberOfCases = SimpleInput.getInt("Combien de cases souhaitez vous parcourir ? \n");
                                    // Vérification que le nombre de cases à parcourir ne dépasse pas la taille du plateau
                                    int newRow = i + numberOfCases;
                                    int newCol = j - numberOfCases;
                                    if (newRow >= 0 && newRow < board.length && newCol >= 0 && newCol < board[i].length) {
                                        board[i][j] = 0;
                                        board[newRow][newCol] = 1;
                                        moved = true;
                                    } else {
                                        System.out.println("Déplacement impossible : le nombre que vous avez rentré dépasse la limite du plateau.");
                                    }
                                }
                            }
                        }
                    }
                }
            }
           


            
        }
        displayBoard(board);
        // Affichage du gagnant
        if (currentPlayer == player) {
            System.out.println("\n\nC'est " + currentPlayer + " qui a gagné !");
        } else {
            System.out.println("\n\nC'est " + currentPlayer + " qui a gagné !");
        }



    }

    /**
     * Change le joueur courant
     * @param currentPlayer Le joueur courant
     * @param playerA Le nom du premier joueur
     * @param computer représente l'ordinateur
     * @return le nouveau joueur courant
     */

    static String changePlayer(String currentPlayer, String player, String computer) {
        if (currentPlayer == player) {
            currentPlayer = computer;
        } else {
            currentPlayer = player;
        }

        return currentPlayer;
    }

    /**
     * Affiche le plateau de jeu
     * @param board le plateau de jeu en mettant en exergue les positions gagnantes
     */
    static void displayBoard(int[][] board) {
       
        System.out.println();
        for (int i = 0; i < board.length; i++) {
            if ((board.length-1-i >= 10)){
                System.out.print((board.length-1-i) + "");
            } else{
                System.out.print((board.length-1-i) + " ");
            }
            
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 1){
                    System.out.print("| o ");
                } else if(board[i][j] == 2) {
                    System.out.print("| x ");
                } else {
                    System.out.print("|  " + " ");
                }
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.print(" ");
        for (int i = 0; i < board[0].length; i++) {
            if(i < 10){
                System.out.print("   " + i );
            } else{
                System.out.print("  " + i );
            }
            
        }

    }


    /**
     * Initialise le plateau de jeu
     * @param size la taille du plateau
     * @return le plateau de jeu
     */
    static int[][] initBoard(int size) {
        if (size <= 0){
            size = 8;
        }
        int pawnWidth = (int) Math.floor((Math.random() * (size - 2)) + 1);
        int pawnLength = (int) Math.floor((Math.random() * (size - 2)) + 1);

        int[][] board = new int[size][size];
        board[pawnWidth][pawnLength] = 1;

        return board;
    }

    /**
     * Teste la fonction changePlayer
     * @param currentPlayer Le joueur courant
     * @param playerA Le nom du premier joueur
     * @param playerB Le nom du second joueur
     * @param newPlayer Le nom de nouveau joueur qui doit être celui qui n'est pas le joueur courant
     * @param result Le résultat attendu
     */
    static void testCaseChangePlayer(String currentPlayer, String player, String computer, String newPlayer, boolean result){
        newPlayer = changePlayer(currentPlayer, player, computer);
        if (currentPlayer != newPlayer){
            result = true;
        }
        System.out.println("Le joueur a bien changé : " +  result  +"\n");
    }

    /**
     * Appelle différents appels de test de testCaseChangePlayer
     */
    static void testChangePlayer(){
        System.out.println("***Test changePlayer***");
        testCaseChangePlayer("X", "X", "O", "O", true);
        testCaseChangePlayer("O", "O", "X", "X", true);
        testCaseChangePlayer("Malki", "Malki", "Louis", "Louis", true);
    }

    static void testInitBoardPawn(){
        System.out.println("***Test initBoard (génération du pion)***");
        int[][] board = initBoard(10);
        int pawnWidth = 0;
        int pawnLength = 0;
        boolean pawnFound = false;
        for (int i = 0; i < board.length && !pawnFound; i++) {
            for (int j = 0; j < board[i].length && !pawnFound; j++) {
                if (board[i][j] == 1){
                    pawnWidth = i;
                    pawnLength = j;
                    pawnFound = true;
                }
            }
        }
        if (pawnWidth >= 1 && pawnWidth <= 8 && pawnLength >= 1 && pawnLength <= 8){
            System.out.println("Le pion a bien été placé !\n");
        } else {
            System.out.println("Le pion n'a pas été placé !\n");
        }
    }

    /**
     * Appelle différents appels de test de testCasInitBoard
     */
    static void testInitBoard(){
        System.out.println("***Test initBoard***");
        testCasInitBoard(10, true);
        testCasInitBoard(8, true);
        testCasInitBoard(0, true);
        testCasInitBoard(4, true);
        testCasInitBoard(5, true);
        testCasInitBoard(-2, true);
        testCasInitBoard(-1000, true);
    }

    /**
     * Teste la fonction initBoard
     * @param size La taille du plateau de jeu
     * @param res Le résultat attendu
     */
    static void testCasInitBoard(int size, boolean res){
        int[][] board = initBoard(size);
        System.out.print("Avec une taille de " + size + " : ");
        if (board.length == size && board[0].length == size){
            res = true;
        }
        System.out.println("Le plateau a bien été initialisé ! : " + res + "\n");
    }
    
    /**
     * Place les positions gagnantes sur le plateau de jeu
     * @param board le plateau de jeu
     * @return le plateau de jeu avec les positions gagnantes
     */
    static int[][] putWinningPositions(int[][] board) {
        int maxSize = board.length;
        double phi = (1 + Math.sqrt(5)) / 2; // Nombre d'or
    
        for (int i = 0; i < maxSize; i++) {
            int x = (int) Math.floor(i * phi);
            int y = (int) Math.floor(i * phi * phi);
    
            if (x < maxSize && y < maxSize) {
                board[x][y] = 2;
                board[y][x] = 2;
            }
        }
    
        // Inverser symétriquement les croix.
        for (int i = 0; i < maxSize / 2; i++) {
            for (int j = 0; j < maxSize; j++) {
                int temp = board[i][j];
                board[i][j] = board[maxSize - 1 - i][j];
                board[maxSize - 1 - i][j] = temp;
            }
        }
    
        return board;
    }


  
}
