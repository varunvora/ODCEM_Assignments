package Question3;

import java.util.HashMap;
import java.util.HashSet;

public class Tree {
    HashMap<Integer, Node> nodeMap;
    private int highestKey = 1;

    Tree() {
        this.nodeMap = new HashMap<>();
    }

    class Node {
        int id;
        String name;
        HashSet<Integer> parents;
        HashSet<Integer> children;

        Node(int id, String name) {
            this.id = id;
            this.name = name;
            this.parents = new HashSet<>();
            this.children = new HashSet<>();
        }


        public String toString() {

            StringBuilder parentsString = new StringBuilder();
            for (int parent :  this.parents)
                parentsString.append(parent);
                parentsString.append(" ");

            StringBuilder chilrenString = new StringBuilder();
            for (int child: this.children) {
                chilrenString.append(child);
                chilrenString.append(" ");
            }

            return  "\nId: " + this.id +
                    "\nName: " + this.name +
                    "\nParents: " + parentsString +
                    "\nChildren: " + chilrenString + "\n";
        }
    }

    int createNode(String name) {
        /*
        creates a new node in the tree
        returns corresponding node id.
         */

        //find a nodeId
        while (this.nodeMap.containsKey(highestKey))
            this.highestKey++;

        Node newNode = new Node(this.highestKey, name);
        this.nodeMap.put(this.highestKey, newNode);

        return this.highestKey;
    }

    boolean isNodeExists(int nodeId) {
        return this.nodeMap.keySet().contains(nodeId);
    }

    boolean addDependency(int parentId, int childId) {
        /*
        creates a dependency in the tree.
        Returns true if successful.
        false if the given node ids do not exist or if the dependency creates a cycle.
        */

        Node parentNode = this.nodeMap.get(parentId);
        Node childNode = this.nodeMap.get(childId);

        if (parentNode == null || childNode == null || parentId == childId)
            return false;

        parentNode.children.add(childId);
        childNode.parents.add(parentId);

        //check if adding this dependency created a cycle
        if (this.isCyclic()) {
            this.deleteDependency(parentId, childId);
            return false;
        }
        return true;
    }

    boolean deleteDependency(int parentId, int childId) {
        /*
        deletes a given dependency from the tree.
        Returns true if that dependency is deleted or does not exist
        Returns false if the given nodeIds do not exist
         */
        if (!isNodeExists(parentId) || !isNodeExists(childId))
            return false;

        getChildren(parentId).remove(childId);
        getParents(childId).remove(parentId);

        return true;
    }

    boolean deleteNode(int nodeId) {
        /*
        deletes a node from the tree and its relationships
        returns true if the node was deleted
        returns false if the node id does not exist
         */
        if (!isNodeExists(nodeId))
            return false;

        //remove this child from all its parents
        for (int parent : this.getParents(nodeId))
            this.deleteDependency(parent, nodeId);

        //remove this parent from all its children
        for (int child: this.getChildren(nodeId))
            this.deleteDependency(nodeId, child);

        //remove this node from the tree
        this.nodeMap.remove(nodeId);

        return true;
    }

    HashSet<Integer> getParents(int nodeId) {
        /*
        Returns a set of node ids.
        Empty set for no parents or invalid nodeId.
         */
        if (!isNodeExists(nodeId))
            return new HashSet<>();

        return this.nodeMap.get(nodeId).parents;
    }

    HashSet<Integer> getChildren(int nodeId) {
        if (!isNodeExists(nodeId))
            return new HashSet<>();

        return this.nodeMap.get(nodeId).children;
    }

    HashSet<Integer> getAncestors(int nodeId) {
        if (!isNodeExists(nodeId))
            return new HashSet<>();

        HashSet<Integer> parents = getParents(nodeId);
        HashSet<Integer> ancestors = new HashSet<>(parents);
        for (int parent: parents)
            ancestors.addAll(this.getAncestors(parent));

        return ancestors;
    }

    HashSet<Integer> getDescendants(int nodeId) {
        if (!isNodeExists(nodeId))
            return new HashSet<>();

        HashSet<Integer> children = getChildren(nodeId);
        HashSet<Integer> descendants = new HashSet<>(children);


        for (int child: children)
            descendants.addAll(this.getDescendants(child));

        return descendants;
    }

    boolean isCyclic() {

        //ref: https://www.youtube.com/watch?v=joqmqvHC_Bo

        HashSet<Integer> visited = new HashSet<>();
        HashSet<Integer> recStack = new HashSet<>();

        for (int nodeId: this.nodeMap.keySet()) {
            if (this.dfs(nodeId, visited, recStack))
                return true;
        }

        return false;
    }

    private boolean dfs(int nodeId, HashSet<Integer> visited, HashSet<Integer> recStack) {

        if (!visited.contains(nodeId)) {
            visited.add(nodeId);
            recStack.add(nodeId);

            for (int child : this.getChildren(nodeId)) {
                if (!visited.contains(child) && dfs(child, visited, recStack))
                    return true;
                else if (recStack.contains(child))
                    return true;
            }
        }
        recStack.remove(nodeId);
        return false;
    }

    public String toString() {
        StringBuilder treeString = new StringBuilder();

        for (Node nodeObj: this.nodeMap.values()) {
            treeString.append("---");
            treeString.append(nodeObj.toString());
            treeString.append("---");
        }
        return "" + treeString;
    }
}
