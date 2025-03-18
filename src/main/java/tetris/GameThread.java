package tetris;

public class GameThread extends Thread{

    private GameArea ga;
    private GameForm gf;
    private int level = 1;
    private int score;
    private int scorePerLevel;
    private int speedupPerLevel = 100;

    private int pause = 1000;
    private int speed = 100;
    public GameThread(GameArea ga, GameForm gf)
    {
        this.ga = ga;
        this.gf = gf;
    }
    @Override
    public void run() {
        while (true) {
            ga.spawnBlock();
            while(ga.moveBlockDown())
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(ga.isBlockOutOfBounds()){
                System.out.println("Gameover");
                break;
            }
            ga.moveBlockToBackground();
            score += ga.clearLines();
//            gf.updateScore(score);
//
//            int lvl = score/scorePerLevel + 1;
//            if(lvl > level)
//            {
//                level = lvl;
//                gf.updateLevel(level);
//                pause -=speedupPerLevel;
//            }
            }
    }

}
