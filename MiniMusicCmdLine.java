import javax.sound.midi.*;

public class MiniMusicCmdLine{

    Sequencer player;

    public static void main(String[] args){
        MiniMusicCmdLine mini = new MiniMusicCmdLine();
        if(args.length < 2){
            System.out.println("Don't forget the instrument and note args!");
        }
        else{
            int instrument = Integer.parseInt(args[0]);
            int note = Integer.parseInt(args[1]);
            mini.play(instrument, note);

            try{
                Thread.sleep(2000);
                mini.closeSequencer();
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
            
        }

    }

    public void play(int instrument, int note){
        try{
            initializeSequencer();
            Sequence seq = new Sequence(Sequence.PPQ, 4);
            Track track = seq.createTrack();

            MidiEvent event = null;

            ShortMessage first = new ShortMessage();
            first.setMessage(192, 1, instrument, 0);
            MidiEvent changeInstrument = new MidiEvent(first, 1);
            track.add(changeInstrument);

            ShortMessage a = new ShortMessage();
            a.setMessage(144, 1, note, 100);
            MidiEvent noteOn = new MidiEvent(a,1);
            track.add(noteOn);

            ShortMessage b = new ShortMessage();
            b.setMessage(128, 1, note, 100);
            MidiEvent noteOff = new MidiEvent(b, 16);
            track.add(noteOff);

            player.setSequence(seq);
            player.start();

        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void closeSequencer(){
        try{
            player.stop();
            player.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        
    }

    public void initializeSequencer(){
        try{
            player = MidiSystem.getSequencer();
            player.open();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

    }

}