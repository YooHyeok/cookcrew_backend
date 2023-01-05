package kfq.cookcrew.reciepe;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Date;
import java.time.LocalDate;

@RestController
public class RecipeDBController {

    final String serviceKey = "9bf066dd90674a4e8702";
    @Autowired
    RecipeRepository recipeRepository;

    @GetMapping("/recipedb")
    public String recipe() {
        StringBuilder urlBuilder = new StringBuilder("http://openapi.foodsafetykorea.go.kr/api/");
        try {
            urlBuilder.append( URLEncoder.encode("9bf066dd90674a4e8702", "UTF-8")); /*Service Key*/
            urlBuilder.append("/" + URLEncoder.encode("COOKRCP01", "UTF-8"));
            urlBuilder.append("/" + URLEncoder.encode("json", "UTF-8"));

            urlBuilder.append("/" + URLEncoder.encode("1001", "UTF-8"));
            urlBuilder.append("/" + URLEncoder.encode("1100", "UTF-8"));

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            System.out.println("Response code: " + conn.getResponseCode());
            BufferedReader rd;
            String wRecipe = "";
            String numInfo = "";
            String isManual07NotNull = "";

            if(conn.getResponseCode() == 200) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(sb.toString());
                JsonObject responseObject = element.getAsJsonObject().get("COOKRCP01").getAsJsonObject();
                JsonArray itemArray = responseObject.get("row").getAsJsonArray();
                System.out.println(itemArray.size());

                for (int i=0; i<itemArray.size(); i++) {
                    JsonObject item = itemArray.get(i).getAsJsonObject();
                    String recipeName = item.get("RCP_NM").getAsString(); // 메뉴명
                    String recipePat2 = item.get("RCP_PAT2").getAsString(); // 요리 종류

                    String infoWgt1 = item.get("INFO_WGT").getAsString(); // 중량 (1인분)
                    String infoEng1 = item.get("INFO_ENG").getAsString(); // 열량
                    String mat = item.get("RCP_PARTS_DTLS").getAsString(); // 재료 정보
                    double infoWgt;
                    double infoEng;
                    if (infoWgt1.isBlank()) {
                        infoWgt = 0;
                    } else {
                        infoWgt = Double.parseDouble(infoWgt1);}

                    if (infoEng1.isBlank()){
                        infoEng = 0;
                    } else {
                        infoEng = Double.parseDouble(infoEng1);}

                    System.out.println("String" + infoWgt1 +"///Double" + infoWgt);
                    System.out.println("String" + infoEng1 +"///Double" + infoEng);

                    //float infoEng = Float.parseFloat(infoEng1);



                    String attFileNoMain1 = item.get("ATT_FILE_NO_MAIN").getAsString();
                    String manual01 = item.get("MANUAL01").getAsString();
                    String manual02 = item.get("MANUAL02").getAsString();
                    String manual03 = item.get("MANUAL03").getAsString();
                    String manual04 = item.get("MANUAL04").getAsString();
                    String manual05 = item.get("MANUAL05").getAsString();
                    String manual06 = item.get("MANUAL06").getAsString();
                    String manual07 = item.get("MANUAL07").getAsString();
                    if(!manual07.isEmpty()) {
                        isManual07NotNull = "있다";
                    }

                    String[] manual = {manual01, manual02, manual03, manual04, manual05, manual06};

                    String manualImg01 = item.get("MANUAL_IMG01").getAsString();
                    String manualImg02 = item.get("MANUAL_IMG02").getAsString();
                    String manualImg03 = item.get("MANUAL_IMG03").getAsString();
                    String manualImg04 = item.get("MANUAL_IMG04").getAsString();
                    String manualImg05 = item.get("MANUAL_IMG05").getAsString();
                    String manualImg06 = item.get("MANUAL_IMG06").getAsString();


                    String[] manualImg = {manualImg01,manualImg02, manualImg03, manualImg04, manualImg05, manualImg06};

                    String[] taggedManualImg = new String[6];
                    String[] taggedManual = new String[6];

                    for (int j = 0 ; j< 6 ; j++) {
                        taggedManualImg[j] = "<img src =" + "\""+ manualImg[j] +"\"" + "/>";
                        taggedManual[j] = "<p>" + manual[j] + "</p>";
                    }

                    String[] step = new String[6];
                    String wholeRecipe = "";
                    for ( int j = 0 ; j < step.length ; j++ ) {
                        step[j] = taggedManualImg[j].concat(taggedManual[j]);
                        wholeRecipe +=step[j];

                    }


//		        String wholeRecipe1 = manual01+"<br>"+manual02+"<br>"+manual03+"<br>"+manual04+"<br>"+manual05+"<br>"+
//		        					manual06+"<br>";
//		        numInfo += " <br>  양 : "+infoWgt1+" 열량 : "+ infoEng1 ;
//		        wRecipe +=manual01+"<br>"+manual02+"<br>"+manual03+"<br>"+manual04+"<br>"+manual05+"<br>"+
//    					manual06+"<br><br><br><br><br>";
                    Date regDate = new Date(System.currentTimeMillis());

                    recipeRepository.save(new Recipe(null,"식약처 조리식품의 레시피 DB",recipeName,wholeRecipe,0,true,new Date(System.currentTimeMillis()),null,infoEng,attFileNoMain1,null,mat,null,0d));
                    //System.out.println(wholeRecipe1);
                    //System.out.println(attFileNoMain1);
                    //System.out.println(numInfo);
                    System.out.println(manual07);


                }
            }
            rd.close();
            conn.disconnect();
            return isManual07NotNull;
        }catch(Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

}
