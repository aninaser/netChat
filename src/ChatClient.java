/**
 * Created by Aunteek Naser on 2/3/2017.
 */
import java.net.*;
import java.io.*;
import java.awt.*;
public class ChatClient extends Frame implements Runnable {
    //what are all of these protected from, why are they protected?
    protected DataInputStream i;
    protected DataOutputStream o;
    protected TextArea output;
    protected TextField input;
    protected Thread listener;
    public ChatClient (String title, InputStream i, OutputStream o) {
        //what is super? what is title?
        super (title);
        //what is a buffered input stream?
        this.i = new DataInputStream (new BufferedInputStream(i));
        this.o = new DataOutputStream(new BufferedOutputStream(o));
        setLayout(new BorderLayout());
        add ("Center", output = new TextArea());
        output.setEditable(false);
        add ("South", input = new TextField());
        pack ();
        //this is deprecated, does that mean it can't run, and is there a replacement that does the same thing?
        show ();
        //what does requestFocus do?
        input.requestFocus();
        //what is a thread? Is it a single chat?
        listener = new Thread (this);
        listener.start();
    }
    public void run () {
        //what does try do?
        try {
            while (true) {
                String line = i.readUTF();
                output.appendText(line + "\n");
            }
        //what does catch do?
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public boolean handleEvent (Event e) {
        if ((e.target == input) && (e.id == Event.ACTION_EVENT)) {
            try {
                o.writeUTF((String) e.arg);
                o.flush();
            } catch (IOException ex) {
                //what does printStackTrace do?
                ex.printStackTrace();
                listener.stop();
            }
            input.setText("");
            return true;
        } else if ((e.target == this) && (e.id == Event.WINDOW_DESTROY)) {
            if (listener != null)
                listener.stop ();
            hide();
            return true;
        }
        //what does the super thing do?
        return super.handleEvent (e);
    }
    //what is an IOException and what does it mean to throw it? Also does it matter where the array brackets are in the String args section?
    public static void main (String args[]) throws IOException {
        if (args.length != 2)
            //why is there no brackets? And what is a runtime exception?
            throw new RuntimeException("Syntax: ChatClient <host> <port>");
        //what does parseInt do and why is it relevant?
        Socket s = new Socket (args[0], Integer.parseInt (args[i]));
        new ChatClient ("Chat " + args[0] + ":" + args[1], s.getInputStream(), s.getOutputStream());
    }
}
