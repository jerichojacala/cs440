/* 
package src.labs.infexf.agents;

// SYSTEM IMPORTS
import edu.bu.labs.infexf.agents.SpecOpsAgent;
import edu.bu.labs.infexf.distance.DistanceMetric;
import edu.bu.labs.infexf.graph.Vertex;
import edu.bu.labs.infexf.graph.Path;

import java.util.*;


import edu.cwru.sepia.environment.model.state.State.StateView;


// JAVA PROJECT IMPORTS


public class InfilExfilAgent
    extends SpecOpsAgent
{

    public InfilExfilAgent(int playerNum)
    {
        super(playerNum);
    }

    // if you want to get attack-radius of an enemy, you can do so through the enemy unit's UnitView
    // Every unit is constructed from an xml schema for that unit's type.
    // We can lookup the "range" of the unit using the following line of code (assuming we know the id):
    //     int attackRadius = state.getUnit(enemyUnitID).getTemplateView().getRange();
    @Override
    public float getEdgeWeight(Vertex src,
                               Vertex dst,
                               StateView state)
    {
        Set<Integer> enemyUnitIds = new HashSet<>();
        enemyUnitIds.addAll(super.getOtherEnemyUnitIDs());
        float euc = 1;
        Vertex enemyLocation;
        float risk = 0;
        float newProximity;
        float oldProximity;
        int escapes = getNeighbors(dst, state).size();
        int blocks = 8-escapes;
        for (Integer enemyUnitId:enemyUnitIds){
            enemyLocation = new Vertex(state.getUnit(enemyUnitId).getXPosition(),state.getUnit(enemyUnitId).getYPosition());
            newProximity = DistanceMetric.chebyshevDistance(enemyLocation, dst);
            oldProximity = DistanceMetric.chebyshevDistance(enemyLocation, src);
            //yes this is a formula completely based on intuition
            risk = (float)(140*Math.sin(oldProximity-newProximity))+(float)(-(0.5)*Math.pow((double)newProximity, 2)+20);
            if (risk < 0){
                risk = 0;
            }
            
            euc += risk;
        }
        euc += blocks;
        return euc;
    }

    @Override
    public boolean shouldReplacePlan(StateView state)
    {
        Vertex myLocation = new Vertex(state.getUnit(this.getMyUnitID()).getXPosition(),state.getUnit(this.getMyUnitID()).getYPosition());
        Set<Integer> enemyUnitIds = new HashSet<>();
        Stack<Vertex> plan = this.getCurrentPlan();
        if (state.getUnit(getEnemyTargetUnitID()) != null){
            Vertex enemyBaseLocation = new Vertex(state.getUnit(getEnemyTargetUnitID()).getXPosition(), state.getUnit(getEnemyTargetUnitID()).getXPosition());
            if (plan == null || plan.size()==0 && (DistanceMetric.chebyshevDistance(myLocation, enemyBaseLocation)) <= 1){
                System.out.println("Plan was null");
                return true;
            }
        }
        enemyUnitIds.addAll(super.getOtherEnemyUnitIDs());
        //Iterator<Vertex> itr = this.getCurrentPlan().iterator();
        /*while (itr.hasNext()){
            Vertex i = itr.next();
            System.out.println(DistanceMetric.chebyshevDistance(i, myLocation));
            for (Integer enemyUnitId:enemyUnitIds){
                Vertex enemyLocation = new Vertex(state.getUnit(enemyUnitId).getXPosition(),state.getUnit(enemyUnitId).getYPosition());
                if (DistanceMetric.chebyshevDistance(i, enemyLocation) <= (float)2 || DistanceMetric.chebyshevDistance(enemyLocation, myLocation) <= (float)4){
                    System.out.println("Changing plan");
                    return true;
                }
            }
        }
        for (int i = plan.size()-1;i >= Math.max(plan.size()-3,0);i--){
            for (Integer enemyUnitId:enemyUnitIds){
                Vertex enemyLocation = new Vertex(state.getUnit(enemyUnitId).getXPosition(),state.getUnit(enemyUnitId).getYPosition());
                if (DistanceMetric.chebyshevDistance(plan.get(i), enemyLocation) <= (float)2 || DistanceMetric.chebyshevDistance(enemyLocation, myLocation) <= (float)3){
                    System.out.println("Changing plan");
                    return true;
                }
            }
        }

        return false;
    }

}
*/