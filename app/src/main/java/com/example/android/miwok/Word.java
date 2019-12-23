package com.example.android.miwok;

public class Word {
    private String mDefaultTranslation;
    private String mMiwokTranslation;
    private  int mAudioResourceId;
    private int mImageResourceId = NO_IMAGE_PROVIEDED;

    public final static int NO_IMAGE_PROVIEDED = -1;

    public Word(String MiwokTranslation, String DefaultTranslation, int AudioResource) {
        mMiwokTranslation = MiwokTranslation;
        mDefaultTranslation= DefaultTranslation;
        mAudioResourceId = AudioResource;
    }

    public Word(String MiwokTranslation, String DefaultTranslation, int ImageResource , int AudioResource) {
        mMiwokTranslation = MiwokTranslation;
        mDefaultTranslation= DefaultTranslation;
        mImageResourceId = ImageResource;
        mAudioResourceId = AudioResource;
    }

    public String getmDefaultTranslation() {
        return mDefaultTranslation;
    }
    public String getmMiwokTranslation() {
        return mMiwokTranslation;
    }

    public int getImageResourceId() {
        return  mImageResourceId;
    }

    public int getmAudioResourceId() {
        return mAudioResourceId;
    }

    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIEDED;
    }
}
