package com.dheeraj.user.registration.controller;

import com.dheeraj.user.registration.helper.NetClientGet;
import com.dheeraj.user.registration.model.pojo.NavDetail;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("nav")
public class NavController {

    @GetMapping("getNavBySchemeCode")
    public NavDetail getNavBySchemeCode(@RequestParam("schemeCode") String schemeCode) {

        List<String> navDetailList = NetClientGet.getAllNavValues();


        List<NavDetail> navDetails = new ArrayList<>();

        Map<String, NavDetail> schemeCodeMap = new HashMap<>();
        for (String line : navDetailList) {
            if (StringUtils.isEmpty(line)) {
                continue;
            }

            String[] vals = line.split(";");

            if (vals.length == 0) {
                continue;
            }

            if (!vals[0].matches("[0-9]+")) {
                continue;
            }

            NavDetail navDetail = new NavDetail(vals);

            if (navDetail == null || StringUtils.isEmpty(navDetail.getSchemeCode())) {
                continue;
            }

            navDetails.add(navDetail);

            schemeCodeMap.put(navDetail.getSchemeCode(), navDetail);

        }

        return schemeCodeMap.get(schemeCode);

    }


    @GetMapping("getNavByIsin")
    public NavDetail getNavByIsin(@RequestParam("isin") String isin) {

        List<String> navDetailList = NetClientGet.getAllNavValues();


        List<NavDetail> navDetails = new ArrayList<>();

        Map<String, NavDetail> schemeCodeMap = new HashMap<>();
        for (String line : navDetailList) {
            if (StringUtils.isEmpty(line)) {
                continue;
            }

            String[] vals = line.split(";");

            if (vals.length == 0) {
                continue;
            }

            if (!vals[0].matches("[0-9]+")) {
                continue;
            }

            NavDetail navDetail = new NavDetail(vals);

            if (navDetail == null || StringUtils.isEmpty(navDetail.getSchemeCode())) {
                continue;
            }

            navDetails.add(navDetail);

            schemeCodeMap.put(navDetail.getIsin(), navDetail);

        }

        return schemeCodeMap.get(isin);

    }
}
