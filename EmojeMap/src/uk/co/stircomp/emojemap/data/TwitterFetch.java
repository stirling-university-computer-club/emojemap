package uk.co.stircomp.emojemap.data;

import java.util.Iterator;

import twitter4j.GeoLocation;
import twitter4j.Status;
import twitter4j.TwitterException;
import uk.co.stircomp.emojemap.SearchTwitter;

public class TwitterFetch {
	private final String[] dictAngry = {"angry", "pissed",
			"annoyed", "irritated", "fuck", "shit", "asshole",
			"bastard", "wütend", "besoffen", "verärgert",
			"gereizt", "scheiße", "arschloch", "bastard",
			"ядосан", "пиян-залян", "раздразнен", "раздразнена",
			"дяволите", "лайна", "задник", "копеле", "en colère",
			"bourré", "agacé", "irrité", "merde", "connard",
			"θυμωμένος", "νευριάσει", "ενοχλημένος", "ερεθισμένο",
			"γαμώ", "σκατά", "μαλάκα", "μπάσταρδος", "naštvaný",
			"podrážděný", "souložit", "hovno", "kretén",
			"rozzlobený", "vred", "irriteret", "fanden", "lort",
			"røvhul", "skiderik", "vihane", "pahane", "ärritunud",
			"pask", "sitapea", "värdjas", "suuttunut", "kännissä",
			"harmissaan", "ärtynyt", "naida", "paska", "kusipää",
			"dühös", "részeg", "bosszús", "irritált", "fasz",
			"szar", "seggfej", "fattyú", "feargach", "ag fuck",
			"cac", "bastaird", "arrabbiato", "sbronzo",
			"infastidito", "irritato", "fanculo", "merda",
			"stronzo", "bastardo", "dusmīgs", "sapīcis", 
			"iekaisušas", "jāšanās", "sūdi", "bastards", "piktas",
			"įsiutęs", "pasipiktinę", "suirzęs", "pyktis",
			"šūdas", "šikna", "šunsnukis", "blet", "naxuj", "nx",
			"kurva", "zertva", "zertwa", "kekse", "pyderastas",
			"pydaras", "pyderas", "gaidys", "boos", "dronken",
			"geërgerd", "geïrriteerde", "neuken", "stront", "lul",
			"bastaard", "zangado", "bêbado", "aborrecido",
			"irritada", "porra", "merda", "idiota", "desgraçado",
			"rrabjata", "imdejqa", "irritata", "bagħal", 
			"nahnevaný", "naštvaný", "podráždený", "súložiť",
			"hovno", "kretén", "jezen", "motenih", "razdraženo",
			"vraga", "sranje", "kreten", "arg", "skit", "rövhål",
			"jävel", "ljut", "uzbuđen", "razdražen", "jebati",
			"sranje", "šupak", "kopile", "sint", "forbanna",
			"irritert", "faen", "dritt", "drittsekk"};
	
	private final String[] dictHappy = {"happy", "great", "cheerful",
			"amazing", "awesome", "smile", "smiling", "pleased",
			"thank", "yay", "good", "glücklich", "fröhlich",
			"erstaunlich", "ehrfürchtige", "Lächeln", "lächelnd",
			"zufrieden", "danken", "gut", "щастлив",
			"радостен", "удивителен", "страхотен", "усмивка",
			"усмихнати", "доволен", "благодаря", "добър",
			"heureux", "gai", "incroyable", "impressionnant",
			"sourire", "souriant", "heureux", "merci",
			"bon", "ευτυχισμένος", "χαρούμενος", "καταπληκτικός",
			"φοβερός", "χαμόγελο", "χαμογελαστά",
			"ευχαριστημένος", "ευχαριστώ", "καλός",
			"šťastný", "veselý", "úžasný", "děsivý", "úsměv",
			"úsměvem", "potěšený", "poděkovat", "dobrý",
			"munter", "fantastisk", "smil", "smilende", "glad",
			"takgod", "õnnelik", "rõõmus", "hämmastav",
			"aukartust äratav", "naeratus", "naerune", "rahul",
			"tänama", "jess", "hea", "onnellinen", "iloinen",
			"hämmästyttävä", "mahtava", "hymy", "hymyilevä",
			"tyytyväinen", "kiittää", "hyvä", "boldog", "vidám",
			"elképesztő", "döbbenetes", "mosoly", "mosolyogva",
			"elégedett", "köszönet", "jó", "sásta", "iontach",
			"uamhnach", "aoibh gháire", "miongháire", "sásta",
			"go raibh maith", "maith", "felice", "allegro",
			"sorprendente", "imponente", "sorriso", "sorridente",
			"contento", "grazie", "buono", "laimīgs", "jautrs",
			"pārsteidzošs", "laba", "smaids", "smiling", 
			"priecīgs", "pateikties", "labs", "laimingas", 
			"linksmas",	"nuostabus", "puikus", "šypsena", 
			"šypsosi", "patenkintas", "dėkoju",	"geras", "aciu",
			"gelukkig", "vrolijk", "verbazingwekkend",
			"ontzagwekkend", "glimlach", "glimlachen", "tevreden",
			"bedanken", "goed", "feliz", "alegre", "surpreendente",
			"impressionante", "sorriso", "sorridente", 
			"satisfeito", "obrigado", "bom", "kuntenti", 
			"ferrieħa", "aqwa", "biża", "daħka", "jitbissem",
			"kuntent", "nirringrazzja", "tajba", "šťastný",
			"veselý", "úžasný", "desivý", "úsmev", "úsmevom",
			"potešený", "poďakovať", "dobrý", "srečna", "veselo",
			"neverjetno", "super", "nasmeh", "nasmejan",
			"zadovoljen", "hvala", "dobra", "lycklig", 
			"fantastiskt", "häftigt", "le", "ler", "tack", "bra",
			"sretan", "veseo", "nevjerojatan", "strašan",
			"osmijeh", "nasmijan", "zadovoljan", "hvala", 
			"dobro", "lykkelig", "munter", "kjempebra", "smil",
			"smilende", "fornøyd", "takke", "god"};
	
