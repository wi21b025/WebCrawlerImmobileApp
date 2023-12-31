package scraper.src;

import model.User;

import java.util.Map;
import java.util.stream.Collectors;

public class WillhabenLinks
{
    // General parameters

    public static final String BASE_URL = "https://www.willhaben.at/iad/immobilien/";

    public static final String ROWS = "&rows=90";
    public static final String START_PAGE = "page=1";
    public static final String IS_NAVIGATION_TRUE = "&isNavigation=true";


    public static final String HAUS_KAUFEN = BASE_URL + "haus-kaufen/haus-angebote?" + START_PAGE + IS_NAVIGATION_TRUE + ROWS;
    public static final String HAUS_MIETEN = BASE_URL + "haus-mieten/haus-angebote?" + START_PAGE + IS_NAVIGATION_TRUE + ROWS;
    public static final String WOHNUNG_KAUFEN =BASE_URL + "eigentumswohnung/eigentumswohnung-angebote?" + START_PAGE + IS_NAVIGATION_TRUE + ROWS;
    public static final String WOHNUNG_MIETEN =BASE_URL + "mietwohnungen/mietwohnung-angebote?" + START_PAGE + IS_NAVIGATION_TRUE + ROWS;
    public static final String GRUNDSTUECKE =BASE_URL + "grundstuecke/grundstueck-angebote?" + START_PAGE + IS_NAVIGATION_TRUE + ROWS;


    // Filters

    public static final String ESTATE_SIZE_FROM = "&ESTATE_SIZE/LIVING_AREA_FROM=";
    public static final String ESTATE_SIZE_TO = "&ESTATE_SIZE/LIVING_AREA_TO=";

    public static final String PRICE_FROM = "&PRICE_FROM=";
    public static final String PRICE_TO = "&PRICE_TO=";

    // Bundesländer
    public static final String BURGERLAND = "&areaId=1";
    public static final String KARNTEN = "&areaId=2";
    public static final String NIEDEROESTERREICH = "&areaId=3";
    public static final String OBEROSTERREICH = "&areaId=4";
    public static final String SALZBURG = "&areaId=5";
    public static final String STEIERMARK = "&areaId=6";
    public static final String TIROL = "&areaId=7";
    public static final String VORARLBERG = "&areaId=8";
    public static final String WIEN = "&areaId=900";


    // Bezirke in Wien
    public static final String WIEN_01 = "&areaId=117223";
    public static final String WIEN_02 = "&areaId=117224";
    public static final String WIEN_03 = "&areaId=117225";
    public static final String WIEN_04 = "&areaId=117226";
    public static final String WIEN_05 = "&areaId=117227";
    public static final String WIEN_06 = "&areaId=117228";
    public static final String WIEN_07 = "&areaId=117229";
    public static final String WIEN_08 = "&areaId=117230";
    public static final String WIEN_09 = "&areaId=117231";
    public static final String WIEN_10 = "&areaId=117232";
    public static final String WIEN_11 = "&areaId=117233";
    public static final String WIEN_12 = "&areaId=117234";
    public static final String WIEN_13 = "&areaId=117235";
    public static final String WIEN_14 = "&areaId=117236";
    public static final String WIEN_15 = "&areaId=117237";
    public static final String WIEN_16 = "&areaId=117238";
    public static final String WIEN_17 = "&areaId=117239";
    public static final String WIEN_18 = "&areaId=117240";
    public static final String WIEN_19 = "&areaId=117241";
    public static final String WIEN_20 = "&areaId=117242";
    public static final String WIEN_21 = "&areaId=117243";
    public static final String WIEN_22 = "&areaId=117244";
    public static final String WIEN_23 = "&areaId=117245";


    // Städte in Burgenland (Burgerland)
    public static final String EISENSTADT = "&areaId=101";
    public static final String EISENSTADT_UMGEBUNG = "&areaId=102";
    public static final String GUSSING = "&areaId=103";
    public static final String JENNERSDORF = "&areaId=104";
    public static final String MATTERSBURG = "&areaId=105";
    public static final String NEUSIEDL_AM_SEE = "&areaId=106";
    public static final String OBERPULLENDORF = "&areaId=107";
    public static final String OBERWART = "&areaId=108";
    public static final String RUST = "&areaId=109";

