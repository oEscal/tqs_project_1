package com.tqs1.api.utils;

public class JsonSamples {

    public static String jsonAirQualityOnePollutant(int expectedScore, String expectedColor, String expectedCategory,
                                                    String expectedPollutant, String[] expectedSimpleName,
                                                    String[] expectedFullName, int[] expectedPollutantScore,
                                                    String[] expectedPollutantColor, String[] expectedPollutantCategory,
                                                    double[] expectedValue, String[] expectedUnits) {
        return "{\n" +
                "        \"datetime\": \"2020-04-04T16:00:00Z\",\n" +
                "        \"data_available\": true,\n" +
                "        \"indexes\": {\n" +
                "            \"baqi\": {\n" +
                "                \"display_name\": \"BreezoMeter AQI\",\n" +
                "                \"aqi\": " + expectedScore + ",\n" +
                "                \"aqi_display\": \"" + expectedScore + "\",\n" +
                "                \"color\": \"" + expectedColor + "\",\n" +
                "                \"category\": \"" + expectedCategory + "\",\n" +
                "                \"dominant_pollutant\": \"" + expectedPollutant + "\"\n" +
                "            }" +
                "        },\n" +
                "        \"pollutants\": {\n" +
                "            \"co\": {\n" +
                "                \"display_name\": \"" + expectedSimpleName[0] + "\",\n" +
                "                \"full_name\": \"" + expectedFullName[0] + "\",\n" +
                "                \"aqi_information\": {\n" +
                "                    \"baqi\": {\n" +
                "                        \"display_name\": \"BreezoMeter AQI\",\n" +
                "                        \"aqi\": " + expectedPollutantScore[0] + ",\n" +
                "                        \"aqi_display\": \"" + expectedPollutantScore[0] + "\",\n" +
                "                        \"color\": \"" + expectedPollutantColor[0] + "\",\n" +
                "                        \"category\": \"" + expectedPollutantCategory[0] + "\"\n" +
                "                    }\n" +
                "                },\n" +
                "                \"concentration\": {\n" +
                "                    \"value\": " + expectedValue[0] + ",\n" +
                "                    \"units\": \"" + expectedUnits[0] + "\"\n" +
                "                }\n" +
                "            },\n" +
                "            \"so2\": {\n" +
                "                \"display_name\": \"" + expectedSimpleName[1] + "\",\n" +
                "                \"full_name\": \"" + expectedFullName[1] + "\",\n" +
                "                \"aqi_information\": {\n" +
                "                    \"baqi\": {\n" +
                "                        \"display_name\": \"BreezoMeter AQI\",\n" +
                "                        \"aqi\": " + expectedPollutantScore[1] + ",\n" +
                "                        \"aqi_display\": \"" + expectedPollutantScore[1] + "\",\n" +
                "                        \"color\": \"" + expectedPollutantColor[1] + "\",\n" +
                "                        \"category\": \"" + expectedPollutantCategory[1] + "\"\n" +
                "                    }\n" +
                "                },\n" +
                "                \"concentration\": {\n" +
                "                    \"value\": " + expectedValue[1] + ",\n" +
                "                    \"units\": \"" + expectedUnits[1] + "\"\n" +
                "                }\n" +
                "            }\n" +
                "        }" +
                "}";
    }
}
