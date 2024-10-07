package src.labs.stealth.agents;

// SYSTEM IMPORTS
import edu.bu.labs.stealth.agents.MazeAgent;
import edu.bu.labs.stealth.graph.Vertex;
import edu.bu.labs.stealth.graph.Path;


import edu.cwru.sepia.environment.model.state.State.StateView;
import edu.cwru.sepia.util.Direction;                           // Directions in Sepia


import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue; // heap in java
import java.util.Set;

import java.lang.Math;


// JAVA PROJECT IMPORTS


public class DijkstraMazeAgent
    extends MazeAgent
{

    public DijkstraMazeAgent(int playerNum)
    {
        super(playerNum);
    }

    @Override
    public Path search(Vertex src,
                       Vertex goal,
                       StateView state)
    {
        PriorityQueue<Path> pq = new PriorityQueue<>(new PathComparator());
        HashMap<Vertex, Path> pi = new HashMap<>();
        HashMap<Vertex, Path> d = new HashMap<>();
        Set<Vertex> visited = new HashSet<>();
        int ny;
        int nx;
        int x;
        int y;
        Vertex u;
        Vertex nu;
        Path currentPath;
        Path newPath;
        float currentCost;
        float cost;
        pq.add(new Path(src));
        //TODO: initialize pq and pi
        while (!(pq.isEmpty())){
            //extract the min and location from pq
            //finalize distance of min
            currentPath = pq.poll();
            currentCost = currentPath.getTrueCost();
            u = currentPath.getDestination();
            x = u.getXCoordinate();
            y = u.getYCoordinate();
            d.put(u,currentPath);
            //loop through neighbors
            for (int dx = -1; dx<=1; dx++){
                for (int dy = -1; dy<=1;dy++){
                    nx = x+dx;
                    ny = y+dy;
                    nu = new Vertex(nx, ny);
                    cost = calculateCost(dx,dy);
                    //add new vertex if not visited
                    if (state.inBounds(nx, ny) && !state.isResourceAt(nx,ny)){
                        if (!(pi.containsKey(nu))){
                            newPath = new Path(nu,cost,currentPath);
                            pq.add(newPath);
                            pi.put(nu,newPath);
                        //check if newly found path is shorter
                        }else if (pi.get(nu).getTrueCost() > d.get(u).getTrueCost() + cost && cost != 0){
                            pq.remove(pi.get(nu));
                            newPath = new Path(nu,cost,currentPath);
                            pq.add(newPath);
                            pi.put(nu,newPath);                        
                        }
                    }
                }
            }
        }
		return d.get(goal).getParentPath();
    }

    public static float calculateCost(int dx, int dy){
        if (dx == 1){
            if (dy == -1){
                //northeast
                return ((float)Math.sqrt(125));
            }else if (dy == 0){
                //east
                return (float)5;
            }else if (dy == 1){
                //southeast
                return ((float)Math.sqrt(26));
            }
        }else if (dx == 0){
            if (dy == -1){
                //north
                return ((float)10);
            }else if (dy == 0){
                //nowhere
                return (float)0;
            }else if (dy == 1){
                //south
                return ((float)1);
            }
        }else if (dx == -1){
            if (dy == -1){
                //northwest
                return ((float)Math.sqrt(125));
            }else if (dy == 0){
                //west
                return (float)5;
            }else if (dy == 1){
                //southwest
                return ((float)Math.sqrt(26));
            }
        }
        throw new IllegalArgumentException("No cases were reached for calculateCost");
    }

    @Override
    public boolean shouldReplacePlan(StateView state)
    {
        return false;
    }

}

//was not really sure how comparator worked, so I consulted a non-documentation website: https://www.geeksforgeeks.org/implement-priorityqueue-comparator-java/
class PathComparator implements Comparator<Path>{
    public int compare(Path p1, Path p2){
        if (p1.getTrueCost() > p2.getTrueCost()){
            return 1;
        }else if (p1.getTrueCost() < p2.getTrueCost()){
            return -1;
        }
        return 0;
    }
}
