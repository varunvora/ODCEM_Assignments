package Question3;

import org.junit.Test;

import java.util.HashSet;


import static org.junit.Assert.*;

public class TestDAG {

    // some node ids
    private int earth;
    private int asia;
    private int africa;
    private int india;
    private int china;
    private int egypt;
    private int bangalore;
    private int northHemi;

    private DAG setTree() {
        /*Set up a sample dag to run all tests on*/

        DAG dag = new DAG();
        earth = dag.createNode("Earth");
        asia = dag.createNode("Asia");
        africa = dag.createNode("Africa");
        india = dag.createNode("India");
        china = dag.createNode("China");
        egypt = dag.createNode("Egypt");
        bangalore = dag.createNode("Bangalore");
        northHemi = dag.createNode("Northen Hemisphere");

        // dag.addDependency(parent, child);
        dag.addDependency(earth, asia);
        dag.addDependency(earth, africa);
        dag.addDependency(asia, india);
        dag.addDependency(africa, egypt);
        dag.addDependency(india, bangalore);
        dag.addDependency(asia, china);

        //multiple parents
        dag.addDependency(earth, northHemi);
        dag.addDependency(northHemi, india);
        dag.addDependency(northHemi, china);
        dag.addDependency(northHemi, egypt);

        return dag;
    }

    private DAG dag = setTree();


    @Test
    public void testCreateNode() {
        DAG tempDAG = new DAG();
        assertEquals(0, tempDAG.nodeMap.size());
        tempDAG.createNode("temp");
        assertEquals(1, tempDAG.nodeMap.size());
        tempDAG.createNode("temp");
        assertEquals(2, tempDAG.nodeMap.size());
    }

    @Test
    public void testGetParents() {
        assertTrue(dag.getParents(asia).contains(earth));
        assertTrue(dag.getParents(africa).contains(earth));
        assertFalse(dag.getParents(africa).contains(asia));
        assertTrue(dag.getParents(bangalore).contains(india));
        assertTrue(dag.getParents(india).contains(northHemi));
        assertTrue(dag.getParents(india).contains(asia));
    }

    @Test
    public void testGetChildren() {
        assertTrue(dag.getChildren(earth).contains(asia));
        assertTrue(dag.getChildren(earth).contains(africa));
        assertTrue(dag.getChildren(africa).contains(egypt));
        assertFalse(dag.getChildren(africa).contains(china));
    }


    @Test
    public void testAddDependency() {

        assertTrue(dag.addDependency(asia, china));
        assertTrue(dag.getChildren(earth).contains(asia));
        assertTrue(dag.getParents(asia).contains(earth));
        assertFalse(dag.addDependency(asia, asia));

        assertFalse(dag.addDependency(africa, 5000));
        assertFalse(dag.addDependency(5000, 5001));

        assertTrue(dag.addDependency(earth, africa));
        assertTrue(dag.getChildren(earth).contains(africa));
        assertTrue(dag.getParents(africa).contains(earth));

    }


    @Test
    public void testDeleteDependency() {
        //adding a false dependency to delete
        assertTrue(dag.addDependency(africa, india));
        assertTrue(dag.getChildren(africa).contains(india));
        assertTrue(dag.getParents(india).contains(africa));

        assertTrue(dag.deleteDependency(africa, india));
        assertFalse(dag.getChildren(africa).contains(india));
        assertFalse(dag.getParents(india).contains(africa));

        assertFalse(dag.deleteDependency(earth, 1024));
    }

    @Test
    public void testIsCyclic() {
        DAG tempDAG = new DAG();

        int grandFather = tempDAG.createNode("Grand father");
        int father = tempDAG.createNode("Father");
        int son = tempDAG.createNode("Son");

        assertFalse(tempDAG.isCyclic());

        //add the node itself to its children
        tempDAG.getChildren(grandFather).add(grandFather);
        assertTrue(tempDAG.isCyclic());

        tempDAG.getChildren(grandFather).remove(grandFather);
        assertFalse(tempDAG.isCyclic());


        //add dependency returns false when the dependency causes a cycle
        assertTrue(tempDAG.addDependency(grandFather, father));
        assertFalse(tempDAG.addDependency(father, grandFather));

        assertTrue(tempDAG.addDependency(father, son));
        assertFalse(tempDAG.addDependency(son, father));
        assertFalse(tempDAG.addDependency(son, grandFather));
    }


    @Test
    public void testDeleteNode() {

        DAG tempDAG = new DAG();
        int grandFather = tempDAG.createNode("Grand father");
        int father = tempDAG.createNode("Father");
        int son = tempDAG.createNode("Son");

        tempDAG.addDependency(grandFather, father);
        tempDAG.addDependency(father, son);

        tempDAG.deleteNode(father);

        assertTrue(tempDAG.isNodeExists(son));
        assertEquals(0, tempDAG.getChildren(grandFather).size());
        assertEquals(0, tempDAG.getParents(son).size());
        assertTrue(tempDAG.deleteNode(grandFather));
        assertTrue(tempDAG.deleteNode(son));
        assertFalse(tempDAG.isNodeExists(son));
    }

    @Test
    public void testGetAncestors() {
        dag = setTree();

        HashSet<Integer> bangaloreAncestors = dag.getAncestors(bangalore);
        assertTrue(bangaloreAncestors.contains(india));
        assertTrue(bangaloreAncestors.contains(asia));
        assertTrue(bangaloreAncestors.contains(earth));
        assertFalse(bangaloreAncestors.contains(egypt));
        assertFalse(bangaloreAncestors.contains(china));
        assertTrue(bangaloreAncestors.contains(northHemi));
        assertFalse(bangaloreAncestors.contains(africa));

        assertTrue(dag.getAncestors(china).contains(asia));
        assertTrue(dag.getAncestors(china).contains(earth));
    }

    @Test
    public void testGetDescendents() {
        dag = setTree();

        assertTrue(dag.getDescendants(earth).contains(asia));
        assertTrue(dag.getDescendants(earth).contains(africa));
        assertTrue(dag.getDescendants(earth).contains(india));
        assertTrue(dag.getDescendants(earth).contains(egypt));
        assertTrue(dag.getDescendants(earth).contains(china));
        assertTrue(dag.getDescendants(earth).contains(bangalore));
        assertTrue(dag.getDescendants(earth).contains(bangalore));

        assertFalse(dag.getDescendants(bangalore).contains(bangalore));
        assertFalse(dag.getDescendants(bangalore).contains(earth));
        assertTrue(dag.getDescendants(asia).contains(bangalore));
    }
}
