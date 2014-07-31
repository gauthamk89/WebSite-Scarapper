
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.StringTokenizer;

public class InterestingPictureModel {

    private String pictureTag;
    private String pictureURL;
    private String newpictureURL;
    private String mobileURL;
    private String heading;

    /**Search for the image*/
    public void SearchImage(String searchTag) {
        pictureTag = searchTag;
        String response = "";
        try {
            /** Create a URL for the desired page            */
            URL url = new URL("https://images.nga.gov/en/search/show_advanced_search_page/?service=search&action=do_advanced_search&form_name=default&artist_last_name=" + pictureTag + "&Classification=Painting&open_access=1");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Read all the text returned by the server
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String str;
            while ((str = in.readLine()) != null) {
                // str is one line of text readLine() strips newline characters
                response += str;
            }
            in.close();
        } catch (IOException e) {
            // Do something reasonable.  This is left for students to do.
        }

        
        int startfarm = response.indexOf("grid_item_list_text = ");
        
        /**only start looking the above pattern is specified*/
        int endfarm = response.indexOf(";", startfarm);
        /** Search for the ; in the response*/
        pictureURL = response.substring(startfarm + 23, endfarm - 1);

        /** SPlit the string based on the delimiter "," and get the assestID into an array*/
        StringTokenizer st2 = new StringTokenizer(pictureURL, ",");
        String arr[] = new String[st2.countTokens()];
        int i = 0;

        /** Check if the array length to see if artist is found 
         * in the database or not
         */
        if(arr.length <= 0)
        {
           newpictureURL = "not found";
           heading = "not found";
           return;
        }
        
        while (st2.hasMoreElements()) {
            arr[i] = (String) st2.nextElement();
            i = i + 1;
        }

        /** Get the picture URL*/
        pictureURL = response.substring(startfarm + 23, endfarm - 1);
        Random r = new Random();
        int x = r.nextInt(arr.length);
        newpictureURL = "https://images.nga.gov/?service=asset&action=show_preview&asset=" + arr[x];

        /** Get the image URL for the mobile Operation*/
        int start = response.indexOf("assetid=" + "\"" + arr[x] + "\"");
        int end_1 = response.indexOf("src=", start);
        int end_2 = response.indexOf("\"", end_1 + 5);
        mobileURL = response.substring(end_1 + 5, end_2);

        /** Get the title of the painting*/
        start = response.indexOf("assetid=" + "\"" + arr[x] + "\"");
        end_1 = response.indexOf("title=", start);
        end_2 = response.indexOf("\"", end_1 + 7);
        heading = response.substring(end_1 + 7, end_2);
    }

    /** Return the name of the artist*/
    public String getPictureTag() {
        return (pictureTag);
    }

    /**Get the mobile URL*/
    public String getMobileURL() {
        return (mobileURL);
    }

    /** Get the title*/
    public String getTitle() {
        return (heading);
    }

    /** Get the Desktop URL*/
    public String getDesktopURL() {
        return (newpictureURL);
    }
}
