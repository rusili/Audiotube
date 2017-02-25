package nyc.c4q.rusili.audiotube.retrofit.JSON;

import java.util.ArrayList;
import java.util.List;

public class JSONResponse {
    private List <items> items = new ArrayList <>();

    public List <JSONResponse.items> getItems () {
        return items;
    }

    public class items {
        String kind;
        String id;
        snippet snippet;
        contentDetails contentDetails;

        public String getKind () {
            return kind;
        }

        public String getId () {
            return id;
        }

        public JSONResponse.items.snippet getSnippet () {
            return snippet;
        }

        public JSONResponse.items.contentDetails getContentDetails () {
            return contentDetails;
        }

        public class snippet {
            String channelId;
            String title;
            String description;
            String channelTitle;

            public String getChannelId () {
                return channelId;
            }

            public String getTitle () {
                return title;
            }

            public String getDescription () {
                return description;
            }

            public String getChannelTitle () {
                return channelTitle;
            }
        }

        public class contentDetails {
            public String getDuration () {
                return duration;
            }

            String duration;
        }
    }
}
