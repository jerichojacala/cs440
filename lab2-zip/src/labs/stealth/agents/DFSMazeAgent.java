package src.labs.stealth.agents;

// SYSTEM IMPORTS
import edu.bu.labs.stealth.agents.MazeAgent;
import edu.bu.labs.stealth.graph.Vertex;
import edu.bu.labs.stealth.graph.Path;


import edu.cwru.sepia.environment.model.state.State.StateView;


import java.util.HashSet;   // will need for dfs
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;     // will need for dfs
import java.util.Set;       // will need for dfs


// JAVA PROJECT IMPORTS


public class DFSMazeAgent
    extends MazeAgent
{

    public DFSMazeAgent(int playerNum)
    {
        super(playerNum);
    }

    @Override
    public Path search(Vertex src,
                       Vertex goal,
                       StateView state)
    {
        Set<Vertex> visited = new HashSet<>();
        return dfs(new Path(src),goal,state,visited);
    }

    public Path dfs(Path p, Vertex goal, StateView state, Set<Vertex> visited){
        Vertex v = p.getDestination();
        int x = v.getXCoordinate();
        int y = v.getYCoordinate();
        int nx;
        int ny;
        Vertex n;
        Path np;
        Path result;
        if (state.inBounds(x, y)){
            
            if (v.equals(goal)){
                //found the destination
                return p.getParentPath();
            }
            //for loop logic-explore and process neighboring nodes
            for (int dx = -1; dx<=1; dx++){
                for (int dy = -1; dy<=1; dy++){
                    nx = x + dx;
                    ny = y + dy;
                    n = new Vertex(nx, ny);
                    np = new Path(n,(float)1,p);
                    if (state.inBounds(nx,ny) && !(visited.contains(n)) && !(state.isResourceAt(nx,ny))){
                        visited.add(n);
                        result = dfs(np, goal, state, visited);
                        if (result != null){
                            return result;
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public boolean shouldReplacePlan(StateView state)
    {
        return false;
    }

}