    // Städte in Kärnten
    public static final String FELDKIRCHEN = "&areaId=201";
    public static final String HERMAGOR = "&areaId=202";
    public static final String KLAGENFURT = "&areaId=203";
    public static final String KLAGENFURT_LAND = "&areaId=204";
    public static final String SANKT_VEIT_AN_DER_GLAN = "&areaId=205";
    public static final String SPITTAL_AN_DER_DRAU = "&areaId=206";
    public static final String VILLACH = "&areaId=207";
    public static final String VILLACH_LAND = "&areaId=208";
    public static final String VOLKERMARKT = "&areaId=209";
    public static final String WOLFSBERG = "&areaId=210";

    // Städte in Niederösterreich
    public static final String AMSTETTEN = "&areaId=301";
    public static final String BADEN = "&areaId=302";
    public static final String BRUCK_AN_DER_LEITHA = "&areaId=303";
    public static final String GMUND = "&areaId=304";
    public static final String GANSERNDORF = "&areaId=305";
    public static final String HOLLABRUNN = "&areaId=306";
    public static final String HORN = "&areaId=307";
    public static final String KORNEUBURG = "&areaId=308";
    public static final String KREMS_LAND = "&areaId=309";
    public static final String KREMS_AN_DER_DONAU = "&areaId=310";
    public static final String LILIENFELD = "&areaId=311";
    public static final String MELK = "&areaId=312";
    public static final String MISTELBACH = "&areaId=313";
    public static final String MODLING = "&areaId=314";
    public static final String NEUNKIRCHEN = "&areaId=315";
    public static final String SANKT_POLTEN = "&areaId=316";
    public static final String SANKT_POLTEN_LAND = "&areaId=317";
    public static final String SCHEIBBS = "&areaId=318";
    public static final String TULLN = "&areaId=319";
    public static final String WAIDHOFEN_AN_DER_THAYA = "&areaId=320";
    public static final String WAIDHOFEN_AN_DER_YBBS = "&areaId=321";
    public static final String WIENER_NEUSTADT = "&areaId=322";
    public static final String WIENER_NEUSTADT_LAND = "&areaId=323";
    public static final String ZWETTL = "&areaId=325";

    // Städte in Oberösterreich
    public static final String BRAUNAU_AM_INN = "&areaId=401";
    public static final String EFERDING = "&areaId=402";
    public static final String FREISTADT = "&areaId=403";
    public static final String GMUNDEN = "&areaId=404";
    public static final String GRIESKIRCHEN = "&areaId=405";
    public static final String KIRCHDORF_AN_DER_KREMS = "&areaId=406";
    public static final String LINZ = "&areaId=407";
    public static final String LINZ_LAND = "&areaId=408";
    public static final String PERG = "&areaId=409";
    public static final String RIED_IM_INNKREIS = "&areaId=410";
    public static final String ROHRBACH = "&areaId=411";
    public static final String SCHARDING = "&areaId=412";
    public static final String STEYR = "&areaId=413";
    public static final String STEYR_LAND = "&areaId=414";
    public static final String URFAHR_UMGEBUNG = "&areaId=415";
    public static final String VOCKLABRUCK = "&areaId=416";
    public static final String WELS = "&areaId=417";
    public static final String WELS_LAND = "&areaId=418";

    // Städte in Salzburg
    public static final String HALLEIN = "&areaId=501";
    public static final String SALZBURG_STADT = "&areaId=502";
    public static final String SALZBURG_UMGEBUNG = "&areaId=503";
    public static final String SANKT_JOHANN_IM_PONGAU = "&areaId=504";
    public static final String TAMSWEG = "&areaId=505";
    public static final String ZELL_AM_SEE = "&areaId=506";

    // Städte in Steiermark
    public static final String BRUCK_MURZZUSCHLAG = "&areaId=601";
    public static final String DEUTSCHLANDSBERG = "&areaId=603";
    public static final String GRAZ = "&areaId=606";
    public static final String GRAZ_UMGEBUNG = "&areaId=610";
    public static final String HARTBERG_FURSTENFELD = "&areaId=611";
    public static final String LEIBNITZ = "&areaId=612";
    public static final String LEOBEN = "&areaId=614";
    public static final String LIEZEN = "&areaId=616";
    public static final String MURAU = "&areaId=617";
    public static final String MURTAL = "&areaId=620";
    public static final String SUDOSTSTEIERMARK = "&areaId=621";
    public static final String VOITSBERG = "&areaId=622";
    public static final String WEIZ = "&areaId=623";

