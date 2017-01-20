package utilities;

import java.io.IOException;

public class Utilities {
	
	
	/**
	 * Executes a shell command (only works in mac)
	 * @param command
	 * 	Shell command to supply to terminal
	 */
	static public void exec(String command) {
		try {
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			System.out.println(command + " failed.");
		}
	}
}
