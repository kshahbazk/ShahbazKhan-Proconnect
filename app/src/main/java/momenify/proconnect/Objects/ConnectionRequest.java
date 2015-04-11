package momenify.proconnect.Objects;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by shahbazkhan on 3/30/15.
 */
@ParseClassName("ConnectionRequests")
public class ConnectionRequest extends ParseObject{
    ParseUser fromUser;
    ParseUser toUser;
    String Status;

    boolean accept;
    public ConnectionRequest(ParseUser fromUser, ParseUser toUser)
    {
        fromUser=ParseUser.getCurrentUser();
        Status = "pending...";

    }

    public ParseUser showRequestsReceived()
    {

        return fromUser;

    }

    public ParseUser showRequestSent()
    {
        return toUser;
    }
    public void Accept()
    {
       accept = true;
       Status = "Accepted";
       fromUser.add("connections", toUser);
       toUser.add("connections", fromUser);
    }
    public void Deny()
    {
     accept = false;
     Status = "Denied";
    }

}
