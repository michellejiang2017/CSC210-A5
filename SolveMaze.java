import java.io.*;
import java.util.Scanner;

class SolveMaze {

  public static Scanner readMaze(String fname){
    Scanner file = null;
    try {
      file = new Scanner(new File(fname));
    } catch (FileNotFoundException e) {
      System.err.println("Cannot locate file.");
      System.exit(-1);  
    }
    return file;
  }

  public static boolean solve(Maze maze, int i, int j) { 
       // base case: success
      MazeLocation current = new MazeLocation(i, j);
      if (maze.getFinish().equals(current)) {
        maze.mazeGrid[i][j] = MazeContents.PATH;
        return true;
      }
        // base case: failure
      if (!maze.isExplorable(i, j)) {
        return false; 
      }
        // mark current square as visited
      maze.mazeGrid[i][j] = MazeContents.VISITED;
        // recursively try 4 directions

      boolean north = solve(maze, i-1, j); 
      boolean south = solve(maze, i+1, j);
      boolean east = solve(maze, i, j+1);
      boolean west = solve(maze, i, j-1);

      boolean success = north || south || east || west;
        // if any worked, mark PATH and return true
        // otherwise mark DEAD_END and return false
        if (success) { 
          maze.mazeGrid[i][j] = MazeContents.PATH;
          return true; 
        } else {
          maze.mazeGrid[i][j] = MazeContents.DEAD_END;
          return false; 
        }
        
  }
  
  public static void main(String[] args) {
    Scanner file = null;
    if(args.length <= 0){
      file = readMaze("maze3");
    }
    else{
      file = readMaze(args[0]);
    }
    
    Maze maze = new Maze();
    maze.makeMaze(file);
    MazeViewer viewer = new MazeViewer(maze);
    MazeLocation start = maze.getStart();
    boolean solved = solve(maze, start.getRow(), start.getCol());
    System.out.println("Solved: " + solved);
  }
}
