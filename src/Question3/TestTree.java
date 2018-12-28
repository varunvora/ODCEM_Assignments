package Question3;

import org.junit.Test;

import java.util.HashSet;


import static org.junit.Assert.*;

public class TestTree {

    // some node ids
    private int earth;
    private int asia;
    private int africa;
    private int india;
    private int china;
    private int egypt;
    private int bangalore;
    private int northHemi;

    private Tree setTree() {
        /*Set up a sample tree to run all tests on*/

        Tree tree = new Tree();
        earth = tree.createNode("Earth");
        asia = tree.createNode("Asia");
        africa = tree.createNode("Africa");
        india = tree.createNode("India");
        china = tree.createNode("China");
        egypt = tree.createNode("Egypt");
        bangalore = tree.createNode("Bangalore");
        northHemi = tree.createNode("Northen Hemisphere");

        // tree.addDependency(parent, child);
        tree.addDependency(earth, asia);
        tree.addDependency(earth, africa);
        tree.addDependency(asia, india);
        tree.addDependency(africa, egypt);
        tree.addDependency(india, bangalore);
        tree.addDependency(asia, china);

        //multiple parents
        tree.addDependency(earth, northHemi);
        tree.addDependency(northHemi, india);
        tree.addDependency(northHemi, china);
        tree.addDependency(northHemi, egypt);

        return tree;
    }

    private Tree tree = setTree();


    @Test
    public void testCreateNode() {
        Tree tempTree = new Tree();
        assertEquals(0, tempTree.nodeMap.size());
        tempTree.createNode("temp");
        assertEquals(1, tempTree.nodeMap.size());
        tempTree.createNode("temp");
        assertEquals(2, tempTree.nodeMap.size());
    }

    @Test
    public void testGetParents() {
        assertTrue(tree.getParents(asia).contains(earth));
        assertTrue(tree.getParents(africa).contains(earth));
        assertFalse(tree.getParents(africa).contains(asia));
        assertTrue(tree.getParents(bangalore).contains(india));
        assertTrue(tree.getParents(india).contains(northHemi));
        assertTrue(tree.getParents(india).contains(asia));
    }

    @Test
    public void testGetChildren() {
        assertTrue(tree.getChildren(earth).contains(asia));
        assertTrue(tree.getChildren(earth).contains(africa));
        assertTrue(tree.getChildren(africa).contains(egypt));
        assertFalse(tree.getChildren(africa).contains(china));
    }


    @Test
    public void testAddDependency() {

        assertTrue(tree.addDependency(asia, china));
        assertTrue(tree.getChildren(earth).contains(asia));
        assertTrue(tree.getParents(asia).contains(earth));
        assertFalse(tree.addDependency(asia, asia));

        assertFalse(tree.addDependency(africa, 5000));
        assertFalse(tree.addDependency(5000, 5001));

        assertTrue(tree.addDependency(earth, africa));
        assertTrue(tree.getChildren(earth).contains(africa));
        assertTrue(tree.getParents(africa).contains(earth));

    }


    @Test
    public void testDeleteDependency() {
        //adding a false dependency to delete
        assertTrue(tree.addDependency(africa, india));
        assertTrue(tree.getChildren(africa).contains(india));
        assertTrue(tree.getParents(india).contains(africa));

        assertTrue(tree.deleteDependency(africa, india));
        assertFalse(tree.getChildren(africa).contains(india));
        assertFalse(tree.getParents(india).contains(africa));

        assertFalse(tree.deleteDependency(earth, 1024));
    }

    @Test
    public void testIsCyclic() {
        Tree tempTree = new Tree();

        int grandFather = tempTree.createNode("Grand father");
        int father = tempTree.createNode("Father");
        int son = tempTree.createNode("Son");

        assertFalse(tempTree.isCyclic());

        //add the node itself to its children
        tempTree.getChildren(grandFather).add(grandFather);
        assertTrue(tempTree.isCyclic());

        tempTree.getChildren(grandFather).remove(grandFather);
        assertFalse(tempTree.isCyclic());


        //add dependency returns false when the dependency causes a cycle
        assertTrue(tempTree.addDependency(grandFather, father));
        assertFalse(tempTree.addDependency(father, grandFather));

        assertTrue(tempTree.addDependency(father, son));
        assertFalse(tempTree.addDependency(son, father));
        assertFalse(tempTree.addDependency(son, grandFather));
    }


    @Test
    public void testDeleteNode() {

        Tree tempTree = new Tree();
        int grandFather = tempTree.createNode("Grand father");
        int father = tempTree.createNode("Father");
        int son = tempTree.createNode("Son");

        tempTree.addDependency(grandFather, father);
        tempTree.addDependency(father, son);

        tempTree.deleteNode(father);

        assertTrue(tempTree.isNodeExists(son));
        assertEquals(0, tempTree.getChildren(grandFather).size());
        assertEquals(0, tempTree.getParents(son).size());
        assertTrue(tempTree.deleteNode(grandFather));
        assertTrue(tempTree.deleteNode(son));
        assertFalse(tempTree.isNodeExists(son));
    }

    @Test
    public void testGetAncestors() {
        tree = setTree();

        HashSet<Integer> bangaloreAncestors = tree.getAncestors(bangalore);
        assertTrue(bangaloreAncestors.contains(india));
        assertTrue(bangaloreAncestors.contains(asia));
        assertTrue(bangaloreAncestors.contains(earth));
        assertFalse(bangaloreAncestors.contains(egypt));
        assertFalse(bangaloreAncestors.contains(china));
        assertTrue(bangaloreAncestors.contains(northHemi));
        assertFalse(bangaloreAncestors.contains(africa));

        assertTrue(tree.getAncestors(china).contains(asia));
        assertTrue(tree.getAncestors(china).contains(earth));
    }

    @Test
    public void testGetDescendents() {
        tree = setTree();

        assertTrue(tree.getDescendants(earth).contains(asia));
        assertTrue(tree.getDescendants(earth).contains(africa));
        assertTrue(tree.getDescendants(earth).contains(india));
        assertTrue(tree.getDescendants(earth).contains(egypt));
        assertTrue(tree.getDescendants(earth).contains(china));
        assertTrue(tree.getDescendants(earth).contains(bangalore));
        assertTrue(tree.getDescendants(earth).contains(bangalore));

        assertFalse(tree.getDescendants(bangalore).contains(bangalore));
        assertFalse(tree.getDescendants(bangalore).contains(earth));
        assertTrue(tree.getDescendants(asia).contains(bangalore));
    }
}
