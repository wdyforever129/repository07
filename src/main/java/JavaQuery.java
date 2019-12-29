import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.helpers.collection.Iterators;

import static org.neo4j.io.fs.FileUtils.deleteRecursively;

public class JavaQuery {
    private static final File databaseDirectory = new File("target/java-query-db");
    String resultString;
    String columnsString;
    String nodeResult;
    String rows = "";

    public static void main(String[] args) {
        JavaQuery javaQuery = new JavaQuery();
        javaQuery.run();
    }

    void run() {
        clearDbPath();

        // START SNIPPET: addData
        GraphDatabaseService db = new GraphDatabaseFactory().newEmbeddedDatabase(databaseDirectory);

        /*try (Transaction tx = db.beginTx()) {
            Node myNode = db.createNode();
            myNode.setProperty("name", "my node");
            tx.success();
        }*/
        // END SNIPPET: addData

        // START SNIPPET: execute
        try (Transaction ignored = db.beginTx();
             Result result = db.execute("MATCH (n {name: 'my node'}) RETURN n, n.name")) {
            while (result.hasNext()) {
                Map<String, Object> row = result.next();
                for (Entry<String, Object> column : row.entrySet()) {
                    rows += column.getKey() + ": " + column.getValue() + "; ";
                }
                rows += "\n";
            }
            System.out.println("result:"+result);
        }
        // END SNIPPET: execute
        // the result is now empty, get a new one
        try (Transaction ignored = db.beginTx();
             Result result = db.execute("MATCH (n {name: '张三'}) RETURN n, n.name")) {
            // START SNIPPET: items
            Iterator<Node> n_column = result.columnAs("n");
            for (Node node : Iterators.asIterable(n_column)) {
                nodeResult = node + ": " + node.getProperty("name");
            }
            System.out.println("nodeResult:"+nodeResult);
            // END SNIPPET: items

            // START SNIPPET: columns
            List<String> columns = result.columns();
            // END SNIPPET: columns
            columnsString = columns.toString();
            resultString = db.execute("MATCH (n {name: 'my node'}) RETURN n, n.name").resultAsString();
        }

        db.shutdown();
    }

    private void clearDbPath() {
        try {
            deleteRecursively(databaseDirectory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
