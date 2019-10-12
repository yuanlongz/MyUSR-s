package dataSource;

import java.util.UUID;


/**
 * generating unreadable globalized unique ids for objects
 * applying ID field
 * @author YUANLONG ZHANG
 *
 */
public class IDFieldFactory {
	public static synchronized String createID() {
		return UUID.randomUUID().toString();
	}
}
