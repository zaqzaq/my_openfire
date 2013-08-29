
package org.jivesoftware.openfire.plugin;

import java.io.File;

import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.openfire.container.PluginManager;

public class TestPlugin implements Plugin {

	@Override
	public void destroyPlugin() {
		System.out.println("ZAQZAQ destory the first OpenfirePlugin");
	}

	@Override
	public void initializePlugin(PluginManager manager, File pluginDirectory) {
		// TODO Auto-generated method stub
		System.out.println("ZAQZAQ create the first OpenfirePlugin");
	}
}
