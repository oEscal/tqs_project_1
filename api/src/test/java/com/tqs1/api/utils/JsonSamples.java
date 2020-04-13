package com.tqs1.api.utils;

public class JsonSamples {

    public static String jsonAirQualitySeveralPollutantData(int[] expectedScore, String[] expectedColor, String[] expectedCategory,
                                                            String[] expectedPollutant, String[] expectedDate, String[] expectedSimpleName,
                                                            String[] expectedFullName, int[] expectedPollutantScore,
                                                            String[] expectedPollutantColor, String[] expectedPollutantCategory,
                                                            double[] expectedValue, String[] expectedUnits) {
        return "[\n" +
        "        {\n" +
                "            \"datetime\": \"" + expectedDate[0] + "\",\n" +
                "            \"data_available\": true,\n" +
                "            \"indexes\": {\n" +
                "                \"baqi\": {\n" +
                "                    \"display_name\": \"BreezoMeter AQI\",\n" +
                "                    \"aqi\": " + expectedScore[0] + ",\n" +
                "                    \"aqi_display\": \"" + expectedScore[0] + "\",\n" +
                "                    \"color\": \"" + expectedColor[0] + "\",\n" +
                "                    \"category\": \"" + expectedCategory[0] + "\",\n" +
                "                    \"dominant_pollutant\": \"" + expectedPollutant[0] + "\"\n" +
                "                },\n" +
                "                \"fra_atmo\": {\n" +
                "                    \"display_name\": \"AQI (FR)\",\n" +
                "                    \"aqi\": 5,\n" +
                "                    \"aqi_display\": \"5\",\n" +
                "                    \"color\": \"#FFA500\",\n" +
                "                    \"category\": \"Average air quality\",\n" +
                "                    \"dominant_pollutant\": \"pm10\"\n" +
                "                }\n" +
                "            },\n" +
                "            \"pollutants\": {\n" +
                "                \"co\": {\n" +
                "                    \"display_name\": \"" + expectedSimpleName[0] + "\",\n" +
                "                    \"full_name\": \"" + expectedFullName[0] + "\",\n" +
                "                    \"aqi_information\": {\n" +
                "                        \"baqi\": {\n" +
                "                            \"display_name\": \"BreezoMeter AQI\",\n" +
                "                            \"aqi\": " + expectedPollutantScore[0] + ",\n" +
                "                            \"aqi_display\": \"" + expectedPollutantScore[0] + "\",\n" +
                "                            \"color\": \"" + expectedPollutantColor[0] + "\",\n" +
                "                            \"category\": \"" + expectedPollutantCategory[0] + "\"\n" +
                "                        }\n" +
                "                    },\n" +
                "                    \"concentration\": {\n" +
                "                        \"value\": " + expectedValue[0] + ",\n" +
                "                        \"units\": \"" + expectedUnits[0] + "\"\n" +
                "                    }\n" +
                "                },\n" +
                "                \"o3\": {\n" +
                "                    \"display_name\": \"" + expectedSimpleName[1] + "\",\n" +
                "                    \"full_name\": \"" + expectedFullName[1] + "\",\n" +
                "                    \"aqi_information\": {\n" +
                "                        \"baqi\": {\n" +
                "                            \"display_name\": \"BreezoMeter AQI\",\n" +
                "                            \"aqi\": " + expectedPollutantScore[1] + ",\n" +
                "                            \"aqi_display\": \"" + expectedPollutantScore[1] + "\",\n" +
                "                            \"color\": \"" + expectedPollutantColor[1] + "\",\n" +
                "                            \"category\": \"" + expectedPollutantCategory[1] + "\"\n" +
                "                        }\n" +
                "                    },\n" +
                "                    \"concentration\": {\n" +
                "                        \"value\": " + expectedValue[1] + ",\n" +
                "                        \"units\": \"" + expectedUnits[1] + "\"\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"datetime\": \"" + expectedDate[1] + "\",\n" +
                "            \"data_available\": true,\n" +
                "            \"indexes\": {\n" +
                "                \"baqi\": {\n" +
                "                    \"display_name\": \"BreezoMeter AQI\",\n" +
                "                    \"aqi\": " + expectedScore[1] + ",\n" +
                "                    \"aqi_display\": \"" + expectedScore[1] + "\",\n" +
                "                    \"color\": \"" + expectedColor[1] + "\",\n" +
                "                    \"category\": \"" + expectedCategory[1] + "\",\n" +
                "                    \"dominant_pollutant\": \"" + expectedPollutant[1] + "\"\n" +
                "                },\n" +
                "                \"fra_atmo\": {\n" +
                "                    \"display_name\": \"AQI (FR)\",\n" +
                "                    \"aqi\": 5,\n" +
                "                    \"aqi_display\": \"5\",\n" +
                "                    \"color\": \"#FFA500\",\n" +
                "                    \"category\": \"Average air quality\",\n" +
                "                    \"dominant_pollutant\": \"pm10\"\n" +
                "                }\n" +
                "            },\n" +
                "            \"pollutants\": {\n" +
                "                \"co\": {\n" +
                "                    \"display_name\": \"" + expectedSimpleName[0] + "\",\n" +
                "                    \"full_name\": \"" + expectedFullName[0] + "\",\n" +
                "                    \"aqi_information\": {\n" +
                "                        \"baqi\": {\n" +
                "                            \"display_name\": \"BreezoMeter AQI\",\n" +
                "                            \"aqi\": " + expectedPollutantScore[0] + ",\n" +
                "                            \"aqi_display\": \"" + expectedPollutantScore[0] + "\",\n" +
                "                            \"color\": \"" + expectedPollutantColor[0] + "\",\n" +
                "                            \"category\": \"" + expectedPollutantCategory[0] + "\"\n" +
                "                        }\n" +
                "                    },\n" +
                "                    \"concentration\": {\n" +
                "                        \"value\": " + expectedValue[0] + ",\n" +
                "                        \"units\": \"" + expectedUnits[0] + "\"\n" +
                "                    }\n" +
                "                },\n" +
                "                \"o3\": {\n" +
                "                    \"display_name\": \"" + expectedSimpleName[1] + "\",\n" +
                "                    \"full_name\": \"" + expectedFullName[1] + "\",\n" +
                "                    \"aqi_information\": {\n" +
                "                        \"baqi\": {\n" +
                "                            \"display_name\": \"BreezoMeter AQI\",\n" +
                "                            \"aqi\": " + expectedPollutantScore[1] + ",\n" +
                "                            \"aqi_display\": \"" + expectedPollutantScore[1] + "\",\n" +
                "                            \"color\": \"" + expectedPollutantColor[1] + "\",\n" +
                "                            \"category\": \"" + expectedPollutantCategory[1] + "\"\n" +
                "                        }\n" +
                "                    },\n" +
                "                    \"concentration\": {\n" +
                "                        \"value\": " + expectedValue[1] + ",\n" +
                "                        \"units\": \"" + expectedUnits[1] + "\"\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "    ]";
    }

    public static String jsonAirQualityOnePollutantData(int expectedScore, String expectedColor, String expectedCategory,
                                                        String expectedPollutant, String expectedDate, String[] expectedSimpleName,
                                                        String[] expectedFullName, int[] expectedPollutantScore,
                                                        String[] expectedPollutantColor, String[] expectedPollutantCategory,
                                                        double[] expectedValue, String[] expectedUnits) {
        return "{\n" +
                "        \"datetime\": \"" + expectedDate + "\",\n" +
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
