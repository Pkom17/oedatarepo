package ci.itech.oedatarepo.controller;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import ci.itech.oedatarepo.model.AppUser;
import ci.itech.oedatarepo.service.AppUserService;
import ci.itech.oedatarepo.service.SiteService;

/**
 * 
 * @author Pascal
 *
 */
@Controller
public class BaseController {

	@Value("${app.mail.from}")
	private String mailFrom;

	@Value("${spring.mail.username}")
	private String mailUsername;

	@Value("${spring.mail.password}")
	private String mailPassword;

	@Value("${data.dir}")
	private String dataDirectory;

	@Autowired
	protected AppUserService accountService;

	@Autowired
	protected SiteService siteService;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private MessageSource messageSource;

	protected static final Logger LOGGER = Logger.getLogger("PCM");

	protected int getCurrentUserId() {
		return this.accountService.findUserByEmail(this.getUsername()).getId();
	}

	protected Map<String, Object> getCurrentUserInfos() {
		return this.userInfos(this.getCurrentUserId());
	}

	protected Map<String, Object> userInfos(Integer id) {
		AppUser user = this.accountService.findUserById(id).get();
		Map<String, Object> userMap = new HashMap<String, Object>();
		if (ObjectUtils.isNotEmpty(user)) {
			userMap.put("userId", user.getId());
			userMap.put("userEmail", user.getEmail());
			userMap.put("userPhone", user.getPhoneContact());
			userMap.put("userFirstName", user.getFirstName());
			userMap.put("userLastName", user.getLastName());
			userMap.put("lastLogin", user.getLastLogin());
			userMap.put("roles", user.getAuthorities());
			ArrayList<Map<String, Object>> sites = new ArrayList<>();
			user.getListOfSite().forEach(s -> {
				Map<String, Object> map = new HashMap<>();
				map.put("id", s.getId());
				map.put("name", s.getName());
				sites.add(map);
			});
			userMap.put("sites", sites);
		}
		return userMap;
	}

	protected List<Order> getSortOrder(String[] sorts) {
		List<Order> orders = new ArrayList<>();
		if (sorts[0].contains(",")) {
			for (String sortOrder : sorts) {
				String[] sort = sortOrder.split(",");
				orders.add(new Order(getSortDirection(sort[1]), sort[0]));
			}
		} else {
			orders.add(new Order(getSortDirection(sorts[1]), sorts[0]));
		}

		return orders;
	}

	private Sort.Direction getSortDirection(String direction) {
		if (direction.equals("asc")) {
			return Sort.Direction.ASC;
		} else if (direction.equals("desc")) {
			return Sort.Direction.DESC;
		}

		return Sort.Direction.ASC;
	}

	protected AppUser getUserById(Integer id) {
		return ObjectUtils.isEmpty(id) ? new AppUser() : accountService.getOne(id);
	}

	protected String getUserEmailById(Integer id) {
		return ObjectUtils.isEmpty(getUserById(id)) ? "--" : getUserById(id).getEmail();
	}

	protected String getUserFirstNameById(Integer id) {
		return ObjectUtils.isEmpty(getUserById(id)) ? "--" : getUserById(id).getFirstName();
	}

	protected String getUserLastNameById(Integer id) {
		return ObjectUtils.isEmpty(getUserById(id)) ? "--" : getUserById(id).getLastName();
	}

	/**
	 * 
	 * @param userId  identifier of a user for whom the e-mail is intended
	 * @param subject Subject of a mail
	 * @param message content of the mail
	 */
	protected void sendEmail(Integer userId, String subject, String message) {

		MimeMessage msg = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg);
		String cc = "phassoa@itech-civ.org";
		String cc1 = "pkomena@itech-civ.org";
		try {
			helper.setTo(getUserById(userId).getEmail());
			helper.setSubject(subject);
			helper.setText(message, true);
			helper.setFrom(mailFrom);
			helper.setCc(cc);
			helper.addCc(cc1);
			mailSender.send(msg);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	protected void sendDirectEmail(Integer userId, String subject, String message) {

		MimeMessage msg = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg);
		try {
			helper.setTo(getUserById(userId).getEmail());
			helper.setSubject(subject);
			helper.setText(message, true);
			helper.setFrom(mailFrom);
			mailSender.send(msg);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	/***
	 * Retrieve translated message from Message ressources
	 * 
	 * @param key    messageKey to translate
	 * @param params
	 * @return String: the translated message
	 */
	protected String trans(String key, Object[] params) {
		return messageSource.getMessage(key, params, key, LocaleContextHolder.getLocale());
	}

	/***
	 * Retrieve translated message from Message ressources
	 * 
	 * @param key messageKey to translate
	 * @return String: the translated message
	 */
	protected String trans(String key) {
		return this.trans(key, null);
	}

	/**
	 * check if a current user has one of admin or support roles
	 * 
	 * @return boolean
	 */
	protected Boolean isAdminOrSuppot() {
		// update pkomena
		List<String> userRoles = this.getUserRoles();
		return userRoles.contains("ROLE_ADMIN") || userRoles.contains("ROLE_SUPPORT");
	}

	/**
	 * @return String comma separated IDs for the connected user
	 */
	protected String getUserSiteIDs() {
		List<Integer> userSiteIds = siteService.getSitesIdsByUser(this.getCurrentUserId());
		String siteIds = userSiteIds.stream().map(e -> e.toString()).collect(Collectors.joining(","));
		// si c'est un admin ou support alors tous les sites
		String roles = this.getCurrentUserInfos().get("roles").toString();
		if ((roles.contains("ADMIN") || roles.contains("SUPPORT") || roles.contains("MANAGER"))) {
			siteIds = null;
		}
		return siteIds;
	}

	/**
	 * check if a current user own the site
	 * 
	 * @return boolean
	 */
	protected Boolean userHasSite(Integer siteId) {
		String roles = this.getCurrentUserInfos().get("roles").toString();
		if ((roles.contains("ADMIN") || roles.contains("SUPPORT") || roles.contains("MANAGER"))) {
			return true;
		}
		return this.isNullOrZero(siteId) ? true
				: (getUserSiteIDs().isEmpty() ? false : getUserSiteIDs().contains(siteId + ""));

	}

	public String getUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	public List<String> getUserRoles() {
		return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
				.map(s -> s.getAuthority()).collect(Collectors.toList());
	}

	public boolean isNullOrZero(Integer i) {
		return i == null || i.intValue() == 0;
	}

	public String getUserAgent(HttpServletRequest request) {
		return (request != null) ? request.getHeader("User-Agent") : "";
	}

	public LocalDate toLocalDate(Date date) {
		return date.toInstant().atZone(ZoneOffset.UTC).toLocalDate();
	}
}
