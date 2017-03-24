package nyc.c4q.rusili.audiotube.utility;

public class Constants {
    public static final String APP_NAME = "Audiotube";

    public static final String DEVELOPER_KEY = "AIzaSyDckagymH1dcK_WWeCyD908ix1OHPZbDpY";
    public static final String BASE_YOUTUBE_URL = "https://www.googleapis.com/youtube/v3/";

    public interface ACTION {
        public static String REPEAT_ACTION = "repeat";
        public static String MAIN_ACTION = "main";
        public static String PREV_ACTION = "prev";
        public static String PLAY_ACTION = "play";
        public static String NEXT_ACTION = "next";
        public static String STARTFOREGROUND_ACTION = "startservice";
        public static String STOPFOREGROUND_ACTION = "stopservice";
        public static String PAUSE_ACTION = "pause";
    }

    public interface NOTIFICATION_ID {
        public static int FOREGROUND_SERVICE = 101;
    }
}
