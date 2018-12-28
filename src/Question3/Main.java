package Question3;

public class Main {
    public static void main(String[] args) {

        int earth, asia, africa, india, china, egypt, bangalore;
        Tree tree = new Tree();
        earth = tree.createNode("Earth");
        asia = tree.createNode("Asia");
        africa = tree.createNode("Africa");
        india = tree.createNode("India");
        china = tree.createNode("China");
        egypt = tree.createNode("Egypt");
        bangalore = tree.createNode("Bangalore");

        // tree.addDependency(parent, child);
        tree.addDependency(earth, asia);
        tree.addDependency(earth, africa);
        tree.addDependency(asia, india);
        tree.addDependency(africa, egypt);
        tree.addDependency(india, bangalore);
        tree.addDependency(asia, china);

        for (int child : tree.getDescendants(earth))
            System.out.println(tree.nodeMap.get(child).name);

        System.out.println(tree.getParents(324));
    }
}
