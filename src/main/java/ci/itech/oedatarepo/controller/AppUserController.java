
package ci.itech.oedatarepo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ci.itech.oedatarepo.dto.UserDTO;
import ci.itech.oedatarepo.exception.OperationFailedException;
import ci.itech.oedatarepo.exception.ResourceNotFoundException;
import ci.itech.oedatarepo.model.AppUser;
import ci.itech.oedatarepo.service.AppUserService;
import ci.itech.oedatarepo.service.DistrictService;
import ci.itech.oedatarepo.service.SiteService;

@Controller
@RequestMapping("/user")
public class AppUserController extends BaseController {

	@Autowired
	private AppUserService entityService;

	@Autowired
	private SiteService siteService;

	@Autowired
	private DistrictService districtService;

	private AppUser appUser;

	// @PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("")
	public String lists(Model model) {
		List<AppUser> users = new ArrayList<AppUser>();
		users = accountService.getAll();
		model.addAttribute("users", users);
		return "user/index";
	}

	@GetMapping(value = "/{id}")
	public String getOneUser(@PathVariable("id") Integer id, Model model) {
		AppUser e = accountService.getOne(id);
		if (e == null) {
			e = new AppUser();
		}
		model.addAttribute("user", e);
		return "user/edit";
	}

	@GetMapping(value = "/delete/{id}")
	public String deleteUser(@PathVariable("id") Integer id) {
		accountService.delete(id);
		return "redirect:/user";
	}

