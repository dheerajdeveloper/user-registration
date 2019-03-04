package com.dheeraj.user.registration.controller;

import com.dheeraj.user.registration.model.Location;
import com.dheeraj.user.registration.model.pojo.AddressChunk;
import com.dheeraj.user.registration.model.pojo.DayData;
import com.dheeraj.user.registration.service.LocationService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
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
@RequestMapping("user-registration/location")
@RestController
public class LocationController {

  @Autowired LocationService locationService;

  @PostMapping("add")
  public Location addLocation(@RequestBody Location location) {
    return locationService.addLocation(location);
  }

  @GetMapping("getLocationForUser")
  public List<Location> getLocationForUser(@RequestParam("userId") long userId) {
    return locationService.getLocationsForUser(userId);
  }

  @GetMapping("getLatestLocationForUser")
  public Location getLatestLocationForUser(@RequestParam("userId") long userId) {
    return locationService.getLatestLocationForUser(userId);
  }

  @GetMapping(
      value = "getLocationForUserForLastNMinute",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody List<Location> getLocationForUserForLastNMinute(
      @RequestParam("userId") long userId, @RequestParam("minute") int minute) {
    List<Location> locationList = locationService.getLocationForUserForLastNMinute(userId, minute);

    return locationList;
  }

  @GetMapping(
      value = "getAddressForUserForLastNMinute",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody List<AddressChunk> getAddressForUserForLastNMinute(
      @RequestParam("userId") long userId, @RequestParam("minute") int minute) {
    List<AddressChunk> locationList =
        locationService.getAddressForUserForLastNMinute(userId, minute);

    return locationList;
  }

  @GetMapping(value = "getLocationForUserForTimeRange", produces = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody List<Location> getLocationForUserForTimeRange(
      @RequestParam("userId") long userId,
      @RequestParam("starttime") String starttime,
      @RequestParam("endtime") String endtime) {
    List<Location> locationList =
        locationService.getLocationForUserForTimeRange(userId, starttime, endtime);

    if (CollectionUtils.isEmpty(locationList)) {
      locationList = new ArrayList<>();
    }

    System.out.println(
        "Total number of location models for getLocationForUserForTimeRange "
            + locationList.size());

    return locationList;
  }

  @GetMapping(value = "getAddressForUserForTimeRange", produces = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody List<AddressChunk> getAddressForUserForTimeRange(
      @RequestParam("userId") long userId,
      @RequestParam("starttime") String starttime,
      @RequestParam("endtime") String endtime) {
    List<AddressChunk> locationList =
        locationService.getAddressForUserForTimeRange(userId, starttime, endtime);

    if (CollectionUtils.isEmpty(locationList)) {
      locationList = new ArrayList<>();
    }

    System.out.println(
        "Total number of location models for getAddressForUserForTimeRange " + locationList.size());

    return locationList;
  }

  @GetMapping(value = "getLocationForUserForLastNDay", produces = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody List<Location> getLocationForUserForLastNDay(
      @RequestParam("userId") long userId, @RequestParam("day") int day) {
    List<Location> locationList = locationService.getLocationForUserForLastNDay(userId, day);

    return locationList;
  }

  @GetMapping(value = "getAddressForUserForLastNDay", produces = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody List<AddressChunk> getAddressForUserForLastNDay(
      @RequestParam("userId") long userId, @RequestParam("day") int day) {
    List<AddressChunk> locationList = locationService.getAddressForUserForLastNDay(userId, day);

    return locationList;
  }
}
