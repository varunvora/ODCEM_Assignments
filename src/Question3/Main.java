package Question3;

public class Main {
    public static void main(String[] args) {

        int earth, asia, africa, india, china, egypt, bangalore;
        DAG dag = new DAG();
        earth = dag.createNode("Earth");
        asia = dag.createNode("Asia");
        africa = dag.createNode("Africa");
        india = dag.createNode("India");
        china = dag.createNode("China");
        egypt = dag.createNode("Egypt");
        bangalore = dag.createNode("Bangalore");

        // dag.addDependency(parent, child);
        dag.addDependency(earth, asia);
        dag.addDependency(earth, africa);
        dag.addDependency(asia, india);
        dag.addDependency(africa, egypt);
        dag.addDependency(india, bangalore);
        dag.addDependency(asia, china);

        System.out.println(dag);
    }
}
