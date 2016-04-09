package com.speechessentials.speechessentials.Utility;

import android.provider.BaseColumns;

/**
 * Created by josephdsmithjr on 4/18/15.
 */
public class TableDataHelper {
    //constructor
    public TableDataHelper(){

    }

    //inner class
    public static abstract class TableInfo implements BaseColumns {

        public static final String DATABASE_NAME = "speech_essentials";

        public static final String TABLE_USERS_NAME = "users";
        public static final String COLUMN_USERS_USERID = "user_id";
        public static final String COLUMN_USERS_NAME = "username";
        public static final String COLUMN_USERS_EMAIL = "email";
        public static final String COLUMN_USERS_GENDER = "gender";
        public static final String COLUMN_USERS_BIRTHDAY = "birthday";

        public static final String TABLE_SCORES_NAME = "scores";
        public static final String COLUMN_SCORES_SCORESID = "scores_id";
        public static final String COLUMN_SCORES_USERNAME = "username";
        public static final String COLUMN_SCORES_SCOREDATE = "scoredate";
        public static final String COLUMN_SCORES_SCORE = "score";

        public static final String TABLE_NOTES_NAME = "notes";
        public static final String COLUMN_NOTES_NOTESID = "notes_id";
        public static final String COLUMN_NOTES_SCOREID = "score_id";
        public static final String COLUMN_NOTES_NOTES = "notes";

        public static final String TABLE_SOUNDNAMES_NAME = "soundnames";
        public static final String COLUMN_SOUNDNAMES_SOUNDNAMEID = "soundname_id";
        public static final String COLUMN_SOUNDNAMES_SOUNDID = "sound_id";
        public static final String COLUMN_SOUNDNAMES_SOUNDPARENTID = "sound_parent_id";
        public static final String COLUMN_SOUNDNAMES_SOUNDPOSITIONSID = "soundpositions_id";
        public static final String COLUMN_SOUNDNAMES_SOUNDNAME = "soundname";
        public static final String COLUMN_SOUNDNAMES_SOUNDPOSITIONNAME = "soundpositionname";
        public static final String COLUMN_SOUNDNAMES_SOUNDIMAGENAME = "image_name";
        public static final String COLUMN_SOUNDNAMES_SOUNDIMAGEAUDIONAME = "imageaudio_name";
        //public static final String COLUMN_SOUNDNAMES_SOUNDAUDIOPATH = "audio_path";

        public static final String TABLE_SOUNDSWAPNAMES_NAME = "soundswapnames";
        public static final String COLUMN_SOUNDSWAPNAMES_SOUNDSWAPNAMEID = "soundname_id";
        public static final String COLUMN_SOUNDSWAPNAMES_SOUNDNAME = "soundname";
        public static final String COLUMN_SOUNDSWAPNAMES_SOUNDPOSITIONNAME = "soundpositionname";
        public static final String COLUMN_SOUNDSWAPNAMES_BEGINNINGNAME = "soundbeginning";
        public static final String COLUMN_SOUNDSWAPNAMES_ENDINGNAME = "soundending";
        public static final String COLUMN_SOUNDSWAPNAMES_SOUNDBEGINNINGRESOURCENAME = "soundbeginningresource";
        public static final String COLUMN_SOUNDSWAPNAMES_SOUNDENDINGRESOURCENAME = "soundendingresource";

        public static final String TABLE_SOUNDSENTENCENAMES_NAME = "soundsentnames";
        public static final String COLUMN_SOUNDSENTENCENAMES_SOUNDNAMEID = "soundsentname_id";
        public static final String COLUMN_SOUNDSENTENCENAMES_SOUNDID = "soundsent_id";
        public static final String COLUMN_SOUNDSENTENCENAMES_SOUNDPARENTID = "soundsent_parent_id";
        public static final String COLUMN_SOUNDSENTENCENAMES_SOUNDPOSITIONSID = "soundsentpositions_id";
        public static final String COLUMN_SOUNDSENTENCENAMES_SOUNDNAME = "soundsentname";
        public static final String COLUMN_SOUNDSENTENCENAMES_SOUNDPOSITIONNAME = "soundsentpositionname";
        public static final String COLUMN_SOUNDSENTENCENAMES_SOUNDIMAGENAME = "sentimage_name";
        public static final String COLUMN_SOUNDSENTENCENAMES_SOUNDIMAGEPATH = "sentimage_path";
        public static final String COLUMN_SOUNDSENTENCENAMES_SOUNDAUDIOPATH = "sentaudio_path";

        public static final String TABLE_SOUNDS_NAME = "sounds";
        public static final String COLUMN_SOUNDS_SOUNDID = "sound_id";
//        public static final String COLUMN_SOUNDS_SOUNDPARENTID = "sound_parent_id";
        public static final String COLUMN_SOUNDS_SOUNDNAME = "name";

        public static final String TABLE_SOUNDPOSITIONS_NAME = "soundpositions";
        public static final String COLUMN_SOUNDPOSITIONS_SOUNDPOSITIONSID = "soundpositions_id";
        public static final String COLUMN_SOUNDPOSITIONS_SOUNDPOSITIONSNAME = "name";

        public static final String TABLE_DIFFSENTENCES_NAME = "diffsentences";
        public static final String COLUMN_DIFFSENTENCES_DIFFSENTENCESID = "diffsentences_id";
        public static final String COLUMN_DIFFSENTENCES_DIFFSENTENCESNAME = "name";
        public static final String COLUMN_DIFFSENTENCES_DIFFSENTENCESIMAGEAUDIONAME = "audioimage_name";

        public static final String TABLE_SETTINGS_NAME = "settings";
        public static final String COLUMN_SETTINGS_SETTINGS_ID = "settings_id";
        public static final String COLUMN_SETTINGS_NAME = "settings_name";
        public static final String COLUMN_SETTINGS_VALUE = "settings_value";
//        public static final String COLUMN_SETTINGS_SHOWSCORINGBUTTONS = "show_scoring_buttons";
//        public static final String COLUMN_SETTINGS_SCORINGSOUNDSENABLED = "scoring_sounds_enabled";
//        public static final String COLUMN_SETTINGS_SHOWAPPROXIMATIONBUTTON = "show_approximation_button";
//        public static final String COLUMN_SETTINGS_SHOWRECORDBUTTON = "show_record_button";
//        public static final String COLUMN_SETTINGS_VOICEAUDIO_ENABLED = "voice_audio_enabled";
//        public static final String COLUMN_SETTINGS_PASSINGATNINTY = "passing_at_ninty";
//        public static final String COLUMN_SETTINGS_SORTBYSYLLABLES = "sort_by_syllables";
    }
}
