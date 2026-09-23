package adversarialsearch;

import java.io.RandomAccessFile;
import java.util.*;
import java.io.IOException;


public class State
{
char [][] board ; // the board as a 2D character array [ height ] [ width ]
int [] agentX ; // the x−coordinates of the agents , agentX [ 0 ] = x_agent0
int [] agentY ; // the y−coordinates of the agents , agentY [ 0 ] = y_agent0
int [] score ; // the amount of food eaten by each agent
int turn ; //who ’ s turn i t i s , agent 0 or agent 1
int food ; // the total amount of food s t i l l avai lable
 

// we create list of moves to keep track on history
Vector<String> moves;

public State()
{
    this.agentX = new int[2];
    this.agentY = new int[2];
    this.score = new int[2];
    this.turn = 0;
    this.food = 0;
    this.moves = new Vector<String>();
}

public void read(String file)
{
    try
    {
		food = 0;
        //access the file for reading
        RandomAccessFile read_file = new RandomAccessFile(file, "r");

        // reading the first line
        String line = read_file.readLine();

        //if the file is empty, we close the file and end the process
        if (line == null)
        {
            read_file.close()   ;
            return;
		}

        //"\\s+" keeps every char different from " "
        String[] parts = line.trim().split("\\s+");
        // store the matrix size
        int width = Integer.parseInt(parts[0]);
        int height = Integer.parseInt(parts[1]);

        this.board = new char[height][width];


        for (int row = 0; row < height; row++)
        {
            line = read_file.readLine();
            if (line == null)
            {
                read_file.close();
                return ;
            }

            for (int col = 0; col < width; col++)
            {
                char cell = line.charAt(col);
                this.board[row][col] = cell;

                if (cell == 'A')
                {
                    this.agentX[0] = col;
                    this.agentY[0] = row;
                    this.board[row][col] = ' ';
                    
                }
                else if (cell == 'B')
                {
                    this.agentX[1] = col;
                    this.agentY[1] = row;
                    this.board[row][col] = ' ';
                
                }
                else if (cell == '*')
                {
                    this.food++;
                }
            }
        }
        read_file.close();
    }
    catch(IOException error)
    {
        System.out.println("Could not open: " + file);
    }
}

public String toString() 
{
    StringBuilder sb = new StringBuilder();

    for (int h = 0; h < board.length; h++) {
        for (int w = 0; w < board[h].length; w++) {
    
            if (agentX[0] == w && agentY[0] == h) {
                sb.append('A');
            }
            
            else if (agentX[1] == w && agentY[1] == h) {
                sb.append('B');
            }
            
            else {
                sb.append(board[h][w]);
            }
        }

        sb.append("\n");
    }

    String result = sb.toString();
    return result;
}
public State copy()
{
    State s = new State();

    s.board = new char[this.board.length][];
    for (int row = 0; row < this.board.length; row++)
    {
        s.board[row] = this.board[row].clone();
    }

    s.agentX = this.agentX.clone();
    s.agentY = this.agentY.clone();
    s.score = this.score.clone();
    s.turn = this.turn;
    s.food = this.food;
    s.moves = new Vector<String>(this.moves);

    return s;
}

public Vector<String> legalMoves(int agent)
{
    Vector<String> moves = new Vector<String>();
		int x = agentX[agent];
		int y = agentY[agent];

		// up
		if (board[y-1][x] != '#') {
			moves.add("up");
		}
		// down
		if (board[y+1][x] != '#') {
			moves.add("down");
		}
		// left
		if (board[y][x-1] != '#') {
			moves.add("left");
		}
		// right
		if (board[y][x+1] != '#') {
			moves.add("right");
		}
		// eat
		if (board[y][x] == '*') {
			moves.add("eat");
		}
		// block
		if (board[y][x] == ' ') {
			moves.add("block");
		}

		return moves;
}

public Vector<String> legalMoves() 
{
    return legalMoves(turn);
}

public void execute(String action)
{
    int agent = turn;

    if (action.equals("up")) {
        agentY[agent]--;
    }
    else if (action.equals("down")) {
        agentY[agent]++;
    }
    else if (action.equals("left")) {
        agentX[agent]--;
    }
    else if (action.equals("right")) {
        agentX[agent]++;
    }
    else if (action.equals("eat")) {
        board[agentY[agent]][agentX[agent]] = ' ';
        score[agent]++;
        food--;
    }
    else if (action.equals("block")) {
        board[agentY[agent]][agentX[agent]] = '#';
    }

    moves.add(action);
    turn = 1 - turn;
}


public boolean isLeaf()
{
    if (food == 0 || legalMoves(0).isEmpty() || legalMoves(1).isEmpty())
    {
        return true;
    }
    else
    {
        return false;
    }
}

public double value(int agent)
{
   int other = 1 - agent;

		if (isLeaf()) {
			if (legalMoves(agent).size() == 0) {
				return -1;
			} else if (legalMoves(other).size() == 0) {
				return 1;
			} else if (food == 0) {
				if (score[agent] > score[other]) {
					return 1;
				} else if (score[agent] == score[other]) {
					return 0;
				} else {
					return -1;
				}
			}
		}
		return 0;
}
}
