package tetris;

public class GameThread extends Thread{

    private GameArea ga;
    private GameForm gf;
    private int level = 1;

    private int score;
    private int scorePerLevel;
    private int speedupPerLevel = 100;

    private int pause = 1000;
    private int speed = 1000;
    private boolean running = true;

    public GameThread(GameArea ga, GameForm gf)
    {
        this.ga = ga;
        this.gf = gf;
    }
    @Override
    public void run() {
        while (running) {
            ga.spawnBlock();
            while(ga.moveBlockDown())
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }//ends
            if(ga.isBlockOutOfBounds()){
                System.out.println("Gameover");
                gf.restartGame();
                running = false;
                break;
            }
            ga.moveBlockToBackground();
            score += ga.clearLines();

            }
    }

    //increases speed here
    public void increaseSpeed() {
        int minSpeed = 200;
        if (speed > minSpeed) {
            speed -= minSpeed; // Increase speed by reducing delay
            System.out.println("New Speed: " + speed + "ms per move");
        } else {
            System.out.println("Maximum speed reached!");
        }
    }

}
