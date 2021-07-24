package Result;

import Model.Event;

public class EventIDResponse extends Response {
    Event event;
    public EventIDResponse(Event e){
        event = e;
    }

    public Event getEvent() {
        return event;
    }
}
