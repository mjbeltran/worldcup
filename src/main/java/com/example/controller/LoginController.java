package com.example.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.example.model.Match;
import com.example.model.Team;
import com.example.model.Users;
import com.example.model.worldcupinfo.A;
import com.example.model.worldcupinfo.B;
import com.example.model.worldcupinfo.C;
import com.example.model.worldcupinfo.D;
import com.example.model.worldcupinfo.E;
import com.example.model.worldcupinfo.F;
import com.example.model.worldcupinfo.G;
import com.example.model.worldcupinfo.Groups;
import com.example.model.worldcupinfo.Grupo;
import com.example.model.worldcupinfo.H;
import com.example.model.worldcupinfo.Stadiums;
import com.example.model.worldcupinfo.Teams;
import com.example.model.worldcupinfo.WorldCupInfo;
import com.example.service.MatchService;
import com.example.service.TeamService;
import com.example.service.UserService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;

	@Autowired
	private TeamService teamService;

	@Autowired
	private MatchService matchService;

	private List<Stadiums> listStadiums;

	private Users user;

	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ModelAndView registration() {
		ModelAndView modelAndView = new ModelAndView();
		Users user = new Users();
		modelAndView.addObject("users", user);
		modelAndView.setViewName("registration");
		return modelAndView;
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid Users user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		Users userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult.rejectValue("email", "error.user",
					"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			// modelAndView.addObject("user", user);
			modelAndView.setViewName("registration");
		} else {
			userService.saveUser(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("users", new Users());
			modelAndView.setViewName("registration");

		}
		return modelAndView;
	}

	@RequestMapping(value = "/admin/home", method = RequestMethod.GET)
	public ModelAndView home() throws JsonParseException, JsonMappingException, IOException {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Users user = userService.findUserByEmail(auth.getName());
		this.user = user;
		modelAndView.addObject("userName",
				"Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");

		Groups groups = getInfoWorlCup();
		modelAndView.addObject("groups", groups.getListeGroup());
		modelAndView.setViewName("admin/home");
		return modelAndView;
	}

	private Groups getInfoWorlCup() throws JsonParseException, JsonMappingException, IOException {

		final String uri = "https://raw.githubusercontent.com/lsv/fifa-worldcup-2018/master/data.json";

		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(uri, String.class);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		WorldCupInfo value = mapper.readValue(result, WorldCupInfo.class);

		this.listStadiums = new ArrayList<>();
		for (int i = 0; i < value.getStadiums().length; i++) {
			this.listStadiums.add(value.getStadiums()[i]);
		}
		getTeamsForGroups(value);
		insertTeamsBd(value);
		return value.getGroups();

	}

	private void insertTeamsBd(WorldCupInfo value) {

		Teams[] teams = value.getTeams();
		String nameTeam1 = teams[0].getName();
		Team team;
		if (teamService.findUserByName(nameTeam1) == null) {
			for (int i = 0; i < teams.length; i++) {
				team = new Team();
				team.setFlag(teams[i].getFlag());
				team.setName(teams[i].getName());
				team.setEmoji(teams[i].getEmoji());
				team.setIso2(teams[i].getIso2());
				teamService.saveTeam(team);
			}
		}

		if (matchService.findByStadium("1").isEmpty()) {
			Match match;
			Team homeTeam;
			Team awayTeam;
			for (Grupo gr : value.getGroups().getListeGroup()) {
				for (int i = 0; i < gr.getMatches().length; i++) {
					match = new Match();
					homeTeam = teamService.findUserByName(gr.getMatches()[i].getHome_equipo().getName());
					awayTeam = teamService.findUserByName(gr.getMatches()[i].getAway_equipo().getName());
					match.setHomeTeam(homeTeam);
					match.setAwayTeam(awayTeam);
					match.setDate(new Date(gr.getMatches()[i].getDate()));
					match.setStadium(gr.getMatches()[i].getStadium());
					matchService.saveMatch(match);
				}
			}
		}

	}

	private void getTeamsForGroups(WorldCupInfo value) {

		SimpleDateFormat fdIni = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat fdFin = new SimpleDateFormat("dd/MM/yyyy");
		Date date;
		dealGroups(value.getGroups());
		Teams[] teams = value.getTeams();
		for (Grupo gr : value.getGroups().getListeGroup()) {
			for (int i = 0; i < gr.getMatches().length; i++) {
				int indexHome = Integer.valueOf(gr.getMatches()[i].getHome_team());
				int indexAway = Integer.valueOf(gr.getMatches()[i].getAway_team());
				gr.getMatches()[i].setHome_equipo(teams[indexHome - 1]);
				gr.getMatches()[i].setAway_equipo(teams[indexAway - 1]);
				try {
					date = fdIni.parse(gr.getMatches()[i].getDate().substring(0, 10));
					gr.getMatches()[i].setDate(fdFin.format(date));
				} catch (ParseException e) {
				}
			}
		}

		// A groupA = value.getGroups().getA();
		// Teams[] teams = value.getTeams();
		// for(int i = 0; i< groupA.getMatches().length; i++) {
		// int indexHome = Integer.valueOf(groupA.getMatches()[i].getHome_team());
		// int indexAway = Integer.valueOf(groupA.getMatches()[i].getAway_team());
		// groupA.getMatches()[i].setHome_equipo(teams[indexHome]);
		// groupA.getMatches()[i].setAway_equipo(teams[indexAway]);
		// }

	}

	private void dealGroups(Groups groups) {

		List<Grupo> listGroup = new ArrayList<Grupo>();
		//
		A groupA = groups.getA();
		Grupo gA = new Grupo();
		gA.setMatches(groupA.getMatches());
		gA.setNameGroup("GrupoA");
		gA.setRunnerup(groupA.getRunnerup());
		gA.setWinner(groupA.getWinner());
		listGroup.add(gA);

		B groupB = groups.getB();
		Grupo gB = new Grupo();
		gB.setMatches(groupB.getMatches());
		gB.setNameGroup("GrupoB");
		gB.setRunnerup(groupB.getRunnerup());
		gB.setWinner(groupB.getWinner());
		listGroup.add(gB);

		C groupC = groups.getC();
		Grupo gC = new Grupo();
		gC.setMatches(groupC.getMatches());
		gC.setNameGroup("GrupoC");
		gC.setRunnerup(groupC.getRunnerup());
		gC.setWinner(groupC.getWinner());
		listGroup.add(gC);

		D groupD = groups.getD();
		Grupo gD = new Grupo();
		gD.setMatches(groupD.getMatches());
		gD.setNameGroup("GrupoD");
		gD.setRunnerup(groupD.getRunnerup());
		gD.setWinner(groupD.getWinner());
		listGroup.add(gD);

		E groupE = groups.getE();
		Grupo gE = new Grupo();
		gE.setMatches(groupE.getMatches());
		gE.setNameGroup("GrupoE");
		gE.setRunnerup(groupE.getRunnerup());
		gE.setWinner(groupE.getWinner());
		listGroup.add(gE);

		F groupF = groups.getF();
		Grupo gF = new Grupo();
		gF.setMatches(groupF.getMatches());
		gF.setNameGroup("GrupoF");
		gF.setRunnerup(groupF.getRunnerup());
		gF.setWinner(groupF.getWinner());
		listGroup.add(gF);

		G groupG = groups.getG();
		Grupo gG = new Grupo();
		gG.setMatches(groupG.getMatches());
		gG.setNameGroup("GrupoG");
		gG.setRunnerup(groupG.getRunnerup());
		gG.setWinner(groupG.getWinner());
		listGroup.add(gG);

		H groupH = groups.getH();
		Grupo gH = new Grupo();
		gH.setMatches(groupH.getMatches());
		gH.setNameGroup("GrupoH");
		gH.setRunnerup(groupH.getRunnerup());
		gH.setWinner(groupH.getWinner());
		listGroup.add(gH);

		groups.setListeGroup(listGroup);

	}

	@RequestMapping(value = "/staduims", method = RequestMethod.GET)
	public ModelAndView staduims() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("userName", user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("stadiums", this.listStadiums);
		modelAndView.setViewName("stadiums");
		return modelAndView;
	}
}
