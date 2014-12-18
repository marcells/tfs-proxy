package App;

import spark.Request;

import com.microsoft.tfs.core.TFSTeamProjectCollection;
import com.microsoft.tfs.core.clients.build.IBuildDefinition;
import com.microsoft.tfs.core.clients.build.IBuildDetail;
import com.microsoft.tfs.core.clients.build.IBuildDetailSpec;
import com.microsoft.tfs.core.clients.build.IBuildQueryResult;
import com.microsoft.tfs.core.clients.build.IBuildServer;
import com.microsoft.tfs.core.clients.build.flags.BuildQueryOrder;
import com.microsoft.tfs.core.clients.build.flags.BuildStatus;
import com.microsoft.tfs.core.clients.workitem.project.Project;
import com.microsoft.tfs.core.clients.workitem.project.ProjectCollection;
import com.microsoft.tfs.core.httpclient.UsernamePasswordCredentials;
import com.microsoft.tfs.core.util.URIUtils;

import static spark.Spark.*;

public class Program {

	public static void main(String[] args) {
		System.setProperty("com.microsoft.tfs.jni.native.base-directory", "/tfsnative");
		
		get("/builds", (req, res) -> {
        	res.type("application/json");
        	
        	TFSTeamProjectCollection tpc = getTFSProjectCollectionByRequestHeaders(req);
        	BuildResponse buildResponse = retrieveBuildResponse(tpc);
			tpc.close();
			
			return buildResponse;
        }, new JsonTransformer());
	}

	private static BuildResponse retrieveBuildResponse(final TFSTeamProjectCollection tpc) {
		BuildResponse response = new BuildResponse();
		
		ProjectCollection projects = tpc.getWorkItemClient().getProjects();
		IBuildServer buildServer = tpc.getBuildServer();
				
		for(Project project : projects.getProjects()) {
	        IBuildDefinition[] buildDefinitionQueryResult = buildServer.queryBuildDefinitions(project.getName());
	        
	        for(IBuildDefinition def : buildDefinitionQueryResult) {
				IBuildDetailSpec spec = buildServer.createBuildDetailSpec(def);
				spec.setStatus(BuildStatus.ALL);
				spec.setMaxBuildsPerDefinition(10);
				spec.setQueryOrder(BuildQueryOrder.START_TIME_DESCENDING);
				
				IBuildQueryResult buildsQueryResult = buildServer.queryBuilds(spec);
				
				for (IBuildDetail build : buildsQueryResult.getBuilds()) {
					response.builds.add(BuildInformation.fromBuildDetailAndBuildServer(build, buildServer));
		        }
			}
		}
		
		return response;
	}
	
	private static TFSTeamProjectCollection getTFSProjectCollectionByRequestHeaders(Request req) {
		return new TFSTeamProjectCollection(
    		URIUtils.newURI(req.headers("url")), 
    		new UsernamePasswordCredentials(req.headers("username"), req.headers("password")));
	}
}
