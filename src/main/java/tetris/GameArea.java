package tetris;

import TetrisBlocks.*;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GameArea extends JPanel {

    private int gridRows;
    private int gridColumns;
    private int gridCellSize;
    private Color[][] background;

    private TetrisBlock block;
    private TetrisBlock[] blocks;
    private boolean useColor = false; //for color option


    public GameArea(int columns, int rows) {
        // Set a fixed preferred size for the game area
        this.setPreferredSize(new Dimension(300, 400)); // Ensure it fits within placeholder
        this.setBackground(Color.BLACK);
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 5));

        // Initialize grid properties
        this.gridColumns = columns;
        this.gridRows = rows;
        // Slightly extend beyond the grid for a clean border
        int panelWidth = gridColumns * gridCellSize + 20; // Extra padding
        int panelHeight = gridRows * gridCellSize + 20;

        // Calculate grid cell size dynamically
        int width = getPreferredSize().width;
        int height = getPreferredSize().height;
        gridCellSize = Math.min(width / gridColumns, height / gridRows);

        background = new Color[gridRows][gridColumns];
        blocks = new TetrisBlock[]{new i_Shape(),new J_Shape()
                ,new L_Shape(), new O_Shape(), new S_Shape(), new T_Shape(), new Z_Shape()};
        //background[0][0] = Color.blue;
        // spawnBlock();
    }

    public void moveBlockToBackground(){
        int[][] shape = block.getShape();
        int h = block.getHeight();
        int w = block.getWidth();

        int xPos = block.getX();
        int yPos = block.getY();

        Color color = block.getColor();

        for(int r = 0; r < h; r++)
        {
            for(int c = 0; c < w; c++)
            {
                if(shape[r][c] == 1)
                {
                    background[r + yPos][c + xPos] = color;
                }
            }
        }
    }

    public void spawnBlock() {
        Random r = new Random();
        block = blocks[r.nextInt(blocks.length)];
        block = new TetrisBlock(block.getShape());

        // Ensure the block spawns within the grid width
        int maxSpawnX = gridColumns - block.getWidth();
        int spawnX = Math.max(0, r.nextInt(maxSpawnX + 1)); // Ensure valid X position

        block.setX(spawnX);

        // Spawn block **above** the grid to give a falling effect
        int spawnY = -block.getHeight(); // Place block above the first row
        block.setY(spawnY);

        repaint();
    }


    private void drawBackground(Graphics g, int xOffset, int yOffset) {
        for (int r = 0; r < gridRows; r++) {
            for (int c = 0; c < gridColumns; c++) {
                if (background[r][c] != null) {
                    int x = xOffset + c * gridCellSize;
                    int y = yOffset + r * gridCellSize;

                    Color drawColor = useColor ? background[r][c] : Color.GRAY;
                    drawGridSquare(g, drawColor, x, y);
                }
            }
        }
    }

    private void drawBlock(Graphics g, int xOffset, int yOffset) {
        if (block == null) return;

        int h = block.getHeight();
        int w = block.getWidth();
        Color c = useColor ? block.getColor() : Color.GRAY;
        int[][] shape = block.getShape();

        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                if (shape[row][col] == 1) {
                    int blockX = block.getX() + col;
                    int blockY = block.getY() + row;

                    // Only draw if the block is within the visible grid (blockY >= 0)
                    if (blockY >= 0) {
                        int x = xOffset + blockX * gridCellSize;
                        int y = yOffset + blockY * gridCellSize;
                        drawGridSquare(g, c, x, y);
                    }
                }
            }
        }
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Calculate the grid dimensions and center it within the panel
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int gridWidth = gridColumns * gridCellSize;
        int gridHeight = gridRows * gridCellSize;

        int xOffset = (panelWidth - gridWidth) / 2;
        int yOffset = (panelHeight - gridHeight) / 2;

        // Fill only a slightly larger black background (not full screen)
        g.setColor(Color.BLACK);
        g.fillRect(xOffset - 10, yOffset - 10, gridWidth + 20, gridHeight + 20);

        g.setColor(Color.WHITE);

        // Draw grid
        for (int y = 0; y < gridRows; y++) {
            for (int x = 0; x < gridColumns; x++) {
                g.drawRect(xOffset + x * gridCellSize, yOffset + y * gridCellSize,
                        gridCellSize, gridCellSize);
            }
        }

        drawBackground(g, xOffset, yOffset);
        drawBlock(g, xOffset, yOffset);
    }


    public boolean isBlockOutOfBounds()
    {
        if(block.getY() < 0)
        {
            return true;
        }
        return false;
    }
   /* private void drawBackground(Graphics g) {
        for (int r = 0; r < gridRows; r++) {
            for (int c = 0; c < gridColumns; c++) {
                if (background[r][c] != null) {
                    int x = c * gridCellSize;
                    int y = r * gridCellSize;

                    // Use stored color or gray if color mode is off
                    Color drawColor = useColor ? background[r][c] : Color.GRAY;
                    drawGridSquare(g, drawColor, x, y);
                }
            }
        }
    }

*/
    private void drawGridSquare(Graphics g, Color color, int x, int y)
    {
        g.setColor(color);
        g.fillRect(x,y,gridCellSize,gridCellSize);
        g.setColor(Color.black);
        g.drawRect(x,y,gridCellSize,gridCellSize);
    }

    public void moveBlockRight()
    {
        if(block == null) return;
        if(checkRight() == false) return;
        block.moveRight();
        repaint();
    }

    public void moveBlockLeft()
    {
        if(block == null) return;
        if(checkLeft() == false)return;
        block.moveLeft();
        repaint();
    }
    public void moveDown()
    {  if(block == null) return;
        while(checkBottom())
        {
            block.moveDown();
        }
        repaint();

    }
    public void rotateBlock()
    {
        if(block == null) return;

        block.rotate();

        // Check if rotation causes out-of-bounds issue
        if(block.getLeftEdge() < 0)
            block.setX(0);
        if(block.getRightEdge() >= gridColumns)
            block.setX(gridColumns - block.getWidth());
        if(block.getBottomEdge() >= gridRows)
            block.setY(gridRows - block.getHeight());

        // If block collides with the background, undo the rotation
        if(isCollision())
            block.rotateBack();

        repaint();
    }
    private boolean isCollision() {
        int[][] shape = block.getShape();
        int w = block.getWidth();
        int h = block.getHeight();

        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                if (shape[row][col] != 0) {
                    int x = block.getX() + col;
                    int y = block.getY() + row;

                    if (y >= 0 && y < gridRows && x >= 0 && x < gridColumns) {
                        if (background[y][x] != null) {
                            return true; // Collision detected
                        }
                    } else {
                        return true; // Out of bounds
                    }
                }
            }
        }
        return false;
    }



    public boolean moveBlockDown()
    {
        if(!checkBottom())
        {

            return false;
        }
        block.moveDown();
        repaint();
        return true;
    }
    private boolean checkBottom()
    {
        if((block.getY() + block.getHeight()) == gridRows)
        {
            return false;
        }
        int[][]shape = block.getShape();
        int w = block.getWidth();
        int h = block.getHeight();

        for(int col = 0; col < w; col++)
        {
            for(int row = h -1; row >= 0; row--)
            {
                if(shape[row][col] != 0)
                {
                    int x = col + block.getX();
                    int y = row + block.getY() + 1;
                    if(y < 0) break;
                    if(background[y][x] != null) return false;
                    break;
                }
            }
        }
        return true;
    }

    private boolean checkLeft(){

        if(block.getLeftEdge() == 0)
        {
            return false;
        }
        int[][]shape = block.getShape();
        int w = block.getWidth();
        int h = block.getHeight();

        for(int row = 0; row < h; row++)
        {
            for(int col = 0; col < w; col++)
            {
                if(shape[row][col] != 0)
                {
                    int x = col + block.getX()-1;
                    int y = row + block.getY();
                    if(y < 0) break;
                    if(background[y][x] != null) return false;
                    break;
                }
            }
        }
        return true;
    }
    private boolean checkRight()
    {
        if(block.getRightEdge() == gridColumns)
        {
            return false;
        }
        int[][]shape = block.getShape();
        int w = block.getWidth();
        int h = block.getHeight();

        for(int row = 0; row < h; row++)
        {
            for(int col = w -1; col >= 0; col--)
            {
                if(shape[row][col] != 0)
                {
                    int x = col + block.getX() + 1;
                    int y = row + block.getY() ;
                    if(y < 0) break;
                    if(background[y][x] != null) return false;
                    break;
                }
            }
        }
        return true;
    }

    public int clearLines()
    {
        boolean lineFilled;
        int linesCleared = 0;
        for(int r = gridRows -1; r >= 0; r--)
        {
            lineFilled = true;
            for(int c = 0; c < gridColumns; c++)
            {
                if(background[r][c] == null)
                {
                    lineFilled = false;
                    break;
                }
            }
            if(lineFilled)
            {
                linesCleared++;
                clearLine(r);
                shiftDown(r);
                clearLine(0);

                repaint();
            }
        }
        return linesCleared;
    }

    private void clearLine(int r)
    {
        for(int i = 0; i < gridColumns; i++)
        {
            background[r][i] = null;
        }
    }

    private void shiftDown(int r)
    {
        for(int row = r; row > 0; row--)
        {
            for(int col = 0; col < gridColumns; col++)
            {
                background[row][col] = background[row-1][col];
            }
        }
    }

    public void setUseColor(boolean useColor) {
        this.useColor = useColor;
        repaint();
    }
}