	@GetMapping(value = "/new")
	public String addUser(Model model) {
		model.addAttribute("userDTO", new UserDTO());
		model.addAttribute("sites", siteService.getAll());
		model.addAttribute("districts",districtService.getAll());
		return "user/new";
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping(value = "/new")
	public String createAppUser(@Valid UserDTO userDTO, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "user/new";
		}
		String email = userDTO.getEmail();
		String contact = userDTO.getPhoneContact();

		AppUser user = entityService.findUserByEmail(email);
		if (user != null) {
			model.addAttribute("message_error", "Cette adresse e-mail est dejà utilisée");
			return "user/new";
		}
		user = entityService.findUserByPhoneContact(contact);
		if (user != null) {
			model.addAttribute("message_error", "Ce contact est dejà utilisé");
			return "user/new";
		}
		String password = userDTO.getPassword();
		String repassword = userDTO.getRepassword();
		if (!password.equals(repassword)) {
			model.addAttribute("message_error", "Mots de passe non concordants");
			return "user/new";
		}
		AppUser u = new AppUser();
		u.setPassword(password);
		u.setFirstName(userDTO.getFirstName());
		u.setLastName(userDTO.getLastName());
		u.setPhoneContact(contact);
		u.setEmail(email);
		u.setPasswordExpireAt(userDTO.getPasswordExpireAt());
		u.setCreatedAt(new java.util.Date());
		u.setActive(userDTO.isActive());
		u.setLocked(userDTO.isLocked());
		u.setRole(userDTO.getRole());
		u.setCreatedBy(this.getCurrentUserId());
		try {
			u = entityService.create(u, true);

			// // add sites to users
			// List<Integer> sitesTab = (ObjectUtils.isNotEmpty(userDTO.getSites()))
			// ? Arrays.asList(userDTO.getSites().split(",")).stream().map(e ->
			// Integer.parseInt(e)).collect(
			// Collectors.toList())
			// : new ArrayList<Integer>();
			// for (int j = 0; j < sitesTab.size(); j++) {
			// if (ObjectUtils.isNotEmpty(sitesTab.get(j))) {
			// accountService.addSiteToUser(email, sitesTab.get(j));
			// }
			// }

		} catch (Exception e) {
			e.printStackTrace();
			throw new OperationFailedException(e.getLocalizedMessage());
		}
		model.addAttribute("message_success", "Ajout effectué avec succès");
		// if (u != null) {
		// // send a notification via a mail
		// String mailSubject = this.trans("mail.account.new.subject", null);
		// String mailContent = this.trans("mail.account.new.content",
		// new Object[] { model.getEmail(), model.getPassword() });
		// this.sendEmail(u.getId(), mailSubject, mailContent);

		// return new ResponseEntity<>(u, HttpStatus.CREATED);
		// } else {
		// return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		// }

		return "user/new";
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping("/update")
	public String update(Model model, UserDTO userDTO) {
		try {
			AppUser updatedUser = accountService.findUserById(userDTO.getId()).get();
			if (ObjectUtils.isEmpty(updatedUser)) {
				model.addAttribute("message_error", "Utilisateur non trouvé");
				return "user/edit";
			}
			appUser = new AppUser();
			AppUser u = accountService.findUserByEmail(userDTO.getEmail());
			if (u != null && !userDTO.getEmail().equalsIgnoreCase(u.getEmail())) {
				model.addAttribute("message_error", "Cette adresse e-mail est dejà utilisée");
				return "user/edit";
			}

			u = accountService.findUserByPhoneContact(userDTO.getPhoneContact());
			if (u != null && !userDTO.getPhoneContact().equalsIgnoreCase(u.getPhoneContact())) {
				model.addAttribute("message_error", "Ce contact est dejà utilisé");
				return "user/edit";
			}
			boolean passwordMustBeCrypt = false;
			updatedUser.setActive(userDTO.isActive());
			updatedUser.setEmail(userDTO.getEmail());
			updatedUser.setFirstName(userDTO.getFirstName());
			updatedUser.setLastName(userDTO.getLastName());
			updatedUser.setLocked(userDTO.isLocked());
			if (StringUtils.isNotEmpty(userDTO.getPassword())) { // si le mot de passe est defini lors de la
																	// modification alors on met a jour le mot de passe.
				String password = userDTO.getPassword();
				String repassword = userDTO.getRepassword();
				if (!password.equals(repassword)) {
					throw new RuntimeException("Mots de passe non concordants");
				}
				updatedUser.setPassword(userDTO.getPassword());
				passwordMustBeCrypt = true;

				// // send a notification via a mail
				// String mailSubject = this.trans("mail.account.update.subject", null);
				// String mailContent = this.trans("mail.account.update.content",
				// new Object[] { user.getEmail(), user.getPassword() });
				// this.sendEmail(u.getId(), mailSubject, mailContent);

			} else { // sinon ...
				passwordMustBeCrypt = false;
			}
			updatedUser.setPasswordExpireAt(userDTO.getPasswordExpireAt());
			updatedUser.setPhoneContact(userDTO.getPhoneContact());

			updatedUser.setLastUpdatedBy(this.getCurrentUserId());
			updatedUser.setLastUpdatedAt(new java.util.Date());
			updatedUser.setRole(userDTO.getRole());
			accountService.removeAllSites(updatedUser.getEmail());
			appUser = accountService.create(updatedUser, passwordMustBeCrypt);

			// add sites to users
			// List<Integer> sitesTab = (ObjectUtils.isNotEmpty(user.getSites()))
			// ? Arrays.asList(user.getSites().split(",")).stream().map(e ->
			// Integer.parseInt(e)).collect(
			// Collectors.toList())
			// : new ArrayList<Integer>();
			// for (int j = 0; j < sitesTab.size(); j++) {
			// if (ObjectUtils.isNotEmpty(sitesTab.get(j))) {
			// accountService.addSiteToUser(user.getEmail(), sitesTab.get(j));
			// }
			// }

		} catch (Exception e) {
			model.addAttribute("message_error", e.getMessage());
			return "user/edit";
		}
		model.addAttribute("user", appUser);
		model.addAttribute("message_success", "Ajout effectué avec succès");
		return "user/edit";
	}

	@GetMapping("/profile/edit")
	public String showProfile(Model model) {
		model.addAttribute("user", new AppUser());
		try {
			accountService.findUserById(getCurrentUserId()).ifPresent(updatedUser -> {
				model.addAttribute("user", updatedUser);
			});
		} catch (Exception e) {
			model.addAttribute("message_error", e.getMessage());
			return "user/profile";
		}
		return "user/profile";
	}

	@PostMapping("/profile/edit")
	public String editProfile(Model model, @Valid AppUser user) {
		if (!this.isAdminOrSuppot()) {
			if (!Objects.equals(this.getCurrentUserId(), user.getId())) {
				throw new AccessDeniedException("Impossible de modifier les informations de cet utilisateur");
			}
		}
		appUser = new AppUser();
		try {
			accountService.findUserById(user.getId()).ifPresent(updatedUser -> {
				updatedUser.setLastName(user.getLastName());
				updatedUser.setFirstName(user.getFirstName());
				updatedUser.setPhoneContact(user.getPhoneContact());
				updatedUser.setLastUpdatedBy(this.getCurrentUserId());
				updatedUser.setLastUpdatedAt(new java.util.Date());
				appUser = accountService.update(updatedUser, false);
			});
		} catch (Exception e) {
			model.addAttribute("message_error", e.getMessage());
			return "user/profile";
		}
		model.addAttribute("user", appUser);
		model.addAttribute("message_success", "Modification effectuée avec succès");
		return "user/profile";
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/details/{id}")
	public String detailByUserId(Model model, @PathVariable("id") int id) {
		appUser = new AppUser();
		UserDTO userDTO = new UserDTO();
		try {
			appUser = accountService.getOne(id);
			if (ObjectUtils.isEmpty(appUser)) {
				throw new ResourceNotFoundException("Impossible de restituer les données de l'utilisateur");
			}
		} catch (Exception ex) {
			model.addAttribute("message_error", ex.getMessage());
			return "user/edit";
		}
		BeanUtils.copyProperties(appUser, userDTO);
		userDTO.setRepassword(null);
		model.addAttribute("user", userDTO);
		return "user/edit";
	}

}