    // Städte in Tirol
    public static final String IMST = "&areaId=701";
    public static final String INNSBRUCK = "&areaId=702";
    public static final String INNSBRUCK_LAND = "&areaId=703";
    public static final String KITZBUHEL = "&areaId=704";
    public static final String KUFSTEIN = "&areaId=705";
    public static final String LANDECK = "&areaId=706";
    public static final String LIENZ = "&areaId=707";
    public static final String REUTTE = "&areaId=708";
    public static final String SCHWAZ = "&areaId=709";

    // Städte in Vorarlberg
    public static final String BLUDENZ = "&areaId=801";
    public static final String BREGENZ = "&areaId=802";
    public static final String DORNBIRN = "&areaId=803";
    public static final String FELDKIRCH = "&areaId=804";

    public static String getUrlForUser(User user)
    {
        Map<String, String> filter = user.getFilter().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> String.valueOf(e.getValue())));
        //String url = buildUrl(filter);
        //System.out.println("Generated URL for " + user.getEmail() + ": " + url);

        // Use the buildUrl method to construct the URL from the filters
        return buildUrl(filter);
    }

    private static String buildUrl(Map<String, String> filter)
    {
        // Determine the base URL based on the category
        String kategorie = filter.get("kategorie");
        String baseUrl = getBaseUrlForKategorie(kategorie);

        // Append parameters from the filter to the base URL
        StringBuilder urlBuilder = new StringBuilder(baseUrl);


        String preisFrom = filter.getOrDefault("preis_from", "").trim();
        String preisTo = filter.getOrDefault("preis_to", "").trim();

        // Append the price_from parameter if it's not empty after trimming
        if (!preisFrom.isEmpty())
        {
            urlBuilder.append(PRICE_FROM).append(preisFrom);
        }

        // Append the price_to parameter if it's not empty after trimming
        if (!preisTo.isEmpty())
        {
            urlBuilder.append(PRICE_TO).append(preisTo);
        }

        // Append each filter parameter
        urlBuilder.append(ESTATE_SIZE_FROM).append(filter.getOrDefault("area_from", ""));
        urlBuilder.append(ESTATE_SIZE_TO).append(filter.getOrDefault("area_to", ""));
        //urlBuilder.append(PRICE_FROM).append(filter.getOrDefault("preis_from", ""));
        //urlBuilder.append(PRICE_TO).append(filter.getOrDefault("preis_to", ""));

        String bundesland = filter.getOrDefault("bundesland", "").toLowerCase();
        String ort = filter.getOrDefault("ort", "");
        String bezirk = filter.getOrDefault("bezirk", "");



      /*  if (filter.containsKey("bezirk") && filter.get("bundesland").equalsIgnoreCase("wien")) {
            String bezirkCode = getWienBezirkCode(filter.get("bezirk"));
            urlBuilder.append(bezirkCode);
        }

        String areaId = getAreaIdForOrt(filter.get("bundesland"), filter.get("ort"));
        if (areaId != null && !areaId.isEmpty()) {
            urlBuilder.append(areaId);
        }
*/

        if (bundesland.equals("wien")) {

            urlBuilder.append(bezirk.isEmpty() ? WIEN : getWienBezirkCode(bezirk));
        } else {

            if (!ort.isEmpty()) {

                String areaId = getAreaIdForOrt(bundesland, ort);
                if (!areaId.isEmpty()) {
                    urlBuilder.append(areaId);
                }
            } else {

                String bundeslandAreaId = getBundeslandAreaId(bundesland);
                if (!bundeslandAreaId.isEmpty()) {
                    urlBuilder.append(bundeslandAreaId);
                }
            }
        }

        return urlBuilder.toString();
    }

    private static String getBundeslandAreaId(String bundesland) {
        switch (bundesland) {
            case "burgenland": return BURGERLAND;
            case "kärnten": return KARNTEN;
            case "niederösterreich": return NIEDEROESTERREICH;
            case "oberösterreich": return OBEROSTERREICH;
            case "salzburg": return SALZBURG;
            case "steiermark": return STEIERMARK;
            case "tirol": return TIROL;
            case "vorarlberg": return VORARLBERG;
            default: return ""; // or handle unknown bundesland
        }
    }

    private static String getBaseUrlForKategorie(String kategorie) {
        switch (kategorie) {
            case "Haus kaufen":
                return HAUS_KAUFEN;
            case "Haus mieten":
                return HAUS_MIETEN;
            case "Wohnung kaufen":
                return WOHNUNG_KAUFEN;
            case "Wohnung mieten":
                return WOHNUNG_MIETEN;
            case "Grundstücke":
                return GRUNDSTUECKE;

            default:
                return "Kategorie fehler"; // or some error handling or default URL
        }
    }

    private static String getOberosterreichOrtCode(String ort) {
        switch (ort) {
            case "Braunau am Inn": return BRAUNAU_AM_INN;
            case "Eferding": return EFERDING;
            case "Freistadt": return FREISTADT;
            case "Gmunden": return GMUNDEN;
            case "Grieskirchen": return GRIESKIRCHEN;
            case "Kirchdorf an der Krems": return KIRCHDORF_AN_DER_KREMS;
            case "Linz": return LINZ;
            case "Linz-Land": return LINZ_LAND;
            case "Perg": return PERG;
            case "Ried im Innkreis": return RIED_IM_INNKREIS;
            case "Rohrbach": return ROHRBACH;
            case "Schärding": return SCHARDING;
            case "Steyr": return STEYR;
            case "Steyr-Land": return STEYR_LAND;
            case "Urfahr-Umgebung": return URFAHR_UMGEBUNG;
            case "Vöcklabruck": return VOCKLABRUCK;
            case "Wels": return WELS;
            case "Wels-Land": return WELS_LAND;
            default: return ""; // or some default handling or error message
        }
    }


    private static String getAreaIdForOrt(String bundesland, String ort) {
        switch (bundesland.toLowerCase()) {
            case "burgenland":
                return getBurgenlandOrtCode(ort);
            case "kärnten":
                return getKarntenOrtCode(ort);
            case "niederösterreich":
                return getNiederosterreichOrtCode(ort);
            case "oberösterreich":
                return getOberosterreichOrtCode(ort);
            case "salzburg":
                return getSalzburgOrtCode(ort);
            case "steiermark":
                return getSteiermarkOrtCode(ort);
            case "tirol":
                return getTirolOrtCode(ort);
            case "vorarlberg":
                return getVorarlbergOrtCode(ort);
            case "wien":
                return getWienBezirkCode(ort);  // Wien is handled by Bezirk
            default:
                return ""; // or some default handling if bundesland is not recognized
        }
    }

    private static String getVorarlbergOrtCode(String ort) {
        switch (ort) {
            case "Bludenz": return BLUDENZ;
            case "Bregenz": return BREGENZ;
            case "Dornbirn": return DORNBIRN;
            case "Feldkirch": return FELDKIRCH;
            default: return ""; // or some default handling or error message
        }
    }

    private static String getTirolOrtCode(String ort) {
        switch (ort) {
            case "Imst": return IMST;
            case "Innsbruck": return INNSBRUCK;
            case "Innsbruck Land": return INNSBRUCK_LAND;
            case "Kitzbühel": return KITZBUHEL;
            case "Kufstein": return KUFSTEIN;
            case "Landeck": return LANDECK;
            case "Lienz": return LIENZ;
            case "Reutte": return REUTTE;
            case "Schwaz": return SCHWAZ;
            default: return ""; // or some default handling or error message
        }
    }

    private static String getSteiermarkOrtCode(String ort) {
        switch (ort) {
            case "Bruck-Mürzzuschlag": return BRUCK_MURZZUSCHLAG;
            case "Deutschlandsberg": return DEUTSCHLANDSBERG;
            case "Graz": return GRAZ;
            case "Graz-Umgebung": return GRAZ_UMGEBUNG;
            case "Hartberg-Fürstenfeld": return HARTBERG_FURSTENFELD;
            case "Leibnitz": return LEIBNITZ;
            case "Leoben": return LEOBEN;
            case "Liezen": return LIEZEN;
            case "Murau": return MURAU;
            case "Murtal": return MURTAL;
            case "Südoststeiermark": return SUDOSTSTEIERMARK;
            case "Voitsberg": return VOITSBERG;
            case "Weiz": return WEIZ;
            default: return ""; // or some default handling or error message
        }
    }

    private static String getNiederosterreichOrtCode(String ort) {
        switch (ort) {
            case "Amstetten": return AMSTETTEN;
            case "Baden": return BADEN;
            case "Bruck an der Leitha": return BRUCK_AN_DER_LEITHA;
            case "Gmünd": return GMUND;
            case "Gänserndorf": return GANSERNDORF;
            case "Hollabrunn": return HOLLABRUNN;
            case "Horn": return HORN;
            case "Korneuburg": return KORNEUBURG;
            case "Krems Land": return KREMS_LAND;
            case "Krems an der Donau": return KREMS_AN_DER_DONAU;
            case "Lilienfeld": return LILIENFELD;
            case "Melk": return MELK;
            case "Mistelbach": return MISTELBACH;
            case "Mödling": return MODLING;
            case "Neunkirchen": return NEUNKIRCHEN;
            case "Sankt Pölten": return SANKT_POLTEN;
            case "Sankt Pölten Land": return SANKT_POLTEN_LAND;
            case "Scheibbs": return SCHEIBBS;
            case "Tulln": return TULLN;
            case "Waidhofen an der Thaya": return WAIDHOFEN_AN_DER_THAYA;
            case "Waidhofen an der Ybbs": return WAIDHOFEN_AN_DER_YBBS;
            case "Wiener Neustadt": return WIENER_NEUSTADT;
            case "Wiener Neustadt Land": return WIENER_NEUSTADT_LAND;
            case "Zwettl": return ZWETTL;
            default: return ""; // or some default handling or error message
        }
    }

    private static String getBurgenlandOrtCode(String ort)
    {
        switch (ort) {
            case "Eisenstadt": return EISENSTADT;
            case "Eisenstadt-Umgebung": return EISENSTADT_UMGEBUNG;
            case "Rust": return RUST;
            case "Neusiedl am See": return NEUSIEDL_AM_SEE;
            case "Oberpullendorf": return OBERPULLENDORF;
            case "Oberwart": return OBERWART;
            case "Güssing": return GUSSING;
            case "Jennersdorf": return JENNERSDORF;
            case "Mattersburg": return MATTERSBURG;
            default: return ""; // or some default handling
        }
    }

    private static String getSalzburgOrtCode(String ort) {
        switch (ort) {
            case "Hallein": return HALLEIN;
            case "Salzburg Stadt": return SALZBURG_STADT;
            case "Salzburg-Umgebung": return SALZBURG_UMGEBUNG;
            case "Sankt Johann im Pongau": return SANKT_JOHANN_IM_PONGAU;
            case "Tamsweg": return TAMSWEG;
            case "Zell am See": return ZELL_AM_SEE;
            default: return ""; // or some default handling or error message
        }
    }

    private static String getKarntenOrtCode(String ort) {
        switch (ort) {
            case "Klagenfurt": return KLAGENFURT;
            case "Villach": return VILLACH;
            case "Spittal an der Drau": return SPITTAL_AN_DER_DRAU;
            case "Feldkirchen": return FELDKIRCHEN;
            case "Völkermarkt": return VOLKERMARKT;
            case "Wolfsberg": return WOLFSBERG;
            case "Sankt Veit an der Glan": return SANKT_VEIT_AN_DER_GLAN;
            case "Hermagor": return HERMAGOR;
            case "Villach Land": return VILLACH_LAND;
            case "Klagenfurt Land": return KLAGENFURT_LAND;

            default: return "";
        }
    }


    private static String getWienBezirkCode(String bezirk) {
        switch (bezirk) {
            case "01": return WIEN_01;
            case "02": return WIEN_02;
            case "03": return WIEN_03;
            case "04": return WIEN_04;
            case "05": return WIEN_05;
            case "06": return WIEN_06;
            case "07": return WIEN_07;
            case "08": return WIEN_08;
            case "09": return WIEN_09;
            case "10": return WIEN_10;
            case "11": return WIEN_11;
            case "12": return WIEN_12;
            case "13": return WIEN_13;
            case "14": return WIEN_14;
            case "15": return WIEN_15;
            case "16": return WIEN_16;
            case "17": return WIEN_17;
            case "18": return WIEN_18;
            case "19": return WIEN_19;
            case "20": return WIEN_20;
            case "21": return WIEN_21;
            case "22": return WIEN_22;
            case "23": return WIEN_23;
            default: return ""; // or some default handling if bezirk is not recognized
        }
    }



}