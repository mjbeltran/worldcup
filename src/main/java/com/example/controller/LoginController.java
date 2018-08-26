package com.example.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.example.model.ListMatchsForm;
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
	
	public static final String URL_HABITACLIA = "https://www.habitaclia.com/viviendas-ascensor-viladecans.htm?hab=3&m2=70&ordenar=mas_recientes&pmax=210000";
	public static final String URL_IDEALISTA = "https://www.idealista.com/venta-viviendas/barcelona-barcelona/con-precio-hasta_220000,metros-cuadrados-mas-de_60,de-tres-dormitorios,de-cuatro-cinco-habitaciones-o-mas,ascensor/";
	public static final String URL_FOTOCASA = "https://www.fotocasa.es/es/comprar/pisos/viladecans/todas-las-zonas/l?sortType=publicationDate&latitude=41.3185&longitude=2.01945&maxPrice=225000&minRooms=3&minSurface=80&combinedLocationIds=724,9,8,233,381,8301,0,0,0";


	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		executeScrapingWeb();
		return modelAndView;
	}

	private void executeScrapingWeb() {

		final String username = "tran67@hotmail.com";
		final String password = "hotmail1234";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.live.com");
		props.put("mail.smtp.port", "25");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication(username, password);
			}
		});


		String bodyMessage = dataHabitaclia(URL_HABITACLIA);

		bodyMessage = "HABITACLIA\n\n" + bodyMessage + "FOTOCASA\n\n" + dataFotocasa(URL_FOTOCASA);

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("tran67@hotmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse("mjbeltrangarcia@gmail.com,carlotalv80@gmail.com"));
			message.setSubject("Testing WallaFlat");
			message.setText("Dear Carlota," + "\n\n No spam to my email, please!");
			message.setContent(bodyMessage, "text/html");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	private static String dataFotocasa(String urlFotocasa) {
		String bodyMessage = "";

		// Compruebo si me da un 200 al hacer la petición
		if (getStatusConnectionCode(urlFotocasa) == 200) {

			Document document = getHtmlDocument(urlFotocasa);

			Elements entradas = document.select("div.re-Searchresult-itemRow");
			int i = 0;

			// Paseo cada una de las entradas
			for (Element elem : entradas) {
				String titulo = elem.select("a.re-Card-link").text();
				Elements ele = elem.select("a.re-Card-link");
				String caracteristicas = elem.getElementsByClass("re-Card-priceContainer re-Card-price--featured")
						.toString();

				System.out.println(entradas.get(i) + "               " + titulo + "");
				System.out.println("<TABLE border ='1'><TR><TD>" + entradas.get(i) + "</TD><TD>" + "" + "</TD><tr><td>"
						+ titulo + "</td></tr></table>");
				if (!ele.isEmpty()) {
					System.out.println("<TABLE border ='1'><TR><TD>https://www.fotocasa.es/"
							+ ele.get(0).attributes().get("href") + "</TD><tr></table>");
					bodyMessage = bodyMessage + "<TABLE border ='1'>" + "<TR><TD>https://www.fotocasa.es/"
							+ ele.get(0).attributes().get("href") + "</TD><tr>" + "<TR><TD>" + entradas.get(i)
							+ "</TD><TD>" + "" + "</TD>" + "<tr><td>" + titulo + "</td></tr>" + "<tr><td>"
							+ caracteristicas + "</td></tr>" + "</table>";
				}
				i++;
				// Con el método "text()" obtengo el contenido que hay dentro de las etiquetas
				// HTML
				// Con el método "toString()" obtengo todo el HTML con etiquetas incluidas
			}

		} else
			System.out.println("El Status Code no es OK es: " + getStatusConnectionCode(URL_HABITACLIA));
		return bodyMessage;
	}

	private static String dataHabitaclia(String urlHabitaclia) {

		String bodyMessage = "";

		// Compruebo si me da un 200 al hacer la petición
		if (getStatusConnectionCode(urlHabitaclia) == 200) {

			Document document = getHtmlDocument(URL_HABITACLIA);

			Elements entradas2 = document.select("div.list-item");
			int i = 0;

			// Paseo cada una de las entradas
			for (Element elem : entradas2) {
				String titulo = elem.getElementsByClass("list-item-title").text();
				String caracteristicas = elem.getElementsByClass("list-item-feature").toString();

				System.out.println(entradas2.get(i) + "               " + titulo + "");
				System.out.println("<TABLE border ='1'><TR><TD>" + entradas2.get(i) + "</TD><TD>" + "" + "</TD><tr><td>"
						+ titulo + "</td></tr></table>");
				// System.out.println("<TABLE border
				// ='1'><TR><TD>https://www.fotocasa.es/"+entradas.get(i).attributes().get("href")+"</TD><tr></table>");
				bodyMessage = bodyMessage + "<TABLE border ='1'><TR><TD>" + entradas2.get(i) + "</TD><TD>" + ""
						+ "</TD>" + "<tr><td>" + titulo + "</td></tr>" + "<tr><td>" + caracteristicas + "</td></tr>"
						+ "</table>";
				i++;
				// Con el método "text()" obtengo el contenido que hay dentro de las etiquetas
				// HTML
				// Con el método "toString()" obtengo todo el HTML con etiquetas incluidas
			}

		} else
			System.out.println("El Status Code no es OK es: " + getStatusConnectionCode(URL_HABITACLIA));
		return bodyMessage;
	}

	/**
	 * Con esta método compruebo el Status code de la respuesta que recibo al hacer
	 * la petición EJM: 200 OK 300 Multiple Choices 301 Moved Permanently 305 Use
	 * Proxy 400 Bad Request 403 Forbidden 404 Not Found 500 Internal Server Error
	 * 502 Bad Gateway 503 Service Unavailable
	 * 
	 * @param url
	 * @return Status Code
	 */
	public static int getStatusConnectionCode(String url) {

		Response response = null;

		try {
			// response = Jsoup.connect(urlIdealista).get(); // URL shortened!
			response = Jsoup.connect(url).userAgent(
					"Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.110 Safari/537.36")
					.timeout(100000000).ignoreHttpErrors(true).execute();
		} catch (IOException ex) {
			System.out.println("Excepción al obtener el Status Code: " + ex.getMessage());
		}
		return response.statusCode();
	}

	/**
	 * Con este método devuelvo un objeto de la clase Document con el contenido del
	 * HTML de la web que me permitirá parsearlo con los métodos de la librelia
	 * JSoup
	 * 
	 * @param url
	 * @return Documento con el HTML
	 */
	public static Document getHtmlDocument(String url) {

		Document doc = null;
		try {
			doc = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).get();
		} catch (IOException ex) {
			System.out.println("Excepción al obtener el HTML de la página" + ex.getMessage());
		}
		return doc;
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
	

	@RequestMapping(value = "/grupos", method = RequestMethod.POST)
	public ModelAndView registrationGroups(Grupo grupo) {
		ModelAndView modelAndView = new ModelAndView();
//		Users user = new Users();
//		modelAndView.addObject("users", user);
//		modelAndView.setViewName("registration");
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
		modelAndView.addObject("partido", new Match());

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
					match.setGroupo(gr.getNameGroup());
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
		gA.setNameGroup("Grupo-A");
		gA.setRunnerup(groupA.getRunnerup());
		gA.setWinner(groupA.getWinner());
		listGroup.add(gA);

		B groupB = groups.getB();
		Grupo gB = new Grupo();
		gB.setMatches(groupB.getMatches());
		gB.setNameGroup("Grupo-B");
		gB.setRunnerup(groupB.getRunnerup());
		gB.setWinner(groupB.getWinner());
		listGroup.add(gB);

		C groupC = groups.getC();
		Grupo gC = new Grupo();
		gC.setMatches(groupC.getMatches());
		gC.setNameGroup("Grupo-C");
		gC.setRunnerup(groupC.getRunnerup());
		gC.setWinner(groupC.getWinner());
		listGroup.add(gC);

		D groupD = groups.getD();
		Grupo gD = new Grupo();
		gD.setMatches(groupD.getMatches());
		gD.setNameGroup("Grupo-D");
		gD.setRunnerup(groupD.getRunnerup());
		gD.setWinner(groupD.getWinner());
		listGroup.add(gD);

		E groupE = groups.getE();
		Grupo gE = new Grupo();
		gE.setMatches(groupE.getMatches());
		gE.setNameGroup("Grupo-E");
		gE.setRunnerup(groupE.getRunnerup());
		gE.setWinner(groupE.getWinner());
		listGroup.add(gE);

		F groupF = groups.getF();
		Grupo gF = new Grupo();
		gF.setMatches(groupF.getMatches());
		gF.setNameGroup("Grupo-F");
		gF.setRunnerup(groupF.getRunnerup());
		gF.setWinner(groupF.getWinner());
		listGroup.add(gF);

		G groupG = groups.getG();
		Grupo gG = new Grupo();
		gG.setMatches(groupG.getMatches());
		gG.setNameGroup("Grupo-G");
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
	
	
	@RequestMapping(value = "/saveMatchs", method = RequestMethod.POST)
	public ModelAndView saveMatchs(ListMatchsForm listMatchs) {
		ModelAndView modelAndView = new ModelAndView();
		return modelAndView;
	}
}
