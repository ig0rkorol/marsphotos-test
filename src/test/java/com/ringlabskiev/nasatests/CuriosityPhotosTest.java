package com.ringlabskiev.nasatests;

import com.ringlabskiev.utils.NasaUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SerenityRunner.class)
public class CuriosityPhotosTest extends BaseTest{

    @Test
    public void verifyCuriosityMetadata() throws ParseException, UnsupportedEncodingException {
        final int SOL = 1000;
        final int INDEX_FROM = 0;
        final int INDEX_TO = 10;

        List itemsBySol = getItemsBySol(SOL, INDEX_FROM, INDEX_TO);
        List itemsByEarthDate = getItemsByEarth(SOL, INDEX_FROM, INDEX_TO);

        assertThat(itemsBySol, equalTo(itemsByEarthDate));
        verifyImagesIdentical(itemsBySol, itemsByEarthDate);
    }

    private List getImagesFromCuriosityResponse(Response response, int from, int to){
        return ((ArrayList)response.jsonPath().getMap(".").get("photos")).subList(from, to);
    }

    private void verifyImagesIdentical(List solItems, List earthItems) throws UnsupportedEncodingException {
        int size=solItems.size();
        for(int i=0;i<size;i++){
            String urlImageBySol = getImageBase64String((String) ((HashMap)solItems.get(0)).get("img_src"));
            String urlImageByEarth = getImageBase64String((String) ((HashMap)earthItems.get(0)).get("img_src"));
            assertThat("Images different for camera ", urlImageByEarth, equalTo(urlImageBySol));
        }
    }

    private List getItemsBySol(int sol, int from, int to){
        Response bySol = getCurisityDataBySol(sol);
        verifyResponseCode(bySol.statusCode(), 200, "sol");
        return getImagesFromCuriosityResponse(bySol, from, to);
    }

    private List getItemsByEarth(int sol, int from, int to) throws ParseException {
        Response byEarthDate = getCuriosityDataByEarthDate(NasaUtils.getEarthDateBySol(sol));
        verifyResponseCode(byEarthDate.statusCode(), 200, "earth_date");
        return getImagesFromCuriosityResponse(byEarthDate, from, to);
    }

    private String getImageBase64String(String url) throws UnsupportedEncodingException {
        return new String(Base64.encodeBase64(RestAssured.get(url).asByteArray()), "UTF-8");
    }
}
