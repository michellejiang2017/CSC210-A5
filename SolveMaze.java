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
   * @param current the current location in the maze
   * @return true if a path to the finish exists from the current location, false otherwise
   */
  public static boolean solve(Maze maze, MazeLocation current) { 
       // base case: success
       int i = current.getRow(); 
       int j = current.getCol();
      if (current.equals(maze.getFinish())) {
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

      boolean success = solve(maze, current.neighbor(MazeDirection.NORTH))
      || solve(maze, current.neighbor(MazeDirection.SOUTH))
      || solve(maze, current.neighbor(MazeDirection.EAST))
      || solve(maze, current.neighbor(MazeDirection.WEST));
        // if any worked, mark PATH and return true
        // otherwise mark DEAD_END and return false
        if (success) { 
          maze.mazeGrid[i][j] = MazeContents.PATH;
          return true; 
        } else {
          maze.mazeGrid[i][j] = MazeContents.DEAD_END;
          return success; 
        }
        
  }
  
  /**
   * Main method to read a maze file, solve the maze, and display the solution. If no command-line argument is provided, it defaults to reading "maze3".
   * @param args command-line arguments, where args[0] is the maze file name (optional)
   */
  public static void main(String[] args) {
    Scanner file;
    if (args.length <= 0) {
        file = readMaze("maze1");
    } else {
        file = readMaze(args[0]);
    }

    Maze maze = new Maze(file);
    MazeLocation start = maze.getStart();
    boolean solved = solve(maze, start);
    System.out.println(solved);
}
}