	private final String[] dictSad = {"poor", "hurt", "sad",
			"unhappy", "unfair", "broke", "hospital", "death",
			"cry", "cried", "schlecht", "sich", "verletzen",
			"traurig", "unglücklich", "unfair", "pleite",
			"Krankenhaus", "Tod", "Schrei", "rief", "беден",
			"боли", "тъжен", "нещастен", "несправедлив", "проби",
			"болница", "смърт", "вик", "викаха", "pauvres",
			"blesser", "triste", "malheureux", "injuste", "cassé",
			"hôpital", "mort", "pleurer", "se écria", "φτωχός",
			"βλάβη", "λυπημένος", "δυστυχής", "άδικος", "έσπασε",
			"νοσοκομείο", "θάνατος", "κραυγή", "φώναξε", "špatný",
			"bolet", "smutný", "nešťastný", "nespravedlivý",
			"zlomil", "nemocnice", "smrt", "výkřik", "vykřikl",
			"fattige", "ondt", "trist", "ulykkelig", "brød",
			"død", "råb", "råbte", "vaene", "valu", "kurb", 
			"õnnetu", "ebaõiglane", "pankrotis", "haigla", "surm",
			"nutma", "hüüdis", "huono", "satuttaa", "surullinen",
			"onneton", "epäoikeudenmukainen", "hajosi", 
			"sairaala", "kuolema", "itkeä", "huusi", "szegény",
			"fáj", "szomorú", "boldogtalan", "tisztességtelen",
			"tört", "kórház", "halál", "kiáltás", "kiáltotta", "bocht",
			"Gortaítear", "brónach", "míshásta", "éagórach",
			"bhris", "ospidéal", "bás", "caoin", "povero", "male",
			"triste", "infelice", "ingiusto", "rotto", "ospedale",
			"morte", "grido", "gridò", "slikts", "ievainots",
			"skumjš", "nelaimīgs", "negodīgs", "izputējis",
			"slimnīca", "nāve", "sauciens", "iesaucās", "prastas",
			"sužeistas", "liūdnas", "nelaimingas", "nesąžiningas",
			"sumušė", "ligoninė", "mirtis", "rekia", "šaukė",
			"pijn doen", "verdrietig", "ongelukkig", "oneerlijk",
			"brak", "ziekenhuis", "dood", "schreeuw", "riep",
			"pobre", "machucar", "triste", "infeliz", "injusto",
			"quebrado", "morte", "grito", "chorou", "foqra",
			"iweġġgħu", "diqa", "ināusta", "kissru", "isptar",
			"mewt", "zlý", "bolieť", "smutný", "nešťastný", 
			"nespravodlivý", "zlomil", "nemocnice", "smrť",
			"výkrik", "vykríkol", "slaba", "boli", "žalostno",
			"nesrečen", "nepošteno", "zlomil", "bolnišnica",
			"smrt", "jok", "je vzkliknil", "dålig", "ont", 
			"sorgligt", "olycklig", "orättvist", "bröt", 
			"sjukhus", "död", "rop", "grät", "siromašan",
			"ozlijeđen", "tužan", "nezadovoljan", "nepravedan",
			"razbio", "bolnica", "smrt", "vapaj", "plakala",
			"dårlig", "vondt", "trist", "ulykkelig",
			"urettferdig", "blakk", "sykehus", "død", "gråte",
			"gråt"};
	
