/**
 * @author Wajdi Mlichi
 * @Date 30 mai 2017
 */
package tn.com.st2i.workflow.settings;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component("workflowConfig")
public class WorkflowConfig {

	@Bean(name = "workflowProperties")
	public static Properties wfProperties() {
		Properties prop = new Properties();
		InputStream input = null;
		try {
			String filename = "config.properties";
			input = WorkflowConfig.class.getClassLoader().getResourceAsStream(filename);
			if (input == null) {
				System.out.println("Sorry, unable to find " + filename);
				return null;
			}
			prop.load(input);
			return prop;

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
	}
}