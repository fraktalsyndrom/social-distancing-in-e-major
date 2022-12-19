# social-distancing-in-e-major
A procedurally generated continuous performance of the pieces from socialdistancinginemajor.com

## Adding music
The repository doesn't contain any music, but you can add your own. Create a folder named "wav/" wherever you're running the program from and drop however many audio files (in .wav format) you want there.

## Streaming to YouTube
The audio from this software is being streamed to YouTube using ffmpeg on a Linux server. To achieve this, run the following command, creates a live stream with a simple black background image:

    ffmpeg -f s16le -ar 44100 -ac 2 -re -i {AUDIO_OUTPUT_SOURCE} -f lavfi -i color=color=black -f flv "rtmp://a.rtmp.youtube.com/live2/{STREAM_KEY}"
  
