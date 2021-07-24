package Result;

import Model.Person;

public class PersonIDResponse extends Response {
    Person person;
    public PersonIDResponse(Person p){
        person = p;
    }

    public Person getPerson() {
        return person;
    }
}
