package src.labs.stealth.agents;

// SYSTEM IMPORTS
import edu.bu.labs.stealth.agents.MazeAgent;
import edu.bu.labs.stealth.graph.Vertex;
import edu.bu.labs.stealth.graph.Path;


import edu.cwru.sepia.environment.model.state.State.StateView;


import java.util.HashSet;       // will need for bfs
import java.util.Queue;         // will need for bfs
import java.util.LinkedList;    // will need for bfs
import java.util.Set;           // will need for bfs


// JAVA PROJECT IMPORTS


public class BFSMazeAgent
    extends MazeAgent
{

    public BFSMazeAgent(int playerNum)
    {
        super(playerNum);
    }

    @Override
    public Path search(Vertex src,
                       Vertex goal,
                       StateView state)
    {
        Queue<Path> queue = new LinkedList<>();
        Set<Vertex> visited = new HashSet<>();
        int x;
        int y;
        int nx;
        int ny;
        Vertex v;
        Vertex n;
        Path p;
        Path np;
        Path start = new Path(src);
        queue.add(start);
        while (!queue.isEmpty()){
            p = queue.remove();
            v = p.getDestination();
            x = v.getXCoordinate();
            y = v.getYCoordinate();
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
                            queue.add(np);
                            visited.add(n);
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
