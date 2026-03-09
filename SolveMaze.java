import java.io.*;
import java.util.Scanner;
/**
 * Recursive maze solving program.
 */
class SolveMaze {

  /**
   * Reads a maze from a file and returns a Scanner object for it. Exits with an error message if the file cannot be found.
   * @param fname the name of the maze file to read
   * @return a Scanner object for the maze file
   */
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

  /**
   * Recursive method to solve the maze. Returns true if a path from (i, j) to the finish exists, false otherwise.
   * @param maze the maze to solve
   * @param i the current row index
   * @param j the current column index
   * @return true if a path from (i, j) to the finish exists,
   */
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
  
  /**
   * Main method to read a maze file, solve the maze, and display the solution. If no command-line argument is provided, it defaults to reading "maze3".
   * @param args command-line arguments, where args[0] is the maze file name (optional)
   */
  public static void main(String[] args) {
    Scanner file = null;
    if(args.length <= 0) {
      file = readMaze("maze3");
    } else{
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
