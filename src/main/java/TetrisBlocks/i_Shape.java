package TetrisBlocks;

import tetris.TetrisBlock;

public class i_Shape extends TetrisBlock {
    public i_Shape() {
        super(new int[][]{{1,1,1,1}});
    }

    @Override
    public void rotate()
    {
        super.rotate();
        if(this.getWidth() == 1)
        {
            this.setX(this.getX()+1);
            this.setY(this.getY()-1);
        }
        else
        {
            this.setX(this.getX()-1);
            this.setY(this.getY()+1);
        }
    }
}
