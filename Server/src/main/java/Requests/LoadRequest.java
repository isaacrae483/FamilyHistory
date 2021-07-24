package Requests;

import java.util.Random;

import Model.Event;
import Model.Person;
import Model.User;

public class LoadRequest {

    User[] users;
    Person[] persons;
    Event[] events;

    public LoadRequest(User[] u, Person[] p, Event[] e){
        users = u;
        persons = p;
        events = e;

    }

    public User[] getUsers() {
        return users;
    }

    public Person[] getPerson() {
        return persons;
    }

    public Event[] getEvents() {
        return events;
    }
}


