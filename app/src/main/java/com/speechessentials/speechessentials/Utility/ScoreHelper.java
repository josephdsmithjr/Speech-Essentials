package com.speechessentials.speechessentials.Utility;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by josephdsmithjr on 5/1/15.
 */
public class ScoreHelper {

    public boolean saveScore(Context context, String username, ArrayList<Integer> listOfCorrectSoundImageNameIds, ArrayList<Integer> listOfApproximateSoundImageNameIds, ArrayList<Integer> listOfIncorrectSoundImageNameIds, String game){
        boolean success = true;
        try {
            DatabaseOperationsHelper databaseOperationsHelper = new DatabaseOperationsHelper(context);
            ArrayList<Integer> allIdList = getAllIdsInList(listOfCorrectSoundImageNameIds, listOfApproximateSoundImageNameIds, listOfIncorrectSoundImageNameIds);
            ArrayList<String> allIdsWithSoundList = databaseOperationsHelper.getSoundAndSoundNameIdList(databaseOperationsHelper, allIdList, game);
            ArrayList<String> distinctSoundList = databaseOperationsHelper.getDistinctSoundList(databaseOperationsHelper, allIdList, game);
            for(int i = 0; i < distinctSoundList.size(); i++){
                ArrayList<Integer> listOfCorrectIdsForSound = new ArrayList<>();
                ArrayList<Integer> listOfApproximateIdsForSound = new ArrayList<>();
                ArrayList<Integer> listOfIncorrectIdsForSound = new ArrayList<>();
                for(int j = 0; j < allIdsWithSoundList.size(); j++){
                    String[] allIdsArray = allIdsWithSoundList.get(j).split(",");
                    String sound = allIdsArray[0];
                    Integer soundNameId = Integer.valueOf(allIdsArray[1]);
                    if(sound.equals(distinctSoundList.get(i)) && listOfCorrectSoundImageNameIds.contains(soundNameId)){
                        listOfCorrectIdsForSound.add(soundNameId);
                    } else if(sound.equals(distinctSoundList.get(i)) && listOfApproximateSoundImageNameIds.contains(soundNameId)){
                        listOfApproximateIdsForSound.add(soundNameId);
                    } else if(sound.equals(distinctSoundList.get(i)) && listOfIncorrectSoundImageNameIds.contains(soundNameId)){
                        listOfIncorrectIdsForSound.add(soundNameId);
                    }
                }
                ArrayList<String> scoreList = getSoundPositionScoreNames(distinctSoundList.get(i),listOfCorrectIdsForSound, listOfApproximateIdsForSound, listOfIncorrectIdsForSound, databaseOperationsHelper, game);
                for(int s = 0; s < scoreList.size(); s++){
                    databaseOperationsHelper.addScore(databaseOperationsHelper, username, scoreList.get(s));
                }
            }
        } catch(Exception e){
            success = false;
        }
        return success;
    }
    //TODO: Fix this to include other games! sentences, word swap, etc
    public ArrayList<String> getSoundPositionScoreNames(String soundName, ArrayList<Integer> correctList, ArrayList<Integer> approximateList, ArrayList<Integer> incorrectList, DatabaseOperationsHelper dop, String game){
        ArrayList<String> soundPositionScoreNameList = new ArrayList<>();
        int correctBeginning = 0;
        int correctMiddle = 0;
        int correctEnding = 0;
        int approximateBeginning = 0;
        int approximateMiddle = 0;
        int approximateEnding = 0;
        int incorrectBeginning = 0;
        int incorrectMiddle = 0;
        int incorrectEnding = 0;
        String gameType = "";
        for(int i = 0; i < correctList.size(); i++){
            String soundPositionName = dop.getSoundPositionNameFromSoundImageId(dop, correctList.get(i));
            if(soundPositionName.contains("beginning")){
                correctBeginning++;
            } else if(soundPositionName.contains("middle")){
                correctMiddle++;
            } else {
                correctEnding++;
            }
            if(soundPositionName.contains("sentence")){
                gameType = "sentence";
            } else if(soundPositionName.contains("swap")){
                gameType = "swap";
            } else {
                gameType = "word";
            }
        }
        for(int i = 0; i < approximateList.size(); i++){
            String soundPositionName = dop.getSoundPositionNameFromSoundImageId(dop, approximateList.get(i));
            if(soundPositionName.contains("beginning")){
                approximateBeginning++;
            } else if(soundPositionName.contains("middle")){
                approximateMiddle++;
            } else {
                approximateEnding++;
            }
            if(soundPositionName.contains("sentence")){
                gameType = "sentence";
            } else if(soundPositionName.contains("swap")){
                gameType = "swap";
            } else {
                gameType = "word";
            }
        }
        for(int i = 0; i < incorrectList.size(); i++){
            String soundPositionName = dop.getSoundPositionNameFromSoundImageId(dop, incorrectList.get(i));
            if(soundPositionName.contains("beginning")){
                incorrectBeginning++;
            } else if(soundPositionName.contains("middle")){
                incorrectMiddle++;
            } else {
                incorrectEnding++;
            }
            if(soundPositionName.contains("sentence")){
                gameType = "sentence";
            } else if(soundPositionName.contains("swap")){
                gameType = "swap";
            } else {
                gameType = "word";
            }
        }
        //f-beginning words|7/7|40%
        //f-middle words|5/10|50%
        //f-ending words|6/6|100%
        float beginningScorePercent = Math.round(((float) correctBeginning / (correctBeginning + incorrectBeginning + approximateBeginning))* 100);
        float middleScorePercent = Math.round(((float) correctMiddle / (correctMiddle + incorrectMiddle + approximateMiddle))* 100);
        float endingScorePercent = Math.round(((float) correctEnding / (correctEnding + incorrectEnding + approximateEnding))* 100);
        String beginningScore = soundName + "- " + game + " beginning," + correctBeginning + "/" + (correctBeginning + incorrectBeginning + approximateBeginning)+ "," + beginningScorePercent + "%";
        String middleScore = soundName + "- " + game + " middle," + correctMiddle + "/" + (correctMiddle + incorrectMiddle + approximateMiddle)+ "," + middleScorePercent + "%";
        String endingScore = soundName + "- " + game + " ending," + correctEnding + "/" + (correctEnding + incorrectEnding + approximateEnding)+ "," + endingScorePercent + "%";
        if(correctBeginning + incorrectBeginning + approximateBeginning >0){
            soundPositionScoreNameList.add(beginningScore);
        }
        if(correctMiddle + incorrectMiddle + approximateMiddle > 0){
            soundPositionScoreNameList.add(middleScore);
        }
        if(correctEnding + incorrectEnding + approximateEnding > 0){
            soundPositionScoreNameList.add(endingScore);
        }
        return soundPositionScoreNameList;
    }

    public ArrayList<Integer> getAllIdsInList(ArrayList<Integer> correctList, ArrayList<Integer> approximateList, ArrayList<Integer> incorrectList){
        ArrayList<Integer> allIdList = new ArrayList<>();
        for(int i = 0; i < correctList.size(); i++){
            allIdList.add(correctList.get(i));
        }
        for(int i = 0; i < approximateList.size(); i++){
            allIdList.add(approximateList.get(i));
        }
        for(int i = 0; i < incorrectList.size(); i++){
            allIdList.add(incorrectList.get(i));
        }
        return allIdList;
    }

//    public String makeCommaSeparatedStringFromIntegerList(ArrayList<Integer> listOfIds){
//        String commaSeparatedString = "";
//        for(int i = 0; i < listOfIds.size(); i++){
//            if(i == listOfIds.size()-1){
//                commaSeparatedString += listOfIds.get(i) + "";
//            } else {
//                commaSeparatedString += listOfIds.get(i) + ", ";
//            }
//        }
//        return commaSeparatedString;
//    }
}