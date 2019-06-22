package humansvsmonsters;

import java.util.Scanner;

enum States {
    EMPTY, HUMAN, MONSTER
}

public class HumansVsMonsters {

    static final int BOARD_DIM = 20;
    static final int DIR_UP = 0;
    static final int DIR_RIGHT = 1;
    static final int DIR_DOWN = 2;
    static final int DIR_LEFT = 3;

    static FieldUnits[][] board = new FieldUnits[BOARD_DIM][BOARD_DIM];
    //static final int NUM_OF_HUMANS = 1;
    static final int NUM_OF_MONSTERS = (BOARD_DIM <= 5) ? 1 : (int) (BOARD_DIM * BOARD_DIM * 0.05);
    static int round;

    public static void initBoard() {

        for (int y = 0; y < BOARD_DIM; y++) {
            for (int x = 0; x < BOARD_DIM; x++) {
                board[y][x] = new FieldUnits(States.EMPTY);
            }
        }

    }

    public static void clearBoard() {

        for (int y = 0; y < BOARD_DIM; y++) {
            for (int x = 0; x < BOARD_DIM; x++) {
                board[y][x].setState(States.EMPTY);
            }
        }

    }

    public static void printBoard() {

        final int SPACE_PER_UNIT = FieldUnits.getStrLengthofUnit();
        StringBuilder sb = new StringBuilder(BOARD_DIM * BOARD_DIM * SPACE_PER_UNIT);
        System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("ROUND: " + round);
        for (int y = 0; y < BOARD_DIM; y++) {
            for (int x = 0; x < BOARD_DIM; x++) {
                sb.append(board[y][x].getState());
            }
            sb.append('\n');
        }

        System.out.println(sb.toString());
    }

    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        initBoard();
        Human human = new Human(BOARD_DIM / 2, BOARD_DIM / 2, DIR_DOWN);
        Monster[] monsters = new Monster[NUM_OF_MONSTERS];

        for (int i = 0; i < NUM_OF_MONSTERS; i++) {
            monsters[i] = new Monster((int) (Math.random() * BOARD_DIM), (int) (Math.random() * BOARD_DIM), DIR_RIGHT);
        }

        while (round < 50) {
            round++;
            clearBoard();
            for (int m = 0; m < NUM_OF_MONSTERS; m++) {
                monsters[m].move();
                int[] posYXMonster = monsters[m].getPosition();
                board[posYXMonster[0]][posYXMonster[1]].setState(States.MONSTER);
            }
            human.move();
            int[] posYXHuman = human.getPosition();
            board[posYXHuman[0]][posYXHuman[1]].setState(States.HUMAN);
            printBoard();
            System.out.println("PRESS ENTER");
            sc.nextLine();

        }

    }
}







class FieldUnits {

    static final String ANSI_WHITE = "\u001B[32m";
    static final String ANSI_GREEN_BG = "\u001B[42m";
    static final String ANSI_RED_BG = "\u001B[41m";
    static final String ANSI_RESET = "\u001B[0m";

    static final String EMPTY_FIELD = " .";
    static final String HUMAN_CHAR = ANSI_WHITE + ANSI_GREEN_BG + "\uD83E\uDDD9" + ANSI_RESET;
    static final String MONSTER_CHAR = ANSI_RED_BG + "\uD83E\uDDDB" + ANSI_RESET;

    private String state;

    
    public FieldUnits(States state) {
        this.state = convertStateToString(state);
    }

    String getState() {
        return this.state;
    }

    void setState(States state) {
        this.state = convertStateToString(state);
    }

    static String convertStateToString(States state) {
        String str = "";

        switch (state) {
            case HUMAN:
                str = HUMAN_CHAR;
                break;
            case MONSTER:
                str = MONSTER_CHAR;
                break;
            case EMPTY:
                str = EMPTY_FIELD;
                break;

        }

        return str;
    }

    static int getStrLengthofUnit() {
        return EMPTY_FIELD.length();
    }

}




class MovingType {

    static final int BOUNDARY = HumansVsMonsters.BOARD_DIM;
    protected int y;
    protected int x;
    protected int dir;

    protected void calculateNextStep(Object obj) {

        final int MOVE_UNITS = (obj instanceof Human) ? 2 : 1;

        this.dir = (this.dir + 4) % 4;
        switch (this.dir) {

            case 0:
                this.y -= MOVE_UNITS;
                break;
            case 1:
                this.x += MOVE_UNITS;
                break;
            case 2:
                this.y += MOVE_UNITS;
                break;
            case 3:
                this.x -= MOVE_UNITS;
                break;

        }
        y = (y + BOUNDARY) % BOUNDARY;
        x = (x + BOUNDARY) % BOUNDARY;

    }

    public void move() {}
    
    public int[] getPosition() {
        int[] pos = {this.y, this.x};
        return pos;
    }

}





class Human extends MovingType {

    private int stepCou;

    public Human(int y, int x, int dir) {
        this.y = y;
        this.x = x;
        this.dir = dir;
    }

    @Override
    public void move() {
        ++this.stepCou;
        if (this.stepCou % 3 == 0) {    //change direction randomly every 3. step
            this.dir += (int) (Math.random() * 3) - 1;
        }
        calculateNextStep(this);
    }

}






class Monster extends MovingType {

    public Monster(int y, int x, int dir) {
        super();
        this.y = y;
        this.x = x;
        this.dir = dir;
    }

    @Override
    public void move() {

        this.dir -= 1; //contant turn counterClockwise
        calculateNextStep(this);

    }

}
