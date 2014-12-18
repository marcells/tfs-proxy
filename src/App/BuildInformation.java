package App;

import java.util.Date;

import com.microsoft.tfs.core.clients.build.IBuildDetail;
import com.microsoft.tfs.core.clients.build.IBuildServer;

public class BuildInformation {
	public String buildDefinition;
	public String teamProjectDefinition;
	public String buildNumber;
	public String uri;
	public String buildStatusText;
	public String requestedBy;
	public String requestedFor;
	public Date startTime;
	public Date finishTime;
	public boolean isBuildFinished;
	public String buildReasonText;
	
	public static BuildInformation fromBuildDetailAndBuildServer(IBuildDetail buildDetail, IBuildServer buildServer) {
		//IBuildInformationNode[] nodes = buildDetail.getInformation().getNodes();
		
		BuildInformation buildInformation = new BuildInformation();
		
		buildInformation.buildDefinition = buildDetail.getBuildDefinition().getName();
		buildInformation.teamProjectDefinition = buildDetail.getTeamProject();
		buildInformation.buildNumber = buildDetail.getBuildNumber();
		buildInformation.uri = buildDetail.getURI();
		buildInformation.buildStatusText = buildServer.getDisplayText(buildDetail.getStatus());
		buildInformation.requestedBy = buildDetail.getRequestedBy();
		buildInformation.requestedFor = buildDetail.getRequestedFor();
		buildInformation.startTime = buildDetail.getStartTime().getTime();
		buildInformation.finishTime = buildDetail.getFinishTime().getTime();
		buildInformation.isBuildFinished = buildDetail.isBuildFinished();
		buildInformation.buildReasonText = buildServer.getDisplayText(buildDetail.getReason());
		
		return buildInformation;
	}
}