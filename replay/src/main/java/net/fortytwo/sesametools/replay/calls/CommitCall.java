
package net.fortytwo.sesametools.replay.calls;

import net.fortytwo.sesametools.replay.SailConnectionCall;
import org.openrdf.sail.SailConnection;
import org.openrdf.sail.SailException;

import java.util.StringTokenizer;

/**
 * Author: josh
 * Date: Apr 30, 2008
 * Time: 5:00:13 PM
 */
public class CommitCall extends SailConnectionCall<SailConnection, Object> {
    public CommitCall(final String id) {
        super(id, Type.COMMIT);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(id).append(DELIM).append(type);

        return sb.toString();
    }

    public CommitCall(final String id,
                      final Type type,
                      final StringTokenizer tok) {
        super(id, type);
    }

    public Object execute(final SailConnection sc) throws SailException {
        sc.commit();
        return null;
    }
}
