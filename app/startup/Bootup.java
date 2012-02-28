package startup;

import models.Config;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

@OnApplicationStart
public class Bootup extends Job{
	@Override
	public void doJob() throws Exception {
		Config.updateSystemConfig();
	}
}
