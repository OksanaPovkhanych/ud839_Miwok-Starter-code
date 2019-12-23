package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener afChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        // Permanent loss of audio focus
                        // Pause playback immediately
                        releaseMediaPlayer();
                    }
                    else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        mediaPlayer.pause();
                        mediaPlayer.seekTo(0);
                        // Lower the volume, keep playing
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        // Your app has been granted audio focus again
                        // Raise volume to normal, restart playback if necessary
                        mediaPlayer.start();
                    }
                }
            };

    MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void  onStop() {
        super.onStop();
        releaseMediaPlayer();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.word_list);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();
        words.add( new Word("мама","mom", R.drawable.family_mother, R.raw.song));
        words.add( new Word("тато","father", R.drawable.family_father, R.raw.song2));
        words.add( new Word("брат","brother", R.drawable.family_younger_sister, R.raw.song3));
        words.add( new Word("сестра","sister", R.drawable.family_younger_sister, R.raw.song4));
        words.add( new Word("баба","grandmother", R.drawable.family_grandmother, R.raw.song2));
        words.add( new Word("дідо","grandfather", R.drawable.family_grandfather, R.raw.song2));
        words.add( new Word("племінниця","niece", R.drawable.family_older_sister, R.raw.song2));
        words.add( new Word("дяьдко","uncle", R.drawable.family_son, R.raw.song2));
        words.add( new Word("тітка","aunt", R.drawable.family_younger_sister, R.raw.song2));

        WordAdapter adapter = new WordAdapter(this, words, R.color.category_family);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Word word = words.get(position);
                releaseMediaPlayer();
                int result = mAudioManager.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(FamilyActivity.this, word.getmAudioResourceId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(onCompletionListener);
                }
            }
        });
    }
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;
            mAudioManager.abandonAudioFocus(afChangeListener);
        }
    }
}
