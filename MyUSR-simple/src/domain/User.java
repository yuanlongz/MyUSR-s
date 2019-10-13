package domain;

import dataSource.MapperFactory;
import dataSource.UserMapper;

public abstract class User implements DomainObject{
	private UserMapper mapper;
	private String name, id, account, password;
	private Role role;

	public User(String name, String id, String account, String password,
			Role role) {
		super();
		this.name = name;
		this.id = id;
		this.account = account;
		this.password = password;
		this.role = role;
	}

	public User() {
		super();
	}

	// lazy initialization
	public User(String id) {
		super();
		this.name = null;
		this.id = id;
		this.account = null;
		this.password = null;
		this.role = null;
	}

	/**
	 * For lazy load propurse, return obj with only id
	 * 
	 * @param account
	 * @return
	 * @throws Exception
	 */
	public static User getUserByAccount(String account) throws Exception {
		return UserMapper.findWithAccountEmail(account);
	}
	
	public static User getUserById(String id) throws Exception {
		return UserMapper.findWithID(id);
	}

	/**
	 * load every empty field with values from DB
	 */
	private void load() {
		try {
			User user = UserMapper.findWithID(this.id);
			this.name = user.name;
			this.account = user.account;
			this.password = user.password;
			this.role = user.role;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void add() {
		try {
			mapper = (UserMapper) MapperFactory.getMapper(this);
			mapper.insert(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update() {
		try {
			mapper = (UserMapper) MapperFactory.getMapper(this);
			mapper.update(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delete() {
		try {
			mapper = (UserMapper) MapperFactory.getMapper(this);
			mapper.delete(this.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void remove(String id) {
		try {
			UserMapper mapper = (UserMapper) MapperFactory
					.getMapper(new Customer());
			mapper.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getName() {
		// lazy load: ghost
		if (name == null)
			load();
		return name;
	}
	
	@Override
	public String getId() {
		return id;
	}

	public String getAccount() {
		// lazy load: ghost
		if (account == null)
			load();
		return account;
	}

	public String getPassword() {
		// lazy load: ghost
		if (password == null)
			load();
		return password;
	}

	public Role getRole() {
		// lazy load: ghost
		if (role == null)
			load();
		return role;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
