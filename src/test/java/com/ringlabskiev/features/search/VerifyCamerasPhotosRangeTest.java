package com.ringlabskiev.features.search;

import io.restassured.path.json.JsonPath;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;

@RunWith(SerenityRunner.class)
public class VerifyCamerasPhotosRangeTest extends BaseTest{

    @Test
    public void verifyPhotosCountFromAllCamerasTest(){
        final int SOL = 1000;
        Map<String, Integer> photosPerCamera = getImagesCountForEachCamera(SOL);
        verifyCamerasWorksWell(photosPerCamera);
    }

    private void verifyCamerasWorksWell(Map<String, Integer> photosPerCamera){
        Map.Entry<String, Integer> max = photosPerCamera.entrySet().stream().max(Map.Entry.comparingByValue(Integer::compareTo)).get();
        Map.Entry<String, Integer> min = photosPerCamera.entrySet().stream().min(Map.Entry.comparingByValue(Integer::compareTo)).get();

        // Note:  if any camera make 0 photos test marked as failed
        assertThat("Photos have absent for camera name=" + min, min, not(equalTo(0)));
        String message = String.format("Camera %s makes MIN photos count %s \n Camera %s makes MAX photos count %s\n " +
                "These count different at least 10 times",
                min.getKey(), min.getValue(), max.getKey(), max.getValue());
        assertThat(message, max.getValue(), not(greaterThanOrEqualTo(10*min.getValue())));
    }

    private List getAllCamerasNames(JsonPath curiosityData){
        return curiosityData.get("photos.rover.cameras[0].name");
    }

    private Map<String, Integer> getImagesCountForEachCamera(int sol){
        Map<String, Integer> photosCountPerCamera = new HashMap<>();
        JsonPath allData = getCurisityDataBySol(sol).jsonPath();
        List<String> camerasNames = getAllCamerasNames(allData);
        String queryByCameraName = "photos.findAll{photos -> photos.camera.name=='%s'}";

        for (String name : camerasNames){
            String query = String.format(queryByCameraName, name);
            int count = ((ArrayList)allData.get(query)).size();
            photosCountPerCamera.put(name, count);
        }
        return photosCountPerCamera;
    }
}