	public TwitterFetch(DataManager dm) {
		
		GeoLocation[] locs = new GeoLocation[31];
		// United Kingdom
		locs[0]  = new GeoLocation(51.479114, -0.344507);
		// Austria
		locs[1]  = new GeoLocation(47.179770, 10.883520);
		// Belgium
		locs[2] = new GeoLocation(50.311810, 5.647682);
		// Bulgaria
		locs[3] = new GeoLocation(43.146300, 24.010447);
		// Cyprus
		locs[4] = new GeoLocation(34.900812, 32.947825);
		// Czech Republic
		locs[5] = new GeoLocation(50.233787, 13.684490);
		// Denmark
		locs[6] = new GeoLocation(57.174257, 9.935044);
		// Estonia
		locs[7] = new GeoLocation(57.984111, 26.826522);
		// Finland
		locs[8] = new GeoLocation(62.110607, 25.140876);
		// France
		locs[9] = new GeoLocation(44.797641, 1.231944);
		// Germany
		locs[10] = new GeoLocation(48.411170, 9.570567);
		// Greece
		locs[11] = new GeoLocation(39.102816, 22.130719);
		// Hungary
		locs[12] = new GeoLocation(47.248415, 17.350716);
		// Ireland
		locs[13] = new GeoLocation(52.188952, -8.924238);
		// Italy
		locs[14] = new GeoLocation(46.001021, 11.791484);
		// Latvia
		locs[15] = new GeoLocation(56.896384, 22.351146);
		// Lithuania
		locs[16] = new GeoLocation(57.081929, 25.086741);
		// Luxemburg
		locs[17] = new GeoLocation(49.672327, 6.124948);
		// Malta
		locs[18] = new GeoLocation(35.896627, 14.412535);
		// Netherlands
		locs[19] = new GeoLocation(52.368225, 4.864127);
		// Poland
		locs[20] = new GeoLocation(50.307645, 19.476077);
		// Portugal
		locs[21] = new GeoLocation(53.210036, 16.828372);
		// Romania
		locs[22] = new GeoLocation(44.678628, 28.283095);
		// Slovakia
		locs[23]  = new GeoLocation(48.217173, 17.264880);
		// Slovenia
		locs[24] = new GeoLocation(46.205147, 14.137271);
		// Spain
		locs[25] = new GeoLocation(42.768888, -7.676614);
		// Sweden
		locs[26] = new GeoLocation(57.192143, 14.180188);
		// Croatia
		locs[27] = new GeoLocation(44.228954, 15.750099);
		// Norway
		locs[28] = new GeoLocation(59.632641, 9.877080);
		// Ukraine
		locs[29] = new GeoLocation(49.926425, 24.242031);
		// Andorra
		locs[30] = new GeoLocation(42.543404, 1.567198);		
		
		
		for (int l = 0; l < locs.length; l++) {
			
			processRequest(dm, "", locs[l]);
			
		}

	}

	private void processRequest(DataManager dm, String query, GeoLocation geo) {

		try {

			Iterator<Status> iter = SearchTwitter.search(query, geo)
					.getTweets().iterator();
			while (iter.hasNext()) {
				Status status = iter.next();
				
				// Get the location and add to the map.
				if (status.getGeoLocation() != null) {
					//System.out.println("<<" + status.getText() + ">>");
					String city = dm.getGeoResolver().findCountry(
							status.getGeoLocation().getLatitude(),
							status.getGeoLocation().getLongitude());					
					
					int happiness = captureOccurrences(status.getText(), Emotion.HAPPY);
					int anger = captureOccurrences(status.getText(), Emotion.ANGRY);
					int sad = captureOccurrences(status.getText(), Emotion.SAD);
					
					// Since we are happy then let's improve regional happiness and decrease sadness
					dm.modifyIndex(Region.getRegionIndex(city), Emotion.HAPPY, 1.0f + ((float)happiness / 100));
					dm.modifyIndex(Region.getRegionIndex(city), Emotion.ANGRY, 1.0f - ((float)happiness / 100));
					dm.modifyIndex(Region.getRegionIndex(city), Emotion.SAD, 1.0f - ((float)happiness / 100));
					
				}

			}

		} catch (TwitterException e) {
			e.printStackTrace();
		}

	}
	
	private int captureOccurrences(String message, int emotion) {
				
		// Start the emotional switch		
		String[] dictionary = null;
		
		switch (emotion) {
		
		case Emotion.ANGRY:	
			dictionary = dictAngry;
			break;
			
		case Emotion.FEAR:	
			dictionary = dictAngry;
			break;
			
		case Emotion.HAPPY:	
			dictionary = dictHappy;
			break;
			
		case Emotion.SAD:	
			dictionary = dictSad;
			break;
			
		default:
			break;
		
		
		} // End emotion switch
		
		// Return the modifier.
		return countDictionaryOccurrences(message, dictionary);
		
	}
	
	private int countDictionaryOccurrences(String message, String[] dictionary) {
		
		int result = 0;
		message = message.toLowerCase();
		
		for (int i = 0; i < dictionary.length; i++) {
			
			if (message.contains(dictionary[i])) {
				result++;
			}
			
		}
		
		//result *= 10;
		
		if (result > 100) result = 99;
		
		return result;
		
	}

}
