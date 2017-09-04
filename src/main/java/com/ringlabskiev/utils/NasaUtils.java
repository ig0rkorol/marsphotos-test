package com.ringlabskiev.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by ikorol on 04.09.2017.
 */
public class NasaUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(NasaUtils.class);
    static final float SECONDS_IN_SOL = 88775.244147F;
    static final String CURIOSITY_START_DATE = "06/08/2012";

    /**
     *
     * @param sol int value of solar days from start mission
     * @return String represent Earth date on sol
     * @throws ParseException
     */
    public static String getEarthDateBySol(int sol) throws ParseException {
        LOGGER.info("Calculate earth Date for sol value {} ", sol);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = dateFormat.parse(CURIOSITY_START_DATE);
        long curiosityStartDateTimestamp = date.getTime();

        float dateBySol = curiosityStartDateTimestamp + SECONDS_IN_SOL*1000*(sol);
        long dateBySolMilliSeconds = (long)dateBySol;
        DateFormat dateFormatForQuery = new SimpleDateFormat("yyyy-MM-dd");
        String earthDate = dateFormatForQuery.format(dateBySolMilliSeconds);
        LOGGER.info("Earth Date for sol value is {} ", earthDate);
        return earthDate;
    }

    /**
     * Compare min and max photos count from cameras
     * @param camerasPhotoCount
     * @return false if photos count different at least 10 times
     */
    public  static boolean doesCamerasWorkWell(List camerasPhotoCount){
        int minPhotosCount = (int) camerasPhotoCount.get(camerasPhotoCount.indexOf(Collections.min(camerasPhotoCount)));
        int maxPhotosCount = (int) camerasPhotoCount.get(camerasPhotoCount.indexOf(Collections.max(camerasPhotoCount)));
        return Math.round(maxPhotosCount/minPhotosCount)>=10;
    }

}
