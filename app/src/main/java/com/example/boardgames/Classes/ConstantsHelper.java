package com.example.boardgames.Classes;

import java.text.DateFormat;

public class ConstantsHelper {

    public static final String LOGGED = "logged";
    public static final String USERNAME = "username";

    public static final String REQUEST_URL_LOGIN = "https://boardgamegeek.com/login/api/v1";
    public static final String REQUEST_URL_REGISTER = "https://boardgamegeek.com/api/accounts";

    public static final String USER_XML_URL = "https://api.geekdo.com/xmlapi2/user?name=";
    public static final String HOTNESS_XML_URL = "https://api.geekdo.com/xmlapi2/hot?type=boardgame";
    public static final String TOP100_XML_URL = "https://www.boardgamegeek.com/xmlapi2/forum?id=10&page=1";
    public static final String HOTNESS_GAME_URL = "https://api.geekdo.com/xmlapi2/thing=boardgame?id=";

    public static final String SHARE_PREFERENCE_FILE = "MyPrefs";

    //Fri, 20 Mar 2020 16:33:06 +0000
    public static final String DATE_FORMAT = "E, d MMM yyyy HH:mm:ss Z";
    public static final int DATE_FORMAT_TO = DateFormat.LONG;

    public static final long MIN_TIME_MS = 10;
    public static final long MIN_DISTANCE_MIN = 1000 * 60 * 1;

}
