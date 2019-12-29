/*import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.function.Function;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neo4j.harness.ServerControls;
import org.neo4j.harness.TestServerBuilder;
import org.neo4j.harness.TestServerBuilders;
import org.neo4j.kernel.configuration.Settings;
import org.neo4j.kernel.configuration.ssl.LegacySslPolicyConfig;
import org.neo4j.server.ServerTestUtils;
import org.neo4j.server.configuration.ServerSettings;
import org.neo4j.doc.test.rule.SuppressOutput;
import org.neo4j.doc.server.HTTP;

import static org.junit.Assert.assertEquals;
import static org.neo4j.helpers.collection.Iterators.count;
import static org.neo4j.server.ServerTestUtils.getSharedTestTemporaryFolder;

public class neo4jFound{//ExtensionTestingDocIT
    @Rule
    public SuppressOutput suppressOutput = SuppressOutput.suppressAll();

*//*    // START SNIPPET: testExtension
    @Path("")
    public static class MyUnmanagedExtension
    {
        @GET
        public Response myEndpoint()
        {
            return Response.ok().build();
        }
    }*//*

*//*    @Test
    public void testMyExtension() throws Exception
    {
        // Given
        try ( ServerControls server = getServerBuilder()
                .withExtension( "/myExtension", MyUnmanagedExtension.class )
                .newServer() )
        {
            // When
            HTTP.Response response = HTTP.GET(
                    HTTP.GET( server.httpURI().resolve( "myExtension" ).toString() ).location() );

            // Then
            assertEquals( 200, response.status() );
        }
    }*//*

    @Test
    public void testMyExtensionWithFunctionFixture() throws Exception{
        // Given
        try ( ServerControls server = getServerBuilder().withExtension( "/myExtension", MyUnmanagedExtension.class )
                .withFixture( new Function<GraphDatabaseService, Void>(){
                    @Override
                    public Void apply( GraphDatabaseService graphDatabaseService ) throws RuntimeException
                    {
                        try ( Transaction tx = graphDatabaseService.beginTx() )
                        {
                            graphDatabaseService.createNode( Label.label( "User" ) );
                            tx.success();
                        }
                        return null;
                    }
                } )
                .newServer() ) {
            // When
            Result result = server.graph().execute( "MATCH (n:User) return n" );

            // Then
            assertEquals( 1, count( result ) );
        }
    }
    // END SNIPPET: testExtension

    private TestServerBuilder getServerBuilder( ) throws IOException {
        TestServerBuilder serverBuilder = TestServerBuilders.newInProcessBuilder();
        String path = ServerTestUtils.getRelativePath(
                getSharedTestTemporaryFolder(), LegacySslPolicyConfig.certificates_directory );
        serverBuilder.withConfig( LegacySslPolicyConfig.certificates_directory.name(), path )
                .withConfig( ServerSettings.script_enabled.name(), Settings.TRUE );
        return serverBuilder;
    }
}*/
