package ci.itech.oedatarepo.service;

public interface LoginService {
	boolean isAuthenticated();

	void login(String username, String password);
}
