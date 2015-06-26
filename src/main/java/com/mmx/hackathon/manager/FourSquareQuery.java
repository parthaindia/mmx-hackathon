package com.mmx.hackathon.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Partha
 */
public class FourSquareQuery {

    public static String readJsonFromUrl(String url) throws IOException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            String json = (String) (jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public ArrayList<HashMap> userInfoQuery(String lati, String longi, String search_query) throws Exception {
        if (lati == null || lati.equals("") || longi == null || longi.equals("") || search_query == null || search_query.equals("")) {
            return null;
        }
        Map param = new HashMap();
        param.put("lattitude", lati);
        param.put("longitude", longi);
        param.put("searchquery", search_query);
        DBManager.getDB().addDefaultMap("searchQuery", param);
//        String url = "https://api.foursquare.com/v2/venues/search?client_id=VDR00UBHJK2Z5PBCHQPG1HFE5BGFIRHF4ILWB0NEX512J2JQ&client_secret=DBSSDIAGE3NZ0YNJQUSUUYBBYQOPSJY4GIR14VB20GA1MYJN&v=20130815&ll=12.9667,77.5667&query=sushi";
        String url = "https://api.foursquare.com/v2/venues/search?client_id=VDR00UBHJK2Z5PBCHQPG1HFE5BGFIRHF4ILWB0NEX512J2JQ&client_secret=DBSSDIAGE3NZ0YNJQUSUUYBBYQOPSJY4GIR14VB20GA1MYJN&v=20130815&ll=" + lati + "," + longi + "&query=" + search_query;

        ArrayList<HashMap> retList = new ArrayList();

        String resultJson = readJsonFromUrl(url);
        JSONObject jobj = new JSONObject(resultJson);
        JSONObject responseObj = jobj.getJSONObject("response");

        if (responseObj != null) {
            JSONArray jbj1 = responseObj.getJSONArray("venues");
            if (jbj1 != null) {
                for (int i = 0; i < jbj1.length(); i++) {
                    JSONObject jb = (JSONObject) jbj1.get(i);
                    if (!jb.getString("id").equals("null")) {
                        HashMap<String, Object> rowMap = new HashMap<String, Object>();
                        rowMap.put("eventID", jb.getString("id"));
                        rowMap.put("contact", jb.get("contact"));
                        rowMap.put("name", jb.getString("name"));
                        JSONObject jlocation = jb.getJSONObject("location");
                        if (jlocation != null) {
                            String jlat = jlocation.get("lat") + "";
                            String jlng = jlocation.get("lng") + "";
                            rowMap.put("latitude", jlat);
                            rowMap.put("longitude", jlng);

                            JSONArray jformatedAdd = jlocation.getJSONArray("formattedAddress");
                            if (jformatedAdd != null) {
                                String address = "";
                                for (int f = 0; f < jformatedAdd.length(); f++) {

                                    if (!address.equals("")) {
                                        address = address + "," + (String) jformatedAdd.get(f);
                                    } else {
                                        address = (String) jformatedAdd.get(f);
                                    }
                                }

                                rowMap.put("formatedAdd", address);

                            }
                        }

                        JSONObject stats = jb.getJSONObject("stats");
                        if (stats != null) {
                            String checkinsCount = stats.getInt("checkinsCount") + "";
                            String usersCount = stats.getInt("usersCount") + "";
                            String tipCount = stats.getInt("tipCount") + "";

                            rowMap.put("checkinsCount", checkinsCount);
                            rowMap.put("usersCount", usersCount);
                            rowMap.put("tipCount", tipCount);

                        }

                        JSONArray jcat = jb.getJSONArray("categories");
                        if (jcat != null) {
                            for (int j = 0; j < jcat.length(); j++) {
                                JSONObject jbcatObj = (JSONObject) jcat.get(j);
                                if (jbcatObj != null) {
                                    rowMap.put("catId", jbcatObj.getString("id"));
                                }

                            }
                        }
                        retList.add(rowMap);
                    }

                }
            }
        }
        return retList;
    }

    public List<HashMap> defaultSearchQuery(String lati, String longi) throws Exception {
        String[] queryList = {"food", "hotel", "restarents", "bikes", "punchershop", "hospital"};

        ArrayList finalList = new ArrayList();
        for (int i = 0; i < queryList.length; i++) {
            ArrayList<HashMap> initialList = new ArrayList<HashMap>();
            initialList = userInfoQuery(lati, longi, queryList[i]);
            finalList.addAll(initialList);
        }

        return finalList;

    }

    public JSONObject locationNews(String longitude, String latitude) throws IOException, JSONException {

        String url = "https://alertifyme-news.p.mashape.com/news.php?X-Mashape-Key=HougjGKsd6mshiXvuUCRCD0MyhR8p1waKoljsnnMvb8IRLE07A&Accept=application/json";

        ArrayList<HashMap> retList = new ArrayList();

        String resultJson = readJsonFromUrl(url);
        JSONObject jobj = new JSONObject(resultJson);

        return jobj;

    }
}
