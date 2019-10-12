package dataSource;

import java.util.HashMap;
import java.util.Map;

import domain.Item;
import domain.User;

/**
 * holding the loaded items, and reduce the query time
 * @author YUANLONG ZHANG
 *
 * @param <E>
 */
public class IdentityMap<E> {
	//map <id, object>
	private Map<String, E> IDMap = new HashMap<String, E>();
	//map <accountEmail, object>
	private Map<String, E> AEMap = new HashMap<String, E>();
	//map <Name, object>
	private Map<String, E> NMap = new HashMap<String, E>();
	private static Map<Class, IdentityMap> singletons = new HashMap<Class, IdentityMap> ();
	
	public static <E> IdentityMap<E> getInstance(Class<E> e){
		IdentityMap<E> result = singletons.get(e);
		if (result == null) {
			result = new IdentityMap<E>();
			singletons.put(e.getClass(), result);
		}
		return result;
	}
	
	public void putWithID(String id, E obj) throws Exception {
		IDMap.put(id, obj);
		//consider to put in other map for future convenience
		if (obj instanceof User) {
			AEMap.put(((User) obj).getAccount(), obj);
		}  else if (obj instanceof Item) {
			NMap.put(((Item) obj).getName(), obj);
		}
	}
	
	public E getWithID(String id) {
		return IDMap.get(id);
	}
	
	public E getWithAE(String accountEmail) {
		return AEMap.get(accountEmail);
	}
	
	
	public E getWithN(String name) {
		return NMap.get(name);
	}
}
