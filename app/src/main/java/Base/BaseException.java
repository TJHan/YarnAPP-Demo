package Base;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Created by tjhan on 2017-06-06.
 */

public class BaseException extends Exception {
    @Override
    public void printStackTrace() {
        super.printStackTrace();

    }

    @Override
    public void printStackTrace(PrintStream s) {
        super.printStackTrace(s);
    }

    @Override
    public void printStackTrace(PrintWriter s) {
        super.printStackTrace(s);
    }
    private void ExecEventHandler()
    {

    }
}
