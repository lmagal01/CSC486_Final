package tetris;

import java.awt.*;
import java.util.Random;

public class TetrisBlock {
    private Color color;
    private int[][] shape;
    private int x ,y;
    private int[][][] shapes;
    private int currentRotation;
    private Color[] availColors = {Color.green,Color.red,Color.blue, Color.orange, Color.cyan,Color.yellow, Color.MAGENTA};
    private Color lastColor = null;
    public TetrisBlock(int [][] shape)
    {
        this.shape = shape;
        this.color = getNewColor();
        initShapes();

    }

    private void initShapes()
    {
        shapes = new int[4][][];
        for(int i = 0; i < 4; i++)
        {
            int r = shape[0].length;
            int c = shape.length;

            shapes[i] = new int[r][c];

            for(int y = 0; y < r; y++)
            {
                for(int x = 0; x < c; x++)
                {
                    shapes[i][y][x] = shape[c-x-1][y];
                }
            }
            shape = shapes[i];
        }
    }

    public int[][] getShape()
    {
        return shape;
    }
    public Color getColor()
    {
        return color;
    }
    public Color getNewColor(){
        Random r = new Random();
        Color newColor = availColors[r.nextInt(availColors.length)];

        while(newColor.equals(lastColor))
        {
            newColor = availColors[r.nextInt(availColors.length)];
        }
        lastColor = newColor;
        return newColor;
    }
    public int getHeight(){return shape.length;}
    public int getWidth(){return shape[0].length;}
    public int getX(){return x;}
    public void setX(int newX){x = newX;}
    public int getY(){return y;}
    public void setY(int newY){y = newY;}

    public void moveDown(){y++;}
    public void moveLeft(){x--;}
    public void moveRight(){x++;}

    //make block appear randomly
    public void spawn(int gridWidth)
    {
        Random r = new Random();
        currentRotation = r.nextInt(shapes.length);
        shape = shapes[currentRotation];
        y = -getHeight();
        x = r.nextInt(gridWidth-getWidth());

        color = availColors[r.nextInt(availColors.length)];

    }

    //rotate blocks
    public void rotate()
    {
        currentRotation++;
        if(currentRotation > 3)
            currentRotation = 0;
        shape = shapes[currentRotation];
    }

    public int getBottomEdge(){return y + getHeight();}
    public int getLeftEdge(){return x;}
    public int getRightEdge(){return x + getWidth();}

    public void rotateBack() {
        // rotate 3 more times to revert to the original orientation
        this.rotate();
        this.rotate();
        this.rotate();
    }


}

