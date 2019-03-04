package com.dheeraj.user.registration.controller;

import com.dheeraj.user.registration.model.Location;
import com.dheeraj.user.registration.model.pojo.AddressChunk;
import com.dheeraj.user.registration.model.pojo.DayData;
import com.dheeraj.user.registration.service.LocationService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/** Created by dheeraj on 13/09/17. */
@RequestMapping("rest/calorie")
@RestController
public class CalorieRestController {

  @GetMapping("getMyfitnessPalDetails")
  public List<DayData> getMyfitnessPalDetails() throws Exception {
    HttpClient httpclient = HttpClients.createDefault();
    HttpPost httppost = new HttpPost("https://www.myfitnesspal.com/reports/printable_diary");

    DateTime now = new org.joda.time.DateTime();
    now = now.minusDays(1);
    String pattern = "yyyy-MM-dd";
    DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
    String formatted = formatter.print(now);

    System.out.println("formatted" + formatted);

    List<NameValuePair> params = new ArrayList<NameValuePair>();
    params.add(new BasicNameValuePair("username", "Dheeraj2311"));
    params.add(new BasicNameValuePair("from", "2019-01-27"));
    params.add(new BasicNameValuePair("ds1", "2019-01-27"));
    params.add(new BasicNameValuePair("show_food_diary", "1"));
    params.add(new BasicNameValuePair("to", formatted));
    params.add(new BasicNameValuePair("ds2", formatted));
    params.add(
        new BasicNameValuePair(
            "authenticity_token", "fHGiOIvuGMPSig0/CkSbHIjO8x8SzarNMSIMGeQdk7w="));
    httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

    HttpResponse response = httpclient.execute(httppost);
    HttpEntity entity = response.getEntity();

    List<DayData> dayDataList = new ArrayList<DayData>();
    int slno = 0;
    if (entity != null) {
      InputStream instream = entity.getContent();
      String result = getStringFromInputStream(instream);
      Document doc = Jsoup.parse(result);
      Elements dateElements = doc.getElementsByTag("h2");
      Elements dataElements = doc.getElementsByTag("tfoot");
      for (int i = 0; i < dataElements.size(); i++) {
        Elements tdElements = dataElements.get(i).getElementsByTag("td");
        dayDataList.add(
            new DayData(
                slno++,
                dateElements.get(i).childNode(0).toString(),
                getResult(tdElements, 1),
                getResult(tdElements, 2),
                getResult(tdElements, 3),
                getResult(tdElements, 4),
                getResult(tdElements, 5),
                getResult(tdElements, 6),
                getResult(tdElements, 7),
                getResult(tdElements, 8)));
      }
    }

    return dayDataList;
    //
    //    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    //    String json = ow.writeValueAsString(dayDataList);
    //    System.out.println(json);
    //
    //    return json;
  }

  private int getResult(Elements tdElements, int index) {
    return Integer.parseInt(
        tdElements
            .get(index)
            .childNode(0)
            .toString()
            .replace(",", "")
            .replace("m", "")
            .replace("g", ""));
  }

  // convert InputStream to String
  private static String getStringFromInputStream(InputStream is) {

    BufferedReader br = null;
    StringBuilder sb = new StringBuilder();

    String line;
    try {

      br = new BufferedReader(new InputStreamReader(is));
      while ((line = br.readLine()) != null) {
        sb.append(line);
      }

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    return sb.toString();
  }
}
